package com.github.husseinhj.logtap

import android.util.Log
import java.net.Inet4Address
import java.net.ServerSocket
import io.ktor.server.cio.CIO
import android.content.Context
import java.io.RandomAccessFile
import kotlinx.coroutines.launch
import java.nio.channels.FileLock
import kotlinx.coroutines.sync.Mutex
import java.nio.channels.FileChannel
import kotlinx.coroutines.Dispatchers
import android.net.ConnectivityManager
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.asStateFlow
import io.ktor.server.engine.embeddedServer
import io.ktor.server.engine.ApplicationEngine
import com.github.husseinhj.logtap.log.LogStore
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.CoroutineExceptionHandler
import com.github.husseinhj.logtap.utils.isDebuggable
import com.github.husseinhj.logtap.logger.LogTapLogger
import com.github.husseinhj.logtap.server.provideWebServer

private const val TAG = "LogTap"

object LogTap {
    data class Config(
        val port: Int = 8790,
        val capacity: Int = 5000,
        val maxBodyBytes: Long = 64_000,
        val redactHeaders: Set<String> = setOf("Authorization","Cookie","Set-Cookie"),
        val enableOnRelease: Boolean = false
    )

    private val _resolvedAddress: MutableStateFlow<String?> = MutableStateFlow(null)
    val resolvedAddress = _resolvedAddress.asStateFlow()

    @Volatile private var server: ApplicationEngine? = null
    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val bgErrorHandler = CoroutineExceptionHandler { _, t -> Log.e(TAG, "LogTap background error", t) }
    private val engineParentCtx = SupervisorJob() + Dispatchers.IO + bgErrorHandler
    private val startMutex = Mutex()
    @Volatile private var resolvedPort: Int? = null

    @Volatile private var processLock: FileLock? = null

    internal var store: LogStore? = null

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
                    val engine = startServerWithFallback(config, context) // returns a STARTED engine
                    server = engine

                    val port = engine.resolvedConnectors().first().port
                    resolvedPort = port

                    val ip = getDeviceIp(context)
                    _resolvedAddress.value = "http://$ip:$port/"
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

    private fun startServerWithFallback(config: Config, context: Context): ApplicationEngine {
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
                val eng = buildServer(p, context)
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

    @Suppress("unused")
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

    private fun buildServer(port: Int, context: Context): ApplicationEngine {
        val env = provideWebServer(port = port, engineParentCtx = engineParentCtx, context)
        return embeddedServer(CIO, env)
    }

    private fun getDeviceIp(context: Context): String {
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