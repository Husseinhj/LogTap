package com.github.husseinhj.logtap

internal object Resources {
    val indexHtml = """
<!doctype html>
<html>
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>LogTap</title>
    <link rel="stylesheet" href="/app.css" />
  </head>
  <body class="md-body">
    <header class="topbar md-surface md-elev-2">
          <a class="gh" href="https://github.com/Husseinhj/LogTap" target="_blank" rel="noopener" title="Open repository" aria-label="Open GitHub repository">
        <svg viewBox="0 0 16 16" width="22" height="22" aria-hidden="true"><path fill="currentColor" d="M8 0C3.58 0 0 3.58 0 8c0 3.54 2.29 6.53 5.47 7.59.4.07.55-.17.55-.38 0-.19-.01-.82-.01-1.49-2.01.37-2.53-.49-2.69-.94-.09-.23-.48-.94-.82-1.13-.28-.15-.68-.52-.01-.53.63-.01 1.08.58 1.23.82.72 1.21 1.87.87 2.33.66.07-.52.28-.87.51-1.07-1.78-.2-3.64-.89-3.64-3.95 0-.87.31-1.59.82-2.15-.08-.2-.36-1.02.08-2.12 0 0 .67-.21 2.2.82.64-.18 1.32-.27 2-.27.68 0 1.36.09 2 .27 1.53-1.04 2.2-.82 2.2-.82.44 1.1.16 1.92.08 2.12.51.56.82 1.27.82 2.15 0 3.07-1.87 3.75-3.65 3.95.29.25.54.73.54 1.48 0 1.07-.01 1.93-.01 2.2 0 .21.15.46.55.38A8.013 8.013 0 0 0 16 8c0-4.42-3.58-8-8-8Z"/></svg>
      </a>
      <div class="brand md-title">LogTap</div>
      <div class="controls">
        <div class="input-wrap md-field">
          <input id="search" type="search" class="md-input" placeholder="Search (url, method, headers, body)…  ⌘/Ctrl + K" />
          <kbd class="key">K</kbd>
        </div>

        <button id="filtersBtn" class="md-btn md-tonal" title="Show filters">Filters</button>

        <div id="wsStatus" class="status chip" title="WebSocket status">● Disconnected</div>
        <div class="split"></div>

        <div class="menu">
          <button id="exportBtn" class="md-btn" title="Export options">Export ▾</button>
          <div id="exportMenu" class="popover hidden" role="menu" aria-hidden="true">
            <button id="exportJson" class="md-btn block" role="menuitem">Export JSON</button>
            <button id="exportHtml" class="md-btn block" role="menuitem">Export Report</button>
          </div>
        </div>

        <button id="clearBtn" class="md-btn md-tonal" title="Clear logs">Clear</button>
        <button id="themeToggle" class="md-btn md-tonal" title="Toggle light/dark theme">Theme: Dark</button>

        <!-- Filters popover (compact) -->
        <div id="filtersPanel" class="popover hidden" role="dialog" aria-label="Filters">
          <div class="filters-grid">
            <label>View
              <select id="viewMode" class="md-select" title="View mode">
                <option value="mix">Mix (All)</option>
                <option value="network">Network only</option>
                <option value="log">Logger only</option>
              </select>
            </label>

            <label>Method
              <select id="methodFilter" class="md-select" title="Method filter">
                <option value="">All</option>
                <option>GET</option><option>POST</option><option>PUT</option>
                <option>PATCH</option><option>DELETE</option><option>WS</option>
              </select>
            </label>

            <label>Status class
              <select id="statusFilter" class="md-select" title="Status filter (HTTP)">
                <option value="">Any</option>
                <option value="2xx">2xx</option>
                <option value="3xx">3xx</option>
                <option value="4xx">4xx</option>
                <option value="5xx">5xx</option>
              </select>
            </label>

            <label>Codes
              <input id="statusCodeFilter" class="md-input" type="text" inputmode="numeric" pattern="[0-9xX,-,\s]*" placeholder="200, 2xx, 400-404" />
            </label>

            <label>Level
              <select id="levelFilter" class="md-select" title="Log level (Logger)">
                <option value="">Any Level</option>
                <option value="VERBOSE">Verbose</option>
                <option value="DEBUG">Debug</option>
                <option value="INFO">Info</option>
                <option value="WARN">Warn</option>
                <option value="ERROR">Error</option>
                <option value="ASSERT">Assert</option>
              </select>
            </label>

            <label class="chk md-switch"><input type="checkbox" id="jsonPretty"/><span>Pretty JSON</span></label>
            <label class="chk md-switch"><input type="checkbox" id="autoScroll" checked/><span>Auto‑scroll</span></label>
          </div>
        </div>
      </div>
    </header>

    <section class="stats md-surface md-elev-1">
      <div class="chip" id="chipTotal">Total: 0</div>
      <div class="chip" id="chipHttp">HTTP: 0</div>
      <div class="chip" id="chipWs">WS: 0</div>
      <div class="chip" id="chipLog">LOG: 0</div>
      <div class="chip" id="chipGet">GET: 0</div>
      <div class="chip" id="chipPost">POST: 0</div>
    </section>

    <main class="layout">
      <div class="table-wrap md-surface md-elev-1">
        <table id="logtbl" class="md-table">
          <thead>
            <tr>
              <th class="col-id">ID</th>
              <th class="col-time">Time</th>
              <th class="col-kind">Kind</th>
              <th class="col-tag">Tag</th>
              <th class="col-method">Method</th>
              <th class="col-status">Status</th>
              <th class="col-url">URL / Summary</th>
              <th class="col-actions">Actions</th>
            </tr>
          </thead>
          <tbody></tbody>
        </table>
        <div class="repo-link">
          <a href="https://github.com/Husseinhj/LogTap" target="_blank" rel="noopener">View source on GitHub — Husseinhj/LogTap</a>
        </div>
      </div>

      <aside id="drawer" class="drawer md-surface md-elev-2">
        <header class="drawer-head">
          <div>
            <div id="drawerTitle" class="drawer-title">Details</div>
            <div id="drawerSub" class="drawer-sub"></div>
          </div>
          <button id="drawerClose" class="icon-btn md-icon" title="Close (Esc)">×</button>
        </header>
        <nav class="tabs md-segmented">
          <button class="tab active" data-tab="overview" id="tabBtn-overview">Overview</button>
          <button class="tab" data-tab="request" id="tabBtn-request">Request</button>
          <button class="tab" data-tab="response" id="tabBtn-response">Response</button>
          <button class="tab" data-tab="headers" id="tabBtn-headers">Headers</button>
        </nav>
        <section class="tabpanes">
          <div class="tabpane active" id="tab-overview">
            <dl class="kv">
              <div><dt>ID</dt><dd id="ov-id"></dd></div>
              <div><dt>Time</dt><dd id="ov-time"></dd></div>
              <div><dt>Kind</dt><dd id="ov-kind"></dd></div>
              <div><dt>Direction</dt><dd id="ov-dir"></dd></div>
              <div id="row-method"><dt>Method</dt><dd id="ov-method"></dd></div>
              <div id="row-status"><dt>Status</dt><dd id="ov-status"></dd></div>
              <div id="row-url"><dt>URL</dt><dd id="ov-url"></dd></div>
              <div id="row-level" class="hidden"><dt>Level</dt><dd id="ov-level"></dd></div>
              <div id="row-tag" class="hidden"><dt>Tag</dt><dd id="ov-tag"></dd></div>
              <div class="full"><dt>Summary</dt><dd><div class="summary-row"><button id="ov-summary-copy" class="xs md-btn md-tonal" title="Copy Summary">Copy</button><pre class="code" id="ov-summary"></pre></div></dd></div>
              <div id="row-took"><dt>Took</dt><dd id="ov-took"></dd></div>
              <div><dt>Thread</dt><dd id="ov-thread"></dd></div>
              <div class="full" id="row-curl"><dt>cURL</dt><dd><div class="curl-row"><button id="ov-curl-copy" class="xs md-btn md-tonal" title="Copy cURL">Copy</button><pre class="code" id="ov-curl"></pre></div></dd></div>
            </dl>
          </div>
          <div class="tabpane" id="tab-request">
            <h4>Request Body</h4>
            <pre class="code json" id="req-body"></pre>
          </div>
          <div class="tabpane" id="tab-response">
            <h4>Response Body</h4>
            <pre class="code json" id="resp-body"></pre>
          </div>
          <div class="tabpane" id="tab-headers">
            <h4>Headers</h4>
            <div class="columns">
              <div>
                <h5>Request</h5>
                <pre class="code" id="req-headers"></pre>
              </div>
              <div>
                <h5>Response</h5>
                <pre class="code" id="resp-headers"></pre>
              </div>
            </div>
          </div>
        </section>
      </aside>
    </main>

    <script src="/app.js"></script>
  </body>
</html>
""".trimIndent()

    val appCss = """
:root[data-theme="light"]{
  /* Light palette */
  --md-primary:#6750A4; --md-on-primary:#fff; --md-primary-container:#eaddff; --md-on-primary-container:#21005d;
  --md-secondary:#625b71; --md-on-secondary:#000; --md-surface:#f7f8fa; --md-surface-2:#ffffff; --md-surface-3:#f2f4f8;
  --md-outline:#d0d7de; --md-muted:#4b5563; --md-text:#0b1320; --md-success:#16a34a; --md-warn:#d97706; --md-error:#dc2626;
  --code:#f6f8fa; --chip:#eef2f7; --row:#ffffff; --row-hover:#f5f7fb; --shadow:#0002;
}
:root[data-theme="dark"]{
  /* Dark palette */
  --md-primary:#6750A4; --md-on-primary:#fff; --md-primary-container:#eaddff; --md-on-primary-container:#21005d;
  --md-secondary:#625b71; --md-on-secondary:#fff; --md-surface:#0b0f14; --md-surface-2:#0e131a; --md-surface-3:#101720;
  --md-outline:#2c3440; --md-muted:#9aa4b2; --md-text:#e6edf3; --md-success:#22c55e; --md-warn:#fbbf24; --md-error:#ef4444;
  --code:#0d1117; --chip:#141b24; --row:#0f1620; --row-hover:#142030; --shadow:#0008;
}
*{box-sizing:border-box}
html,body{height:100%}
body.md-body{margin:0;background:linear-gradient(180deg, var(--md-surface) 0%, var(--md-surface) 60%, #0a0d12 100%);color:var(--md-text);font:14px system-ui,Segoe UI,Roboto,Helvetica,Arial,sans-serif}

/* Elevation & Surfaces */
.md-surface{background:var(--md-surface-2);border:1px solid var(--md-outline)}
.md-elev-1{box-shadow:0 2px 8px var(--shadow)}
.md-elev-2{box-shadow:0 6px 16px var(--shadow)}

.topbar{position:sticky;top:0;z-index:20;display:flex;gap:16px;align-items:center;padding:12px 16px;backdrop-filter:blur(10px)}
.brand.md-title{font-weight:800;letter-spacing:.3px}
.topbar .gh{display:inline-flex;align-items:center;justify-content:center;color:var(--md-text);opacity:.85;border:1px solid var(--md-outline);border-radius:10px;width:34px;height:34px;margin-left:8px;text-decoration:none}
.topbar .gh:hover{opacity:1;background:var(--md-surface-3)}
.repo-link{padding:10px 12px;text-align:center;color:var(--md-muted);font-size:13px}
.repo-link a{color:var(--md-text);text-decoration:none;border-bottom:1px dashed var(--md-outline)}
.repo-link a:hover{color:var(--md-primary);border-bottom-color:var(--md-primary)}
.controls{display:flex;gap:8px;align-items:center;margin-left:auto}
.controls{flex-wrap:wrap}
.split{width:1px;height:28px;background:var(--md-outline);margin:0 8px}

/* Compact toolbar & popovers */
.controls{position:relative; gap:6px}
.controls .md-btn.block{display:block; width:100%; text-align:left}
.menu{position:relative}
.popover{position:absolute; top:100%; margin-top:8px; right:0; background:var(--md-surface-2); border:1px solid var(--md-outline); border-radius:12px; box-shadow:0 8px 24px var(--shadow); padding:10px; z-index:50; min-width:220px}
.popover.hidden{display:none}
.filters-grid{display:grid; grid-template-columns:1fr 1fr; gap:10px}
@media (max-width: 640px){ .filters-grid{grid-template-columns:1fr} }

/* Inputs */
.md-field{position:relative}
.md-input, .md-select{background:var(--md-surface-3);color:var(--md-text);border:1px solid var(--md-outline);border-radius:12px;padding:10px 12px}
.md-input.narrow{min-width:140px}
.input-wrap .key{position:absolute;right:8px;top:50%;transform:translateY(-50%);opacity:.6;background:#0002;border:1px solid var(--md-outline);border-radius:6px;padding:0 6px;font:11px ui-monospace,Menlo,monospace}
.chk{display:flex;gap:6px;align-items:center;opacity:.9}

/* Buttons */
.md-btn{background:var(--md-primary);color:var(--md-on-primary);border:0;border-radius:12px;padding:8px 12px;cursor:pointer}
.md-btn.md-tonal{background:transparent;border:1px solid var(--md-outline);color:var(--md-text)}
button.xs{padding:4px 10px;border-radius:8px;font-size:12px}
.md-icon{width:28px;height:28px;border-radius:8px;background:transparent;border:1px solid var(--md-outline);color:var(--md-text);font-size:18px;line-height:1}

/* Status chips */
.status.chip{border:1px solid var(--md-outline);border-radius:999px;padding:4px 10px;font:12px ui-monospace,Menlo,monospace}
.status-on{color:var(--md-success);} .status-off{color:var(--md-error);}

/* Stats */
.stats{display:flex;gap:8px;flex-wrap:wrap;padding:10px 16px}
.chip{background:var(--chip);border:1px solid var(--md-outline);padding:6px 10px;border-radius:999px}

/* Layout */
.layout{display:flex;gap:12px;padding:12px;align-items:stretch}
.table-wrap{flex:1 1 auto;overflow:auto;max-height:calc(100vh - 160px);border-radius:14px;transition:width 260ms ease}
.table-wrap::-webkit-scrollbar{height:10px;width:10px}
.table-wrap::-webkit-scrollbar-thumb{background:var(--md-outline);border-radius:10px}
:root{--drawer-w:520px}

/* Table */
.md-table{width:100%;border-collapse:separate;border-spacing:0}
.md-table thead th{position:sticky;top:0;background:var(--md-surface-3);z-index:5;padding:12px;border-bottom:1px solid var(--md-outline);text-align:left}
.md-table tbody td{padding:12px;border-bottom:1px solid var(--md-outline);vertical-align:top}
.md-table tbody tr{background:var(--row);cursor:pointer}
.md-table tbody tr:hover{background:var(--row-hover)}
.md-table tbody tr.selected{outline:1px solid var(--md-primary); outline-offset:-1px}
.col-id{width:72px}.col-time{width:150px}.col-kind{width:100px}.col-tag{width:120px}.col-method{width:92px}.col-status{width:92px}.col-url{width:auto}.col-actions{width:170px}

/* Modes: mix/network/log */
body.mode-network .col-tag{display:none}
body.mode-log .col-method, body.mode-log .col-status, body.mode-log .col-actions{display:none}
/* In log mode, we still use URL/Summary col but hide empty URL line */
body.mode-log .col-url .url{display:none}
/* Optional: in network-only, keep kind visible to distinguish HTTP/WS */
/* No change needed for mix */

/* Badges / pills */
.kind-HTTP{color:#8ab4ff}.kind-WEBSOCKET{color:#7af59b}
.kind-LOG{color:#eab308}
.dir-REQUEST,.dir-OUTBOUND{color:var(--md-warn)}.dir-RESPONSE,.dir-INBOUND{color:var(--md-success)}.dir-ERROR{color:var(--md-error)}.dir-STATE{color:#9bb}
.status-2xx{color:var(--md-success)}.status-3xx{color:#fbbf24}.status-4xx{color:#fca5a5}.status-5xx{color:#fb7185}
.col-method,.col-status{background:transparent;border:none;border-radius:0;text-align:left}


/* Logger level colors */
.md-table tbody tr.level-VERBOSE .col-kind{color:#9bb}
.md-table tbody tr.level-DEBUG   .col-kind{color:#8ab4ff}
.md-table tbody tr.level-INFO    .col-kind{color:#7af59b}
.md-table tbody tr.level-WARN    .col-kind{color:#fbbf24}
.md-table tbody tr.level-ERROR   .col-kind{color:#fb7185}
.md-table tbody tr.level-ASSERT  .col-kind{color:#ff7dd1}

/* WS direction icons */
.ws-ico{margin-left:6px; font:12px ui-monospace,Menlo,monospace; vertical-align:middle}
.ws-send{color:var(--md-warn)}
.ws-recv{color:var(--md-success)}

/* Subtle left accent bar by level */
.md-table tbody tr.level-VERBOSE{box-shadow: inset 3px 0 0 #6b7280}
.md-table tbody tr.level-DEBUG  {box-shadow: inset 3px 0 0 #60a5fa}
.md-table tbody tr.level-INFO   {box-shadow: inset 3px 0 0 #22c55e}
.md-table tbody tr.level-WARN   {box-shadow: inset 3px 0 0 #f59e0b}
.md-table tbody tr.level-ERROR  {box-shadow: inset 3px 0 0 #ef4444}
.md-table tbody tr.level-ASSERT {box-shadow: inset 3px 0 0 #d946ef}

.drawer{border:1px solid transparent;border-radius:14px;height:calc(100vh - 160px);overflow:auto;flex:0 0 0;width:0;opacity:0;pointer-events:none;transition:width 260ms ease, flex-basis 260ms ease, opacity 200ms ease, border-color 200ms ease}
body.drawer-open .drawer{flex-basis:var(--drawer-w);width:var(--drawer-w);opacity:1;pointer-events:auto;border-color:var(--md-outline)}
.drawer-head{display:flex;justify-content:space-between;align-items:center;padding:14px;border-bottom:1px solid var(--md-outline)}
.drawer-title{font-weight:700}.drawer-sub{color:var(--md-muted);font-size:12px;margin-top:4px}

/* Segmented tabs */
.md-segmented{display:flex;gap:6px;padding:10px 12px;border-bottom:1px solid var(--md-outline)}
.md-segmented .tab{background:transparent;color:var(--md-text);border:1px solid var(--md-outline);border-radius:999px;padding:6px 12px}
.md-segmented .tab.active{background:var(--md-surface-3)}

/* Panes */
.tabpanes{padding:12px}
.tabpane{display:none}
.tabpane.active{display:block}
.kv{display:grid;grid-template-columns:140px 1fr;gap:12px 16px}
.kv dt{color:var(--md-muted)} .kv dd{margin:0}
.kv .full{grid-column:1 / -1}
.columns{display:grid;grid-template-columns:1fr 1fr;gap:12px}

/* Code blocks */
.code{background:var(--code);border:1px solid var(--md-outline);border-radius:12px;padding:12px;overflow:auto;max-height:20vh;white-space:pre-wrap;word-break:break-word}
.code.json .k{color:#7aa2f7}.code.json .s{color:#a6e3a1}.code.json .n{color:#f2cdcd}.code.json .b{color:#f9e2af}.code.json .l{color:#f28fad}.code.json .null{color:#cdd6f4;opacity:.8}
#ov-summary{white-space:pre-wrap; word-break:break-word; width:100%; max-height:50vh; overflow:auto}
.curl-row{display:flex; gap:8px; align-items:flex-start; width:100%}
.curl-row .code{flex:1; min-height:160px}
#ov-curl{white-space:pre-wrap; word-break:break-all; overflow:auto; max-height:70vh; width:100%}

/* Helpers */
.muted{color:var(--md-muted)}
.badge{border:1px solid var(--md-outline);border-radius:6px;padding:2px 6px;background:#0002;font:12px ui-monospace,Menlo,monospace}
.action-row{display:flex;gap:6px;flex-wrap:wrap}
.hidden{display:none !important}

/* ===== Responsive ===== */
@media (max-width: 1200px){
  .layout{grid-template-columns:1fr;gap:10px;padding:10px}
  .drawer{height:auto}
  .md-input.narrow{min-width:180px}
}

@media (max-width: 1024px){
  .md-table thead th,.md-table tbody td{padding:10px}
  .col-actions{width:140px}
}

@media (max-width: 900px){
  /* hide low-signal columns to reduce clutter */
  #logtbl thead .col-id, #logtbl tbody .col-id{display:none}
  #logtbl thead .col-kind, #logtbl tbody .col-kind{display:none}
  .col-time{width:96px}
  .col-method{width:80px}
  .col-status{width:80px}
  .col-actions{width:120px}
  .table-wrap{max-height:calc(100vh - 200px)}
  .kv{grid-template-columns:120px 1fr}
}

@media (max-width: 768px){
  .topbar{padding:10px}
  .controls > *{flex:1 1 100%}
  .input-wrap{width:100%}
  .md-input, .md-select{width:100%}
  .md-input.narrow{min-width:unset}
  .stats{padding:8px 10px}
  .layout{padding:8px}
  .table-wrap{border-radius:10px}
  .kv{grid-template-columns:1fr}
  .kv .full{grid-column:1 / -1}
  .columns{grid-template-columns:1fr}
  #ov-curl{max-height:50vh}
  #ov-summary{max-height:40vh}
}

@media (max-width: 600px){
  /* make drawer behave like a full-screen sheet */
  .drawer{position:fixed;inset:56px 0 0 0;z-index:30;border-radius:0;max-height:none;height:auto}
  .drawer-head{position:sticky;top:0;background:var(--md-surface-2);z-index:5}
  .md-segmented{position:sticky;top:48px;background:var(--md-surface-2);z-index:4}
  .table-wrap{max-height:calc(100vh - 240px)}
  .md-table thead th,.md-table tbody td{padding:9px}
  .col-actions{display:none}
}

@media (max-width: 420px){
  .brand{display:none}
  .status.chip{font-size:11px;padding:3px 8px}
  .chip{font-size:12px}
  .md-btn{padding:7px 10px}
  .md-icon{width:26px;height:26px}
}
""".trimIndent()

    val appJs = """
        // ========================= LogTap Viewer (fixed runtime JS) =========================
        // Changes:
        // - Fixed cURL builder (no nested template literal confusions)
        // - Removed fragile ${'$'}{JSON.stringify(...)} nesting inside Kotlin
        // - Stronger error logging so JS errors don't silently stop rendering
        
        // ---- DOM ----
        const tbody = document.querySelector('#logtbl tbody');
        const search = document.querySelector('#search');
        const autoScroll = document.querySelector('#autoScroll');
        const clearBtn = document.querySelector('#clearBtn');
        const methodFilter = document.querySelector('#methodFilter');
        const viewMode = document.querySelector('#viewMode');
        const bodyEl = document.body;
        const statusFilter = document.querySelector('#statusFilter');
        const statusCodeFilter = document.querySelector('#statusCodeFilter');
        const wsStatus = document.querySelector('#wsStatus');
        const levelFilter = document.querySelector('#levelFilter');
        const exportJsonBtn = document.querySelector('#exportJson');
        const exportHtmlBtn = document.querySelector('#exportHtml');
        const filtersBtn = document.querySelector('#filtersBtn');
        const filtersPanel = document.querySelector('#filtersPanel');
        const exportBtn = document.querySelector('#exportBtn');
        const exportMenu = document.querySelector('#exportMenu');
        const themeToggle = document.querySelector('#themeToggle');
        const jsonPretty = document.querySelector('#jsonPretty');
        
        const drawer = document.querySelector('#drawer');
        const drawerClose = document.querySelector('#drawerClose');
        const tabs = Array.from(document.querySelectorAll('.tab'));
        // wire tab clicks
        tabs.forEach(b => b.addEventListener('click', (e) => {
          e.preventDefault();
          e.stopPropagation();
          const name = b.dataset.tab;
          if (!name) return;
          activateTab(name);
        }));
        const curlCopyBtn = document.querySelector('#ov-curl-copy');
        const summaryCopyBtn = document.querySelector('#ov-summary-copy');
        
        // ---- State ----
        let rows = [];
        let filtered = [];
        let filterText = '';
        let selectedIdx = -1;
        let currentEv = null;
        
        // ---- Theme ----
        function applyTheme(t){
          const theme = (t === 'light' || t === 'dark') ? t : 'dark';
          document.documentElement.setAttribute('data-theme', theme);
          if (themeToggle) themeToggle.textContent = 'Theme: ' + (theme.charAt(0).toUpperCase()+theme.slice(1));
        }
        function initTheme(){
          let t = localStorage.getItem('logtap:theme');
          if (!t) t = (window.matchMedia && window.matchMedia('(prefers-color-scheme: light)').matches) ? 'light' : 'dark';
          applyTheme(t);
        }

        // ---- Utils ----
        function escapeHtml(s){ return String(s).replace(/[&<>"']/g, c=>({"&":"&amp;","<":"&lt;",">":"&gt;","\"":"&quot;","'":"&#39;"}[c])); }
        function fmtTime(ts){
          try{
            const d = new Date(ts);
            const hh = String(d.getHours()).padStart(2,'0');
            const mm = String(d.getMinutes()).padStart(2,'0');
            const ss = String(d.getSeconds()).padStart(2,'0');
            const ms = String(d.getMilliseconds()).padStart(3,'0');
            return `${'$'}{hh}:${'$'}{mm}:${'$'}{ss}.${'$'}{ms}`;
          } catch { return String(ts ?? ''); }
        }
        function fmtDateTime(ts){
          try{
            const d = new Date(ts);
            const yyyy = d.getFullYear();
            const mon = String(d.getMonth()+1).padStart(2,'0');
            const day = String(d.getDate()).padStart(2,'0');
            const hh = String(d.getHours()).padStart(2,'0');
            const mm = String(d.getMinutes()).padStart(2,'0');
            const ss = String(d.getSeconds()).padStart(2,'0');
            const ms = String(d.getMilliseconds()).padStart(3,'0');
            return `${'$'}{yyyy}-${'$'}{mon}-${'$'}{day} ${'$'}{hh}:${'$'}{mm}:${'$'}{ss}.${'$'}{ms}`;
          } catch { return String(ts ?? ''); }
        }
        function classForStatus(code){ if(!code) return ''; const x=Math.floor(code/100); return x===2?'status-2xx':x===3?'status-3xx':x===4?'status-4xx':x===5?'status-5xx':''; }
        function hlJson(raw){
          try { const obj=typeof raw==='string'?JSON.parse(raw):raw; const json=JSON.stringify(obj,null,2);
            return json.replace(/"(\w+)":|"(.*?)"|\b(true|false)\b|\b(-?\d+(?:\.\d+)?)\b|null/g,(m,k,s,b,n)=>{
              if(k) return '<span class="k">"'+escapeHtml(k)+'"</span>:';
              if(s!==undefined) return '<span class="s">"'+escapeHtml(s)+'"</span>';
              if(b) return '<span class="b">'+b+'</span>';
              if(n) return '<span class="n">'+n+'</span>';
              return '<span class="null">null</span>'; });
          } catch { return escapeHtml(String(raw ?? '')); }
        }
        function toFile(name, mime, text){ const blob=new Blob([text],{type:mime}); const a=document.createElement('a'); a.href=URL.createObjectURL(blob); a.download=name; a.click(); URL.revokeObjectURL(a.href); }

        // ---- Normalization helpers ----
        function kindOf(ev){
         const k = ev?.kind;
         if (typeof k === 'string') return k.toUpperCase();
         if (k && typeof k === 'object' && 'name' in k) return String(k.name).toUpperCase();
         return String(k ?? '').toUpperCase();
       }
       function dirOf(ev){
         const d = ev?.direction;
         if (typeof d === 'string') return d.toUpperCase();
         if (d && typeof d === 'object' && 'name' in d) return String(d.name).toUpperCase();
         return String(d ?? '').toUpperCase();
       }
       function levelOf(ev){
          let l = (ev?.level || ev?.logLevel || ev?.priority || '').toString().toUpperCase();
          if(!l && typeof ev?.summary === 'string'){
            const m = ev.summary.match(/^\s*\[(VERBOSE|DEBUG|INFO|WARN|ERROR|ASSERT|LOG)\]/i);
            if(m) l = m[1].toUpperCase();
          }
          return l;
       }

       function applyMode(){
          const m = viewMode?.value || 'mix';
          bodyEl.classList.remove('mode-mix','mode-network','mode-log');
          bodyEl.classList.add('mode-'+m);
       }

        // ---- Status code filter helpers ----
        function statusMatches(code, query){
          if(!query) return true;
          if(!code) return false;
          const s = String(query).trim().replace(/\s+/g,'');
          if(!s) return true;
          const tokens = s.split(',').filter(Boolean);
          const c = Number(code);
          for(const t of tokens){
            // class like 2xx / 4XX
            if(/^[0-9][xX]{2}$/.test(t)){
              const k = Number(t[0]);
              if(Math.floor(c/100) === k) return true;
              continue;
            }
            // exact 3-digit
            if(/^\d{3}$/.test(t)){
              if(c === Number(t)) return true;
              continue;
            }
            // range 3xx-3xx or 000-999
            const m = t.match(/^(\d{3})-(\d{3})$/);
            if(m){
              const a = Number(m[1]), b = Number(m[2]);
              if(c >= Math.min(a,b) && c <= Math.max(a,b)) return true;
              continue;
            }
          }
          return false;
        }
        
        // ---- Copy helper with fallback for non-secure contexts ----
        async function copyText(text){
          try{
            if (navigator.clipboard && window.isSecureContext) {
              await navigator.clipboard.writeText(text);
              return true;
            }
          }catch(e){ /* fall back */ }
          try{
            const ta = document.createElement('textarea');
            ta.value = text;
            ta.style.position = 'fixed';
            ta.style.top = '-9999px';
            document.body.appendChild(ta);
            ta.focus(); ta.select();
            const ok = document.execCommand('copy');
            document.body.removeChild(ta);
            return ok;
          }catch(e){ console.warn('copy fallback failed', e); return false; }
        }
        
        // ---- Filters & Stats ----
        function matchesFilters(ev){
          if(filterText){ const hay = JSON.stringify(ev).toLowerCase(); if(!hay.includes(filterText)) return false; }
          const kind = kindOf(ev);
          const mode = viewMode?.value || 'mix';
          if (mode === 'network' && !(kind === 'HTTP' || kind === 'WEBSOCKET')) return false;
          if (mode === 'log' && kind !== 'LOG') return false;
          const m = methodFilter?.value || '';
          if(m){
            if(m==='WS') { if(kind !== 'WEBSOCKET') return false; }
            else { if(kind !== 'HTTP') return false; if((ev.method||'').toUpperCase() !== m) return false; }
          }
          const s = statusFilter?.value || '';
          if(s && ev.status){ const x = Math.floor(ev.status/100)+'xx'; if(x!==s) return false; }
          if (statusCodeFilter && statusCodeFilter.value && !statusMatches(ev.status, statusCodeFilter.value)) return false;
          const lf = (levelFilter?.value || '').toUpperCase();
          if(lf && kind==='LOG'){
            const evLevel = levelOf(ev);
            if(!evLevel || evLevel !== lf) return false;
          }
          return true;
        }
        function renderStats(){
          const total = rows.length; const http = rows.filter(r=>kindOf(r)==='HTTP').length; const ws = rows.filter(r=>kindOf(r)==='WEBSOCKET').length; const log = rows.filter(r=>kindOf(r)==='LOG').length; const get = rows.filter(r=>(r.method||'').toUpperCase()==='GET').length; const post = rows.filter(r=>(r.method||'').toUpperCase()=='POST').length;
          const set=(id,txt)=>{ const el=document.getElementById(id); if(el) el.textContent = txt; };
          set('chipTotal','Total: '+total); set('chipHttp','HTTP: '+http); set('chipWs','WS: '+ws); set('chipLog','LOG: '+log); set('chipGet','GET: '+get); set('chipPost','POST: '+post);
        }
        
        // ---- cURL builder (HTTP only) ----
        function curlFor(ev){
          try{
            if(ev.kind!=='HTTP') return '';
            const parts = ['curl', '-i', '-X', (ev.method||'GET')];
            const url = ev.url || '';
            const hdrs = ev.headers || {};
            for(const [k,v] of Object.entries(hdrs)){
              const vv = Array.isArray(v)? v.join(', '): String(v);
              parts.push('-H', JSON.stringify(k+': '+vv));
            }
            if(ev.bodyPreview!=null && ev.bodyPreview!=='' && ev.method && ev.method.toUpperCase()!=='GET'){
              parts.push('--data-binary', JSON.stringify(String(ev.bodyPreview)));
            }
            parts.push(JSON.stringify(url));
            return parts.join(' ');
          }catch(e){ console.warn('[LogTap] curlFor failed', e); return ''; }
        }
        
        // ---- Table ----
        function renderAll(){
          try{
            filtered = rows.filter(matchesFilters);
            tbody.innerHTML='';
            const fr = document.createDocumentFragment();
            for(const ev of filtered) fr.appendChild(renderRow(ev));
            tbody.appendChild(fr);
            if(autoScroll?.checked) tbody.lastElementChild?.scrollIntoView({block:'end'});
            renderStats();
          }catch(err){ console.error('[LogTap] renderAll error', err); }
        }
        function btn(label, on){
          const b=document.createElement('button');
          b.className='xs ghost';
          b.textContent=label;
          b.addEventListener('click', async (e)=>{
            e.preventDefault();
            e.stopPropagation();
            try { await on(b); } catch(err){ console.warn('button action failed', err); }
          });
          return b;
        }
        function renderRow(ev){
          const tr = document.createElement('tr');
          const kind = kindOf(ev); const dir = dirOf(ev);
          // Build WS direction icon (send/receive)
          let wsIconHtml = '';
          if (kind === 'WEBSOCKET') {
            const isSend = (dir === 'OUTBOUND' || dir === 'REQUEST');
            const isRecv = (dir === 'INBOUND'  || dir === 'RESPONSE');
            wsIconHtml = isSend
              ? '<span class="ws-ico ws-send" title="WebSocket send">⇧</span>'
              : isRecv
                ? '<span class="ws-ico ws-recv" title="WebSocket receive">⇩</span>'
                : '<span class="ws-ico" title="WebSocket">∿</span>';
          }
          const lvl = (kind==='LOG') ? levelOf(ev) : '';
          if(lvl) tr.classList.add('level-'+lvl);
          const tagTxt = ev.tag ? String(ev.tag) : '';
          tr.dataset.id = String(ev.id ?? '');
          const actions = document.createElement('div'); actions.className='action-row';
          if(kind==='HTTP') actions.appendChild(btn('Copy cURL', async (button)=>{
              const ok = await copyText(curlFor(ev));
              if(ok){ const old = button.textContent; button.textContent = 'Copied!'; setTimeout(()=> button.textContent = old, 1200); }
            }));
          const tdActions = document.createElement('td'); tdActions.className='col-actions'; tdActions.appendChild(actions);

          tr.innerHTML =
            `<td class="col-id">${'$'}{ev.id ?? ''}</td>`+
            `<td class="col-time">${'$'}{fmtTime(ev.ts)}</td>`+
            `<td class="col-kind kind-${'$'}{kind}">${'$'}{
              kind==='LOG'
                ? escapeHtml(ev.level || levelOf(ev) || 'LOG')
                : (kind==='WEBSOCKET' ? ('WS'+wsIconHtml) : kind)
            }</td>`+
            `<td class="col-tag">${'$'}{escapeHtml(tagTxt)}</td>`+
            `<td class="col-method">${'$'}{escapeHtml(ev.method || (kind==='WEBSOCKET'?'WS':''))}</td>`+
            `<td class="col-status ${'$'}{classForStatus(ev.status)}">${'$'}{ev.status ?? ''}</td>`+
            `<td class="col-url">`+
              `<div class="url">${'$'}{escapeHtml(ev.url || '')}</div>`+
              (ev.summary ? `<div class="muted">${'$'}{escapeHtml(ev.summary)}</div>` : '')+
            `</td>`;
          // pretty body preview under URL cell (respects global Pretty JSON toggle)
          if (ev.bodyPreview) {
            const pre = document.createElement('pre');
            pre.className = 'code mini body' + (jsonPretty?.checked ? ' json' : '');
            pre.innerHTML = jsonPretty?.checked ? hlJson(ev.bodyPreview) : escapeHtml(String(ev.bodyPreview));
            const urlCell = tr.querySelector('.col-url');
            if (urlCell) urlCell.appendChild(pre);
          }
          tr.appendChild(tdActions);
          tr.addEventListener('click', ()=> openDrawer(ev));
          return tr;
        }
        // ---- Body helper ----
        function bodyFor(ev, which){
          // prefer full body fields if present
          if(which==='request'){
            return ev.requestBodyFull ?? ev.requestBody ?? ev.body ?? ev.bodyFull ?? ev.bodyPreview ?? '';
          } else {
            return ev.responseBodyFull ?? ev.responseBody ?? ev.body ?? ev.bodyFull ?? ev.bodyPreview ?? '';
          }
        }
        
        // ---- Drawer ----
        function setText(id,v){ const el=document.getElementById(id); if(el) el.textContent = v==null?'':String(v); }
        function setJson(id,raw){ const el=document.getElementById(id); if(!el) return; if(!raw){ el.textContent=''; return;} el.innerHTML = hlJson(raw); }
        function activateTab(name){ tabs.forEach(b=>b.classList.toggle('active', b.dataset.tab===name)); document.querySelectorAll('.tabpane').forEach(p=>p.classList.toggle('active', p.id==='tab-'+name)); }
        function show(id, on){ const el=document.getElementById(id); if(!el) return; el.classList.toggle('hidden', !on); }
        function setActiveTabIfHidden(){
          // ensure the active tab button/pane are visible; if not, switch to overview
          const active = document.querySelector('.tab.active');
          if(active && active.classList.contains('hidden')) activateTab('overview');
        }
        function configureDrawerForKind(kind, ev){
          const isLog = (kind === 'LOG');
          // Toggle overview rows
          show('row-method', !isLog);
          show('row-status', !isLog);
          show('row-url', !isLog);
          show('row-took', !isLog);
          show('row-curl', !isLog && ev && kind==='HTTP');
          show('row-level', isLog);
          show('row-tag', isLog);
          // Tabs: hide request/response/headers for LOG
          const showNetTabs = !isLog;
          document.getElementById('tabBtn-request')?.classList.toggle('hidden', !showNetTabs);
          document.getElementById('tabBtn-response')?.classList.toggle('hidden', !showNetTabs);
          document.getElementById('tabBtn-headers')?.classList.toggle('hidden', !showNetTabs);
          document.getElementById('tab-request')?.classList.toggle('hidden', !showNetTabs);
          document.getElementById('tab-response')?.classList.toggle('hidden', !showNetTabs);
          document.getElementById('tab-headers')?.classList.toggle('hidden', !showNetTabs);
          if(!showNetTabs) activateTab('overview');
        }
        function openDrawer(ev){
          if(!drawer) return;
          currentEv = ev;
          const kind = kindOf(ev); const dir = dirOf(ev);
          bodyEl.classList.add('drawer-open');
          const title = (ev.method? (ev.method+' ') : (kind==='WEBSOCKET'?'WS ':'') ) + (ev.url || ev.summary || '');
          const tEl = document.getElementById('drawerTitle'); tEl && tEl.replaceChildren(document.createTextNode(title));
          let sub = `<span class="badge">id ${'$'}{ev.id}</span> ` + (ev.status? `<span class="badge">status ${'$'}{ev.status}</span> ` : '') + (ev.tookMs? `<span class="badge">${'$'}{ev.tookMs} ms</span>` : '');
          if (kind === 'WEBSOCKET') {
            const d = dir;
            const label = (d === 'OUTBOUND' || d === 'REQUEST') ? 'send' : (d === 'INBOUND' || d === 'RESPONSE') ? 'recv' : String(d||'').toLowerCase();
            sub += ` <span class="badge">WS ${'$'}{label}</span>`;
          }
          const sEl = document.getElementById('drawerSub'); if(sEl) sEl.innerHTML = sub;
          setText('ov-id', ev.id); setText('ov-time', fmtDateTime(ev.ts));
          // Set ov-kind: for LOG, use level or fallback; else kind.
          if(kind==='LOG'){
            setText('ov-kind', ev.level || levelOf(ev) || 'LOG');
          } else {
            setText('ov-kind', kind);
          }
          setText('ov-dir', dir);
          setText('ov-method', ev.method || (kind==='WEBSOCKET'?'WS':'')); setText('ov-status', ev.status ?? ''); setText('ov-url', ev.url ?? '');
          setText('ov-level', (ev.level || levelOf(ev) || ''));
          setText('ov-tag', (ev.tag || ''));
          if (jsonPretty?.checked) {
            const el = document.getElementById('ov-summary');
            if (el) { el.classList.add('json'); el.innerHTML = hlJson(ev.summary ?? ''); }
          } else {
            setText('ov-summary', ev.summary ?? '');
          }
          setText('ov-took', ev.tookMs? ev.tookMs+' ms' : '');
          // Show tag in thread field if present
          if(ev.tag) setText('ov-thread', (ev.thread ?? '') + (ev.thread? ' • ' : '') + ev.tag);
          else setText('ov-thread', ev.thread ?? '');
          setJson('req-body', bodyFor(ev,'request'));
          setJson('resp-body', bodyFor(ev,'response'));
          const rh = document.getElementById('req-headers'); if(rh) rh.textContent = ev.headers ? Object.entries(ev.headers).map(([k,v])=> k+': '+(Array.isArray(v)?v.join(', '):v)).join('\n') : '';
          const ph = document.getElementById('resp-headers'); if(ph) ph.textContent = '';
          const oc = document.getElementById('ov-curl'); if(oc) oc.textContent = curlFor(ev);
          if(curlCopyBtn){ curlCopyBtn.onclick = async (e)=>{ e.preventDefault(); e.stopPropagation(); const ocEl = document.getElementById('ov-curl'); const ok = await copyText(ocEl?.textContent || ''); if(ok){ const old = curlCopyBtn.textContent; curlCopyBtn.textContent = 'Copied!'; setTimeout(()=> curlCopyBtn.textContent = old, 1200); } }; }
          const os = document.getElementById('ov-summary');
          if(summaryCopyBtn){ summaryCopyBtn.onclick = async (e)=>{ e.preventDefault(); e.stopPropagation(); const osEl = document.getElementById('ov-summary'); const ok = await copyText(osEl?.textContent || ''); if(ok){ const old = summaryCopyBtn.textContent; summaryCopyBtn.textContent = 'Copied!'; setTimeout(()=> summaryCopyBtn.textContent = old, 1200); } }; }
          configureDrawerForKind(kind, ev);
          activateTab('overview');
        }
        
        // ---- Exports ----
        function currentFiltered(){ return rows.filter(matchesFilters); }
        exportJsonBtn?.addEventListener('click', ()=>{
          try{ const data = JSON.stringify(currentFiltered(), null, 2); const name = 'logtap-'+new Date().toISOString().replace(/[:.]/g,'-')+'.json'; toFile(name, 'application/json', data);}catch(e){ console.error('export json failed', e); }
        });
        exportHtmlBtn?.addEventListener('click', ()=>{
          try{ const data = currentFiltered(); const pre = escapeHtml(JSON.stringify(data, null, 2));
            const html = `<!doctype html><html><head><meta charset=\"utf-8\"><title>LogTap Report</title><style>body{font-family:ui-monospace,Menlo,monospace;background:#0b0d10;color:#e6edf3}pre{white-space:pre-wrap}</style></head><body><h1>LogTap Report</h1><p>Generated ${'$'}{new Date().toLocaleString()}</p><h2>Filtered events (${'$'}{data.length})</h2><pre>${'$'}{pre}</pre></body></html>`;
            const name = 'logtap-report-'+new Date().toISOString().replace(/[:.]/g,'-')+'.html'; toFile(name, 'text/html', html);
          }catch(e){ console.error('export html failed', e); }
        });
        
        // ---- Events ----
        search?.addEventListener('input', ()=>{ filterText = search.value.trim().toLowerCase(); renderAll(); });
        methodFilter?.addEventListener('change', renderAll);
        viewMode?.addEventListener('change', ()=>{ applyMode(); renderAll(); });
        statusFilter?.addEventListener('change', renderAll);
        statusCodeFilter?.addEventListener('input', renderAll);
        levelFilter?.addEventListener('change', renderAll);
        jsonPretty?.addEventListener('change', ()=>{
          renderAll();
          if (currentEv) openDrawer(currentEv);
        });
        clearBtn?.addEventListener('click', async ()=>{ try{ await fetch('/api/clear', {method:'POST'}); }catch{} rows=[]; renderAll(); });
        drawerClose?.addEventListener('click', ()=> bodyEl.classList.remove('drawer-open'));
        // Filters & Export popovers (don't close when clicking inside)
        function isInside(el, target){ return !!(el && target && el instanceof Node && el.contains(target)); }
        function closePopovers(e){
          if (e) {
            const t = e.target;
            // If click is inside either popover or on their trigger buttons, don't close
            if (isInside(filtersPanel, t) || isInside(exportMenu, t) || isInside(filtersBtn, t) || isInside(exportBtn, t)) return;
          }
          filtersPanel?.classList.add('hidden');
          exportMenu?.classList.add('hidden');
        }
        filtersBtn?.addEventListener('click', (e)=>{
          e.preventDefault(); e.stopPropagation();
          const wasOpen = !filtersPanel?.classList.contains('hidden');
          closePopovers();
          if(!wasOpen) filtersPanel?.classList.remove('hidden');
        });
        exportBtn?.addEventListener('click', (e)=>{
          e.preventDefault(); e.stopPropagation();
          const wasOpen = !exportMenu?.classList.contains('hidden');
          closePopovers();
          if(!wasOpen) exportMenu?.classList.remove('hidden');
        });
        // Prevent clicks inside popovers from bubbling to document
        filtersPanel?.addEventListener('click', (e)=> e.stopPropagation());
        exportMenu?.addEventListener('click', (e)=> e.stopPropagation());
        document.addEventListener('click', (e)=> closePopovers(e));
        document.addEventListener('keydown', (e)=>{ if(e.key==='Escape') closePopovers(); });
        themeToggle?.addEventListener('click', ()=>{ const cur = document.documentElement.getAttribute('data-theme') || 'dark'; const next = cur==='dark'?'light':'dark'; applyTheme(next); localStorage.setItem('logtap:theme', next); });
        
        // ---- Bootstrap + WS status ----
        async function bootstrap(){
          initTheme();
          try{ const res = await fetch('/api/logs?limit=1000'); if(!res.ok) throw new Error('HTTP '+res.status); rows = await res.json(); }
          catch(err){ console.error('[LogTap] failed to fetch /api/logs', err); rows=[]; }
          applyMode();
          renderAll();
          try{
            const ws = new WebSocket((location.protocol==='https:'?'wss':'ws')+'://'+location.host+'/ws');
            const on = ()=>{ if(wsStatus){ wsStatus.textContent = '● Connected'; wsStatus.classList.remove('status-off'); wsStatus.classList.add('status-on'); } };
            const off= ()=>{ if(wsStatus){ wsStatus.textContent = '● Disconnected'; wsStatus.classList.remove('status-on'); wsStatus.classList.add('status-off'); } };
            ws.addEventListener('open', on);
            ws.addEventListener('close', off);
            ws.addEventListener('error', off);
            ws.onmessage = (e)=>{ try{ const ev = JSON.parse(e.data); rows.push(ev); if(matchesFilters(ev)){ tbody.appendChild(renderRow(ev)); if(autoScroll?.checked) tbody.lastElementChild?.scrollIntoView({block:'end'}); renderStats(); } }catch(parseErr){ console.warn('[LogTap] bad WS payload', parseErr); } };
          }catch(wsErr){ console.warn('[LogTap] WS setup failed', wsErr); if(wsStatus){ wsStatus.textContent='● Disconnected'; wsStatus.classList.add('status-off'); } }
        }
        bootstrap();
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