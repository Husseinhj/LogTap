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
            Text("Create a socket connection with a sample message")
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