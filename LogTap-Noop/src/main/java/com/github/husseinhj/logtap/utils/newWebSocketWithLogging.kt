package com.github.husseinhj.logtap.utils

import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import com.github.husseinhj.logtap.websocket.LoggingWebSocket
import com.github.husseinhj.logtap.websocket.LoggingWebSocketListener

/**
 * Extension function to create a WebSocket with logging capabilities.
 *
 * This function wraps the provided [WebSocketListener] with a [LoggingWebSocketListener]
 * to log WebSocket events. It also wraps the returned [WebSocket] with a [LoggingWebSocket]
 * to log outgoing messages.
 *
 * ### Example usage:
 * ```kotlin
 * val req = Request.Builder()
 *          .url("wss://echo.websocket.org")
 *          .build()
 * client.newWebSocketWithLogging(req, listener)
 * ```
 * @param request The [Request] used to create the WebSocket.
 * @param listener The [WebSocketListener] to handle WebSocket events.
 * @return A [WebSocket] instance that logs its events and messages.
 */
fun okhttp3.OkHttpClient.newWebSocketWithLogging(
    request: Request,
    listener: WebSocketListener? = null
): WebSocket {
    val loggingListener = LoggingWebSocketListener(listener)
    val ws = this.newWebSocket(request, loggingListener)
    return LoggingWebSocket(ws)
}