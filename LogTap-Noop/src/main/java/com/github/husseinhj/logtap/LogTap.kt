package com.github.husseinhj.logtap

import android.content.Context
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.MutableStateFlow

object LogTap {
    data class Config(
        val port: Int = 8790,
        val capacity: Int = 5000,
        val maxBodyBytes: Long = 64_000,
        val redactHeaders: Set<String> = setOf("Authorization","Cookie","Set-Cookie"),
        val enableOnRelease: Boolean = false
    )

    val resolvedAddress: StateFlow<String?> = MutableStateFlow(null).asStateFlow()

    @Synchronized
    fun start(context: Context, config: Config = Config()) {}

    @Suppress("unused")
    @Synchronized
    fun stop() {}
}