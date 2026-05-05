---
description: Pre-release checklist for tagging a new LogTap version
---

Walk through the release prep without making any commits or pushes. Report status of each item; do not auto-fix.

Argument: $ARGUMENTS — the proposed version (e.g., `0.14.0`). If empty, ask the user.

Checks:

1. **Working tree clean** — `git status` shows no uncommitted changes (or only intended ones).
2. **CHANGELOG.md** — has an entry for the proposed version with bullet points covering changes since the previous tag. Run `git log <previous-tag>..HEAD --oneline` to summarize what's new.
3. **README.md install snippet** — version strings in the `debugImplementation`/`releaseImplementation` lines match the proposed version.
4. **API parity** — invoke `/parity-check` (or run the same checks inline). Report any drift.
5. **Public API stability** — diff public symbols against the previous tag (`git show <previous-tag>:LogTap/src/main/java/com/github/husseinhj/logtap/LogTap.kt` vs current). Flag any breaking change so the user can pick the right semver bump.
6. **CI workflow** — `.github/workflows/publish.yml` exists and references the secrets `OSSRH_USERNAME`, `OSSRH_PASSWORD`, `SIGNING_KEY`, `SIGNING_PASSWORD`.
7. **No debug artifacts checked in** — `app/release/`, `.kotlin/`, `local.properties` should not appear in `git diff`.
8. **Gradle build sanity** — DO NOT run gradle automatically; remind the user to run `./gradlew :LogTap:assembleRelease :LogTap-Noop:assembleRelease` once locally.

Output a checklist with ✓/✗/? for each item and a final summary: `READY TO TAG vX.Y.Z` or `BLOCKERS: ...`.
