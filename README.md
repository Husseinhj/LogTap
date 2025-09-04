# LogTap

> Realtime **HTTP / WebSocket / Logger** inspector for Android (OkHttp3) — with a built-in Ktor web server and a Material-style responsive web UI.  
> Inspect your app’s network calls and logs from any browser on the same network.

---

## 🚀 Features

- **Network**
    - OkHttp3 **Interceptor** for HTTP(S)
    - WebSocket listener proxy
    - Pretty JSON viewer
    - Copy `cURL` command for easy reproduction

- **Logger**
    - Simple logger (`LogTapLogger`) with **auto-tag by caller class**
    - Supports levels: VERBOSE, DEBUG, INFO, WARN, ERROR, ASSERT
    - Optional **Logcat bridge** to stream system logcat

- **UI**
    - Runs on device via embedded Ktor server
    - **Material-inspired**, fully responsive (desktop, tablet, mobile)
    - Modes: `Mix`, `Network only`, `Logger only`
    - Filters: Method, HTTP status code / class, Log level, Full-text search
    - Live updates via WebSocket
    - **Export JSON** or **HTML reports**
    - Copy `cURL` or Summary directly from UI
    - Auto-scroll & toggle JSON pretty-print

---

## 📸 Screenshots

_(add screenshots here)_

| Network View | Logger View | Detail Drawer |
|--------------|-------------|---------------|
| ![Network screenshot](docs/screenshot-network.png) | ![Logger screenshot](docs/screenshot-logger.png) | ![Drawer screenshot](docs/screenshot-drawer.png) |

---

## ⚡ Usage

### 1. Initialize in your Application
```kotlin
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            LogTap.start(this) // Starts embedded server on port 8790
        }
    }
}
```

### 2. OkHttp with interceptor
```kotlin
val client = OkHttpClient.Builder()
    .addInterceptor(LogTapInterceptor())
    .build()
```

### 3. Logger
```kotlin
LogTapLogger.d("Something happened")
LogTapLogger.e("Network error", throwable)
```


### 4. View in browser
Open in your desktop browser:
```
http://<device-ip>:8790/
```

> **Tip – Watch Logcat for the server URL**
>
> When the embedded server is ready, LogTap writes the exact address to **Logcat** so you can copy/paste it easily. Look for a line like:
>
> ```
> 2025-09-04 23:48:14.521 14129-14168 LogTap$start$1          com...hub.husseinhj.logtapsample  I  LogTap server ready at http://192.168.178.66:8790/
> ```
>
> (If you don't see the IP, ensure your app has `ACCESS_NETWORK_STATE` or LogTap will fall back to best-effort interface detection.)

### 5. Advanced: Automatic Logcat collection

For more settings and to automatically collect logs from Android's logger, you can use `LogTapLogcatBridge` together with a `LogTapSinkAdapter`:

```kotlin
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
```

---

## 🔐 Security

- LogTap is meant for **debug builds only**.  
- Don’t enable it in production.  
- Use the provided `-noop` artifact in release builds.  
- You can also guard calls with `if (BuildConfig.DEBUG)`.

---

## 🛠 Development

- UI is embedded in `Resources.kt` (HTML, CSS, JS).
- Served from:
  - `/` → Main web UI
  - `/api/logs` → JSON API
  - `/ws` → WebSocket stream
- Contributions welcome! Please open an issue or PR.

---

## 📜 License

MIT License – see [LICENSE](LICENSE) for details.