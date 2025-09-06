package com.github.husseinhj.logtap.logger

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.CoroutineScope

/**
 * Collects this process's Logcat lines (any android.util.Log usage by your app & libs)
 * and forwards them to the provided sink.
 *
 * No READ_LOGS permission required (own PID only).
 */
object LogTapLogcatBridge {

    /** Your hook to send a log event into LogTap. */
    interface Sink {
        fun onLog(priority: Char, tag: String, message: String, threadId: Int?, time: String?)
    }

    /**
     * Start tailing logcat for **this** PID.
     * @param sink your receiver that forwards to your in-memory queue / WebSocket / server
     */
    fun start(sink: Sink, scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)) {}

    fun stop() {}
}