package com.github.husseinhj.logtap

class LogTapSinkAdapter: LogTapLogcatBridge.Sink {
    override fun onLog(priority: Char, tag: String, message: String, threadId: Int?, time: String?) {}
}