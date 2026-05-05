# Changelog
All notable changes to this project will be documented in this file.
The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

### v0.14.0
Modern viewer redesign + correctness fixes

UI
- Rewrite web viewer (`Resources.kt`) with a modern, theme-aware UX based on the LogTap Viewer design.
- Smart command-search with autocomplete: `level:`, `method:`, `status:` (incl. `2xx`/`4xx`/`5xx` shorthand), `exclude:tag:`, `exclude:message:`, `exclude:method:`, `exclude:status:`. Keyboard navigation (↑/↓/Enter/Tab/Esc).
- One-click favorites (☆/★) save the current query; saved-filters popover with badge count, click to apply, delete to remove. Persisted to localStorage.
- Four themes (Android Studio, Xcode, Grafana, Modern) × dark/light mode. Persisted to localStorage.
- Always-visible metrics (Total / Network / Logs / Errors / Logs/min / Avg Response / Active Filters).
- Two view modes: **Logcat** (Android Studio-style stream with inline request/response/headers) and **Table** (resizable columns, expandable rows, request body under URL, response preview column).
- Right-click context menu on any row: Copy URL / Copy as cURL / Copy Message / Copy as JSON / Exclude tag / Exclude message.
- Smart auto-scroll (off when scrolled up, resumes at bottom). Connection status indicator in status bar with auto-reconnect.
- App icon, name, version, and build number from `/api/info` shown in the toolbar brand.
- PID/TID column in Table view and `pid-tid` prefix in Logcat view (matches `adb logcat -v threadtime`).
- Cmd/Ctrl+K focuses search; Esc closes popovers. High-contrast text selection across themes.

Filtering logic
- `level:` matches both logs and HTTP rows. HTTP rows now derive `level` from status (5xx→ERROR, 4xx→WARN, else INFO), so `level:ERROR` returns server errors as well as ERROR logs.
- `exclude:message:` searches across visible fields (url, body, status, etc.), not just `message`.
- All command/panel filters are case-insensitive.

Backend
- `LogEvent` gains `pid` and `tid` fields. `LogStore.add` stamps `Process.myPid()` automatically.
- HTTP request/response events are paired client-side into a single row with both bodies and headers.

Bug fixes
- `LogTapInterceptor` now reads the active `LogTap.Config` instead of constructing fresh defaults; user-supplied `redactHeaders` and `maxBodyBytes` are honored.
- `LogStore.snapshot(sinceId, limit)` now returns events newer than `sinceId` (was equality match).
- `LogStore.stream` configured with `BufferOverflow.DROP_OLDEST`; live events no longer silently dropped under load.
- WebSocket handler in `server.kt` now collects on the session's coroutine scope; no leaked collectors on session abort.
- Removed the legacy `LogTapEvents` queue; logger output and HTTP/WS events flow through a single `LogStore`. `/logs` (deprecated) removed; use `/api/logs`.
- `LogTapSinkAdapter` writes to the active `LogStore` so `/api/logs` and `/ws` agree on contents.

### v0.12.0

- Add DeviceAppInfo support and update UI with dynamic app details
- Add settings popover for UI customization options
- Refactor settings and filters popover for improved UI and accessibility
- Update stats bar styling for stickiness and visual consistency
- Enhance export menu UI with improved accessibility and dynamic behavior

### v0.11.2
Make LogStore nullable and handle potential null access

- Update LogTap.store to be nullable.
- Add null-safe calls (?.) when accessing LogTap.store in WsLogging.kt, Interceptor.kt, server.kt, and LoggingWebSocket.kt.
- In server.kt, provide an empty list for `/api/logs` if store is null.

### v0.11.1

Enable resource shrinking and minification in release build; update Proguard rules
If you need to use it for release builds, make sure add these to your proguard rules:

```
-keep class com.github.husseinhj.logtap.** { *; }
-keep class io.ktor.** { *; }
-dontwarn io.ktor.**
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn kotlinx.coroutines.**
-keepclassmembers class ** { @kotlinx.serialization.SerialName *; }
-keepattributes *Annotation*, Signature, InnerClasses, EnclosingMethod, Exceptions, SourceFile, LineNumberTable
```

### v0.11.0
Enable minification and add Proguard rules for LogTap

- Enable `isMinifyEnabled` for the release build type in `LogTap/build.gradle.kts`.
- Add Proguard rules to `LogTap/proguard-rules.pro` to keep LogTap's public API, Ktor, OkHttp/Okio, Kotlin coroutines, Kotlinx serialization, and WebSocket message models.

### v0.10.0
Expose LogTap server address and display in sample app

- Introduce `LogTap.resolvedAddress` as a `StateFlow` to observe the server address.
- Update the sample app to display the resolved server address.

### v0.9.0

- Fix GitHub links in Resources.kt to point to the correct repository

### v0.8.0
Refactor LogTap UI and functionality

- Update repository URL from LogTap to LogTapIOS.
- Implement resizable and persistable table columns.
- Change default for "Pretty JSON" to enabled.
- Default "Actions" column to hidden.
- Improve log message display and parsing.
- Minor style adjustments for table padding and layout.
- Switch sample app dependency from LogTap-Noop to LogTap.

### v0.7.0
Refactor LogTap-Noop module and update UI resources

This commit refactors the LogTap-Noop module by:
- Moving classes to more specific subpackages (interceptor, logger, websocket, utils).
- Adding `LogTapInterceptor` and `LoggingWebSocket` classes.
- Introducing `newWebSocketWithLogging` extension function and `LoggingWebSocketListener`.
- Adding UI resources (HTML, CSS, JS) for the LogTap web interface.
- Updating `LogTap.kt` with synchronized methods and a default constructor for `Config`.
- Adding utility functions `isDebuggable`, `logV`, `logD`, `logI`, `logW`, and `logE`.
- Deleting old classes: `LogTapInterceptor`, `LogTapLogcatBridge`, `LogTapLogger`, `LogTapSink`, `LogTapSinkAdapter`, and `WsLogging`.
- Updating the app's build.gradle.kts to use LogTap-Noop.

### v0.6.0
Refactor LogTap for improved modularity and WebSocket logging

Key changes include:
- Added Android Studio, Xcode, Visual Studio, and Grafana themes to the HTML.
- Moving core classes like `LogStore`, `LogEvent`, `LogTapLogger`, and `LogTapLogcatBridge` into new subpackages (`log`, `logger`, `server`, `interceptor`, `websocket`, `utils`) for better organization.
- Introducing `LoggingWebSocket` and `LoggingWebSocketListener` to provide more detailed logging for WebSocket send and receive events, including message content and direction.
- Adding a `newWebSocketWithLogging` extension function for `OkHttpClient` to easily create WebSockets with built-in logging.
- Updating the sample app to utilize the new WebSocket logging features and reflect the refactored class locations.
- Removing the old combined `Resources.kt` and separating its contents into the new package structure.
- Adjusting imports across the library and sample app to match the new package structure.
