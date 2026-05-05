package com.github.husseinhj.logtap.server

import java.time.Duration
import io.ktor.websocket.Frame
import android.content.Context
import kotlin.text.toIntOrNull
import io.ktor.http.ContentType
import kotlin.text.toLongOrNull
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import io.ktor.server.application.call
import io.ktor.server.engine.connector
import io.ktor.server.response.respond
import com.github.husseinhj.logtap.LogTap
import io.ktor.server.application.install
import io.ktor.server.websocket.webSocket
import kotlin.coroutines.CoroutineContext
import io.ktor.server.response.respondText
import io.ktor.server.websocket.WebSockets
import io.ktor.server.websocket.pingPeriod
import com.github.husseinhj.logtap.LogTap.json
import io.ktor.serialization.kotlinx.json.json
import com.github.husseinhj.logtap.LogTap.store
import com.github.husseinhj.logtap.log.LogEvent
import com.github.husseinhj.logtap.utils.buildInfo
import com.github.husseinhj.logtap.utils.Resources
import io.ktor.server.engine.applicationEngineEnvironment
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation

internal fun provideWebServer(port: Int, engineParentCtx: CoroutineContext, context: Context) = applicationEngineEnvironment {
    parentCoroutineContext = engineParentCtx
    connector {
        host = "0.0.0.0"
        this.port = port
    }
    module {
        install(ContentNegotiation) { json(LogTap.json) }
        install(WebSockets) { pingPeriod = Duration.ofSeconds(30); masking = false }

        routing {
            get("/") { call.respondText(Resources.indexHtml, ContentType.Text.Html) }
            get("/app.css") { call.respondText(Resources.appCss, ContentType.Text.CSS) }
            get("/app.js") { call.respondText(Resources.appJs, ContentType.Application.JavaScript) }

            get("/api/logs") {
                val sinceId = call.request.queryParameters["sinceId"]?.toLongOrNull()
                val limit = call.request.queryParameters["limit"]?.toIntOrNull() ?: 500
                val snapshot = store?.snapshot(sinceId, limit) ?: emptyList()
                call.respond(snapshot)
            }
            get("/api/info") {
                val info = context.buildInfo() ?: return@get call.respondText("Unavailable", status = io.ktor.http.HttpStatusCode.InternalServerError)
                call.respond(info)
            }
            post("/api/clear") {
                store?.clear()
                call.respondText("ok")
            }
            webSocket("/ws") {
                // Send backlog from the same store the live stream feeds from
                val backlog = store?.snapshot(null, 200) ?: emptyList()
                for (ev in backlog) {
                    send(Frame.Text(json.encodeToString(LogEvent.serializer(), ev)))
                }
                // Stream new events on this session's coroutine scope
                val s = store
                if (s != null) {
                    s.stream.collect { ev ->
                        try {
                            send(Frame.Text(json.encodeToString(LogEvent.serializer(), ev)))
                        } catch (_: Throwable) {
                            return@collect
                        }
                    }
                } else {
                    for (frame in incoming) { if (frame is Frame.Close) break }
                }
            }
            get("/about") { call.respondText(Resources.aboutHtml) }
        }
    }
}
