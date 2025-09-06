package com.github.husseinhj.logtap.interceptor

import okhttp3.*
import okio.Buffer
import okio.GzipSource
import java.nio.charset.Charset
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CoroutineScope
import com.github.husseinhj.logtap.LogTap
import com.github.husseinhj.logtap.log.LogEvent
import com.github.husseinhj.logtap.log.Direction
import com.github.husseinhj.logtap.log.EventKind

/**
 * An OkHttp interceptor that logs HTTP requests and responses to LogTap.
 *
 * ### Example usage:
 * ```kotlin
 * val client = OkHttpClient.Builder()
 *     .addInterceptor(LogTapInterceptor())
 *     .build()
 * ```
 *
 * This interceptor captures request and response details, including headers and bodies (up to a configured limit),
 * while redacting sensitive headers. It handles various edge cases like streaming bodies, WebSocket upgrades,
 * and gzip-encoded responses.
 */
class LogTapInterceptor : Interceptor {

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val startNs = System.nanoTime()

        // Log request synchronously to avoid reading after body is consumed/closed
        emitRequest(request)

        val response: Response = try {
            chain.proceed(request)
        } catch (e: Exception) {
            scope.launch {
                LogTap.store.add(
                    LogEvent(
                        id = 0, ts = System.currentTimeMillis(),
                        kind = EventKind.HTTP,
                        direction = Direction.ERROR,
                        summary = "HTTP ERROR ${request.method} ${request.url} — ${e.javaClass.simpleName}: ${e.message}",
                        url = request.url.toString(), method = request.method,
                        reason = e.message
                    )
                )
            }
            throw e
        }

        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)
        // Log response synchronously as well
        emitResponse(request, response, tookMs)

        return response
    }

    private fun emitRequest(req: Request) {
        val headers = redact(req.headers)
        val (bodyStr, truncated) = safeReadRequestBody(req)
        scope.launch {
            LogTap.store.add(
                LogEvent(
                    id = 0,
                    ts = System.currentTimeMillis(),
                    kind = EventKind.HTTP,
                    direction = Direction.REQUEST,
                    summary = "→ ${req.method} ${req.url}",
                    url = req.url.toString(),
                    method = req.method,
                    headers = headers.toMultimap(),
                    bodyPreview = bodyStr,
                    bodyIsTruncated = truncated
                )
            )
        }
    }

    private fun emitResponse(req: Request, resp: Response, tookMs: Long) {
        val headers = redact(resp.headers)
        val (bodyStr, truncated, byteCount) = safeReadResponseBody(resp)

        scope.launch {
            LogTap.store.add(
                LogEvent(
                    id = 0,
                    ts = System.currentTimeMillis(),
                    kind = EventKind.HTTP,
                    direction = Direction.RESPONSE,
                    summary = "← ${resp.code} ${resp.message} (${tookMs}ms) ${req.method} ${req.url}",
                    url = req.url.toString(),
                    method = req.method,
                    status = resp.code,
                    tookMs = tookMs,
                    headers = headers.toMultimap(),
                    bodyPreview = bodyStr,
                    bodyIsTruncated = truncated,
                    bodyBytes = byteCount
                )
            )
        }
    }

    private fun redact(headers: Headers): Headers {
        val b = headers.newBuilder()
        headers.names().forEach { name ->
            if (LogTap.Config().redactHeaders.any { it.equals(name, ignoreCase = true) }) {
                b.set(name, "(redacted)")
            }
        }
        return b.build()
    }

    private fun isPlainText(buffer: Buffer): Boolean {
        try {
            val prefix = Buffer()
            val byteCount = if (buffer.size < 64) buffer.size else 64
            buffer.copyTo(prefix, 0, byteCount)
            for (i in 0 until 16) {
                if (prefix.exhausted()) break
                val codePoint = prefix.readUtf8CodePoint()
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) return false
            }
            return true
        } catch (_: Exception) {
            return false
        }
    }

    private fun safeReadRequestBody(request: Request): Pair<String?, Boolean> {
        val body = request.body ?: return null to false
        if (body.isDuplex() || body.isOneShot()) return "(streaming body: duplex/one-shot)" to true
        val buffer = Buffer()
        return try {
            body.writeTo(buffer)
            val contentType = body.contentType()
            val charset: Charset = contentType?.charset(Charsets.UTF_8) ?: Charsets.UTF_8
            val size = buffer.size
            val capped = buffer.clone().readByteString(minOf(size, LogTap.Config().maxBodyBytes)).toByteArray()
            val display = if (isPlainText(Buffer().write(capped))) String(capped, charset) else "(${capped.size} bytes binary)"
            val truncated = size > LogTap.Config().maxBodyBytes
            display to truncated
        } catch (_: Exception) {
            "(unable to read request body)" to true
        }
    }

    private fun isWebSocketUpgrade(resp: Response): Boolean {
        val req = resp.request

        // HTTP/1.1 classic upgrade: 101 + headers
        val isH1Upgrade = resp.code == 101 &&
            resp.header("Connection")?.contains("upgrade", true) == true &&
            resp.header("Upgrade")?.equals("websocket", true) == true

        // Heuristics that are present in both H1/H2 handshakes
        val hasWsHandshakeHeaders =
            req.header("Upgrade")?.equals("websocket", true) == true ||
            req.header("Sec-WebSocket-Key") != null ||
            resp.header("Sec-WebSocket-Accept") != null

        // HTTP/2 Extended CONNECT (RFC 8441): success uses 200, not 101.
        // OkHttp may tunnel this without classic Upgrade headers on the response.
        val isH2ExtendedConnect = resp.code == 200 && hasWsHandshakeHeaders

        return isH1Upgrade || isH2ExtendedConnect
    }

    private fun hasNoResponseBody(resp: Response): Boolean {
        // HEAD has no body; 204/205/304 must not include a body per RFC
        val m = resp.request.method
        return m.equals("HEAD", true) || resp.code == 204 || resp.code == 205 || resp.code == 304
    }

    private fun safeReadResponseBody(response: Response): Triple<String?, Boolean, Int?> {
        val body = response.body ?: return Triple(null, false, null)

        // --- Early exits for cases that never have a readable body ---
        if (isWebSocketUpgrade(response)) {
            return Triple("(websocket handshake; no body)", false, 0)
        }
        if (hasNoResponseBody(response)) {
            return Triple("", false, 0)
        }

        return try {
            val max = LogTap.Config().maxBodyBytes

            // Use a PEAK at the source to avoid consuming downstream
            val source = body.source()
            val peek = source.peek()

            // Try to fill what's readily available; avoid unbounded blocking reads
            // If contentLength is known and small, we can safely request that; else cap.
            val knownLen = body.contentLength()
            val toRead = when {
                knownLen >= 0 -> minOf(knownLen, max).coerceAtLeast(0)
                else -> max.toLong()
            }
            peek.request(toRead) // may be 0 if length unknown and nothing buffered yet

            var buffer = peek.buffer.clone()

            if ("gzip".equals(response.header("Content-Encoding"), ignoreCase = true) && buffer.size > 0L) {
                GzipSource(buffer).use { gz ->
                    val nb = okio.Buffer()
                    nb.writeAll(gz)
                    buffer = nb
                }
            }

            val size = buffer.size
            val capped = buffer.clone().readByteString(minOf(size, max)).toByteArray()

            val contentType = body.contentType()
            val charset: Charset = contentType?.charset(Charsets.UTF_8) ?: Charsets.UTF_8
            val display = if (isPlainText(okio.Buffer().write(capped))) {
                String(capped, charset)
            } else {
                "(${capped.size} bytes binary)"
            }
            Triple(display, size > max, size.toInt())
        } catch (_: Exception) {
            Triple("(unavailable: non-replayable/closed stream)", true, null)
        }
    }
}
