package com.github.husseinhj.logtap

import android.util.Log

object LogTapLogger {
    /** Optional sink to forward logs into LogTap (or elsewhere). */
    interface Sink { fun onLog(priority: Int, tag: String, message: String, tr: Throwable? = null) }
    @Volatile private var sink: Sink? = null
    fun setSink(s: Sink?) { sink = s }

    /** Build-type & level controls */
    @Volatile private var isDebug: Boolean = true            // set from app's BuildConfig.DEBUG
    @Volatile private var allowReleaseLogging: Boolean = false

    enum class Level(val prio: Int) {
        VERBOSE(android.util.Log.VERBOSE),
        DEBUG(android.util.Log.DEBUG),
        INFO(android.util.Log.INFO),
        WARN(android.util.Log.WARN),
        ERROR(android.util.Log.ERROR),
        ASSERT(android.util.Log.ASSERT)
    }

    @Volatile private var minLevel: Level = Level.DEBUG
    @Volatile private var logcatEnabled: Boolean = true
    @Volatile private var sinkEnabled: Boolean = true

    /**
     * Recommended init (Application.onCreate):
     * LogTapLogger.setDebug(BuildConfig.DEBUG)
     * LogTapLogger.setAllowReleaseLogging(false)
     * LogTapLogger.setMinLevel(if (BuildConfig.DEBUG) Level.DEBUG else Level.WARN)
     */
    fun setDebug(debug: Boolean) { isDebug = debug }
    fun setAllowReleaseLogging(allow: Boolean) { allowReleaseLogging = allow }
    fun setMinLevel(level: Level) { minLevel = level }
    fun setLogcatEnabled(enabled: Boolean) { logcatEnabled = enabled }
    fun setSinkEnabled(enabled: Boolean) { sinkEnabled = enabled }

    private fun enabledFor(prio: Int): Boolean {
        val buildOk = if (isDebug) true else allowReleaseLogging
        if (!buildOk) return false
        return prio >= minLevel.prio
    }

    private fun callerTag(): String {
        val stackTrace = Throwable().stackTrace
        // Find the first element outside this logger class
        val element = stackTrace
            .firstOrNull { !it.className.contains(this::class.java.simpleName) }
        val className = element?.className?.substringAfterLast('.') ?: "LogTap"
        return className
    }

    private fun emit(priority: Int, tag: String, message: String, tr: Throwable? = null) {
        if (!sinkEnabled || !enabledFor(priority)) return
        try {
            sink?.onLog(priority, tag, if (tr != null) message + "\n" + Log.getStackTraceString(tr) else message, tr)
        } catch (_: Throwable) {
            // never let logging crash the app
        }
    }

    fun v(message: String) {
        val pr = Log.VERBOSE
        if (!enabledFor(pr)) return
        val tag = callerTag()
        if (logcatEnabled) Log.v(tag, message)
        emit(pr, tag, message)
    }

    fun d(message: String) {
        val pr = Log.DEBUG
        if (!enabledFor(pr)) return
        val tag = callerTag()
        if (logcatEnabled) Log.d(tag, message)
        emit(pr, tag, message)
    }

    fun i(message: String) {
        val pr = Log.INFO
        if (!enabledFor(pr)) return
        val tag = callerTag()
        if (logcatEnabled) Log.i(tag, message)
        emit(pr, tag, message)
    }

    fun w(message: String, tr: Throwable? = null) {
        val pr = Log.WARN
        if (!enabledFor(pr)) return
        val tag = callerTag()
        if (logcatEnabled) Log.w(tag, message, tr)
        emit(pr, tag, message, tr)
    }

    fun e(message: String, tr: Throwable? = null) {
        val pr = Log.ERROR
        if (!enabledFor(pr)) return
        val tag = callerTag()
        if (logcatEnabled) Log.e(tag, message, tr)
        emit(pr, tag, message, tr)
    }
}