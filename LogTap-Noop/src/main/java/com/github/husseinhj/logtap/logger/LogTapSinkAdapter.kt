package com.github.husseinhj.logtap.logger

/**
 * A Logcat sink that pushes log messages into LogTap event store.
 * To start capturing logcat logs, call:
 * ### Example usage:
 * ```kotlin
 * LogTapLogcatBridge.start(LogTapSinkAdapter())
 * ```
 */
class LogTapSinkAdapter : LogTapLogcatBridge.Sink {
    override fun onLog(priority: Char, tag: String, message: String, threadId: Int?, time: String?) {}
}