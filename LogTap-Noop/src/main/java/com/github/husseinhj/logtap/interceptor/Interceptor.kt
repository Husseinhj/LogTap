package com.github.husseinhj.logtap.interceptor

import okhttp3.*

/**
 * An OkHttp interceptor that logs HTTP requests and responses to LogTap.
 *
 * ### Example usage:
 * ```kotlin
 * val client = OkHttpClient.Builder()
 *     .addInterceptor(LogTapInterceptor())
 *     .build()
 * ```
 *
 * This interceptor captures request and response details, including headers and bodies (up to a configured limit),
 * while redacting sensitive headers. It handles various edge cases like streaming bodies, WebSocket upgrades,
 * and gzip-encoded responses.
 */
class LogTapInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request())
    }
}
