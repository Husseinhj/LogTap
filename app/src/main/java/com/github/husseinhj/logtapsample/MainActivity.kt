package com.github.husseinhj.logtapsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.husseinhj.logtapsample.ui.theme.LogTapSampleTheme
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )

        Button(onClick = {
            val client = buildOkHttpWithLogTap()

            openWebSocketWithLogTap(client, "wss://echo.websocket.org", object : okhttp3.WebSocketListener() {
                override fun onOpen(webSocket: okhttp3.WebSocket, response: okhttp3.Response) {
                    super.onOpen(webSocket, response)
                    webSocket.send("{\"menu\": {\n" +
                            "  \"id\": \"file\",\n" +
                            "  \"value\": \"File\",\n" +
                            "  \"popup\": {\n" +
                            "    \"menuitem\": [\n" +
                            "      {\"value\": \"New\", \"onclick\": \"CreateNewDoc()\"},\n" +
                            "      {\"value\": \"Open\", \"onclick\": \"OpenDoc()\"},\n" +
                            "      {\"value\": \"Close\", \"onclick\": \"CloseDoc()\"}\n" +
                            "    ]\n" +
                            "  }\n" +
                            "}}")
                }
            })
        }) {
            Text("Socket")
        }

        Button(onClick = {
            val client = buildOkHttpWithLogTap()
            val request = Request.Builder().url("https://fakestoreapi.com/users").build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }
                override fun onResponse(call: Call, response: Response) {
                    println("GET response: ${'$'}{response.body?.string()}")
                }
            })
        }) {
            Text("API Call")
        }

        Button(onClick = {
            val client = buildOkHttpWithLogTap()

            val json = """
                {
                  "title": "test product",
                  "price": 13.5,
                  "description": "lorem ipsum set",
                  "image": "https://i.pravatar.cc",
                  "category": "electronic"
                }
                """.trimIndent()

            val request = Request.Builder()
                .url("https://fakestoreapi.com/products")
                .post(json.toRequestBody("application/json; charset=utf-8".toMediaType()))
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    println("Post response: ${response.body?.string()}")
                }
            })
        }) {
            Text("Post API Call")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LogTapSampleTheme {
        Greeting("Android")
    }
}