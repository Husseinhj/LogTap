package com.github.husseinhj.logtap

import android.content.Context
import android.util.Log
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode.Companion.RequestTimeout
import io.ktor.server.engine.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.websocket.*
import io.ktor.server.routing.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.cio.CIO
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.websocket.Frame
import kotlinx.coroutines.*
import java.time.Duration

private const val TAG = "LogTap"

object LogTap {
    data class Config(
        val port: Int = 8790,
        val capacity: Int = 5000,
        val maxBodyBytes: Long = 64_000,
        val redactHeaders: Set<String> = setOf("Authorization","Cookie","Set-Cookie"),
        val enableOnRelease: Boolean = false
    )

    @Volatile private var server: ApplicationEngine? = null
    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    internal lateinit var store: LogStore
        private set

    internal val json = kotlinx.serialization.json.Json {
        ignoreUnknownKeys = true
    }

    @Synchronized
    fun start(context: Context, config: Config = Config()) {
        // Debug-only guard
        if (!config.enableOnRelease && !isDebuggable(context)) {
            Log.i(TAG, "Not debuggable; LogTap disabled.")
            return
        }
        if (server != null) return

        store = LogStore(config.capacity)

        // Start engine off the main thread and catch startup errors
        appScope.launch {
            try {
                val engine = embeddedServer(
                    CIO,
                    port = config.port
                ) {
                    install(ContentNegotiation) { json(LogTap.json) }
                    install(WebSockets) { pingPeriod = Duration.ofSeconds(30); masking = false }

                    routing {
                        get("/") { call.respondText(Resources.indexHtml, ContentType.Text.Html) }
                        get("/app.css") { call.respondText(Resources.appCss, ContentType.Text.CSS) }
                        get("/app.js") { call.respondText(Resources.appJs, ContentType.Application.JavaScript) }

                        get("/logs") {
                            val limit = call.request.queryParameters["limit"]?.toIntOrNull() ?: 200
                            call.respond(LogTapEvents.snapshot(limit))
                        }
                        get("/api/logs") {
                            val sinceId = call.request.queryParameters["sinceId"]?.toLongOrNull()
                            val limit = call.request.queryParameters["limit"]?.toIntOrNull() ?: 500
                            call.respond(store.snapshot(sinceId, limit))
                        }
                        post("/api/clear") {
                            store.clear()
                            call.respondText("ok")
                        }
                        webSocket("/ws") {
                            val session = this
                            val collector = CoroutineScope(Dispatchers.IO).launch {
                                store.stream.collect { ev: LogEvent ->
                                    session.send(Frame.Text( json.encodeToString(LogEvent.serializer(), ev)))
                                }
                            }
                            val backlog = LogTapEvents.snapshot(200)
                            for (ev in backlog) {
                                send(Frame.Text( json.encodeToString(LogEvent.serializer(), ev)))
                            }

                            // Then live-stream new events
                            val job = launch(Dispatchers.Default) {
                                LogTapEvents.updates().collect { ev ->
                                    try {
                                        send(Frame.Text( json.encodeToString(LogEvent.serializer(), ev)))
                                    }
                                    catch (_: Throwable) { cancel() } // client likely disconnected
                                }
                            }
                            try {
                                // Drain incoming until client closes
                                for (frame in incoming) {
                                    if (frame is Frame.Close) break
                                }
                            } finally {
                                job.cancel()
                            }
                        }
                        get("/about") { call.respondText(Resources.aboutHtml) }
                    }
                }

                engine.start(wait = false)
                server = engine
                Log.i(TAG, "LogTap started on port ${config.port}.")
            } catch (ce: CancellationException) {
                // engine/coroutine cancelled ⇒ do not crash app
                Log.w(TAG, "LogTap start cancelled", ce)
            } catch (t: Throwable) {
                // bind failures / CIO init errors, etc.
                Log.e(TAG, "Failed to start LogTap", t)
            }
        }
    }

    @Synchronized
    fun stop() {
        try {
            server?.stop(1000, 2000)
        } catch (t: Throwable) {
            Log.w(TAG, "error stopping server", t)
        } finally {
            server = null
        }
    }
}