package com.github.husseinhj.logtap.log

import kotlinx.serialization.Serializable
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi

@Serializable
internal enum class EventKind { HTTP, WEBSOCKET, LOG }

@Serializable
internal enum class Direction { REQUEST, RESPONSE, OUTBOUND, INBOUND, STATE, ERROR }

// kind = LOG is used for application logs collected via LogTapLogger/Logcat bridge
@OptIn(ExperimentalSerializationApi::class)
@Serializable
internal data class LogEvent(
    val id: Long,
    val ts: Long,               // epoch millis
    val kind: EventKind,
    val direction: Direction,
    val summary: String,        // human-readable line
    val url: String? = null,
    val method: String? = null,
    val status: Int? = null,
    val code: Int? = null,      // WS close code, HTTP code duplicate but convenient
    val reason: String? = null, // WS close reason or error reason
    val headers: Map<String, List<String>>? = null,
    val bodyPreview: String? = null,
    val bodyIsTruncated: Boolean = false,
    val bodyBytes: Int? = null,
    val tookMs: Long? = null,
    @EncodeDefault val thread: String = Thread.currentThread().name,
    val level: String? = null,   // "DEBUG", "INFO", "WARN", ...
    val tag: String? = null,     // Android log tag (caller class)
    val pid: Int? = null,        // Process ID (own PID for in-app logs; logcat bridge passes app PID)
    val tid: Int? = null,        // Thread ID (when known, e.g. from logcat threadtime)
)
