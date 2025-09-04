package com.github.husseinhj.logtap

import android.content.Context

object LogTap {
  fun start(context: Context, config: Config) {}
  fun stop() {}
  fun isRunning(): Boolean = false

  data class Config(
    val port: Int = 8790,
    val capacity: Int = 5000,
    val maxBodyBytes: Long = 64_000,
    val redactHeaders: Set<String> = setOf("Authorization","Cookie","Set-Cookie"),
    val enableOnRelease: Boolean = false
  )
}