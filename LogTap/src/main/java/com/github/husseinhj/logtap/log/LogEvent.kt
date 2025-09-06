package com.github.husseinhj.logtap.log

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.serialization.Serializable
import kotlinx.serialization.EncodeDefault
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.channels.BufferOverflow
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
)

internal object LogTapEvents {
    private val seq = java.util.concurrent.atomic.AtomicLong(1L)
    private val queue = java.util.concurrent.ConcurrentLinkedQueue<LogEvent>()

    private val _updates = MutableSharedFlow<LogEvent>(
        replay = 0,
        extraBufferCapacity = 1024,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    fun updates(): SharedFlow<LogEvent> = _updates.asSharedFlow()

    fun push(ev: LogEvent) {
        queue.add(ev)
        _updates.tryEmit(ev) // <-- broadcast to WS listeners
    }

    fun nextId(): Long = seq.getAndIncrement()

    /** Oldest -> newest, limited. */
    fun snapshot(limit: Int): List<LogEvent> {
        if (limit <= 0) return emptyList()
        val all = queue.toList()
        return if (all.size <= limit) all else all.takeLast(limit)
    }
}
