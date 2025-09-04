package com.github.husseinhj.logtapsample


import android.app.Application
import com.github.husseinhj.logtap.LogTap
import com.github.husseinhj.logtap.LogTapInterceptor
import com.github.husseinhj.logtap.newWebSocketWithLogging
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocketListener

// Example integration snippets — place similar code in your app
class LogTapApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Start the embedded viewer on device (only when debuggable)
        LogTap.start(this, LogTap.Config(port = 8790, capacity = 5000))
    }
}

fun buildOkHttpWithLogTap(): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(LogTapInterceptor()) // HTTP(S) logging
    .build()

fun openWebSocketWithLogTap(client: OkHttpClient, url: String, listener: WebSocketListener) {
    val req = Request.Builder().url(url).build()
    client.newWebSocketWithLogging(req, listener)
}
