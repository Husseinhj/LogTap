---
description: Verify public API parity between :LogTap and :LogTap-Noop modules
---

The LogTap library ships two artifacts that MUST have identical public API surfaces: `:LogTap` (real implementation) and `:LogTap-Noop` (empty stubs for release builds). Any drift breaks consumer release builds.

Do these checks:

1. List all public Kotlin declarations (objects, classes, interfaces, top-level functions, public members of objects) under `LogTap/src/main/java/com/github/husseinhj/logtap/**`.
2. List the same under `LogTap-Noop/src/main/java/com/github/husseinhj/logtap/**`.
3. Diff the two sets:
   - Symbols present in LogTap but missing in LogTap-Noop → **PARITY GAP** (release builds will fail to compile).
   - Symbols present in LogTap-Noop but missing in LogTap → dead stub.
   - Same symbol, different signature → ABI mismatch.
4. Pay special attention to: `LogTap` object members, `LogTap.Config` fields, `LogTapInterceptor`, `LogTapLogger` (all public methods + `Level` enum + `Sink` interface), `LogTapLogcatBridge`, `LogTapSinkAdapter`, `newWebSocketWithLogging` extension, `LoggingWebSocketListener`.

Report findings as a table: `Symbol | LogTap | LogTap-Noop | Status`. End with a one-line verdict: PARITY OK or N PARITY ISSUES.

Do NOT modify any files — read only.
