package com.github.husseinhj.logtap.logger

import com.github.husseinhj.logtap.log.LogEvent
import com.github.husseinhj.logtap.log.Direction
import com.github.husseinhj.logtap.log.EventKind
import com.github.husseinhj.logtap.log.LogTapEvents

/**
 * A Logcat sink that pushes log messages into LogTap event store.
 * To start capturing logcat logs, call:
 * ### Example usage:
 * ```kotlin
 * LogTapLogcatBridge.start(LogTapSinkAdapter())
 * ```
 */
class LogTapSinkAdapter : LogTapLogcatBridge.Sink {
    override fun onLog(priority: Char, tag: String, message: String, threadId: Int?, time: String?) {
        val id = LogTapEvents.nextId()
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
        LogTapEvents.push(
            LogEvent(
                id = id,
                ts = now,
                kind = EventKind.LOG,
                direction = Direction.STATE,
                summary = message,
                thread = (threadId?.toString() ?: Thread.currentThread().name),
                level = level,
                tag = tag
            )
        )
    }
}