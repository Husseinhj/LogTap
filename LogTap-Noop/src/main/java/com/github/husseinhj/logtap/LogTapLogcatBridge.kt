package com.github.husseinhj.logtap

import kotlinx.coroutines.Job
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.CoroutineScope

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
  fun start(sink: Sink, scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)) {}

  fun stop() {
    job?.cancel()
    job = null
  }
}