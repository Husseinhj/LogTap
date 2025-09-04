package com.github.husseinhj.logtap

object LogTapLogcatBridge {
  interface Sink { fun onLog(priority: Char, tag: String, message: String, threadId: Int?, time: String?) }
  fun start(pid: Int, sink: Sink, levels: Set<String> = emptySet()) { /* no-op */ }
  fun stop() { /* no-op */ }
  fun isRunning(): Boolean = false
}