# LogTap — Known Issues & Improvements

Findings from a pass over the codebase. Severity tags: **[BUG]** correctness defect, **[SEC]** security/privacy, **[PERF]** efficiency, **[QA]** test/quality, **[DOC]** documentation, **[NIT]** style. Each item cites exact file:line.

## Status (2026-05-05)

Bugs **#1, #2, #3, #4, #5, #12** are **FIXED** in the working tree. `LogTapEvents` legacy queue removed entirely; `LogTap.Config()` calls in `Interceptor.kt` replaced with `LogTap.activeConfig`; `LogStore.snapshot` `sinceId` filter changed from `==` to `>`; WebSocket handler uses session scope; `MutableSharedFlow` overflow strategy is `DROP_OLDEST`. `Resources.kt` rewritten to match the new design (command-search, saved filters, 4 themes × dark/light, Logcat + Table views).

Remaining items below are still open.

---

## Critical bugs (correctness)

### 1. [BUG] `LogTap.Config()` ignored at runtime — interceptor always uses defaults
**Files**: `LogTap/src/main/java/com/github/husseinhj/logtap/interceptor/Interceptor.kt:115, 147, 149, 195`

`LogTapInterceptor` calls `LogTap.Config()` to read `redactHeaders` and `maxBodyBytes`. That constructs a *fresh default* `Config` data class instance every time — it does NOT read the config the user passed to `LogTap.start(context, config)`. Result: custom redact lists and body-size limits are silently dropped.

```kotlin
// current (wrong)
if (LogTap.Config().redactHeaders.any { ... })
val max = LogTap.Config().maxBodyBytes
```

**Fix**: store the active `Config` on `LogTap` (e.g. `internal var config: Config? = null` set in `start()`) and read it via `LogTap.config?.redactHeaders ?: defaultSet`. Same for `maxBodyBytes`. Also cache `Config` once per interceptor call instead of constructing it 3+ times.

---

### 2. [BUG] `LogStore.snapshot(sinceId, limit)` filter is `==` instead of `>`
**File**: `LogTap/src/main/java/com/github/husseinhj/logtap/log/LogStore.kt:31`

```kotlin
val filtered = sinceId?.let { id -> all.filter { it.id == id } } ?: all
```

Returns *only the event with that exact ID*, not all events newer than it. The `/api/logs?sinceId=...` polling endpoint is effectively broken — UI clients reconnecting with `sinceId` get one event back instead of the backlog since their last seen ID.

**Fix**: `it.id > id`.

---

### 3. [BUG] Server routes mix legacy `LogTapEvents` queue with active `LogStore`
**File**: `LogTap/src/main/java/com/github/husseinhj/logtap/server/server.kt:53, 79, 84`

Three places still reference the deprecated `LogTapEvents`:
- `GET /logs` → `LogTapEvents.snapshot(limit)` — never populated by `LogTapInterceptor` (interceptor writes to `LogStore`), so this endpoint returns empty unless logger output happens to flow via `LogTapSinkAdapter` (which DOES still push to `LogTapEvents`).
- WS `/ws` backlog (line 79) → `LogTapEvents.snapshot(200)` — clients receive only logger backlog, not HTTP/WS history.
- WS `/ws` second collector (line 84) → `LogTapEvents.updates()` — duplicate stream, partially live (logger only).

Net effect: HTTP/WebSocket events arrive on `/ws` (via the `store.stream` collector at line 75), but reconnecting clients get a partial backlog. `/logs` is essentially dead code for HTTP traffic.

**Fix**: route everything through `LogStore`. Either delete `LogTapEvents` and migrate `LogTapSinkAdapter` to write into `LogStore`, or wire both producers into a single source. The `/logs` endpoint is also a duplicate of `/api/logs` — remove it.

---

### 4. [BUG] WebSocket handler leaks coroutine on exception
**File**: `LogTap/src/main/java/com/github/husseinhj/logtap/server/server.kt:74-94`

The collector launched at line 74 lives on a freshly-built `CoroutineScope(Dispatchers.IO)` — **not** tied to the WS session lifecycle. If the `for (frame in incoming)` loop throws (network drop, etc.), only `finally` runs. If the surrounding handler exits abnormally before `finally`, the collector keeps running and `session.send()` will fail repeatedly. The second `job` (line 83) IS tied to session scope, but the first one isn't.

**Fix**: launch the first collector with `launch(...)` (uses session scope) instead of `CoroutineScope(Dispatchers.IO).launch`. Also add `try/catch` around `session.send` so a single failed send doesn't kill the collector.

---

### 5. [BUG] `LogTapSinkAdapter` writes to legacy queue, never into `LogStore`
**File**: `LogTap/src/main/java/com/github/husseinhj/logtap/logger/LogTapSinkAdapter.kt:18, 30`

Adapter pushes to `LogTapEvents.push(...)`. Combined with bug #3, logger events flow through a different channel than HTTP events. Live `/ws` viewers see both (because both flows are subscribed in server.kt), but `GET /api/logs` (the polling fallback) only sees HTTP. Inconsistent state across endpoints.

**Fix**: route adapter through `LogTap.store?.add(...)` (suspend; needs scope) so all endpoints see the same queue.

---

## Security / privacy

### 6. [SEC] Plain-text HTTP server with no auth
**File**: `LogTap/src/main/java/com/github/husseinhj/logtap/LogTap.kt:39, 187-190`

Ktor binds `0.0.0.0` with no TLS, no token, no CORS restriction. Any device on the same network can read every request, response body, header (after redaction), and log line — including any data the user's app handles. Authorization header is redacted but request bodies are not (a login POST with `password=...` in the body is fully exposed).

**Mitigations**: (1) bind `127.0.0.1` and require ADB port-forward by default, with a `Config.bindAddress` opt-in for LAN; (2) add a per-session token shown in logcat; (3) extend redaction to common body fields (`password`, `token`, `secret` keys in JSON/form bodies).

### 7. [SEC] Header redaction only — no body redaction
**File**: `LogTap/src/main/java/com/github/husseinhj/logtap/interceptor/Interceptor.kt:67, 88`

`Authorization`/`Cookie` redacted, but bodies stream through verbatim. JWT tokens in JSON, OAuth refresh tokens, PII — all visible in the UI and exported reports.

**Fix**: add `Config.redactBodyKeys: Set<String>` and walk JSON bodies replacing matching keys with `"(redacted)"`. Document the limitation in README.

### 8. [SEC] Process lock file readable by other apps' scoped storage tooling
**File**: `LogTap/src/main/java/com/github/husseinhj/logtap/LogTap.kt:71-89`

`logtap.lock` exists in app-private `filesDir` (Android sandboxes per-uid, so this is OK for inter-app), **but** the file's *existence* is testable in shared backup contexts and the comment claims it guards against multiple LogTap instances — actually it only guards instances *of the same app*. Two different apps both using LogTap will collide on port 8790, which the auto-increment fallback handles correctly. So the lock is fine for its purpose; **doc improvement only**: clarify the comment.

### 9. [SEC] `enableOnRelease=true` ships network server in production
**File**: `LogTap/src/main/java/com/github/husseinhj/logtap/LogTap.kt:36, 94`

The flag exists and is documented as advanced. Risk if flipped accidentally: an attacker on the same network gets full request/response capture from a release build. Consider hardening: log a `Log.w` warning at startup when `enableOnRelease=true && !isDebuggable`, force binding to 127.0.0.1, and require an explicit token.

---

## Robustness

### 10. [BUG] `getDeviceIp` returns 127.0.0.1 silently on no-network or VPN-only
**File**: `LogTap/src/main/java/com/github/husseinhj/logtap/LogTap.kt:192-203`

If `activeNetwork` is null (offline) or only has IPv6 link addresses, the resolved address logs as `http://127.0.0.1:<port>/`. Server is still bound `0.0.0.0`, but the *advertised* address misleads users into thinking the server is loopback-only. Also: `ACCESS_NETWORK_STATE` permission is required for `getLinkProperties` — README mentions this but library doesn't declare it in its manifest.

**Fix**: enumerate `NetworkInterface.getNetworkInterfaces()` as a fallback, prefer non-loopback IPv4. Add `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />` to LogTap module manifest (it merges into hosts).

### 11. [BUG] `startServerWithFallback` calls `server?.stop()` on the *previous* (possibly running) server when handling BindException
**File**: `LogTap/src/main/java/com/github/husseinhj/logtap/LogTap.kt:152, 160`

In the catch block the code calls `server?.stop(...)` — but `server` is the field that holds the *previous successfully-started* engine, not the engine that just failed. If `start()` is called twice (unlikely thanks to the lock), this could shut down the live server. The variable shadowing intent looks like it should be the local `eng`, which never got assigned because `eng.start()` threw.

**Fix**: drop the `server?.stop()` calls inside the loop; the local engine that threw doesn't need an explicit stop after a failed `start(wait=false)`. If it does, capture `eng` before `start()` and stop *that* local on exception.

### 12. [BUG] `LogStore.stream` uses `tryEmit` — silent drop under burst
**File**: `LogTap/src/main/java/com/github/husseinhj/logtap/log/LogStore.kt:13, 21`

`extraBufferCapacity = 512` with `tryEmit` and no overflow strategy. Under heavy logging the emit returns false and the event is dropped from the live stream (still kept in the `deque`). UI viewers miss events with no warning.

**Fix**: configure `MutableSharedFlow(replay=0, extraBufferCapacity=512, onBufferOverflow=BufferOverflow.DROP_OLDEST)` so newest events always go through and viewers see something. Or up the buffer.

---

## Performance

### 13. [PERF] `LogTap.Config()` constructed on every header iteration
**File**: `LogTap/src/main/java/com/github/husseinhj/logtap/interceptor/Interceptor.kt:115`

Inside `headers.names().forEach`, allocates a `Config` per header. For a request with 20 headers that's 20 allocations and 20 default-set creations. Cache once outside the loop (also fixes bug #1).

### 14. [PERF] `Resources.kt` is a 3270-LoC string literal compiled into the AAR
**File**: `LogTap/src/main/java/com/github/husseinhj/logtap/utils/Resources.kt`

Big inlined HTML/CSS/JS held in memory as a `String` once read. Two improvements:
- Move the assets to `src/main/assets/` and serve via `context.assets.open(...)` streamed to the response. Smaller heap footprint, faster build (no string compilation), can use proper editor tooling.
- Or gzip-pre-compress and decompress on first request.

### 15. [PERF] `LoggingWebSocketListener` not located but likely allocates per message
Verify: ensure `JSON` Frame conversion isn't doing `String -> ByteArray -> String` round-trips per WS frame.

---

## Testing & quality

### 16. [QA] Zero real tests
**Files**: `app/src/test/java/...ExampleUnitTest.kt`, `app/src/androidTest/...ExampleInstrumentedTest.kt`

Library module has **no** test directory. Add unit tests for at minimum:
- `LogStore`: capacity wrap, `sinceId` filter, concurrent add/snapshot, `tryEmit` overflow behavior
- `LogTapInterceptor`: redact set application, body truncation at exact boundary, gzip decode, `isWebSocketUpgrade` (H1 + H2 extended-CONNECT), HEAD/204/205/304 short-circuit
- `LogTapLogcatBridge`: regex against real `threadtime` lines, malformed lines, level mapping

### 17. [QA] No lint baseline; AGP lint task likely flags issues
Run `./gradlew :LogTap:lintRelease` and review. Likely findings: missing `@JvmStatic` for Java consumers, unused params, possibly missing `@RequiresApi` annotations on newer ConnectivityManager calls.

### 18. [QA] Sample app uses public test endpoints (`fakestoreapi.com`, `httpbin.org`, `echo.websocket.org`)
**File**: `app/src/main/java/.../MainActivity.kt`

Fine for a playground but flaky CI risk if anyone wires UI tests against them. Document or mock if used in tests.

---

## Code hygiene

### 19. [NIT] Swallowed exceptions
- `Interceptor.kt:133, 151, 232` — `catch (_: Exception)` with no logging. At minimum `Log.v(TAG, ..., e)` to aid diagnosis.
- `LogTap.kt:153, 160, 181` — `catch (_: Throwable) {}` during shutdown is OK, but a single `Log.v` call would help.

### 20. [NIT] Stray `LogTapsample/` directory at repo root
Not in `settings.gradle.kts`. Either delete it or wire it in. Currently it's a tracked-but-empty stub.

### 21. [NIT] `app/release/` checked in by accident
Visible in `git status`. Add to `.gitignore` if not already, and remove with `git rm -r --cached app/release/` before next commit.

### 22. [NIT] `.kotlin/` directory not gitignored
Same as above — Kotlin daemon scratch dir.

### 23. [DOC] Public API has no KDoc
Most of the public surface (`LogTap.start`, `LogTapLogger.*`, `LogTapInterceptor`, `Config` fields) lacks KDoc. The `withJavadocJar()` publish step ships a near-empty javadoc artifact. Add KDoc for every public symbol.

### 24. [DOC] No ProGuard/R8 keep rules
`LogTap/consumer-rules.pro` referenced in build.gradle.kts but not inspected here. Library uses kotlinx.serialization (needs `@Serializable` keep rules) and reflection-free Ktor. Verify consumer-rules.pro has:
```
-keep,allowobfuscation,allowshrinking class com.github.husseinhj.logtap.** { *; }
-keepclassmembers @kotlinx.serialization.Serializable class * { *; }
```
Otherwise R8 in the host app may strip serializers.

### 25. [DOC] `println` in `getSecret` leaks key presence to build output
**File**: `LogTap/build.gradle.kts:110`

```kotlin
println("===========> KEY $name is ${if (key == null) "NULL" else "HAS IT"} <============")
```

Harmless but noisy; remove before publishing or guard behind a `gradle.properties` flag.

---

## Suggested order of work

1. Fix bugs **#1, #2, #5** — these break documented behavior. Tiny diffs, high impact.
2. Add unit tests (#16) covering the fixes so regressions are caught.
3. Address bug **#3, #4** by deleting `LogTapEvents` entirely and routing everything through `LogStore`. Touches `server.kt` + `LogTapSinkAdapter.kt` + `LogEvent.kt`.
4. Tighten security: bind 127.0.0.1 by default + token (#6), body redaction (#7).
5. Move web UI out of `Resources.kt` into `assets/` (#14).
6. KDoc + consumer ProGuard rules (#23, #24) before next publish.
