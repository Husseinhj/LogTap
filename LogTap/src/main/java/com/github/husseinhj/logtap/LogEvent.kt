package com.github.husseinhj.logtap

import kotlinx.serialization.Serializable
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi

@Serializable
enum class EventKind { HTTP, WEBSOCKET }

@Serializable
enum class Direction { REQUEST, RESPONSE, OUTBOUND, INBOUND, STATE, ERROR }

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class LogEvent(
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
    @EncodeDefault val thread: String = Thread.currentThread().name
)
