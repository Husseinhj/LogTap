package com.github.husseinhj.logtap.websocket

import okio.ByteString
import okhttp3.Response
import okhttp3.WebSocket
import kotlinx.coroutines.launch
import okhttp3.WebSocketListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CoroutineScope
import com.github.husseinhj.logtap.LogTap
import com.github.husseinhj.logtap.log.LogEvent
import com.github.husseinhj.logtap.log.Direction
import com.github.husseinhj.logtap.log.EventKind

class LoggingWebSocketListener(
    private val delegate: WebSocketListener? = null
) : WebSocketListener() {
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onOpen(webSocket: WebSocket, response: Response) {
        scope.launch {
            LogTap.store.add(
                LogEvent(
                    id = 0,
                    ts = System.currentTimeMillis(),
                    kind = EventKind.WEBSOCKET,
                    direction = Direction.STATE,
                    summary = "WS OPEN ${response.request.url}",
                    url = response.request.url.toString(),
                    status = response.code,
                    headers = response.headers.toMultimap()
                )
            )
        }
        delegate?.onOpen(LoggingWebSocket(webSocket), response)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        scope.launch {
            LogTap.store.add(
                LogEvent(
                    id = 0,
                    ts = System.currentTimeMillis(),
                    kind = EventKind.WEBSOCKET,
                    direction = Direction.INBOUND,
                    summary = "WS ← text: ${text.take(100)}" + if (text.length > 100) "..." else "",
                    url = null,
                    bodyPreview = text,
                    bodyIsTruncated = text.length > 100,
                )
            )
        }
        delegate?.onMessage(LoggingWebSocket(webSocket), text)
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        scope.launch {
            LogTap.store.add(
                LogEvent(
                    id = 0,
                    ts = System.currentTimeMillis(),
                    kind = EventKind.WEBSOCKET,
                    direction = Direction.INBOUND,
                    summary = "WS ← binary ${bytes.size} bytes",
                    bodyBytes = bytes.size
                )
            )
        }
        delegate?.onMessage(LoggingWebSocket(webSocket), bytes)
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        scope.launch {
            LogTap.store.add(
                LogEvent(
                    id = 0,
                    ts = System.currentTimeMillis(),
                    kind = EventKind.WEBSOCKET,
                    direction = Direction.STATE,
                    summary = "WS CLOSING ($code): $reason",
                    code = code,
                    reason = reason
                )
            )
        }
        delegate?.onClosing(LoggingWebSocket(webSocket), code, reason)
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        scope.launch {
            LogTap.store.add(
                LogEvent(
                    id = 0,
                    ts = System.currentTimeMillis(),
                    kind = EventKind.WEBSOCKET,
                    direction = Direction.STATE,
                    summary = "WS CLOSED ($code) $reason",
                    code = code,
                    reason = reason
                )
            )
        }
        delegate?.onClosed(LoggingWebSocket(webSocket), code, reason)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        scope.launch {
            LogTap.store.add(
                LogEvent(
                    id = 0,
                    ts = System.currentTimeMillis(),
                    kind = EventKind.WEBSOCKET,
                    direction = Direction.ERROR,
                    summary = "WS ERROR ${t.javaClass.simpleName}: ${t.message}",
                    reason = t.message,
                    status = response?.code
                )
            )
        }
        delegate?.onFailure(LoggingWebSocket(webSocket), t, response)
    }
}

