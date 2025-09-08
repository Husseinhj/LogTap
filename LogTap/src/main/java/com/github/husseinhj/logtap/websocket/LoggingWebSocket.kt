package com.github.husseinhj.logtap.websocket

import okio.ByteString
import okhttp3.WebSocket
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CoroutineScope
import com.github.husseinhj.logtap.LogTap
import com.github.husseinhj.logtap.log.LogEvent
import com.github.husseinhj.logtap.log.Direction
import com.github.husseinhj.logtap.log.EventKind

class LoggingWebSocket(private val real: WebSocket) : WebSocket by real {
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun send(text: String): Boolean {
        scope.launch {
            LogTap.store?.add(
                LogEvent(
                    id = 0,
                    ts = System.currentTimeMillis(),
                    kind = EventKind.WEBSOCKET,
                    direction = Direction.OUTBOUND,
                    summary = "WS → text: $text",
                    bodyPreview = text,
                )
            )
        }
        return real.send(text)
    }

    override fun send(bytes: ByteString): Boolean {
        scope.launch {
            LogTap.store?.add(
                LogEvent(
                    id = 0,
                    ts = System.currentTimeMillis(),
                    kind = EventKind.WEBSOCKET,
                    direction = Direction.OUTBOUND,
                    summary = "WS → binary ${bytes.size} bytes",
                    bodyBytes = bytes.size
                )
            )
        }
        return real.send(bytes)
    }

    override fun close(code: Int, reason: String?): Boolean {
        scope.launch {
            LogTap.store?.add(
                LogEvent(
                    id = 0,
                    ts = System.currentTimeMillis(),
                    kind = EventKind.WEBSOCKET,
                    direction = Direction.STATE,
                    summary = "WS CLOSE $code ${reason ?: ""}",
                    code = code,
                    reason = reason
                )
            )
        }
        return real.close(code, reason)
    }
}
