package com.github.husseinhj.logtap

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class LoggingWebSocketListener(
    private val delegate: WebSocketListener
) : WebSocketListener() {
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
        scope.launch {
            LogTap.store.add(
                LogEvent(0, System.currentTimeMillis(), EventKind.WEBSOCKET, Direction.STATE,
                    summary = "WS OPEN ${response.request.url}", url = response.request.url.toString(),
                    status = response.code, headers = response.headers.toMultimap())
            )
        }
        delegate.onOpen(LoggingWebSocket(webSocket), response)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        scope.launch {
            LogTap.store.add(
                LogEvent(0, System.currentTimeMillis(), EventKind.WEBSOCKET, Direction.INBOUND,
                    summary = "WS ← text: $text", url = null,
                    bodyPreview = text, bodyIsTruncated = text.length > 10_000)
            )
        }
        delegate.onMessage(LoggingWebSocket(webSocket), text)
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        scope.launch {
            LogTap.store.add(
                LogEvent(0, System.currentTimeMillis(), EventKind.WEBSOCKET, Direction.INBOUND,
                    summary = "WS ← binary ${bytes.size} bytes", bodyBytes = bytes.size)
            )
        }
        delegate.onMessage(LoggingWebSocket(webSocket), bytes)
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        scope.launch {
            LogTap.store.add(
                LogEvent(0, System.currentTimeMillis(), EventKind.WEBSOCKET, Direction.STATE,
                    summary = "WS CLOSING $code $reason", code = code, reason = reason)
            )
        }
        delegate.onClosing(LoggingWebSocket(webSocket), code, reason)
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        scope.launch {
            LogTap.store.add(
                LogEvent(0, System.currentTimeMillis(), EventKind.WEBSOCKET, Direction.STATE,
                    summary = "WS CLOSED $code $reason", code = code, reason = reason)
            )
        }
        delegate.onClosed(LoggingWebSocket(webSocket), code, reason)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: okhttp3.Response?) {
        scope.launch {
            LogTap.store.add(
                LogEvent(0, System.currentTimeMillis(), EventKind.WEBSOCKET, Direction.ERROR,
                    summary = "WS ERROR ${t.javaClass.simpleName}: ${t.message}", reason = t.message,
                    status = response?.code)
            )
        }
        delegate.onFailure(LoggingWebSocket(webSocket), t, response)
    }
}

/** Wraps outbound calls to log sends & close */
class LoggingWebSocket(private val real: WebSocket) : WebSocket by real {
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun send(text: String): Boolean {
        scope.launch {
            LogTap.store.add(
                LogEvent(0, System.currentTimeMillis(), EventKind.WEBSOCKET, Direction.OUTBOUND,
                    summary = "WS → text: $text", bodyPreview = text,
                )
            )
        }
        return real.send(text)
    }

    override fun send(bytes: ByteString): Boolean {
        scope.launch {
            LogTap.store.add(
                LogEvent(0, System.currentTimeMillis(), EventKind.WEBSOCKET, Direction.OUTBOUND,
                    summary = "WS → binary ${bytes.size} bytes", bodyBytes = bytes.size)
            )
        }
        return real.send(bytes)
    }

    override fun close(code: Int, reason: String?): Boolean {
        scope.launch {
            LogTap.store.add(
                LogEvent(0, System.currentTimeMillis(), EventKind.WEBSOCKET, Direction.STATE,
                    summary = "WS CLOSE $code ${reason ?: ""}", code = code, reason = reason)
            )
        }
        return real.close(code, reason)
    }
}

/** Convenience extension to create a logging WebSocket */
fun okhttp3.OkHttpClient.newWebSocketWithLogging(
    request: Request,
    listener: WebSocketListener
): WebSocket? {
    val loggingListener = LoggingWebSocketListener(listener)
    val ws = this.newWebSocket(request, loggingListener)
    return LoggingWebSocket(ws)
}
