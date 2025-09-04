package com.github.husseinhj.logtap

import android.os.Process
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Collects this process's Logcat lines (any android.util.Log usage by your app & libs)
 * and forwards them to the provided sink.
 *
 * No READ_LOGS permission required (own PID only).
 */
object LogTapLogcatBridge {
    private var job: Job? = null

    /** Your hook to send a log event into LogTap. */
    interface Sink {
        fun onLog(priority: Char, tag: String, message: String, threadId: Int?, time: String?)
    }

    /**
     * Start tailing logcat for **this** PID.
     * @param sink your receiver that forwards to your in-memory queue / WebSocket / server
     */
    fun start(sink: Sink, scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)) {
        if (job?.isActive == true) return
        val pid = Process.myPid().toString()

        job = scope.launch {
            // -v threadtime → “MM-DD HH:MM:SS.mmm  PID  TID  PRI  TAG: message”
            val pb = ProcessBuilder("logcat", "--pid", pid, "-v", "threadtime")
                .redirectErrorStream(true)

            val proc = pb.start()
            val reader = BufferedReader(InputStreamReader(proc.inputStream))

            val regex = Regex("""^\s*(\d\d-\d\d\s+\d\d:\d\d:\d\d\.\d+)\s+(\d+)\s+(\d+)\s+([VDIWEAF])\s+(\S+)\s*:\s(.*)$""")

            reader.useLines { seq ->
                seq.forEach { line ->
                    val m = regex.matchEntire(line)
                    if (m != null) {
                        val (ts, _pid, tid, pri, tag, msg) = m.destructured
                        sink.onLog(pri.first(), tag, msg, tid.toIntOrNull(), ts)
                    } else {
                        // Non-matching lines (continuations); treat as info with empty tag
                        sink.onLog('I', "", line, null, null)
                    }
                }
            }
        }
    }

    fun stop() {
        job?.cancel()
        job = null
    }
}