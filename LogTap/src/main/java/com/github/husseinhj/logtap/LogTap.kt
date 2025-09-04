package com.github.husseinhj.logtap

import android.annotation.SuppressLint
import android.util.Log
import java.time.Duration
import kotlinx.coroutines.*
import java.net.InetAddress
import io.ktor.server.cio.CIO
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import io.ktor.server.engine.*
import io.ktor.websocket.Frame
import io.ktor.http.ContentType
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import android.net.wifi.WifiManager
import io.ktor.server.application.*
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import java.net.Inet4Address
import kotlin.time.toKotlinDuration

private const val TAG = "LogTap"

object LogTap {
    data class Config(
        val port: Int = 8790,
        val capacity: Int = 5000,
        val maxBodyBytes: Long = 64_000,
        val redactHeaders: Set<String> = setOf("Authorization","Cookie","Set-Cookie"),
        val enableOnRelease: Boolean = false
    )

    @Volatile private var server: EmbeddedServer<*, *>? = null
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
                    install(WebSockets) { pingPeriod = Duration.ofSeconds(30).toKotlinDuration(); masking = false }

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
                                collector.cancel()
                            }
                        }
                        get("/about") { call.respondText(Resources.aboutHtml) }
                    }
                }

                engine.start(wait = false)
                server = engine

                val ip = getDeviceIp(context)
                LogTapLogger.i("LogTap server ready at http://$ip:${config.port}/")
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

    @SuppressLint("MissingPermission")
    fun getDeviceIp(context: Context): String {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return "127.0.0.1"
        val props = cm.getLinkProperties(network) ?: return "127.0.0.1"
        for (addr in props.linkAddresses) {
            val host = addr.address
            if (host is Inet4Address && !host.isLoopbackAddress) {
                return host.hostAddress ?: "127.0.0.1"
            }
        }
        return "127.0.0.1"
    }
}