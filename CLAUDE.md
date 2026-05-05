# LogTap — Project Guide for Claude

## What this is

**LogTap** — realtime HTTP/WebSocket/Logger inspector for Android. OkHttp3 interceptor + embedded Ktor web server (default port 8790) serving a Material-style web UI. Library + sample app combined in one repo. Published to JitPack as `com.github.Husseinhj.LogTap:logtap` and `:logtap-noop`.

Maintainer: Hussein Habibi Juybari (`husseinhj`). License: MIT.

## Repo layout

```
.
├── LogTap/            # main library (full impl, debug-only)
├── LogTap-Noop/       # API-compatible no-op variant for release builds
├── app/               # sample app (Compose playground exercising library)
├── LogTapsample/      # legacy stub dir — NOT in settings.gradle.kts, ignore
├── docs/              # screenshots only
├── .github/workflows/publish.yml   # tag-driven Maven Central publish
├── build.gradle.kts   # root
├── settings.gradle.kts
├── README.md
└── CHANGELOG.md
```

`settings.gradle.kts` includes only `:app`, `:LogTap`, `:LogTap-Noop`.

## Build

- AGP 8.13.0, Kotlin 2.0.20, Java 17, compileSdk/targetSdk 36, minSdk 29
- Ktor 2.3.12 (CIO + websockets + content-negotiation)
- OkHttp 4.12.0, Okio 3.7.0, kotlinx.serialization 1.6.3, coroutines 1.8.1
- Compose BOM 2024.09.00 (sample app only)
- Version libs: `gradle/libs.versions.toml`

Common commands:
```bash
./gradlew :app:installDebug          # install sample
./gradlew :LogTap:assembleRelease    # build library AAR
./gradlew :LogTap:publishToMavenLocal
PUBLISH_VERSION=0.13.1 ./gradlew :LogTap:publish     # see workflow for full flow
./gradlew test                        # placeholder tests only
```

CI publish trigger: tag `v*.*.*` push or workflow_dispatch with version input. Builds both `logtap` and `logtap-noop`, ships to Sonatype Central Portal. Requires secrets: `OSSRH_USERNAME`, `OSSRH_PASSWORD`, `SIGNING_KEY`, `SIGNING_PASSWORD`.

## Architecture

```
com.github.husseinhj.logtap
├── LogTap                  (object — start/stop entry, port fallback 8790→8810→OS-assigned, FileLock guard, IP detection)
├── log/
│   ├── LogEvent            (Serializable; kind=HTTP|WEBSOCKET|LOG, direction=REQUEST|RESPONSE|OUTBOUND|INBOUND|STATE|ERROR)
│   ├── LogStore            (capacity-bounded ArrayDeque + Mutex + MutableSharedFlow; ACTIVE store)
│   └── LogTapEvents        (legacy ConcurrentLinkedQueue + flow; PARTIALLY DEPRECATED — see Issues)
├── server/server.kt        (Ktor routes: GET / /app.css /app.js /about, /logs /api/logs /api/info, POST /api/clear, WS /ws)
├── interceptor/Interceptor.kt    (LogTapInterceptor : okhttp3.Interceptor — peek body, gzip-decode, redact, truncate)
├── logger/
│   ├── LogTapLogger        (auto-tag from stack frame, Sink-based emission, level/debug/release gates)
│   ├── LogTapLogcatBridge  (logcat tail via ProcessBuilder, threadtime parser)
│   └── LogTapSinkAdapter   (Sink → LogTapEvents.push; bridges logger output to UI)
├── websocket/
│   ├── LoggingWebSocketListener
│   └── LoggingWebSocket
└── utils/
    ├── Resources.kt        (~3270 LoC inlined HTML/CSS/JS for web UI)
    ├── ContextExt.kt       (app name/version/icon-base64/device info)
    ├── LogExt.kt           (logV/logD/logI/logW/logE convenience)
    └── newWebSocketWithLogging.kt
```

Data flow: Interceptor + LogTapLogger + LogcatBridge → LogStore (and LogTapEvents — see bug list) → SharedFlow → WS clients + REST snapshots → embedded UI in browser.

## Public API (stable surface)

- `LogTap.start(context, config)` / `LogTap.stop()` / `LogTap.resolvedAddress: StateFlow<String?>`
- `LogTap.Config(port=8790, capacity=5000, maxBodyBytes=64_000, redactHeaders={Authorization,Cookie,Set-Cookie}, enableOnRelease=false)`
- `LogTapInterceptor()` — drop-in OkHttp3 interceptor
- `LogTapLogger.{v,d,i,w,e}(...)`, `LogTapLogger.Level`, setters: `setSink/setDebug/setMinLevel/setLogcatEnabled/setSinkEnabled/setAllowReleaseLogging`
- `LogTapLogcatBridge.start(sink, scope?)` / `stop()`, `Sink` interface
- `LogTapSinkAdapter()` — ready-made Sink wiring logcat → store
- `OkHttpClient.newWebSocketWithLogging(request, listener)` extension; `LoggingWebSocketListener(delegate?)`

LogTap-Noop module mirrors all of the above as empty bodies / empty flows. Keep API parity when adding/changing public symbols.

## Conventions

- Kotlin only. Library has no Compose deps; UI is HTML/CSS/JS strings in `Resources.kt`.
- `internal` modifier guards everything not part of the public surface.
- Coroutines: `Dispatchers.IO` for blocking work, `SupervisorJob` for app scope, `Mutex` for store.
- Logging: use `Log.<lvl>(TAG, ...)` from inside library. Tag = `"LogTap"` or class name.
- Header redaction set is configurable via `Config.redactHeaders`. Default redacts auth + cookies.
- Body capture is bounded by `Config.maxBodyBytes` (default 64 KB) — peeked, never consumed.
- Debug-only by default: `start()` early-exits unless `isDebuggable(context)` or `enableOnRelease=true`.
- Process lock at `filesDir/logtap.lock` prevents two instances of same app from binding the port.

## Working in this repo

- `LogTap` and `LogTap-Noop` MUST keep API parity. If you change signatures in one, update the other.
- Web UI changes happen in `LogTap/src/main/java/com/github/husseinhj/logtap/utils/Resources.kt` (giant inlined string). Edit carefully — no formatter, no type checker.
- Use `LogStore` for new event-emitting code, NOT `LogTapEvents` (it's legacy and currently buggy — see ISSUES.md).
- When adding a public symbol: add it in both modules, document in README, bump version in CHANGELOG.md.
- Sample app is the manual test bed: `app/src/main/java/.../MainActivity.kt` has buttons covering HTTP verbs, gzip, timeouts, status codes, WebSocket echo.
- No automated tests exist beyond AndroidJUnit4 placeholders. Adding tests is encouraged.

## Known issues

See `ISSUES.md` at repo root. Several confirmed correctness bugs in interceptor config plumbing and server routes. Read before touching `Interceptor.kt`, `server.kt`, or `LogStore.snapshot`.

## References

- README.md — user-facing install/usage
- CHANGELOG.md — version history
- Repo: https://github.com/Husseinhj/LogTap
- JitPack: https://jitpack.io/#husseinhj/Logtap
