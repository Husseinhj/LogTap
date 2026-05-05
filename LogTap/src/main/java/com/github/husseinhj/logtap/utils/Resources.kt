package com.github.husseinhj.logtap.utils

internal object Resources {
    val indexHtml = """
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>LogTap - Modern Log Viewer</title>
    <link rel="stylesheet" href="/app.css"/>
</head>
<body>
    <div id="root" class="theme-android"></div>
    <script src="/app.js"></script>
</body>
</html>
""".trimIndent()

    val appCss = """
* { margin: 0; padding: 0; box-sizing: border-box; }
html, body { height: 100%; }
body {
    font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'SF Pro Text', sans-serif;
    background: #0a0a0a;
    color: #e4e4e4;
    overflow: hidden;
}
#root { width: 100vw; height: 100vh; display: flex; flex-direction: column; background: var(--bg-primary); color: var(--text-primary); }

/* Text selection — force readable contrast across themes/modes */
::selection { background: var(--accent); color: #ffffff; text-shadow: none; }
::-moz-selection { background: var(--accent); color: #ffffff; text-shadow: none; }

/* THEMES (dark by default) */
.theme-android {
    --bg-primary: #1e1e1e; --bg-secondary: #252526; --bg-tertiary: #2d2d30;
    --text-primary: #cccccc; --text-secondary: #858585;
    --accent: #3794ff;
    --verbose: #bbbbbb; --debug: #569cd6; --info: #4ec9b0;
    --warn: #dcdcaa; --error: #f48771; --assert: #f48771;
    --border: #3f3f46; --hover: rgba(255,255,255,0.05);
}
.theme-android.light-mode {
    --bg-primary: #ffffff; --bg-secondary: #f5f5f5; --bg-tertiary: #eeeeee;
    --text-primary: #1e1e1e; --text-secondary: #666666;
    --accent: #0066cc;
    --verbose: #666666; --debug: #0066cc; --info: #00a67e;
    --warn: #cc8800; --error: #d32f2f; --assert: #d32f2f;
    --border: #d0d0d0; --hover: rgba(0,0,0,0.05);
}
.theme-xcode {
    --bg-primary: #292a30; --bg-secondary: #1f1f24; --bg-tertiary: #36373c;
    --text-primary: #ffffff; --text-secondary: #8b8b8b;
    --accent: #0a84ff;
    --verbose: #8b8b8b; --debug: #6699ff; --info: #00cca3;
    --warn: #ffcc00; --error: #ff453a; --assert: #ff453a;
    --border: #3a3a3f; --hover: rgba(255,255,255,0.07);
}
.theme-xcode.light-mode {
    --bg-primary: #ffffff; --bg-secondary: #f7f7f7; --bg-tertiary: #efefef;
    --text-primary: #000000; --text-secondary: #6e6e6e;
    --accent: #007aff;
    --verbose: #6e6e6e; --debug: #0066cc; --info: #00a896;
    --warn: #f5a623; --error: #d70015; --assert: #d70015;
    --border: #d1d1d6; --hover: rgba(0,0,0,0.06);
}
.theme-grafana {
    --bg-primary: #0b0c0e; --bg-secondary: #111217; --bg-tertiary: #181b1f;
    --text-primary: #d4d4d8; --text-secondary: #9ca0ab;
    --accent: #ff5722;
    --verbose: #9ca0ab; --debug: #33b5e5; --info: #52c41a;
    --warn: #faad14; --error: #ff4d4f; --assert: #ff4d4f;
    --border: #26282e; --hover: rgba(255,255,255,0.06);
}
.theme-grafana.light-mode {
    --bg-primary: #f7f8fa; --bg-secondary: #ffffff; --bg-tertiary: #e9ecef;
    --text-primary: #212529; --text-secondary: #6c757d;
    --accent: #e53935;
    --verbose: #6c757d; --debug: #1e88e5; --info: #43a047;
    --warn: #fb8c00; --error: #e53935; --assert: #e53935;
    --border: #dee2e6; --hover: rgba(0,0,0,0.05);
}
.theme-modern {
    --bg-primary: #0f0f0f; --bg-secondary: #1a1a1a; --bg-tertiary: #242424;
    --text-primary: #f0f0f0; --text-secondary: #a0a0a0;
    --accent: #00d9ff;
    --verbose: #808080; --debug: #6366f1; --info: #14b8a6;
    --warn: #f59e0b; --error: #ef4444; --assert: #ef4444;
    --border: #2a2a2a; --hover: rgba(255,255,255,0.08);
}
.theme-modern.light-mode {
    --bg-primary: #fafafa; --bg-secondary: #ffffff; --bg-tertiary: #f0f0f0;
    --text-primary: #0f0f0f; --text-secondary: #6b7280;
    --accent: #0891b2;
    --verbose: #9ca3af; --debug: #4f46e5; --info: #059669;
    --warn: #d97706; --error: #dc2626; --assert: #dc2626;
    --border: #e5e7eb; --hover: rgba(0,0,0,0.04);
}

/* TOOLBAR */
.tb { background: var(--bg-secondary); border-bottom: 1px solid var(--border); padding: 12px 16px; display: flex; gap: 12px; align-items: center; flex-wrap: wrap; position: relative; }
.brand { display: flex; align-items: center; gap: 12px; margin-right: 12px; min-width: 0; }
.brand-icon { width: 32px; height: 32px; border-radius: 8px; background: linear-gradient(135deg, #6366f1 0%, #a855f7 100%); display: flex; align-items: center; justify-content: center; font-size: 13px; color: white; font-weight: bold; overflow: hidden; flex-shrink: 0; }
.brand-icon img { width: 100%; height: 100%; object-fit: cover; }
.brand-text { min-width: 0; }
.brand-name { font-size: 14px; font-weight: 600; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; max-width: 200px; }
.brand-meta { font-size: 11px; color: var(--text-secondary); font-weight: 400; }

.search-wrap { position: relative; flex: 1 1 300px; min-width: 200px; }
.search-input { width: 100%; padding: 8px 36px 8px 12px; background: var(--bg-tertiary); border: 1px solid var(--border); border-radius: 6px; color: var(--text-primary); font-size: 14px; outline: none; }
.search-input:focus { border-color: var(--accent); }
.star-btn { position: absolute; right: 8px; top: 50%; transform: translateY(-50%); background: transparent; border: none; color: var(--accent); cursor: pointer; font-size: 16px; padding: 4px; opacity: 0.7; transition: opacity 0.15s; }
.star-btn:hover { opacity: 1; }

.suggest { position: absolute; top: calc(100% + 4px); left: 0; right: 0; background: var(--bg-tertiary); border: 1px solid var(--border); border-radius: 8px; box-shadow: 0 8px 24px rgba(0,0,0,0.3); z-index: 1000; max-height: 300px; overflow-y: auto; }
.suggest-hdr { padding: 6px 12px; font-size: 11px; color: var(--text-secondary); border-bottom: 1px solid var(--border); font-weight: 600; }
.suggest-item { padding: 10px 12px; cursor: pointer; border-bottom: 1px solid var(--border); transition: background 0.1s; }
.suggest-item:last-child { border-bottom: none; }
.suggest-item.sel { background: var(--hover); }
.suggest-cmd { font-family: monospace; font-size: 13px; color: var(--accent); margin-bottom: 2px; }
.suggest-cmd .ph { color: var(--text-secondary); font-style: italic; }
.suggest-desc { font-size: 12px; color: var(--text-secondary); }
.suggest-ex { font-size: 11px; color: var(--text-secondary); margin-top: 2px; opacity: 0.7; }

.icon-btn { padding: 8px; background: var(--bg-tertiary); border: 1px solid var(--border); border-radius: 6px; color: var(--text-primary); cursor: pointer; font-size: 18px; width: 36px; height: 36px; display: flex; align-items: center; justify-content: center; position: relative; transition: background 0.1s; line-height: 1; }
.icon-btn:hover { background: var(--hover); }
.icon-btn.active { background: var(--accent); color: #fff; border-color: var(--accent); }
.icon-btn.danger { color: var(--error); }
.icon-btn.accent { color: var(--accent); }
.toolbar-right { margin-left: auto; display: flex; gap: 8px; align-items: center; }

.badge { position: absolute; top: -4px; right: -4px; background: var(--error); color: white; border-radius: 10px; font-size: 10px; font-weight: bold; padding: 2px 5px; min-width: 16px; text-align: center; line-height: 1; }

.popover { position: absolute; background: var(--bg-secondary); border: 1px solid var(--border); border-radius: 8px; box-shadow: 0 8px 32px rgba(0,0,0,0.3); z-index: 1000; padding: 12px; }
.popover-section { padding: 8px 4px; font-size: 11px; color: var(--text-secondary); font-weight: 600; text-transform: uppercase; }
.popover select, .popover input[type=text] { width: 100%; padding: 8px 12px; background: var(--bg-tertiary); border: 1px solid var(--border); border-radius: 6px; color: var(--text-primary); font-size: 13px; cursor: pointer; outline: none; }
.popover select:focus, .popover input:focus { border-color: var(--accent); }

.saved-list { max-height: 300px; overflow: auto; }
.saved-empty { padding: 24px; text-align: center; color: var(--text-secondary); font-size: 13px; }
.saved-row { padding: 12px; background: var(--bg-tertiary); border-radius: 6px; margin-bottom: 8px; display: flex; justify-content: space-between; align-items: center; gap: 8px; }
.saved-row .body { flex: 1; min-width: 0; cursor: pointer; }
.saved-row .body:hover { color: var(--accent); }
.saved-q { font-size: 12px; font-family: monospace; color: var(--text-secondary); white-space: nowrap; overflow: hidden; text-overflow: ellipsis; }
.saved-ts { font-size: 10px; color: var(--text-secondary); margin-top: 4px; }
.del-btn { padding: 6px 10px; background: transparent; border: 1px solid var(--border); border-radius: 4px; color: var(--error); cursor: pointer; font-size: 12px; }

/* FILTER PANEL */
.filter-panel { width: 100%; padding: 16px; background: var(--bg-tertiary); border-radius: 8px; border: 1px solid var(--border); display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 16px; }
.filter-panel label { font-size: 12px; color: var(--text-secondary); display: block; margin-bottom: 4px; }
.filter-panel select, .filter-panel input { width: 100%; padding: 6px 8px; background: var(--bg-secondary); border: 1px solid var(--border); border-radius: 4px; color: var(--text-primary); font-size: 14px; outline: none; }
.exclude-row { display: flex; gap: 8px; margin-bottom: 8px; }
.exclude-row select { flex: 0 0 140px; }
.exclude-row input { flex: 1; }
.exclude-row button { padding: 6px 12px; background: var(--accent); border: none; border-radius: 4px; color: white; cursor: pointer; font-size: 14px; }
.chips { display: flex; flex-wrap: wrap; gap: 6px; }
.chip { padding: 4px 8px; background: var(--bg-secondary); border-radius: 12px; font-size: 12px; display: flex; align-items: center; gap: 4px; }
.chip button { background: none; border: none; color: var(--error); cursor: pointer; font-size: 14px; padding: 0 4px; line-height: 1; }

/* METRICS BAR */
.metrics { background: var(--bg-secondary); border-bottom: 1px solid var(--border); padding: 12px 16px; display: flex; gap: 24px; font-size: 13px; flex-wrap: wrap; }
.metric { display: flex; align-items: center; gap: 8px; }
.metric .l { font-size: 11px; color: var(--text-secondary); text-transform: uppercase; }
.metric .v { font-size: 16px; font-weight: 600; }

/* MAIN AREA */
.main { flex: 1; display: flex; overflow: hidden; min-height: 0; }

/* LOGCAT VIEW */
.logcat { flex: 1; overflow: auto; padding: 8px 0; font-family: 'JetBrains Mono', 'SF Mono', Menlo, Consolas, monospace; font-size: 12px; line-height: 1.5; background: var(--bg-primary); }
.logcat-row { padding: 1px 16px; white-space: pre-wrap; cursor: default; word-break: break-all; }
.logcat-row:hover { background: var(--hover); }
.logcat-time { color: var(--text-secondary); }
.logcat-tid { color: var(--text-secondary); }
.logcat-tag { font-weight: 600; }
.lvl-V { color: var(--verbose); }
.lvl-D { color: var(--debug); }
.lvl-I { color: var(--info); }
.lvl-W { color: var(--warn); }
.lvl-E { color: var(--error); }
.lvl-A { color: var(--assert); }
.logcat-net-line { color: var(--text-secondary); padding-left: 140px; white-space: pre-wrap; word-break: break-all; }
.logcat-net-line .k { color: var(--accent); }
.logcat-net-line .req { color: var(--warn); }
.logcat-net-line .res-ok { color: var(--info); }
.logcat-net-line .res-err { color: var(--error); }

/* TABLE VIEW */
.tbl-wrap { flex: 1; overflow: auto; min-height: 0; }
.tbl-head { display: grid; gap: 12px; padding: 10px 16px; background: var(--bg-secondary); border-bottom: 1px solid var(--border); font-size: 11px; font-weight: 600; color: var(--text-secondary); text-transform: uppercase; position: sticky; top: 0; z-index: 1; min-width: fit-content; }
.tbl-head .col { display: flex; align-items: center; position: relative; user-select: none; }
.col-resize { position: absolute; right: -6px; top: -10px; bottom: -10px; width: 12px; cursor: col-resize; z-index: 10; }
.col-resize::after { content: ''; display: block; width: 1px; height: 100%; margin: 0 auto; background: var(--border); }
.col-resize:hover::after, .col-resize.dragging::after { background: var(--accent); width: 2px; }
.tbl-body { min-width: fit-content; }
.tbl-row { display: grid; gap: 12px; padding: 10px 16px; border-bottom: 1px solid var(--border); font-size: 13px; cursor: pointer; transition: background 0.1s; align-items: start; }
.tbl-row:hover { background: var(--hover); }
.tbl-row.selected { background: var(--hover); border-left: 3px solid var(--accent); padding-left: 13px; }
.tbl-row.compact { padding: 4px 16px; }
.tbl-row.spacious { padding: 16px; }
.tbl-cell { overflow: hidden; word-break: break-word; }
.tbl-cell .tt-id { color: var(--text-secondary); font-family: monospace; font-size: 11px; }
.tbl-cell .tt-time { color: var(--text-secondary); font-family: monospace; font-size: 11px; white-space: nowrap; }
.type-pill { display: inline-block; padding: 1px 6px; border-radius: 4px; font-size: 10px; font-weight: 600; text-transform: uppercase; }
.type-http { background: rgba(20,184,166,0.15); color: var(--info); }
.type-log { background: rgba(86,156,214,0.15); color: var(--debug); }
.type-ws { background: rgba(168,85,247,0.15); color: #a855f7; }
.method-tag { font-family: monospace; font-weight: 600; font-size: 11px; }
.status-pill { display: inline-block; padding: 1px 6px; border-radius: 4px; font-size: 11px; font-weight: 600; }
.status-2 { background: rgba(20,184,166,0.15); color: var(--info); }
.status-3 { background: rgba(86,156,214,0.15); color: var(--debug); }
.status-4 { background: rgba(245,158,11,0.15); color: var(--warn); }
.status-5 { background: rgba(239,68,68,0.15); color: var(--error); }
.url-cell { word-break: break-all; }
.url-body { font-size: 11px; color: var(--text-secondary); margin-top: 4px; font-family: monospace; max-height: 5em; overflow: hidden; text-overflow: ellipsis; white-space: pre-wrap; word-break: break-word; }
.resp-cell { font-family: monospace; font-size: 11px; max-height: 5em; overflow: hidden; word-break: break-all; white-space: pre-wrap; }
.resp-ok { color: var(--info); }
.resp-err { color: var(--error); }
.tbl-row .expand-area { grid-column: 1/-1; padding: 12px; background: var(--bg-tertiary); border-radius: 6px; margin-top: 8px; }
.exp-section { margin-bottom: 12px; }
.exp-section:last-child { margin-bottom: 0; }
.exp-label { font-size: 11px; color: var(--text-secondary); font-weight: 600; text-transform: uppercase; margin-bottom: 4px; }
.exp-content { font-family: monospace; font-size: 12px; padding: 8px; background: var(--bg-secondary); border-radius: 4px; white-space: pre-wrap; word-break: break-all; max-height: 240px; overflow: auto; }
.headers-grid { display: grid; grid-template-columns: max-content 1fr; gap: 4px 12px; font-family: monospace; font-size: 12px; padding: 8px; background: var(--bg-secondary); border-radius: 4px; }
.headers-grid .hk { color: var(--accent); }
.headers-grid .hv { color: var(--text-primary); word-break: break-all; }

/* DETAIL PANEL */
.detail { flex: 0 0 40%; min-width: 320px; background: var(--bg-secondary); border-left: 1px solid var(--border); display: flex; flex-direction: column; overflow: hidden; }
.detail-hdr { padding: 12px 16px; border-bottom: 1px solid var(--border); display: flex; justify-content: space-between; align-items: center; }
.detail-title { font-size: 14px; font-weight: 600; }
.detail-tabs { display: flex; gap: 4px; padding: 8px 16px; border-bottom: 1px solid var(--border); }
.detail-tab { padding: 6px 12px; background: transparent; border: 1px solid var(--border); border-radius: 4px; color: var(--text-secondary); cursor: pointer; font-size: 12px; }
.detail-tab.active { background: var(--accent); color: white; border-color: var(--accent); }
.detail-body { flex: 1; overflow: auto; padding: 16px; }

/* CONTEXT MENU */
.ctx-menu { position: fixed; background: var(--bg-secondary); border: 1px solid var(--border); border-radius: 8px; box-shadow: 0 8px 32px rgba(0,0,0,0.5); z-index: 10000; padding: 6px; min-width: 200px; backdrop-filter: blur(8px); }
.ctx-item { padding: 8px 12px; font-size: 13px; cursor: pointer; border-radius: 4px; display: flex; align-items: center; gap: 8px; }
.ctx-item:hover { background: var(--hover); }
.ctx-sep { height: 1px; background: var(--border); margin: 4px 0; }

/* STATUS BAR */
.status-bar { background: var(--bg-secondary); border-top: 1px solid var(--border); padding: 6px 16px; display: flex; gap: 16px; align-items: center; font-size: 12px; color: var(--text-secondary); }
.status-bar .right { margin-left: auto; display: flex; gap: 12px; align-items: center; }
.conn-dot { width: 8px; height: 8px; border-radius: 50%; }
.conn-connecting { background: var(--warn); animation: pulse 1.2s ease-in-out infinite; }
.conn-connected { background: var(--info); }
.conn-disconnected { background: var(--error); }
@keyframes pulse { 0%,100% { opacity: 1; } 50% { opacity: 0.4; } }

/* AUTOSCROLL TOAST */
.toast { position: fixed; bottom: 50px; left: 50%; transform: translateX(-50%); background: var(--bg-tertiary); border: 1px solid var(--border); border-radius: 6px; padding: 8px 12px; font-size: 12px; cursor: pointer; box-shadow: 0 4px 12px rgba(0,0,0,0.3); z-index: 100; }
.toast:hover { background: var(--accent); color: white; }

/* MISC */
.hidden { display: none !important; }
.muted { color: var(--text-secondary); }
button { font-family: inherit; }
""".trimIndent()

    val appJs = """
(function(){
  'use strict';
  var ${'$'} = function(sel){ return document.querySelector(sel); };
  var ${'$'}${'$'} = function(sel){ return Array.prototype.slice.call(document.querySelectorAll(sel)); };
  var root = ${'$'}('#root');

  // ---------- STATE ----------
  var state = {
    logs: [],          // unified rows
    httpPending: {},   // requestId -> row index for pairing response
    nextLocalId: 1,
    selectedId: null,
    expandedIds: {},
    theme: localStorage.getItem('logtap-theme') || 'android',
    darkMode: JSON.parse(localStorage.getItem('logtap-dark-mode') || 'true'),
    viewMode: localStorage.getItem('logtap-view-mode') || 'logcat',
    density: localStorage.getItem('logtap-density') || 'comfortable',
    searchQuery: '',
    autoScroll: true,
    connectionStatus: 'connecting',
    appInfo: null,
    filters: {
      type: 'all', method: 'all', status: 'all', level: 'all',
      excludePackages: [], excludeMessages: []
    },
    savedFilters: JSON.parse(localStorage.getItem('logtap-saved-filters') || '[]'),
    showFilters: false,
    showSavedFilters: false,
    showSettings: false,
    showSuggestions: false,
    suggestions: [],
    suggestSel: 0,
    excludeInput: '',
    excludeType: 'package',
    columnWidths: (function(){
      var def = [60,110,110,80,150,120,80,450,400];
      try {
        var saved = JSON.parse(localStorage.getItem('logtap-cols') || 'null');
        if (Array.isArray(saved) && saved.length === def.length) return saved;
      } catch(e){}
      return def;
    })(),
    contextMenu: null
  };

  function pidTid(log){
    var pid = (log.pid != null) ? String(log.pid) : '';
    var tid = (log.tid != null) ? String(log.tid) : (log.thread || '');
    if (!pid && !tid) return '';
    if (!pid) return tid;
    if (!tid) return pid;
    return pid + '-' + tid;
  }

  // ---------- THEME APPLY ----------
  function applyTheme(){
    root.className = 'theme-' + state.theme + (state.darkMode ? '' : ' light-mode');
  }

  // ---------- EVENT NORMALIZATION ----------
  // Server LogEvent -> UI row
  // Pair HTTP REQUEST + RESPONSE into single row keyed by url+method+thread within ~60s
  function ingest(ev){
    if (ev.kind === 'HTTP') {
      if (ev.direction === 'REQUEST') {
        var row = {
          uid: 'http-' + ev.id,
          serverId: ev.id,
          ts: ev.ts,
          type: 'http',
          method: ev.method || 'GET',
          url: ev.url || '',
          status: null,
          duration: null,
          requestHeaders: ev.headers || null,
          requestBody: ev.bodyPreview || null,
          responseHeaders: null,
          responseBody: null,
          tag: 'HTTP',
          level: 'INFO',
          message: (ev.method || 'GET') + ' ' + (ev.url || ''),
          thread: ev.thread,
          summary: ev.summary,
          completed: false
        };
        var key = (ev.method||'') + '|' + (ev.url||'') + '|' + ev.thread;
        state.httpPending[key] = state.logs.length;
        state.logs.push(row);
      } else if (ev.direction === 'RESPONSE') {
        var key = (ev.method||'') + '|' + (ev.url||'') + '|' + ev.thread;
        var idx = state.httpPending[key];
        var derivedLevel = (ev.status >= 500) ? 'ERROR' : (ev.status >= 400) ? 'WARN' : 'INFO';
        if (idx !== undefined && state.logs[idx] && !state.logs[idx].completed) {
          var r = state.logs[idx];
          r.status = ev.status;
          r.duration = ev.tookMs;
          r.responseHeaders = ev.headers;
          r.responseBody = ev.bodyPreview;
          r.level = derivedLevel;
          r.completed = true;
          delete state.httpPending[key];
        } else {
          // Orphan response
          state.logs.push({
            uid: 'http-' + ev.id, serverId: ev.id, ts: ev.ts, type: 'http',
            method: ev.method||'', url: ev.url||'', status: ev.status,
            duration: ev.tookMs, responseHeaders: ev.headers, responseBody: ev.bodyPreview,
            tag: 'HTTP', level: derivedLevel, message: ev.summary, thread: ev.thread, completed: true
          });
        }
      } else if (ev.direction === 'ERROR') {
        state.logs.push({
          uid: 'http-' + ev.id, serverId: ev.id, ts: ev.ts, type: 'http',
          method: ev.method||'', url: ev.url||'', status: null, duration: null,
          tag: 'HTTP', level: 'ERROR', message: ev.summary, thread: ev.thread, completed: true,
          error: ev.reason
        });
      }
    } else if (ev.kind === 'WEBSOCKET') {
      state.logs.push({
        uid: 'ws-' + ev.id, serverId: ev.id, ts: ev.ts, type: 'ws',
        method: ev.method || 'WS', url: ev.url || '', status: ev.code,
        direction: ev.direction, tag: 'WebSocket',
        level: ev.direction === 'ERROR' ? 'ERROR' : 'INFO',
        message: ev.summary, thread: ev.thread,
        responseBody: ev.bodyPreview, completed: true
      });
    } else { // LOG
      state.logs.push({
        uid: 'log-' + ev.id, serverId: ev.id, ts: ev.ts, type: 'log',
        level: (ev.level || 'INFO').toUpperCase(),
        tag: ev.tag || 'Log',
        message: ev.summary || '',
        thread: ev.thread,
        completed: true
      });
    }
  }

  // ---------- COMMAND PARSER ----------
  function parseSearchCommands(query){
    var cmds = { exclude: { tags: [], messages: [], methods: [], statuses: [] },
                 include: { tags: [], messages: [], methods: [], statuses: [], levels: [] },
                 plain: '' };
    var parts = query.match(/(?:[^\s"]+|"[^"]*")+/g) || [];
    var plainParts = [];
    parts.forEach(function(p){
      var clean = function(v){ return (v||'').replace(/"/g,''); };
      if (p.indexOf('exclude:') === 0) {
        var rest = p.substring(8);
        var i = rest.indexOf(':');
        if (i > 0) {
          var t = rest.substring(0, i), v = clean(rest.substring(i+1));
          if (t === 'tag' && v) cmds.exclude.tags.push(v);
          else if (t === 'message' && v) cmds.exclude.messages.push(v);
          else if (t === 'method' && v) cmds.exclude.methods.push(v.toUpperCase());
          else if (t === 'status' && v) cmds.exclude.statuses.push(v);
        }
      } else if (p.indexOf('include:') === 0 || p.indexOf('level:') === 0 || p.indexOf('method:') === 0 || p.indexOf('status:') === 0) {
        var ci = p.indexOf(':');
        var pref = p.substring(0, ci), val = clean(p.substring(ci+1));
        if (pref === 'level' && val) cmds.include.levels.push(val.toUpperCase());
        else if ((pref === 'method' || pref === 'include') && val) cmds.include.methods.push(val.toUpperCase());
        else if ((pref === 'status' || pref === 'include') && val) cmds.include.statuses.push(val);
      } else {
        plainParts.push(clean(p));
      }
    });
    cmds.plain = plainParts.join(' ');
    return cmds;
  }

  // ---------- FILTER ----------
  function ciContains(field, q){
    if (field == null || q == null || q === '') return false;
    return String(field).toLowerCase().indexOf(String(q).toLowerCase()) !== -1;
  }
  function fullText(log){
    return (log.type === 'http')
      ? [(log.method||''), (log.url||''), (log.status==null?'':String(log.status)), (log.message||''), (log.requestBody||''), (log.responseBody||''), (log.tag||''), (log.level||''), (log.error||'')].join(' ')
      : [(log.tag||''), (log.level||''), (log.message||''), (log.url||''), (log.responseBody||''), (log.method||''), (log.direction||'')].join(' ');
  }
  function getFiltered(){
    var cmds = parseSearchCommands(state.searchQuery);
    var f = state.filters;
    return state.logs.filter(function(log){
      var lvl = (log.level||'').toUpperCase();
      var meth = (log.method||'').toUpperCase();
      var statStr = (log.status==null) ? '' : String(log.status);

      // ---- Panel filters ----
      if (f.type !== 'all' && log.type !== f.type) return false;
      if (f.method !== 'all' && (log.type !== 'http' || meth !== f.method.toUpperCase())) return false;
      if (f.status !== 'all') {
        if (log.type !== 'http' || log.status == null) return false;
        if (f.status === '2xx' && (log.status < 200 || log.status >= 300)) return false;
        if (f.status === '4xx' && (log.status < 400 || log.status >= 500)) return false;
        if (f.status === '5xx' && log.status < 500) return false;
      }
      if (f.level !== 'all' && lvl !== f.level.toUpperCase()) return false;

      // ---- Panel exclusions (substring, case-insensitive) ----
      if (f.excludePackages.some(function(p){ return ciContains(log.tag, p); })) return false;
      if (f.excludeMessages.some(function(m){ return ciContains(fullText(log), m); })) return false;

      // ---- Command exclusions ----
      // exclude:tag:X    → drop if log.tag contains X
      if (cmds.exclude.tags.some(function(t){ return ciContains(log.tag, t); })) return false;
      // exclude:message:X → drop if any visible field contains X
      if (cmds.exclude.messages.some(function(m){ return ciContains(fullText(log), m); })) return false;
      // exclude:method:X → drop only HTTP rows whose method matches
      if (cmds.exclude.methods.length > 0 && log.type === 'http') {
        if (cmds.exclude.methods.some(function(m){ return meth === m.toUpperCase(); })) return false;
      }
      // exclude:status:X → drop only HTTP rows whose status matches
      if (cmds.exclude.statuses.length > 0 && log.type === 'http') {
        if (cmds.exclude.statuses.some(function(s){
          if (statStr === s) return true;
          // Allow class shorthand: 4xx, 5xx
          if (/^[1-5]xx${'$'}/i.test(s) && log.status != null) {
            var cls = parseInt(s.charAt(0), 10);
            return log.status >= cls*100 && log.status < (cls+1)*100;
          }
          return false;
        })) return false;
      }

      // ---- Command inclusions (positive filters) ----
      // level:X applies to ALL types (http rows have derived level too)
      if (cmds.include.levels.length > 0) {
        if (cmds.include.levels.indexOf(lvl) === -1) return false;
      }
      // method:X only matches HTTP rows
      if (cmds.include.methods.length > 0) {
        if (log.type !== 'http') return false;
        if (cmds.include.methods.indexOf(meth) === -1) return false;
      }
      // status:X only matches HTTP rows; supports class shorthand 2xx/4xx/5xx
      if (cmds.include.statuses.length > 0) {
        if (log.type !== 'http' || log.status == null) return false;
        var ok = cmds.include.statuses.some(function(s){
          if (statStr === s) return true;
          if (/^[1-5]xx${'$'}/i.test(s)) {
            var cls = parseInt(s.charAt(0), 10);
            return log.status >= cls*100 && log.status < (cls+1)*100;
          }
          return false;
        });
        if (!ok) return false;
      }

      // ---- Plain text search across all visible fields ----
      if (cmds.plain) {
        if (!ciContains(fullText(log), cmds.plain)) return false;
      }

      return true;
    });
  }

  // ---------- METRICS ----------
  function computeMetrics(){
    var total = state.logs.length;
    var network = 0, logCount = 0, errors = 0, durSum = 0, durN = 0;
    var oneMinAgo = Date.now() - 60000;
    var perMin = 0;
    state.logs.forEach(function(l){
      if (l.type === 'http') {
        network++;
        if (l.status && l.status >= 400) errors++;
        if (typeof l.duration === 'number') { durSum += l.duration; durN++; }
      } else if (l.type === 'log') {
        logCount++;
        if (l.level === 'ERROR' || l.level === 'ASSERT') errors++;
      }
      if (l.ts >= oneMinAgo) perMin++;
    });
    return {
      total: total, network: network, logs: logCount,
      errors: errors, perMin: perMin,
      avgResponseTime: durN > 0 ? Math.round(durSum / durN) : 0
    };
  }

  // ---------- HELPERS ----------
  function fmtTime(ts){
    var d = new Date(ts);
    var pad = function(n,w){ var s = String(n); while (s.length < w) s = '0' + s; return s; };
    return pad(d.getHours(),2) + ':' + pad(d.getMinutes(),2) + ':' + pad(d.getSeconds(),2) + '.' + pad(d.getMilliseconds(),3);
  }
  function escHtml(s){
    if (s == null) return '';
    return String(s).replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/"/g,'&quot;');
  }
  function levelChar(l){
    if (!l) return ' ';
    return l.charAt(0);
  }
  function statusClass(s){
    if (s == null) return '';
    if (s >= 200 && s < 300) return 'status-2';
    if (s >= 300 && s < 400) return 'status-3';
    if (s >= 400 && s < 500) return 'status-4';
    return 'status-5';
  }
  function copyText(t){
    try { navigator.clipboard.writeText(t); } catch(e) {
      var ta = document.createElement('textarea');
      ta.value = t; document.body.appendChild(ta); ta.select();
      try { document.execCommand('copy'); } catch(e2){}
      document.body.removeChild(ta);
    }
  }
  function asCurl(log){
    if (log.type !== 'http') return '';
    var s = "curl -X " + (log.method||'GET') + " '" + (log.url||'') + "'";
    if (log.requestHeaders) {
      Object.keys(log.requestHeaders).forEach(function(k){
        var vals = log.requestHeaders[k] || [];
        vals.forEach(function(v){ s += " -H '" + k + ": " + v + "'"; });
      });
    }
    if (log.requestBody) s += " --data '" + log.requestBody.replace(/'/g, "'\\''") + "'";
    return s;
  }
  function debounce(fn, ms){
    var t; return function(){ var args = arguments; clearTimeout(t); t = setTimeout(function(){ fn.apply(null, args); }, ms); };
  }

  // ---------- SUGGESTIONS ----------
  var ALL_SUGGESTIONS = [
    { cmd: 'exclude:tag:', desc: 'Hide logs from a tag/package', example: 'exclude:tag:MainActivity' },
    { cmd: 'exclude:message:', desc: 'Hide logs containing text', example: 'exclude:message:timeout' },
    { cmd: 'exclude:method:', desc: 'Hide HTTP method', example: 'exclude:method:GET' },
    { cmd: 'exclude:status:', desc: 'Hide HTTP status', example: 'exclude:status:404' },
    { cmd: 'level:', desc: 'Show only this log level', example: 'level:ERROR' },
    { cmd: 'method:', desc: 'Show only this HTTP method', example: 'method:POST' },
    { cmd: 'status:', desc: 'Show only this status code', example: 'status:200' },
    { cmd: 'level:ERROR', desc: 'Show only errors', example: '' },
    { cmd: 'level:WARN', desc: 'Show only warnings', example: '' },
    { cmd: 'level:INFO', desc: 'Show only info logs', example: '' },
    { cmd: 'level:DEBUG', desc: 'Show only debug logs', example: '' },
    { cmd: 'level:VERBOSE', desc: 'Show only verbose logs', example: '' },
    { cmd: 'method:GET', desc: 'Show only GET requests', example: '' },
    { cmd: 'method:POST', desc: 'Show only POST requests', example: '' },
    { cmd: 'method:PUT', desc: 'Show only PUT requests', example: '' },
    { cmd: 'method:DELETE', desc: 'Show only DELETE requests', example: '' },
    { cmd: 'status:200', desc: 'Show only 200 OK', example: '' },
    { cmd: 'status:404', desc: 'Show only 404', example: '' },
    { cmd: 'status:500', desc: 'Show only 500', example: '' },
    { cmd: 'status:2xx', desc: 'Show only 2xx success', example: '' },
    { cmd: 'status:4xx', desc: 'Show only 4xx client errors', example: '' },
    { cmd: 'status:5xx', desc: 'Show only 5xx server errors', example: '' }
  ];
  function updateSuggestions(){
    var inp = ${'$'}('#search-input');
    if (!inp) return;
    var pos = inp.selectionStart || inp.value.length;
    var before = inp.value.substring(0, pos);
    var lastWord = before.split(' ').pop();
    if (!lastWord) { state.showSuggestions = false; state.suggestions = []; return; }
    state.suggestions = ALL_SUGGESTIONS.filter(function(s){
      return s.cmd.toLowerCase().indexOf(lastWord.toLowerCase()) === 0;
    });
    state.suggestSel = 0;
    state.showSuggestions = state.suggestions.length > 0;
  }
  function applySuggestion(cmd){
    var inp = ${'$'}('#search-input');
    if (!inp) return;
    var pos = inp.selectionStart || inp.value.length;
    var before = state.searchQuery.substring(0, pos);
    var after = state.searchQuery.substring(pos);
    var words = before.split(' ');
    words[words.length - 1] = cmd;
    var nv, np;
    if (cmd.charAt(cmd.length-1) === ':') {
      nv = words.join(' ') + after;
      np = words.join(' ').length;
    } else {
      nv = words.join(' ') + ' ' + after;
      np = words.join(' ').length + 1;
    }
    state.searchQuery = nv;
    state.showSuggestions = false;
    inp.value = nv;
    inp.focus();
    try { inp.setSelectionRange(np, np); } catch(e){}
    updateSearchUI();
  }

  // Targeted DOM updates that DO NOT touch the search input element
  // Used during typing/autocomplete so focus and cursor position are preserved.
  function refreshSuggestPanel(){
    var sw = ${'$'}('.search-wrap');
    if (!sw) return;
    var old = sw.querySelector('.suggest');
    if (old) old.remove();
    if (state.showSuggestions && state.suggestions.length > 0) {
      sw.appendChild(buildSuggestPanel());
    }
  }
  function refreshStarBtn(){
    var sw = ${'$'}('.search-wrap');
    if (!sw) return;
    var oldStar = sw.querySelector('.star-btn');
    if (oldStar) oldStar.remove();
    if (state.searchQuery.trim()) {
      var saved = isCurrentSaved();
      var btn = h('button', {
        class:'star-btn',
        title: saved ? 'Remove from favorites' : 'Save to favorites',
        onclick: function(e){ e.stopPropagation(); toggleSaveCurrent(); }
      }, saved ? '★' : '☆');
      // Insert before suggest panel (if any) so star stays inside input area
      var sug = sw.querySelector('.suggest');
      if (sug) sw.insertBefore(btn, sug); else sw.appendChild(btn);
    }
  }
  function refreshSavedBadge(){
    var savedBtn = ${'$'}${'$'}('.icon-btn').filter(function(b){ return b.title === 'Saved Filters'; })[0];
    if (!savedBtn) return;
    var old = savedBtn.querySelector('.badge');
    if (old) old.remove();
    if (state.savedFilters.length > 0) {
      savedBtn.appendChild(h('span', {class:'badge'}, String(state.savedFilters.length)));
    }
  }
  function replaceSection(sel, builder){
    var old = ${'$'}(sel);
    if (old && old.parentNode) old.parentNode.replaceChild(builder(), old);
  }
  function updateSearchUI(){
    refreshSuggestPanel();
    refreshStarBtn();
    refreshSavedBadge();
    replaceSection('.metrics', buildMetrics);
    replaceSection('.main', buildMain);
    replaceSection('#status-bar', buildStatusBar);
  }

  // ---------- SAVED FILTERS ----------
  function isCurrentSaved(){
    var q = state.searchQuery.trim();
    return q && state.savedFilters.some(function(f){ return f.query === q; });
  }
  function toggleSaveCurrent(){
    var q = state.searchQuery.trim();
    if (!q) return;
    var existing = state.savedFilters.filter(function(f){ return f.query === q; });
    if (existing.length > 0) {
      state.savedFilters = state.savedFilters.filter(function(f){ return f.query !== q; });
    } else {
      state.savedFilters.push({ id: Date.now() + ':' + Math.random(), name: q, query: q, timestamp: new Date().toISOString() });
    }
    localStorage.setItem('logtap-saved-filters', JSON.stringify(state.savedFilters));
    // Use targeted updates so the input keeps focus + cursor position
    updateSearchUI();
    // Also refresh saved-filters popover if open
    if (state.showSavedFilters) render();
  }
  function loadSaved(f){
    state.searchQuery = f.query;
    state.showSavedFilters = false;
    render();
  }
  function deleteSaved(id){
    state.savedFilters = state.savedFilters.filter(function(f){ return f.id !== id; });
    localStorage.setItem('logtap-saved-filters', JSON.stringify(state.savedFilters));
    render();
  }

  // ---------- CONTEXT MENU ----------
  function showContextMenu(x, y, items){
    state.contextMenu = { x: x, y: y, items: items };
    render();
  }
  function hideContextMenu(){
    if (state.contextMenu) { state.contextMenu = null; render(); }
  }
  document.addEventListener('click', function(){ hideContextMenu(); });

  // ---------- RENDER ----------
  function h(tag, attrs, children){
    var el = document.createElement(tag);
    if (attrs) {
      Object.keys(attrs).forEach(function(k){
        var v = attrs[k];
        if (v == null || v === false) return;
        if (k === 'class') el.className = v;
        else if (k === 'style' && typeof v === 'object') {
          Object.keys(v).forEach(function(s){ el.style[s] = v[s]; });
        } else if (k.indexOf('on') === 0 && typeof v === 'function') {
          el.addEventListener(k.substring(2).toLowerCase(), v);
        } else if (k === 'html') {
          el.innerHTML = v;
        } else {
          el.setAttribute(k, v);
        }
      });
    }
    if (children != null) {
      if (!Array.isArray(children)) children = [children];
      children.forEach(function(c){
        if (c == null || c === false) return;
        if (typeof c === 'string' || typeof c === 'number') el.appendChild(document.createTextNode(String(c)));
        else el.appendChild(c);
      });
    }
    return el;
  }

  function render(){
    applyTheme();
    root.innerHTML = '';
    root.appendChild(buildToolbar());
    root.appendChild(buildMetrics());
    root.appendChild(buildMain());
    root.appendChild(buildStatusBar());
    if (state.contextMenu) root.appendChild(buildContextMenu());
  }

  function buildToolbar(){
    var info = state.appInfo;
    var iconHtml = info && info.appIconBase64
      ? '<img src="data:image/png;base64,' + info.appIconBase64 + '"/>'
      : 'LT';
    var brand = h('div', {class:'brand'},[
      h('div', {class:'brand-icon', html: iconHtml}),
      h('div', {class:'brand-text'}, [
        h('div', {class:'brand-name'}, info ? info.appName : 'LogTap'),
        h('div', {class:'brand-meta'}, info ? ('v' + info.versionName + ' (build ' + info.versionCode + ')') : 'Connecting...')
      ])
    ]);

    var saved = isCurrentSaved();
    var starBtn = state.searchQuery.trim()
      ? h('button', {class:'star-btn', title: saved ? 'Remove from favorites' : 'Save to favorites', onclick: function(e){ e.stopPropagation(); toggleSaveCurrent(); }}, saved ? '★' : '☆')
      : null;

    var inp = h('input', {
      id: 'search-input', class: 'search-input', type: 'text',
      placeholder: 'Search or use commands: exclude:tag:MainActivity, level:ERROR...',
      value: state.searchQuery,
      autocomplete: 'off', spellcheck: 'false',
      oninput: function(e){ state.searchQuery = e.target.value; updateSuggestions(); updateSearchUI(); },
      onkeydown: function(e){
        if (state.showSuggestions && state.suggestions.length > 0) {
          if (e.key === 'ArrowDown') { e.preventDefault(); state.suggestSel = (state.suggestSel + 1) % state.suggestions.length; refreshSuggestPanel(); return; }
          if (e.key === 'ArrowUp') { e.preventDefault(); state.suggestSel = (state.suggestSel - 1 + state.suggestions.length) % state.suggestions.length; refreshSuggestPanel(); return; }
          if (e.key === 'Enter' || e.key === 'Tab') { e.preventDefault(); applySuggestion(state.suggestions[state.suggestSel].cmd); return; }
          if (e.key === 'Escape') { state.showSuggestions = false; refreshSuggestPanel(); return; }
        }
      },
      onfocus: function(){ updateSuggestions(); refreshSuggestPanel(); },
      onblur: function(){ setTimeout(function(){ state.showSuggestions = false; refreshSuggestPanel(); }, 150); }
    });

    var suggestPanel = (state.showSuggestions && state.suggestions.length > 0)
      ? buildSuggestPanel()
      : null;

    var searchWrap = h('div', {class:'search-wrap'}, [inp, starBtn, suggestPanel]);

    var btnFilters = h('button', {class:'icon-btn' + (state.showFilters?' active':''), title:'Filters', onclick: function(e){ e.stopPropagation(); state.showFilters = !state.showFilters; render(); }}, '☰');
    var btnSavedBadge = state.savedFilters.length > 0 ? h('span', {class:'badge'}, String(state.savedFilters.length)) : null;
    var btnSaved = h('button', {class:'icon-btn' + (state.showSavedFilters?' active':''), title:'Saved Filters', onclick: function(e){ e.stopPropagation(); state.showSavedFilters = !state.showSavedFilters; state.showSettings = false; render(); }}, ['★', btnSavedBadge]);

    var btnTheme = h('button', {class:'icon-btn', title: state.darkMode ? 'Switch to Light' : 'Switch to Dark', onclick: function(){ state.darkMode = !state.darkMode; localStorage.setItem('logtap-dark-mode', JSON.stringify(state.darkMode)); render(); }}, state.darkMode ? '◐' : '◑');
    var btnClear = h('button', {class:'icon-btn danger', title:'Clear logs', onclick: clearLogs}, '🗑');
    var btnExport = h('button', {class:'icon-btn accent', title:'Export JSON', onclick: exportJson}, '💾');
    var btnSettings = h('button', {class:'icon-btn' + (state.showSettings?' active':''), title:'Settings', onclick: function(e){ e.stopPropagation(); state.showSettings = !state.showSettings; state.showSavedFilters = false; render(); }}, '⚙');

    var settingsPanel = state.showSettings ? buildSettingsPanel() : null;
    var savedPanel = state.showSavedFilters ? buildSavedPanel() : null;

    var right = h('div', {class:'toolbar-right'}, [btnTheme, btnClear, btnExport, h('div', {style:{position:'relative'}}, [btnSettings, settingsPanel])]);

    var filterPanel = state.showFilters ? buildFilterPanel() : null;

    return h('div', {class:'tb', onclick: function(e){ e.stopPropagation(); }}, [brand, searchWrap, btnFilters, btnSaved, right, savedPanel, filterPanel]);
  }

  function preserveFocus(id){
    var el = document.getElementById(id);
    if (el && document.activeElement !== el) {
      var pos = el.selectionStart;
      el.focus();
      if (pos != null) try { el.setSelectionRange(pos, pos); } catch(e){}
    }
  }

  function buildSuggestPanel(){
    var hdr = h('div', {class:'suggest-hdr'}, '💡 Command Suggestions');
    var items = state.suggestions.map(function(s, i){
      var cmd = h('div', {class:'suggest-cmd'}, s.cmd);
      if (s.cmd.charAt(s.cmd.length-1) === ':') cmd.appendChild(h('span', {class:'ph'}, '<value>'));
      var children = [cmd, h('div', {class:'suggest-desc'}, s.desc)];
      if (s.example) children.push(h('div', {class:'suggest-ex'}, 'Example: ' + s.example));
      return h('div', {
        class: 'suggest-item' + (i === state.suggestSel ? ' sel' : ''),
        onmousedown: function(e){ e.preventDefault(); e.stopPropagation(); applySuggestion(s.cmd); },
        onmouseenter: function(){ state.suggestSel = i; }
      }, children);
    });
    return h('div', {class:'suggest'}, [hdr].concat(items));
  }

  function buildSettingsPanel(){
    var themeSel = h('select', {value: state.theme, onchange: function(e){ state.theme = e.target.value; localStorage.setItem('logtap-theme', state.theme); render(); }}, [
      h('option', {value:'android'}, 'Android Studio'),
      h('option', {value:'xcode'}, 'Xcode'),
      h('option', {value:'grafana'}, 'Grafana'),
      h('option', {value:'modern'}, 'Modern')
    ]);
    themeSel.value = state.theme;
    var viewSel = h('select', {value: state.viewMode, onchange: function(e){ state.viewMode = e.target.value; localStorage.setItem('logtap-view-mode', state.viewMode); render(); }}, [
      h('option', {value:'logcat'}, 'Logcat View'),
      h('option', {value:'table'}, 'Table View')
    ]);
    viewSel.value = state.viewMode;
    var densSel = h('select', {value: state.density, onchange: function(e){ state.density = e.target.value; localStorage.setItem('logtap-density', state.density); render(); }}, [
      h('option', {value:'compact'}, 'Compact'),
      h('option', {value:'comfortable'}, 'Comfortable'),
      h('option', {value:'spacious'}, 'Spacious')
    ]);
    densSel.value = state.density;
    return h('div', {class:'popover', style:{top:'calc(100% + 8px)', right:'0', minWidth:'220px'}}, [
      h('div', {class:'popover-section'}, 'Settings'),
      h('div', {class:'popover-section', style:{fontSize:'12px', textTransform:'none'}}, 'Theme'),
      themeSel,
      h('div', {class:'popover-section', style:{fontSize:'12px', textTransform:'none', marginTop:'8px'}}, 'View Mode'),
      viewSel,
      h('div', {class:'popover-section', style:{fontSize:'12px', textTransform:'none', marginTop:'8px'}}, 'Density'),
      densSel
    ]);
  }

  function buildSavedPanel(){
    var rows = state.savedFilters.length === 0
      ? [h('div', {class:'saved-empty'}, 'No saved filters yet')]
      : state.savedFilters.map(function(f){
          return h('div', {class:'saved-row'}, [
            h('div', {class:'body', onclick: function(){ loadSaved(f); }}, [
              h('div', {style:{fontSize:'13px', fontWeight:'500'}}, f.name),
              h('div', {class:'saved-q'}, f.query || '(no query)'),
              h('div', {class:'saved-ts'}, new Date(f.timestamp).toLocaleString())
            ]),
            h('button', {class:'del-btn', onclick: function(e){ e.stopPropagation(); deleteSaved(f.id); }}, '🗑')
          ]);
        });
    return h('div', {class:'popover', style:{top:'56px', right:'16px', minWidth:'380px', maxWidth:'500px'}}, [
      h('div', {style:{fontSize:'14px', fontWeight:'600', marginBottom:'12px'}}, 'Saved Filters'),
      h('div', {class:'saved-list'}, rows)
    ]);
  }

  function buildFilterPanel(){
    var f = state.filters;
    function sel(label, val, options, onchange){
      var s = h('select', {onchange: onchange}, options.map(function(o){
        return h('option', {value: o.v}, o.l);
      }));
      s.value = val;
      return h('div', {}, [h('label', {}, label), s]);
    }
    var typeBox = sel('Type', f.type, [
      {v:'all', l:'All'}, {v:'http', l:'HTTP'}, {v:'log', l:'Logs'}, {v:'ws', l:'WebSocket'}
    ], function(e){ f.type = e.target.value; render(); });
    var methodBox = sel('Method', f.method, [
      {v:'all', l:'All'}, {v:'GET', l:'GET'}, {v:'POST', l:'POST'}, {v:'PUT', l:'PUT'}, {v:'DELETE', l:'DELETE'}, {v:'PATCH', l:'PATCH'}
    ], function(e){ f.method = e.target.value; render(); });
    var statusBox = sel('Status', f.status, [
      {v:'all', l:'All'}, {v:'2xx', l:'2xx'}, {v:'4xx', l:'4xx'}, {v:'5xx', l:'5xx'}
    ], function(e){ f.status = e.target.value; render(); });
    var levelBox = sel('Level', f.level, [
      {v:'all', l:'All'}, {v:'VERBOSE', l:'VERBOSE'}, {v:'DEBUG', l:'DEBUG'}, {v:'INFO', l:'INFO'}, {v:'WARN', l:'WARN'}, {v:'ERROR', l:'ERROR'}, {v:'ASSERT', l:'ASSERT'}
    ], function(e){ f.level = e.target.value; render(); });

    var excTypeSel = h('select', {onchange: function(e){ state.excludeType = e.target.value; render(); }}, [
      h('option', {value:'package'}, 'Package/Tag'),
      h('option', {value:'message'}, 'Message')
    ]);
    excTypeSel.value = state.excludeType;

    var excInput = h('input', {
      id: 'exc-input', type:'text', placeholder: 'Exclude by ' + state.excludeType + '...',
      value: state.excludeInput,
      oninput: function(e){ state.excludeInput = e.target.value; },
      onkeydown: function(e){ if (e.key === 'Enter') addExclusion(); }
    });

    var excBtn = h('button', {onclick: addExclusion}, 'Add');
    var excRow = h('div', {class:'exclude-row'}, [excTypeSel, excInput, excBtn]);

    var chips = [];
    f.excludePackages.forEach(function(p){
      chips.push(h('span', {class:'chip'}, [
        '📦 ' + p,
        h('button', {onclick: function(){ f.excludePackages = f.excludePackages.filter(function(x){ return x !== p; }); render(); }}, '×')
      ]));
    });
    f.excludeMessages.forEach(function(m){
      chips.push(h('span', {class:'chip'}, [
        '💬 ' + m,
        h('button', {onclick: function(){ f.excludeMessages = f.excludeMessages.filter(function(x){ return x !== m; }); render(); }}, '×')
      ]));
    });

    var exclusionsBox = h('div', {style:{gridColumn:'span 2'}}, [
      h('label', {}, 'Exclude Filters'),
      excRow,
      h('div', {class:'chips'}, chips)
    ]);

    return h('div', {class:'filter-panel'}, [typeBox, methodBox, statusBox, levelBox, exclusionsBox]);
  }

  function addExclusion(){
    var v = state.excludeInput.trim();
    if (!v) return;
    if (state.excludeType === 'package') state.filters.excludePackages.push(v);
    else state.filters.excludeMessages.push(v);
    state.excludeInput = '';
    render();
  }

  function buildMetrics(){
    var m = computeMetrics();
    function mk(label, val, color){
      return h('div', {class:'metric'}, [
        h('div', {class:'l'}, label),
        h('div', {class:'v', style:{color: color}}, String(val))
      ]);
    }
    return h('div', {class:'metrics'}, [
      mk('Total', m.total, 'var(--text-primary)'),
      mk('Network', m.network, 'var(--info)'),
      mk('Logs', m.logs, 'var(--debug)'),
      mk('Errors', m.errors, 'var(--error)'),
      mk('Logs/min', m.perMin, 'var(--warn)'),
      mk('Avg Response', m.avgResponseTime + 'ms', 'var(--accent)'),
      mk('Filters', activeFilterCount(), 'var(--accent)')
    ]);
  }

  function activeFilterCount(){
    var f = state.filters; var c = 0;
    ['type','method','status','level'].forEach(function(k){ if (f[k] !== 'all') c++; });
    c += f.excludePackages.length + f.excludeMessages.length;
    if (state.searchQuery.trim()) c++;
    return c;
  }

  function buildMain(){
    var filtered = getFiltered();
    if (state.viewMode === 'logcat') {
      return h('div', {class:'main'}, [buildLogcatView(filtered)]);
    }
    var children = [buildTableView(filtered)];
    var sel = state.selectedId ? state.logs.filter(function(l){ return l.uid === state.selectedId; })[0] : null;
    if (sel) children.push(buildDetail(sel));
    return h('div', {class:'main'}, children);
  }

  // ---------- LOGCAT VIEW ----------
  function buildLogcatView(filtered){
    var container = h('div', {class:'logcat', id:'logcat-container', onscroll: handleScroll});
    filtered.forEach(function(log){ container.appendChild(renderLogcatRow(log)); });
    if (filtered.length === 0) container.appendChild(h('div', {style:{padding:'24px', textAlign:'center', color:'var(--text-secondary)'}}, 'No logs match current filters'));
    setTimeout(function(){
      if (state.autoScroll) container.scrollTop = container.scrollHeight;
    }, 0);
    return container;
  }

  function renderLogcatRow(log){
    var time = fmtTime(log.ts);
    var pidStr = pidTid(log);
    var lvl = levelChar(log.level || 'I');
    var clsLvl = 'lvl-' + lvl;
    var tagPart = '';
    var msgPart = '';
    if (log.type === 'log') {
      tagPart = log.tag || '';
      msgPart = log.message || '';
    } else if (log.type === 'http') {
      tagPart = 'HTTP/' + (log.method || '');
      var statusStr = log.status != null ? log.status : '...';
      var dur = log.duration != null ? (log.duration + 'ms') : '';
      msgPart = (log.method || '') + ' ' + statusStr + ' ' + (log.url || '') + (dur ? ' (' + dur + ')' : '');
    } else if (log.type === 'ws') {
      tagPart = 'WS';
      msgPart = '[' + (log.direction || '') + '] ' + (log.message || '');
    }

    var row = h('div', {class:'logcat-row', oncontextmenu: function(e){ e.preventDefault(); e.stopPropagation(); openContextMenu(e, log); }},
      [
        h('span', {class:'logcat-time'}, time + '  '),
        h('span', {class:'logcat-tid', title:'pid-tid'}, pidStr + '  '),
        h('span', {class: clsLvl}, lvl + ' '),
        h('span', {class:'logcat-tag ' + clsLvl}, tagPart + ': '),
        h('span', {class: log.type === 'log' ? clsLvl : ''}, msgPart)
      ]
    );

    // Inline net details for HTTP
    if (log.type === 'http') {
      if (log.requestHeaders) {
        var keys = Object.keys(log.requestHeaders);
        keys.forEach(function(k){
          var vals = log.requestHeaders[k];
          (vals || []).forEach(function(v){
            row.appendChild(h('div', {class:'logcat-net-line'}, [h('span',{class:'k'}, k + ': '), h('span',{}, v)]));
          });
        });
      }
      if (log.requestBody) {
        row.appendChild(h('div', {class:'logcat-net-line req'}, '> ' + log.requestBody));
      }
      if (log.responseBody) {
        var cls = (log.status >= 400 ? 'res-err' : 'res-ok');
        row.appendChild(h('div', {class:'logcat-net-line ' + cls}, '< ' + log.responseBody));
      }
      if (log.error) {
        row.appendChild(h('div', {class:'logcat-net-line res-err'}, 'ERROR: ' + log.error));
      }
    } else if (log.type === 'ws' && log.responseBody) {
      row.appendChild(h('div', {class:'logcat-net-line'}, '  ' + log.responseBody));
    }
    return row;
  }

  // ---------- TABLE VIEW ----------
  function buildTableView(filtered){
    var widths = state.columnWidths;
    var gridTemplate = widths.map(function(w){ return w + 'px'; }).join(' ');
    var headers = ['ID', 'Time', 'PID/TID', 'Type', 'Tag/Method', 'Level/Status', 'Size', 'Message/URL', 'Response'];
    var headEl = h('div', {class:'tbl-head', style:{gridTemplateColumns: gridTemplate}},
      headers.map(function(label, i){
        var col = h('div', {class:'col'}, [h('span', {}, label)]);
        if (i < widths.length - 1) {
          var resize = h('div', {class:'col-resize', onmousedown: function(e){ startColResize(i, e); }});
          col.appendChild(resize);
        }
        return col;
      })
    );
    var bodyEl = h('div', {class:'tbl-body'});
    filtered.forEach(function(log){ bodyEl.appendChild(renderTableRow(log, gridTemplate)); });
    if (filtered.length === 0) bodyEl.appendChild(h('div', {style:{padding:'24px', textAlign:'center', color:'var(--text-secondary)'}}, 'No logs match current filters'));
    var wrap = h('div', {class:'tbl-wrap', id:'tbl-wrap', onscroll: handleScroll}, [headEl, bodyEl]);
    setTimeout(function(){
      if (state.autoScroll) wrap.scrollTop = wrap.scrollHeight;
    }, 0);
    return wrap;
  }

  function renderTableRow(log, gridTemplate){
    var densityCls = state.density === 'compact' ? ' compact' : (state.density === 'spacious' ? ' spacious' : '');
    var selectedCls = log.uid === state.selectedId ? ' selected' : '';
    var typeCls = 'type-' + log.type;
    var typeLabel = log.type === 'http' ? 'HTTP' : log.type === 'ws' ? 'WS' : 'LOG';
    var size = log.responseBody ? log.responseBody.length : (log.requestBody ? log.requestBody.length : '');
    var tagOrMethod = log.type === 'http' ? log.method : log.tag;
    var levelOrStatus;
    if (log.type === 'http' && log.status != null) {
      levelOrStatus = h('span', {class:'status-pill ' + statusClass(log.status)}, String(log.status));
    } else {
      levelOrStatus = h('span', {class: 'lvl-' + levelChar(log.level || '')}, log.level || (log.type === 'http' ? '...' : ''));
    }

    var msgUrl;
    if (log.type === 'http') {
      var children = [h('div', {class:'url-cell'}, log.url || '')];
      if (log.requestBody) children.push(h('div', {class:'url-body'}, '↑ ' + log.requestBody));
      msgUrl = h('div', {}, children);
    } else if (log.type === 'ws') {
      msgUrl = h('div', {class:'url-cell'}, '[' + (log.direction || '') + '] ' + (log.message || ''));
    } else {
      msgUrl = h('div', {}, log.message || '');
    }

    var resp;
    if (log.type === 'http' && log.responseBody) {
      var cls = (log.status >= 400 ? 'resp-err' : 'resp-ok');
      resp = h('div', {class:'resp-cell ' + cls}, log.responseBody);
    } else if (log.type === 'ws' && log.responseBody) {
      resp = h('div', {class:'resp-cell'}, log.responseBody);
    } else {
      resp = h('div', {class:'muted'}, '');
    }

    var row = h('div', {
      class: 'tbl-row' + densityCls + selectedCls,
      style: {gridTemplateColumns: gridTemplate},
      onclick: function(){ state.selectedId = log.uid; render(); },
      ondblclick: function(){
        state.expandedIds[log.uid] = !state.expandedIds[log.uid];
        render();
      },
      oncontextmenu: function(e){ e.preventDefault(); e.stopPropagation(); openContextMenu(e, log); }
    }, [
      h('div', {class:'tbl-cell'}, h('span', {class:'tt-id'}, '#' + (log.serverId || ''))),
      h('div', {class:'tbl-cell'}, h('span', {class:'tt-time'}, fmtTime(log.ts))),
      h('div', {class:'tbl-cell'}, h('span', {class:'tt-time', title:'pid-tid'}, pidTid(log) || '—')),
      h('div', {class:'tbl-cell'}, h('span', {class:'type-pill ' + typeCls}, typeLabel)),
      h('div', {class:'tbl-cell'}, h('span', {class: log.type === 'http' ? 'method-tag' : ('lvl-' + levelChar(log.level||''))}, tagOrMethod || '')),
      h('div', {class:'tbl-cell'}, levelOrStatus),
      h('div', {class:'tbl-cell muted'}, String(size)),
      h('div', {class:'tbl-cell'}, msgUrl),
      h('div', {class:'tbl-cell'}, resp)
    ]);

    if (state.expandedIds[log.uid]) {
      row.appendChild(buildExpandArea(log));
    }
    return row;
  }

  function buildExpandArea(log){
    var sections = [];
    if (log.requestHeaders) {
      sections.push(h('div', {class:'exp-section'}, [
        h('div', {class:'exp-label'}, 'Request Headers'),
        renderHeaders(log.requestHeaders)
      ]));
    }
    if (log.requestBody) {
      sections.push(h('div', {class:'exp-section'}, [
        h('div', {class:'exp-label'}, 'Request Body'),
        h('div', {class:'exp-content'}, log.requestBody)
      ]));
    }
    if (log.responseHeaders) {
      sections.push(h('div', {class:'exp-section'}, [
        h('div', {class:'exp-label'}, 'Response Headers'),
        renderHeaders(log.responseHeaders)
      ]));
    }
    if (log.responseBody) {
      sections.push(h('div', {class:'exp-section'}, [
        h('div', {class:'exp-label'}, 'Response Body'),
        h('div', {class:'exp-content'}, log.responseBody)
      ]));
    }
    if (log.error) {
      sections.push(h('div', {class:'exp-section'}, [
        h('div', {class:'exp-label'}, 'Error'),
        h('div', {class:'exp-content'}, log.error)
      ]));
    }
    if (sections.length === 0) {
      sections.push(h('div', {class:'muted'}, 'No additional details'));
    }
    return h('div', {class:'expand-area'}, sections);
  }

  function renderHeaders(headers){
    var grid = h('div', {class:'headers-grid'});
    Object.keys(headers).forEach(function(k){
      var vals = headers[k] || [];
      vals.forEach(function(v){
        grid.appendChild(h('div', {class:'hk'}, k));
        grid.appendChild(h('div', {class:'hv'}, v));
      });
    });
    return grid;
  }

  // ---------- DETAIL PANEL ----------
  function buildDetail(log){
    var tabs = ['overview'];
    if (log.type === 'http') { tabs.push('request'); tabs.push('response'); }
    if (!log._detailTab) log._detailTab = 'overview';

    var tabBtns = tabs.map(function(t){
      return h('button', {class:'detail-tab' + (log._detailTab === t ? ' active' : ''), onclick: function(){ log._detailTab = t; render(); }}, t.charAt(0).toUpperCase() + t.substring(1));
    });

    var bodyEl;
    if (log._detailTab === 'overview') {
      var rows = [];
      rows.push(['Time', new Date(log.ts).toLocaleString()]);
      rows.push(['Type', log.type.toUpperCase()]);
      if (log.type === 'http') {
        rows.push(['Method', log.method || '']);
        rows.push(['URL', log.url || '']);
        if (log.status != null) rows.push(['Status', String(log.status)]);
        if (log.duration != null) rows.push(['Duration', log.duration + 'ms']);
      } else {
        rows.push(['Tag', log.tag || '']);
        rows.push(['Level', log.level || '']);
      }
      rows.push(['Thread', log.thread || '']);
      bodyEl = h('div', {}, rows.map(function(r){
        return h('div', {style:{marginBottom:'12px'}}, [
          h('div', {style:{fontSize:'11px', color:'var(--text-secondary)', textTransform:'uppercase', marginBottom:'4px'}}, r[0]),
          h('div', {style:{fontSize:'13px', wordBreak:'break-all'}}, r[1])
        ]);
      }).concat([
        h('div', {style:{marginTop:'12px'}}, [
          h('div', {style:{fontSize:'11px', color:'var(--text-secondary)', textTransform:'uppercase', marginBottom:'4px'}}, 'Message'),
          h('div', {class:'exp-content'}, log.message || '')
        ])
      ]));
    } else if (log._detailTab === 'request') {
      bodyEl = h('div', {}, [
        log.requestHeaders ? h('div', {class:'exp-section'}, [h('div', {class:'exp-label'}, 'Headers'), renderHeaders(log.requestHeaders)]) : h('div', {class:'muted'}, 'No request headers'),
        log.requestBody ? h('div', {class:'exp-section'}, [h('div', {class:'exp-label'}, 'Body'), h('div', {class:'exp-content'}, log.requestBody)]) : h('div', {class:'muted'}, 'No request body')
      ]);
    } else if (log._detailTab === 'response') {
      bodyEl = h('div', {}, [
        log.responseHeaders ? h('div', {class:'exp-section'}, [h('div', {class:'exp-label'}, 'Headers'), renderHeaders(log.responseHeaders)]) : h('div', {class:'muted'}, 'No response headers'),
        log.responseBody ? h('div', {class:'exp-section'}, [h('div', {class:'exp-label'}, 'Body'), h('div', {class:'exp-content'}, log.responseBody)]) : h('div', {class:'muted'}, 'No response body')
      ]);
    }

    return h('div', {class:'detail'}, [
      h('div', {class:'detail-hdr'}, [
        h('div', {class:'detail-title'}, log.type === 'http' ? ((log.method||'') + ' ' + (log.url||'')) : (log.tag || log.message || '')),
        h('button', {class:'icon-btn', title:'Close', onclick: function(){ state.selectedId = null; render(); }}, '✕')
      ]),
      h('div', {class:'detail-tabs'}, tabBtns),
      h('div', {class:'detail-body'}, bodyEl)
    ]);
  }

  // ---------- COLUMN RESIZE ----------
  var resizing = null;
  function startColResize(i, e){
    e.preventDefault(); e.stopPropagation();
    resizing = { i: i, startX: e.clientX, startW: state.columnWidths[i] };
    document.addEventListener('mousemove', onColResize);
    document.addEventListener('mouseup', stopColResize);
  }
  function onColResize(e){
    if (!resizing) return;
    var diff = e.clientX - resizing.startX;
    var nw = Math.max(50, resizing.startW + diff);
    state.columnWidths[resizing.i] = nw;
    // Update grid template directly without full render
    var head = ${'$'}('.tbl-head');
    var rows = ${'$'}${'$'}('.tbl-row');
    var template = state.columnWidths.map(function(w){ return w + 'px'; }).join(' ');
    if (head) head.style.gridTemplateColumns = template;
    rows.forEach(function(r){ r.style.gridTemplateColumns = template; });
  }
  function stopColResize(){
    if (!resizing) return;
    localStorage.setItem('logtap-cols', JSON.stringify(state.columnWidths));
    resizing = null;
    document.removeEventListener('mousemove', onColResize);
    document.removeEventListener('mouseup', stopColResize);
  }

  // ---------- AUTO SCROLL ----------
  function handleScroll(e){
    var t = e.target;
    var atBottom = t.scrollHeight - t.scrollTop - t.clientHeight < 50;
    if (!atBottom && state.autoScroll) state.autoScroll = false;
    else if (atBottom && !state.autoScroll) state.autoScroll = true;
    renderStatusBar();
  }

  function renderStatusBar(){
    var sb = ${'$'}('#status-bar');
    if (sb && sb.parentNode) {
      sb.parentNode.replaceChild(buildStatusBar(), sb);
    }
  }

  // ---------- CONTEXT MENU OPEN ----------
  function openContextMenu(e, log){
    var items = [];
    if (log.type === 'http') {
      items.push({label:'Copy URL', action: function(){ copyText(log.url || ''); }});
      items.push({label:'Copy as cURL', action: function(){ copyText(asCurl(log)); }});
      items.push({sep: true});
    }
    items.push({label:'Copy Message', action: function(){ copyText(log.message || ''); }});
    items.push({label:'Copy as JSON', action: function(){ copyText(JSON.stringify(log, null, 2)); }});
    items.push({sep: true});
    if (log.tag) items.push({label:'Exclude tag: ' + log.tag, action: function(){ state.filters.excludePackages.push(log.tag); render(); }});
    if (log.message) items.push({label:'Exclude message text', action: function(){ state.filters.excludeMessages.push(log.message.substring(0, 40)); render(); }});
    showContextMenu(e.clientX, e.clientY, items);
  }
  function buildContextMenu(){
    var cm = state.contextMenu;
    var children = cm.items.map(function(it){
      if (it.sep) return h('div', {class:'ctx-sep'});
      return h('div', {class:'ctx-item', onclick: function(e){ e.stopPropagation(); it.action(); hideContextMenu(); }}, it.label);
    });
    return h('div', {class:'ctx-menu', style:{left: cm.x + 'px', top: cm.y + 'px'}}, children);
  }

  // ---------- STATUS BAR ----------
  function buildStatusBar(){
    var filtered = getFiltered();
    var connDot = h('span', {class: 'conn-dot conn-' + state.connectionStatus});
    var connText;
    if (state.connectionStatus === 'connecting') connText = 'Connecting...';
    else if (state.connectionStatus === 'connected') connText = 'Connected';
    else connText = 'Disconnected';
    var asLabel = state.autoScroll ? 'Auto-scroll: ON' : 'Auto-scroll: OFF (scroll to bottom to resume)';
    return h('div', {class:'status-bar', id:'status-bar'}, [
      h('span', {}, 'Theme: ' + state.theme),
      h('span', {}, 'View: ' + state.viewMode),
      h('span', {}, 'Showing ' + filtered.length + ' of ' + state.logs.length + ' logs'),
      h('div', {class:'right'}, [
        h('span', {}, asLabel),
        h('span', {style:{display:'flex', alignItems:'center', gap:'6px'}}, [connDot, connText])
      ])
    ]);
  }

  // ---------- ACTIONS ----------
  function clearLogs(){
    if (!confirm('Clear all logs?')) return;
    fetch('/api/clear', {method: 'POST'}).catch(function(){});
    state.logs = [];
    state.httpPending = {};
    state.selectedId = null;
    state.expandedIds = {};
    render();
  }
  function exportJson(){
    var data = JSON.stringify(getFiltered(), null, 2);
    var blob = new Blob([data], {type:'application/json'});
    var url = URL.createObjectURL(blob);
    var a = document.createElement('a');
    a.href = url;
    a.download = 'logtap-export-' + new Date().toISOString().slice(0,19).replace(/:/g,'-') + '.json';
    document.body.appendChild(a); a.click(); document.body.removeChild(a);
    setTimeout(function(){ URL.revokeObjectURL(url); }, 1000);
  }

  // ---------- DATA LOADING ----------
  function loadAppInfo(){
    fetch('/api/info').then(function(r){ return r.json(); }).then(function(d){
      state.appInfo = d;
      refreshLiveAreas();
    }).catch(function(){});
  }
  function loadBacklog(){
    fetch('/api/logs?limit=500').then(function(r){ return r.json(); }).then(function(arr){
      arr.forEach(ingest);
      refreshLiveAreas();
    }).catch(function(){});
  }
  var ws = null;
  function connectWs(){
    state.connectionStatus = 'connecting';
    refreshLiveAreas();
    try {
      var loc = window.location;
      var proto = loc.protocol === 'https:' ? 'wss:' : 'ws:';
      ws = new WebSocket(proto + '//' + loc.host + '/ws');
      ws.onopen = function(){ state.connectionStatus = 'connected'; refreshLiveAreas(); };
      ws.onclose = function(){
        state.connectionStatus = 'disconnected';
        refreshLiveAreas();
        setTimeout(connectWs, 2000);
      };
      ws.onerror = function(){ /* onclose will fire */ };
      ws.onmessage = function(e){
        try {
          var ev = JSON.parse(e.data);
          ingest(ev);
          renderThrottled();
        } catch(err) { console.error('Bad message', err); }
      };
    } catch(e) {
      state.connectionStatus = 'disconnected';
      refreshLiveAreas();
      setTimeout(connectWs, 3000);
    }
  }

  // Throttle re-renders during high-frequency log streams.
  // IMPORTANT: never call full render() here — it would re-create the search input
  // and steal focus from the user mid-typing. Update only data-driven sections.
  var rerenderPending = false;
  function renderThrottled(){
    if (rerenderPending) return;
    rerenderPending = true;
    requestAnimationFrame(function(){
      rerenderPending = false;
      refreshLiveAreas();
    });
  }
  function refreshLiveAreas(){
    refreshSavedBadge();
    replaceSection('.metrics', buildMetrics);
    replaceSection('.main', buildMain);
    replaceSection('#status-bar', buildStatusBar);
    // App info banner — only update brand text/icon if it changed
    var bn = ${'$'}('.brand-name');
    var bm = ${'$'}('.brand-meta');
    var bi = ${'$'}('.brand-icon');
    if (state.appInfo && bn && bm && bi) {
      bn.textContent = state.appInfo.appName || 'LogTap';
      bm.textContent = 'v' + state.appInfo.versionName + ' (build ' + state.appInfo.versionCode + ')';
      if (state.appInfo.appIconBase64 && !bi.querySelector('img')) {
        bi.innerHTML = '<img src="data:image/png;base64,' + state.appInfo.appIconBase64 + '"/>';
      }
    }
  }

  // ---------- KEYBOARD SHORTCUTS ----------
  document.addEventListener('keydown', function(e){
    if ((e.ctrlKey || e.metaKey) && e.key === 'k') {
      e.preventDefault();
      var inp = ${'$'}('#search-input');
      if (inp) inp.focus();
    }
    if (e.key === 'Escape') {
      if (state.contextMenu) { hideContextMenu(); return; }
      if (state.showSettings) { state.showSettings = false; render(); return; }
      if (state.showSavedFilters) { state.showSavedFilters = false; render(); return; }
      if (state.showFilters) { state.showFilters = false; render(); return; }
      if (state.selectedId) { state.selectedId = null; render(); return; }
    }
  });

  // ---------- BOOT ----------
  applyTheme();
  render();
  loadAppInfo();
  loadBacklog();
  connectWs();
  // Periodic light refresh for time-sensitive metrics (logs/min) — never touches the input.
  setInterval(function(){
    replaceSection('.metrics', buildMetrics);
    replaceSection('#status-bar', buildStatusBar);
  }, 5000);
})();
""".trimIndent()

    val aboutHtml = """
          <!doctype html>
          <html><head><meta charset="utf-8"/><title>About LogTap</title><link rel="stylesheet" href="/app.css"/></head>
          <body><main style="padding:16px; max-width:800px">
          <h2>About</h2>
          <p>LogTap exposes your app's HTTP and WebSocket traffic for local inspection. Built for DEBUG builds.</p>
          <ul>
            <li>HTTP via OkHttp Interceptor</li>
            <li>WebSocket via wrapper & listener proxy</li>
            <li>Web UI at <code>/</code>, JSON at <code>/api/logs</code>, WS stream at <code>/ws</code></li>
          </ul>
          <p><b>Security:</b> this server binds to the device's local network interface. For safety, only enable in debug builds or protect behind a dev network/VPN.</p>
          </main></body></html>
    """.trimIndent()
}
