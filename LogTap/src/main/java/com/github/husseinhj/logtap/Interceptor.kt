package com.github.husseinhj.logtap

import okhttp3.*
import okio.Buffer
import okio.GzipSource
import java.nio.charset.Charset
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CoroutineScope

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
                        id = 0, ts = System.currentTimeMillis(), kind = EventKind.HTTP,
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

    private fun safeReadResponseBody(response: Response): Triple<String?, Boolean, Int?> {
        val body = response.body ?: return Triple(null, false, null)
        return try {
            var source = body.source()
            source.request(Long.MAX_VALUE)
            var buffer = source.buffer

            if ("gzip".equals(response.headers["Content-Encoding"], ignoreCase = true)) {
                GzipSource(buffer.clone()).use { gz ->
                    val newBuffer = Buffer()
                    newBuffer.writeAll(gz)
                    buffer = newBuffer
                }
            }

            val size = buffer.size
            val max = LogTap.Config().maxBodyBytes
            val capped = buffer.clone().readByteString(minOf(size, max)).toByteArray()
            val contentType = body.contentType()
            val charset: Charset = contentType?.charset(Charsets.UTF_8) ?: Charsets.UTF_8
            val display = if (isPlainText(Buffer().write(capped))) String(capped, charset) else "(${capped.size} bytes binary)"
            Triple(display, size > max, size.toInt())
        } catch (e: Exception) {
            e.printStackTrace()
            Triple("(unable to read response body)", true, null)
        }
    }
}
