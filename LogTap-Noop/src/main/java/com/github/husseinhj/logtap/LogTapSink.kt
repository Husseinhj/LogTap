// module: logtap (and mirrored in logtap-noop with same package/signature)
package com.github.husseinhj.logtap

interface LogTapSink {
    fun onLog(level: String, tag: String?, message: String, throwable: Throwable? = null)
}