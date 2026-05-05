package com.github.husseinhj.logtap.logger

import com.github.husseinhj.logtap.LogTap
import com.github.husseinhj.logtap.log.LogEvent
import com.github.husseinhj.logtap.log.Direction
import com.github.husseinhj.logtap.log.EventKind
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * A Logcat sink that pushes log messages into the active LogTap event store.
 * ### Example usage:
 * ```kotlin
 * LogTapLogcatBridge.start(LogTapSinkAdapter())
 * ```
 */
class LogTapSinkAdapter : LogTapLogcatBridge.Sink {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onLog(priority: Char, tag: String, message: String, threadId: Int?, time: String?) {
        val now = System.currentTimeMillis()
        val level = when (priority) {
            'V' -> "VERBOSE"
            'D' -> "DEBUG"
            'I' -> "INFO"
            'W' -> "WARN"
            'E' -> "ERROR"
            'A', 'F' -> "ASSERT"
            else -> "LOG"
        }
        val event = LogEvent(
            id = 0,
            ts = now,
            kind = EventKind.LOG,
            direction = Direction.STATE,
            summary = message,
            thread = threadId?.toString() ?: Thread.currentThread().name,
            level = level,
            tag = tag,
            tid = threadId
        )
        scope.launch { LogTap.store?.add(event) }
    }
}
