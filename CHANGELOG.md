# Changelog
All notable changes to this project will be documented in this file.
The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

### v0.11.0
Enable minification in LogTap and add Proguard rules for LogTap

- Enable `isMinifyEnabled` for the release build type in `LogTap/build.gradle.kts`.
- Add Proguard rules to `LogTap/proguard-rules.pro` to keep LogTap's public API, Ktor, OkHttp/Okio, Kotlin coroutines, Kotlinx serialization, and WebSocket message models.
- 
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
