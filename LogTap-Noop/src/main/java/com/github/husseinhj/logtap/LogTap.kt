package com.github.husseinhj.logtap

import android.content.Context

object LogTap {
    data class Config(
        val port: Int = 8790,
        val capacity: Int = 5000,
        val maxBodyBytes: Long = 64_000,
        val redactHeaders: Set<String> = setOf("Authorization","Cookie","Set-Cookie"),
        val enableOnRelease: Boolean = false
    )

    @Synchronized
    fun start(context: Context, config: Config = Config()) {}

    @Suppress("unused")
    @Synchronized
    fun stop() {}
}