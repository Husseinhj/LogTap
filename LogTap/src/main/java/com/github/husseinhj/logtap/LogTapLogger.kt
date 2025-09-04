package com.github.husseinhj.logtap

import android.util.Log

object LogTapLogger {
    /** Optional sink to forward logs into LogTap (or elsewhere). */
    interface Sink { fun onLog(priority: Int, tag: String, message: String, tr: Throwable? = null) }
    @Volatile private var sink: Sink? = null
    fun setSink(s: Sink?) { sink = s }

    private fun callerTag(): String {
        val stackTrace = Throwable().stackTrace
        // Find the first element outside this logger class
        val element = stackTrace
            .firstOrNull { !it.className.contains(this::class.java.simpleName) }
        val className = element?.className?.substringAfterLast('.') ?: "LogTap"
        return className
    }

    private fun emit(priority: Int, tag: String, message: String, tr: Throwable? = null) {
        try {
            sink?.onLog(priority, tag, if (tr != null) message + "\n" + Log.getStackTraceString(tr) else message, tr)
        } catch (_: Throwable) {
            // never let logging crash the app
        }
    }

    fun d(message: String) {
        val tag = callerTag(); Log.d(tag, message); emit(Log.DEBUG, tag, message)
    }

    fun i(message: String) {
        val tag = callerTag(); Log.i(tag, message); emit(Log.INFO, tag, message)
    }

    fun w(message: String, tr: Throwable? = null) {
        val tag = callerTag(); Log.w(tag, message, tr); emit(Log.WARN, tag, message, tr)
    }

    fun e(message: String, tr: Throwable? = null) {
        val tag = callerTag(); Log.e(tag, message, tr); emit(Log.ERROR, tag, message, tr)
    }

    fun v(message: String) {
        val tag = callerTag(); Log.v(tag, message); emit(Log.VERBOSE, tag, message)
    }
}