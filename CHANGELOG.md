# Changelog
All notable changes to this project will be documented in this file.
The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

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
