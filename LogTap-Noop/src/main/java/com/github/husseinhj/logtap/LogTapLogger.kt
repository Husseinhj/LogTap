package com.github.husseinhj.logtap

object LogTapLogger {
  enum class Level { VERBOSE, DEBUG, INFO, WARN, ERROR, ASSERT }
  fun setDebug(debug: Boolean) {}
  fun setAllowReleaseLogging(allow: Boolean) {}
  fun setMinLevel(level: Level) {}
  fun setSinkEnabled(enabled: Boolean) {}
  fun setLogcatEnabled(enabled: Boolean) {}

  fun v(msg: String) {}
  fun d(msg: String) {}
  fun i(msg: String) {}
  fun w(msg: String, tr: Throwable? = null) {}
  fun e(msg: String, tr: Throwable? = null) {}
}