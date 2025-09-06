package com.github.husseinhj.logtap.log

import java.util.ArrayDeque
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.atomic.AtomicLong
import kotlinx.coroutines.flow.MutableSharedFlow

internal class LogStore(private val capacity: Int) {
    private val deque = ArrayDeque<LogEvent>(capacity)
    private val nextId = AtomicLong(1)
    private val mutex = Mutex()
    val stream = MutableSharedFlow<LogEvent>(replay = 0, extraBufferCapacity = 512)

    suspend fun add(event: LogEvent) {
        val withIds = event.copy(id = nextId.getAndIncrement())
        mutex.withLock {
            if (deque.size == capacity) deque.removeFirst()
            deque.addLast(withIds)
        }
        stream.tryEmit(withIds)
    }

    suspend fun clear() {
        mutex.withLock { deque.clear() }
    }

    suspend fun snapshot(sinceId: Long? = null, limit: Int = 500): List<LogEvent> {
        return mutex.withLock {
            val all = deque.toList()
            val filtered = sinceId?.let { id -> all.filter { it.id == id } } ?: all
            filtered.takeLast(limit)
        }
    }
}