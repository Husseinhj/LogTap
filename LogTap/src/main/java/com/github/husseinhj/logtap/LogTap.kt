package com.github.husseinhj.logtap

import android.annotation.SuppressLint
import android.util.Log
import java.time.Duration
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.CoroutineExceptionHandler
import java.net.InetAddress
import java.net.ServerSocket
import io.ktor.server.cio.CIO
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import io.ktor.server.engine.*
import io.ktor.server.engine.applicationEngineEnvironment
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

import java.io.RandomAccessFile
import java.nio.channels.FileLock
import java.nio.channels.FileChannel

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
    private val bgErrorHandler = CoroutineExceptionHandler { _, t -> Log.e(TAG, "LogTap background error", t) }
    private val engineParentCtx = SupervisorJob() + Dispatchers.IO + bgErrorHandler
    private val startMutex = Mutex()
    @Volatile private var resolvedPort: Int? = null

    @Volatile private var processLock: FileLock? = null

    internal lateinit var store: LogStore
        private set

    internal val json = kotlinx.serialization.json.Json {
        ignoreUnknownKeys = true
    }

    private fun canBind(port: Int): Boolean {
        if (port == 0) return true
        return try {
            ServerSocket().use { sock ->
                sock.reuseAddress = true
                sock.bind(java.net.InetSocketAddress("0.0.0.0", port))
            }
            true
        } catch (_: Throwable) {
            false
        }
    }

    private fun tryAcquireProcessLock(context: Context): Boolean {
        return try {
            val dir = context.filesDir
            val lockFile = java.io.File(dir, "logtap.lock")
            if (!lockFile.exists()) lockFile.createNewFile()
            val channel: FileChannel = RandomAccessFile(lockFile, "rw").channel
            val lock = channel.tryLock()
            if (lock != null) {
                processLock = lock
                true
            } else {
                false
            }
        } catch (t: Throwable) {
            // If locking fails for any reason, assume another instance and do not start
            Log.w(TAG, "Could not acquire LogTap process lock; skipping start", t)
            false
        }
    }

    @Synchronized
    fun start(context: Context, config: Config = Config()) {
        // Debug-only guard
        if (!config.enableOnRelease && !isDebuggable(context)) {
            Log.i(TAG, "Not debuggable; LogTap disabled.")
            return
        }

        // Ensure only one LogTap server instance per process
        if (!tryAcquireProcessLock(context)) {
            Log.i(TAG, "Another LogTap instance appears to be running; skipping start.")
            return
        }

        store = LogStore(config.capacity)

        // Start engine off the main thread and catch startup errors
        appScope.launch(bgErrorHandler) {
            startMutex.withLock {
                if (server != null) return@withLock
                try {
                    val engine = startServerWithFallback(config) // returns a STARTED engine
                    server = engine

                    val port = engine.resolvedConnectors().first().port
                    resolvedPort = port

                    val ip = getDeviceIp(context)
                    LogTapLogger.i("LogTap server ready at http://$ip:$port/")
                } catch (ce: CancellationException) {
                    // engine/coroutine cancelled ⇒ do not crash app
                    Log.w(TAG, "LogTap start cancelled", ce)
                } catch (t: Throwable) {
                    // bind failures / CIO init errors, etc.
                    Log.e(TAG, "Failed to start LogTap", t)
                }
            }
        }
    }

    private fun startServerWithFallback(config: Config): ApplicationEngine {
        val candidates = mutableListOf<Int>()
        if (config.port != 0) {
            candidates += config.port
            candidates += (config.port + 1)..(config.port + 20)
        }
        candidates += 0 // OS-assigned as last resort

        var lastError: Throwable? = null
        for (p in candidates) {
            if (!canBind(p)) continue
            try {
                val eng = buildServer(p, config)
                // Start may throw BindException from CIO internal coroutine
                eng.start(wait = false)
                return eng
            } catch (e: java.net.BindException) {
                lastError = e
                try {
                    // Ensure any partially started engine is stopped
                    server?.stop(200, 500)
                } catch (_: Throwable) {}
                // Try next port candidate
            } catch (t: Throwable) {
                // If the throwable root cause is BindException, retry on next port
                val cause = t.cause
                if (cause is java.net.BindException) {
                    lastError = cause
                    try { server?.stop(200, 500) } catch (_: Throwable) {}
                    continue
                }
                // Other failures are fatal for startup; rethrow
                throw t
            }
        }
        throw lastError ?: IllegalStateException("No free port found for LogTap")
    }

    private fun buildServer(port: Int, config: Config): ApplicationEngine {
        val env = applicationEngineEnvironment {
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
                                session.send(Frame.Text(json.encodeToString(LogEvent.serializer(), ev)))
                            }
                        }
                        val backlog = LogTapEvents.snapshot(200)
                        for (ev in backlog) {
                            send(Frame.Text(json.encodeToString(LogEvent.serializer(), ev)))
                        }
                        val job = launch(Dispatchers.Default) {
                            LogTapEvents.updates().collect { ev ->
                                try {
                                    send(Frame.Text(json.encodeToString(LogEvent.serializer(), ev)))
                                } catch (_: Throwable) { cancel() }
                            }
                        }
                        try {
                            for (frame in incoming) { if (frame is Frame.Close) break }
                        } finally {
                            job.cancel(); collector.cancel()
                        }
                    }
                    get("/about") { call.respondText(Resources.aboutHtml) }
                }
            }
        }
        return embeddedServer(CIO, env)
    }

    @Synchronized
    fun stop() {
        try {
            server?.stop(1000, 2000)
        } catch (t: Throwable) {
            Log.w(TAG, "error stopping server", t)
        } finally {
            resolvedPort = null
            try {
                processLock?.release()
            } catch (_: Throwable) {}
            processLock = null
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