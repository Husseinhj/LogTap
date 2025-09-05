package com.github.husseinhj.logtapsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.github.husseinhj.logtap.LogTapLogger
import com.github.husseinhj.logtap.newWebSocketWithLogging
import com.github.husseinhj.logtapsample.ui.theme.LogTapSampleTheme
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.OkHttpClient
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.io.IOException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LogTapSampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())) {
        Text(text = "LogTap Playground", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        Text(text = "Hello $name! Use these to generate HTTP, WebSocket, and Logger events.", style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(16.dp))

        // ===== Logger Demos =====
        Text(text = "Logger", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(6.dp))
        FlowRow {
            Button({ LogTapLogger.v("VERBOSE sample log") }) { Text("Log VERBOSE") }
            Button({ LogTapLogger.d("DEBUG sample log") }) { Text("Log DEBUG") }
            Button({ LogTapLogger.i("INFO sample log") }) { Text("Log INFO") }
            Button({ LogTapLogger.w("WARN sample log") }) { Text("Log WARN") }
            Button({ LogTapLogger.e("ERROR sample log") }) { Text("Log ERROR") }
        }
        Spacer(Modifier.height(16.dp))

        // ===== HTTP: Basic =====
        Text(text = "HTTP – Basics", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(6.dp))
        FlowRow {
            Button({ httpGet("https://fakestoreapi.com/users") }) { Text("GET users") }
            Button({ httpPostJson("https://fakestoreapi.com/products", sampleProductJson()) }) { Text("POST product") }
            Button({ httpPutJson("https://fakestoreapi.com/products/7", sampleProductUpdateJson()) }) { Text("PUT product") }
            Button({ httpDelete("https://fakestoreapi.com/products/7") }) { Text("DELETE product") }
        }
        Spacer(Modifier.height(16.dp))

        // ===== HTTP: Edge Cases =====
        Text(text = "HTTP – Edge cases", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(6.dp))
        FlowRow {
            Button({ httpGet("https://httpbin.org/status/404") }) { Text("GET 404") }
            Button({ httpGet("https://httpbin.org/status/500") }) { Text("GET 500") }
            Button({ httpGet("https://httpbin.org/delay/3") }) { Text("GET delay 3s") }
            Button({ httpGetWithHeaders("https://httpbin.org/headers") }) { Text("GET headers") }
            Button({ httpGetLarge("https://httpbin.org/json") }) { Text("GET json big") }
            Button({ httpGetGzip("https://httpbin.org/gzip") }) { Text("GET gzip") }
            Button({ httpTimeout("https://10.255.255.1/") }) { Text("Timeout host") }
        }
        Spacer(Modifier.height(16.dp))

        // ===== WebSocket =====
        Text(text = "WebSocket", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(6.dp))
        FlowRow {
            Button({ openEchoWebSocket("wss://echo.websocket.org") }) { Text("WS connect") }
            Button({ openEchoWebSocketAndSend("wss://echo.websocket.org", "{\"hello\":\"world\"}") }) { Text("WS Echo send JSON") }
        }
    }
}

private fun client(): OkHttpClient = buildOkHttpWithLogTap()

private fun httpGet(url: String) {
    val req = Request.Builder().url(url).build()
    client().newCall(req).enqueue(logCallbacks("GET $url"))
}

private fun httpPostJson(url: String, body: String) {
    val req = Request.Builder().url(url)
        .post(body.toRequestBody("application/json; charset=utf-8".toMediaType()))
        .build()
    client().newCall(req).enqueue(logCallbacks("POST $url"))
}

private fun httpPutJson(url: String, body: String) {
    val req = Request.Builder().url(url)
        .put(body.toRequestBody("application/json; charset=utf-8".toMediaType()))
        .build()
    client().newCall(req).enqueue(logCallbacks("PUT $url"))
}

private fun httpDelete(url: String) {
    val req = Request.Builder().url(url).delete().build()
    client().newCall(req).enqueue(logCallbacks("DELETE $url"))
}

private fun httpGetWithHeaders(url: String) {
    val req = Request.Builder().url(url)
        .header("Authorization", "Bearer test-token-123")
        .header("X-Debug", "true")
        .build()
    client().newCall(req).enqueue(logCallbacks("GET(H) $url"))
}

private fun httpGetLarge(url: String) = httpGet(url)

private fun httpGetGzip(url: String) = httpGet(url)

private fun httpTimeout(url: String) {
    // Unroutable IP to trigger timeout
    httpGet(url)
}

private fun sampleProductJson() = """
    {"title":"test product","price":13.5,"description":"lorem ipsum","image":"https://i.pravatar.cc","category":"electronic"}
""".trimIndent()

private fun sampleProductUpdateJson() = """
    {"title":"updated product","price":21.0}
""".trimIndent()

private fun logCallbacks(label: String) = object : Callback {
    override fun onFailure(call: Call, e: IOException) {
        LogTapLogger.e("$label failed: ${e.message}", e)
    }
    override fun onResponse(call: Call, response: Response) {
        LogTapLogger.d("$label -> ${response.code}")
        response.close()
    }
}

private fun openEchoWebSocket(url: String) {
    val ws = client().newWebSocketWithLogging(
        Request.Builder().url(url).build(),
        object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                LogTapLogger.i("WS open: ${response.request.url}")

                val sampleJson = """{"title":"test product","price":13.5,"description":"lorem ipsum set","image":"https://i.pravatar.cc","category":"electronic"}"""
                webSocket.send(sampleJson)
            }
            override fun onMessage(webSocket: WebSocket, text: String) {
                LogTapLogger.d("WS message: $text")
            }
            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                LogTapLogger.w("WS closing: $code $reason")
            }
            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                LogTapLogger.i("WS closed: $code $reason")
            }
            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                LogTapLogger.e("WS failure: ${t.message}", t)
            }
        }
    )
}

private fun openEchoWebSocketAndSend(url: String, payload: String) {
    client().newWebSocketWithLogging(
        Request.Builder().url(url).build(),
        object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                LogTapLogger.d("WS send: $payload")
                webSocket.send(payload)
            }
            override fun onMessage(webSocket: WebSocket, text: String) {
                LogTapLogger.d("WS recv: $text")
                webSocket.close(1000, "done")
            }
            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                LogTapLogger.e("WS failure: ${t.message}", t)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LogTapSampleTheme {
        Greeting("Android")
    }
}