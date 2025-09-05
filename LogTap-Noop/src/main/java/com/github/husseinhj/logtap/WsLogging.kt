package com.github.husseinhj.logtap

import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener

fun okhttp3.OkHttpClient.newWebSocketWithLogging(
    request: Request,
    listener: WebSocketListener
): WebSocket {
    return this.newWebSocket(request, listener)
}
