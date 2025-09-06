package com.github.husseinhj.logtap.utils

import com.github.husseinhj.logtap.logger.LogTapLogger

/**
 * Logs a verbose message using [LogTapLogger].
 *
 * @param msg The message to log.
 *
 * ### Example usage:
 * ```kotlin
 * class Example {
 *     fun testLogging() {
 *         logV("This is a verbose log")
 *     }
 * }
 * ```
 */
inline fun logV(msg: String) = LogTapLogger.v(msg)

/**
 * Logs a debug message using [LogTapLogger].
 *
 * @param msg The message to log.
 *
 * ### Example usage:
 * ```kotlin
 * class Example {
 *     fun testLogging() {
 *         logD("This is a debug log")
 *     }
 * }
 * ```
 */
inline fun logD(msg: String) = LogTapLogger.d(msg)

/**
 * Logs an info message using [LogTapLogger].
 *
 * @param msg The message to log.
 *
 * ### Example usage:
 * ```kotlin
 * class Example {
 *     fun testLogging() {
 *         logI("This is an info log")
 *     }
 * }
 * ```
 */
inline fun logI(msg: String) = LogTapLogger.i(msg)

/**
 * Logs a warning message using [LogTapLogger].
 *
 * @param msg The message to log.
 * @param tr  Optional throwable to include in the log.
 *
 * ### Example usage:
 * ```kotlin
 * class Example {
 *     fun testLogging() {
 *         logW("This is a warning log")
 *         logW("Warning with exception", RuntimeException("Test exception"))
 *     }
 * }
 * ```
 */
inline fun logW(msg: String, tr: Throwable? = null) = LogTapLogger.w(msg, tr)

/**
 * Logs an error message using [LogTapLogger].
 *
 * @param msg The message to log.
 * @param tr  Optional throwable to include in the log.
 *
 * ### Example usage:
 * ```kotlin
 * class Example {
 *     fun testLogging() {
 *         logE("This is an error log")
 *         logE("Error with exception", RuntimeException("Something went wrong"))
 *     }
 * }
 * ```
 */
inline fun logE(msg: String, tr: Throwable? = null) = LogTapLogger.e(msg, tr)