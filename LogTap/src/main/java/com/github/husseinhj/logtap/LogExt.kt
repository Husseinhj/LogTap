package com.github.husseinhj.logtap

/** Usage: this.logD(\"hello\") or AnyClass().logE(\"oops\") */
inline fun Any.logV(msg: String) = LogTapLogger.v(msg)
inline fun Any.logD(msg: String) = LogTapLogger.d(msg)
inline fun Any.logI(msg: String) = LogTapLogger.i(msg)
inline fun Any.logW(msg: String, tr: Throwable? = null) = LogTapLogger.w(msg, tr)
inline fun Any.logE(msg: String, tr: Throwable? = null) = LogTapLogger.e(msg, tr)