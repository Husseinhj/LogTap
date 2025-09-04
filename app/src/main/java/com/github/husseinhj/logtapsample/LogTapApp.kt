package com.github.husseinhj.logtapsample

import okhttp3.Request
import okhttp3.OkHttpClient
import android.app.Application
import okhttp3.WebSocketListener
import com.github.husseinhj.logtap.LogTap
import com.github.husseinhj.logtap.LogTapInterceptor
import com.github.husseinhj.logtap.LogTapLogcatBridge
import com.github.husseinhj.logtap.LogTapLogger
import com.github.husseinhj.logtap.LogTapSinkAdapter
import com.github.husseinhj.logtap.newWebSocketWithLogging

class LogTapApp : Application() {
    private val logSink = LogTapSinkAdapter()

    override fun onCreate() {
        super.onCreate()

        LogTapLogcatBridge.start(logSink)
        LogTapLogger.d("LogTapLogcatBridge started")
        LogTap.start(this, LogTap.Config(port = 8790, capacity = 5000))

        LogTapLogger.setDebug(BuildConfig.DEBUG)
        LogTapLogger.setAllowReleaseLogging(false)
        LogTapLogger.setMinLevel(
            if (BuildConfig.DEBUG)
                LogTapLogger.Level.DEBUG
            else
                LogTapLogger.Level.WARN
        )
    }
}

fun buildOkHttpWithLogTap(): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(LogTapInterceptor()) // HTTP(S) logging
    .build()

fun openWebSocketWithLogTap(client: OkHttpClient, url: String, listener: WebSocketListener) {
    val req = Request.Builder().url(url).build()
    client.newWebSocketWithLogging(req, listener)
}
