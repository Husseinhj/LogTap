package com.github.husseinhj.logtap.websocket

import okio.ByteString
import okhttp3.WebSocket

class LoggingWebSocket(private val real: WebSocket) : WebSocket by real {

    override fun send(text: String): Boolean {
        return real.send(text)
    }

    override fun send(bytes: ByteString): Boolean {
        return real.send(bytes)
    }

    override fun close(code: Int, reason: String?): Boolean {
        return real.close(code, reason)
    }
}
