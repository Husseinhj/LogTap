package com.github.husseinhj.logtap.websocket

import okio.ByteString
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class LoggingWebSocketListener(
    private val delegate: WebSocketListener? = null
) : WebSocketListener() {

    override fun onOpen(webSocket: WebSocket, response: Response) {
        delegate?.onOpen(LoggingWebSocket(webSocket), response)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        delegate?.onMessage(LoggingWebSocket(webSocket), text)
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        delegate?.onMessage(LoggingWebSocket(webSocket), bytes)
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        delegate?.onClosing(LoggingWebSocket(webSocket), code, reason)
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        delegate?.onClosed(LoggingWebSocket(webSocket), code, reason)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        delegate?.onFailure(LoggingWebSocket(webSocket), t, response)
    }
}

