package com.github.husseinhj.logtap.utils

internal object Resources {
    val indexHtml = """
<!doctype html>
<html>
  <head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>LogTap</title>
    <link rel="stylesheet" href="/app.css"/>
    <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:wght@400&display=swap" rel="stylesheet"/>
  </head>
  <body class="ui">
    <!-- Header -->
    <header class="hdr blur elev">
      <div class="brand">
        <a class="logo gh" href="https://github.com/Husseinhj/LogTap" target="_blank" rel="noopener" title="Open GitHub repository" aria-label="Open GitHub repository">
          <svg class="gh-ico" viewBox="0 0 16 16" aria-hidden="true"><path d="M8 0C3.58 0 0 3.58 0 8a8 8 0 0 0 5.47 7.59c.4.07.55-.17.55-.38 0-.19-.01-.82-.01-1.49-2.01.37-2.53-.49-2.69-.94-.09-.23-.48-.94-.82-1.13-.28-.15-.68-.52-.01-.53.63-.01 1.08.58 1.23.82.72 1.21 1.87.87 2.33.66.07-.52.28-.87.51-1.07-1.78-.2-3.64-.89-3.64-3.95 0-.87.31-1.59.82-2.15-.08-.2-.36-1.02.08-2.12 0 0 .67-.21 2.2.82.62-.17 1.29-.27 2-.27s1.38.09 2 .27c1.53-1.04 2.2-.82 2.2-.82.44 1.1.16 1.92.08 2.12.51.56.82 1.27.82 2.15 0 3.07-1.87 3.75-3.65 3.95.29.25.54.73.54 1.48 0 1.07-.01 1.93-.01 2.2 0 .21.15.46.55.38A8.01 8.01 0 0 0 16 8c0-4.42-3.58-8-8-8Z"/></svg>
        </a>
        <div class="titles">
          <div class="title">LogTap</div>
          <div class="sub">Inspect HTTP · WebSocket · Logs</div>
        </div>
      </div>
      <nav class="bar">
        <div id="wsStatus" class="chip stat">● Disconnected</div>
        <div class="search field">
          <svg class="ico" viewBox="0 0 24 24"><path d="M15.5 14h-.79l-.28-.27A6.471 6.471 0 0 0 16 9.5 6.5 6.5 0 1 0 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5Zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14Z"/></svg>
          <input id="search" class="input" type="search" placeholder="Search url, method, headers, body…  ⌘/Ctrl + K"/>
        </div>
        <button id="filtersBtn" class="btn ghost" title="Filters" aria-haspopup="true" aria-expanded="false">
          <span class="material-symbols-outlined" aria-hidden="true">filter_list</span>
          <span class="label">Filters</span>
          <span class="material-symbols-outlined dropdown" aria-hidden="true">arrow_drop_down</span>
        </button>
        <div class="menu">
          <button id="exportBtn" class="icon" title="Export" aria-label="Export">
            <span class="material-symbols-outlined" aria-hidden="true">ios_share</span>
          </button>
          <div id="exportMenu" class="popover hidden" role="menu" aria-hidden="true">
            <button id="exportJson" class="btn block" role="menuitem">Export JSON</button>
            <button id="exportHtml" class="btn block" role="menuitem">Export Report</button>
          </div>
        </div>
        <button id="clearBtn" class="icon" title="Clear all logs" aria-label="Clear all logs">
          <span class="material-symbols-outlined" aria-hidden="true">delete_sweep</span>
        </button>
        <button id="themeToggle" class="icon" title="Toggle theme" aria-label="Toggle theme">
          <span class="material-symbols-outlined ico-sun" aria-hidden="true">light_mode</span>
          <span class="material-symbols-outlined ico-moon" aria-hidden="true">dark_mode</span>
        </button>

        <!-- Filters popover (Material 3 sheet) -->
        <div id="filtersPanel" class="popover hidden fp" role="dialog" aria-label="Filters">
          <div class="fp-head">
            <div class="fp-title">Filters</div>
            <div class="fp-sub">Narrow what you see in the table</div>
          </div>
          <div class="fp-grid">
            <label class="fp-field">View
              <select id="viewMode" class="select">
                <option value="mix">Mix (All)</option>
                <option value="network">Network only</option>
                <option value="log">Logger only</option>
              </select>
            </label>
            <label class="fp-field">Method
              <select id="methodFilter" class="select">
                <option value="">All</option>
                <option>GET</option><option>POST</option><option>PUT</option>
                <option>PATCH</option><option>DELETE</option><option>WS</option>
              </select>
            </label>
            <label class="fp-field">Status class
              <select id="statusFilter" class="select">
                <option value="">Any</option>
                <option value="2xx">2xx</option>
                <option value="3xx">3xx</option>
                <option value="4xx">4xx</option>
                <option value="5xx">5xx</option>
              </select>
            </label>
            <label class="fp-field">Codes
              <input id="statusCodeFilter" class="input" type="text" inputmode="numeric" pattern="[0-9xX,-,\s]*" placeholder="200, 2xx, 400-404"/>
            </label>
            <label class="fp-field">Level
              <select id="levelFilter" class="select">
                <option value="">Any Level</option>
                <option value="VERBOSE">Verbose</option>
                <option value="DEBUG">Debug</option>
                <option value="INFO">Info</option>
                <option value="WARN">Warn</option>
                <option value="ERROR">Error</option>
                <option value="ASSERT">Assert</option>
              </select>
            </label>
            <label class="fp-field">Colors
              <select id="colorScheme" class="select">
                <option value="android">Android Studio</option>
                <option value="xcode">Xcode</option>
                <option value="vscode">Visual Studio Code</option>
                <option value="grafana">Grafana</option>
              </select>
            </label>
            <label class="fp-checkbox">
              <input type="checkbox" id="jsonPretty"/>
              <span class="box"></span>
              <span class="lbl">Pretty JSON</span>
            </label>
            <label class="fp-checkbox">
              <input type="checkbox" id="autoScroll" checked/>
              <span class="box"></span>
              <span class="lbl">Auto‑scroll</span>
            </label>
            <div class="fp-cols">
              <div class="fp-cols-title">Columns</div>
              <label class="fp-checkbox"><input type="checkbox" id="colId" checked/><span class="box"></span><span class="lbl">ID</span></label>
              <label class="fp-checkbox"><input type="checkbox" id="colTime" checked/><span class="box"></span><span class="lbl">Time</span></label>
              <label class="fp-checkbox"><input type="checkbox" id="colKind" checked/><span class="box"></span><span class="lbl">Kind</span></label>
              <label class="fp-checkbox"><input type="checkbox" id="colTag" checked/><span class="box"></span><span class="lbl">Tag</span></label>
              <label class="fp-checkbox"><input type="checkbox" id="colMethod" checked/><span class="box"></span><span class="lbl">Method</span></label>
              <label class="fp-checkbox"><input type="checkbox" id="colStatus" checked/><span class="box"></span><span class="lbl">Status</span></label>
              <label class="fp-checkbox"><input type="checkbox" id="colUrl" checked/><span class="box"></span><span class="lbl">URL / Summary</span></label>
              <label class="fp-checkbox"><input type="checkbox" id="colActions" checked/><span class="box"></span><span class="lbl">Actions</span></label>
            </div>
          </div>
          <div class="fp-actions">
            <button id="filtersReset" class="btn ghost"><span class="material-symbols-outlined" aria-hidden="true">restart_alt</span> Reset</button>
            <button id="filtersClose" class="btn"><span class="material-symbols-outlined" aria-hidden="true">done</span> Apply</button>
          </div>
        </div>
      </nav>
    </header>

    <!-- Stat pills -->
    <section class="stats">
      <div class="chip" id="chipTotal">Total: 0</div>
      <div class="chip" id="chipHttp">HTTP: 0</div>
      <div class="chip" id="chipWs">WS: 0</div>
      <div class="chip" id="chipLog">LOG: 0</div>
      <div class="chip" id="chipGet">GET: 0</div>
      <div class="chip" id="chipPost">POST: 0</div>
    </section>

    <main class="shell">
      <div class="panel elev">
        <table id="logtbl" class="tbl">
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
      </div>

      <aside id="drawer" class="drawer elev">
        <div class="d-resize" id="drawerResizer" aria-hidden="true" title="Drag to resize"></div>
        <header class="d-head">
          <div>
            <div id="drawerTitle" class="d-title">Details</div>
            <div id="drawerSub" class="d-sub"></div>
          </div>
          <button id="drawerClose" class="icon" title="Close (Esc)">×</button>
        </header>
        <nav class="tabs">
          <button class="tab active" data-tab="overview" id="tabBtn-overview">Overview</button>
          <button class="tab" data-tab="request" id="tabBtn-request">Request</button>
          <button class="tab" data-tab="response" id="tabBtn-response">Response</button>
          <button class="tab" data-tab="headers" id="tabBtn-headers">Headers</button>
        </nav>
        <section class="panes">
          <div class="pane active" id="tab-overview">
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
              <div class="full"><dt>Summary</dt><dd><div class="sum"><button id="ov-summary-copy" class="icon" title="Copy Summary" aria-label="Copy Summary"><span class="material-symbols-outlined" aria-hidden="true">content_copy</span></button><pre class="code" id="ov-summary"></pre></div></dd></div>
              <div id="row-took"><dt>Took</dt><dd id="ov-took"></dd></div>
              <div><dt>Thread</dt><dd id="ov-thread"></dd></div>
              <div class="full" id="row-curl"><dt>cURL</dt><dd><div class="curl"><button id="ov-curl-copy" class="icon" title="Copy cURL" aria-label="Copy cURL"><span class="material-symbols-outlined" aria-hidden="true">content_copy</span></button><pre class="code" id="ov-curl"></pre></div></dd></div>
            </dl>
          </div>
          <div class="pane" id="tab-request">
            <h4>Request Body</h4>
            <pre class="code json" id="req-body"></pre>
          </div>
          <div class="pane" id="tab-response">
            <h4>Response Body</h4>
            <pre class="code json" id="resp-body"></pre>
          </div>
          <div class="pane" id="tab-headers">
            <h4>Headers</h4>
            <div class="cols">
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
   <div class="repo">
     <a href="https://github.com/Husseinhj/LogTap" target="_blank" rel="noopener">
       <svg class="gh-ico" viewBox="0 0 16 16" aria-hidden="true"><path d="M8 0C3.58 0 0 3.58 0 8a8 8 0 0 0 5.47 7.59c.4.07.55-.17.55-.38 0-.19-.01-.82-.01-1.49-2.01.37-2.53-.49-2.69-.94-.09-.23-.48-.94-.82-1.13-.28-.15-.68-.52-.01-.53.63-.01 1.08.58 1.23.82.72 1.21 1.87.87 2.33.66.07-.52.28-.87.51-1.07-1.78-.2-3.64-.89-3.64-3.95 0-.87.31-1.59.82-2.15-.08-.2-.36-1.02.08-2.12 0 0 .67-.21 2.2.82.62-.17 1.29-.27 2-.27s1.38.09 2 .27c1.53-1.04 2.2-.82 2.2-.82.44 1.1.16 1.92.08 2.12.51.56.82 1.27.82 2.15 0 3.07-1.87 3.75-3.65 3.95.29.25.54.73.54 1.48 0 1.07-.01 1.93-.01 2.2 0 .21.15.46.55.38A8.01 8.01 0 0 0 16 8c0-4.42-3.58-8-8-8Z"/></svg>
       GitHub — Husseinhj/LogTap
     </a>
     <div class="made">Made with ❤️ from Germany</div>
   </div>

  </body>
</html>
""".trimIndent()

    val appCss = """
/* ========================= Material 3 (tokens + components) ========================= */
/* Color roles */
:root[data-theme="light"]{
  --md-sys-color-primary:#6750A4;         /* Indigo 500-ish */
  --md-sys-color-on-primary:#FFFFFF;
  --md-sys-color-primary-container:#EADDFF;
  --md-sys-color-on-primary-container:#21005E;
  --md-sys-color-secondary:#625B71;
  --md-sys-color-on-secondary:#FFFFFF;
  --md-sys-color-secondary-container:#E8DEF8;
  --md-sys-color-on-secondary-container:#1D192B;
  --md-sys-color-surface:#FFFBFE;
  --md-sys-color-surface-dim:#E6E0E9;
  --md-sys-color-surface-bright:#FEF7FF;
  --md-sys-color-surface-container:#F3EDF7;
  --md-sys-color-surface-container-high:#ECE6F0;
  --md-sys-color-surface-container-highest:#E6E0E9;
  --md-sys-color-on-surface:#1D1B20;
  --md-sys-color-on-surface-variant:#49454F;
  --md-sys-color-outline:#79747E;
  --md-sys-color-outline-variant:#CAC4D0;
  --md-sys-color-error:#B3261E;
  --md-sys-color-on-error:#FFFFFF;
  --md-sys-color-inverse-surface:#313033;
  --md-sys-color-inverse-on-surface:#F4EFF4;
  --md-sys-color-scrim:#000000;
}
:root[data-theme="dark"]{
  --md-sys-color-primary:#D0BCFF;
  --md-sys-color-on-primary:#371E73;
  --md-sys-color-primary-container:#4F378B;
  --md-sys-color-on-primary-container:#EADDFF;
  --md-sys-color-secondary:#CCC2DC;
  --md-sys-color-on-secondary:#332D41;
  --md-sys-color-secondary-container:#4A4458;
  --md-sys-color-on-secondary-container:#E8DEF8;
  --md-sys-color-surface:#141218;
  --md-sys-color-surface-dim:#141218;
  --md-sys-color-surface-bright:#3B383E;
  --md-sys-color-surface-container:#1D1B20;
  --md-sys-color-surface-container-high:#2B2930;
  --md-sys-color-surface-container-highest:#36343B;
  --md-sys-color-on-surface:#E6E0E9;
  --md-sys-color-on-surface-variant:#CAC4D0;
  --md-sys-color-outline:#938F99;
  --md-sys-color-outline-variant:#49454F;
  --md-sys-color-error:#F2B8B5;
  --md-sys-color-on-error:#601410;
  --md-sys-color-inverse-surface:#E6E0E9;
  --md-sys-color-inverse-on-surface:#313033;
  --md-sys-color-scrim:#000000;
}

/* Semantic aliases used by existing markup (mapped to M3 roles) */
:root{
  --bg:var(--md-sys-color-surface);
  --bg2:var(--md-sys-color-surface-bright);
  --surface:var(--md-sys-color-surface-container);
  --surface-2:var(--md-sys-color-surface-container-high);
  --text:var(--md-sys-color-on-surface);
  --muted:var(--md-sys-color-on-surface-variant);
  --line:var(--md-sys-color-outline-variant);
  --chip:var(--md-sys-color-surface-container-highest);
  --code:var(--md-sys-color-on-surface);
  --codebg:var(--md-sys-color-surface-dim);
  --primary:var(--md-sys-color-primary);
  --on-primary:var(--md-sys-color-on-primary);
  --accent:var(--md-sys-color-primary);
  --accent2:#22c55e; /* success (not in core M3 set) */
  --warn:#f59e0b;    /* warning (custom) */
  --err:var(--md-sys-color-error);

  /* Elevation levels (M3 uses surface tonal overlays; we approximate with shadow) */
  --elev-1:0 1px 2px rgba(0,0,0,.14), 0 1px 3px 1px rgba(0,0,0,.12);
  --elev-2:0 2px 6px rgba(0,0,0,.18), 0 1px 2px rgba(0,0,0,.08);
  --elev-3:0 6px 10px rgba(0,0,0,.20), 0 1px 3px rgba(0,0,0,.10);

  /* State layer opacities */
  --state-hover: .08;
  --state-focus: .12;
  --state-pressed: .12;
  --drawer-w:560px;
}

*{box-sizing:border-box}
html,body{height:100%}
body.ui{margin:0;background:var(--bg);color:var(--text);font:14px ui-sans-serif,system-ui,-apple-system,"Segoe UI",Roboto,Helvetica,Arial}

/* ========================= Top App Bar (M3) ========================= */
.hdr{position:sticky;top:0;z-index:40;display:flex;align-items:center;justify-content:space-between;padding:12px 16px;background:var(--md-sys-color-surface);border-bottom:1px solid var(--line)}
.brand{display:flex;gap:12px;align-items:center}
.logo{width:40px;height:40px;border-radius:12px;display:grid;place-items:center;border:1px solid var(--line);background:var(--md-sys-color-surface-container);color:var(--accent)}
.logo svg{width:22px;height:22px;fill:currentColor}
.titles .title{font-weight:700;letter-spacing:.2px}
.titles .sub{color:var(--muted);font-size:12px}

/* ========================= Inputs & Buttons (M3) ========================= */
.field{position:relative;display:flex;align-items:center}
.field .ico{position:absolute;left:10px;top:50%;transform:translateY(-50%);width:18px;height:18px;opacity:.7;fill:var(--muted)}
.input{background:var(--surface);color:var(--text);border:1px solid var(--line);border-radius:12px;padding:10px 36px 10px 34px}
.select{background:var(--surface);color:var(--text);border:1px solid var(--line);border-radius:12px;padding:10px 12px}

.btn{--bgc:var(--md-sys-color-primary);--fgc:var(--md-sys-color-on-primary);background:var(--bgc);color:var(--fgc);border:0;border-radius:20px;padding:8px 16px;cursor:pointer;position:relative;overflow:hidden}
.btn.ghost{--bgc:transparent;--fgc:var(--text);border:1px solid var(--line)}
.btn.block{display:block;width:100%;text-align:left}
.btn.xs{padding:6px 10px;border-radius:16px}
.btn::after,.icon::after{content:"";position:absolute;inset:0;background:currentColor;opacity:0;transition:opacity .15s}
.btn:hover::after,.icon:hover::after{opacity:var(--state-hover)}
.btn:active::after,.icon:active::after{opacity:var(--state-pressed)}
#filtersBtn{display:flex;align-items:center;gap:4px}
#filtersBtn .material-symbols-outlined.dropdown{font-size:20px;opacity:.7}
#filtersBtn .label{font-size:14px}

.icon{width:36px;height:36px;border-radius:12px;background:transparent;border:1px solid var(--line);color:var(--text);font-size:18px;line-height:1;display:grid;place-items:center;position:relative;overflow:hidden}
.icon.solid{background:var(--surface)}
.icon .material-symbols-outlined{font-size:20px}

.bar{display:flex;gap:8px;align-items:center;flex-wrap:wrap}
.menu{position:relative}

.popover{position:absolute;top:100%;margin-top:8px;right:0;background:var(--md-sys-color-surface);border:1px solid var(--line);border-radius:12px;box-shadow:var(--elev-3);padding:10px;z-index:50;min-width:220px}
.popover.hidden{display:none}

/* Filters popover (Material 3) */
.fp{ padding:0; min-width: 360px; max-width: 520px; }
.fp-head{ padding:14px 16px 8px; border-bottom:1px solid var(--line); }
.fp-title{ font-weight:700; }
.fp-sub{ color:var(--muted); font-size:12px; margin-top:2px; }
.fp-grid{ display:grid; grid-template-columns:1fr 1fr; gap:12px 12px; padding:12px 12px 4px; }
.fp-field .input, .fp-field .select{ width:100%; }
.fp-switch{ display:flex; align-items:center; gap:10px; padding:6px 4px; }
.fp-actions{ display:flex; justify-content:flex-end; gap:8px; padding:10px 12px; border-top:1px solid var(--line); }
.fp-actions .btn{ display:inline-flex; align-items:center; gap:6px; }
@media (max-width:520px){ .fp{ min-width: 280px; } .fp-grid{ grid-template-columns:1fr; } }

# Export menu buttons styled as Material 3 list items
#exportMenu .btn.block {
  background: transparent;
  border: 0;
  border-radius: 12px;
  padding: 10px 16px;
  font-size: 14px;
  color: var(--text);
  justify-content: flex-start;
  width: 100%;
  text-align: left;
}
#exportMenu .btn.block:hover::after {
  opacity: var(--state-hover);
}
#exportMenu .btn.block:active::after {
  opacity: var(--state-pressed);
}

/* Material 3 checkboxes inside filter panel */
.fp-checkbox {
  display:flex;
  align-items:center;
  gap:10px;
  padding:6px 4px;
  cursor:pointer;
  user-select:none;
}
.fp-checkbox input {
  appearance:none;
  -webkit-appearance:none;
  width:18px;
  height:18px;
  border:2px solid var(--line);
  border-radius:4px;
  background:var(--surface);
  display:grid;
  place-items:center;
  margin:0;
}
.fp-checkbox input:checked {
  background:var(--md-sys-color-primary);
  border-color:var(--md-sys-color-primary);
}
.fp-checkbox input:checked::before {
  content:"✓";
  color:var(--md-sys-color-on-primary);
  font-size:14px;
  line-height:1;
}
.fp-checkbox .lbl {
  font-size:14px;
}

/* Filters: Columns grid */
.fp-cols{ grid-column:1 / -1; display:grid; grid-template-columns:repeat(2, minmax(0,1fr)); gap:6px 12px; padding-top:4px; }
.fp-cols-title{ grid-column:1 / -1; font-weight:600; color:var(--muted); margin:4px 0; }
@media (max-width:520px){ .fp-cols{ grid-template-columns:1fr; } }

/* Column visibility (body classes) */
body.hide-col-id      #logtbl .col-id{display:none}
body.hide-col-time    #logtbl .col-time{display:none}
body.hide-col-kind    #logtbl .col-kind{display:none}
body.hide-col-tag     #logtbl .col-tag{display:none}
body.hide-col-method  #logtbl .col-method{display:none}
body.hide-col-status  #logtbl .col-status{display:none}
body.hide-col-url     #logtbl .col-url{display:none}
body.hide-col-actions #logtbl .col-actions{display:none}

/* ========================= Assist/Stat Chips (M3) ========================= */
.stats{display:flex;gap:8px;flex-wrap:wrap;padding:10px 16px}
.chip{background:var(--md-sys-color-surface-container-high);border:1px solid var(--line);padding:6px 12px;border-radius:999px;transition:background .15s,border-color .15s,color .15s,box-shadow .15s}

.chip.stat{font:12px ui-monospace,Menlo,monospace}

/* Clickable stats */
.stats .chip{cursor:default; user-select:none}
.stats .chip.clickable{cursor:pointer; position:relative}
.stats .chip.clickable:hover{background:var(--md-sys-color-surface-container-highest); box-shadow:0 0 0 3px color-mix(in srgb,var(--accent) 22%, transparent) inset}
.stats .chip.clickable:active{box-shadow:0 0 0 4px color-mix(in srgb,var(--accent) 32%, transparent) inset}
/* Selected (visible even in dark): allow aria-pressed or .active */
.stats .chip.active,
.stats .chip[aria-pressed="true"]{
  background:var(--md-sys-color-primary);
  color:var(--md-sys-color-on-primary);
  border-color:transparent;
  box-shadow:0 0 0 2px color-mix(in srgb,var(--md-sys-color-primary) 60%, transparent), 0 1px 2px rgba(0,0,0,.25);
  font-weight:700;
  transform:translateY(-1px);
}
/* Add an obvious check dot on the left for selected state */
.stats .chip.active::before,
.stats .chip[aria-pressed="true"]::before{
  content:"";
  display:inline-block;
  width:10px; height:10px; border-radius:50%;
  background:var(--md-sys-color-on-primary);
  margin-right:8px;
  box-shadow:0 0 0 3px color-mix(in srgb,var(--md-sys-color-on-primary) 35%, transparent);
  vertical-align:middle;
}
/* Keyboard focus ring for accessibility */
.stats .chip.clickable:focus-visible{
  outline:none;
  box-shadow:0 0 0 3px color-mix(in srgb,var(--md-sys-color-primary) 70%, transparent);
}

/* WS colorful status (kept) */
#wsStatus{transition:background-color .2s ease,color .2s ease,border-color .2s ease}
#wsStatus.status-on{background:rgba(34,197,94,.15);color:#16a34a;border-color:#16a34a33}
#wsStatus.status-off{background:rgba(244,63,94,.15);color:#ef4444;border-color:#ef444433}
#wsStatus.status-connecting{background:rgba(245,158,11,.18);color:#d97706;border-color:#d9770633}

/* ========================= Layout ========================= */
.shell{display:flex;gap:12px;padding:12px;align-items:stretch}
.stats{ overflow-x:auto; -webkit-overflow-scrolling:touch; scrollbar-width:thin; }
.panel{flex:1 1 auto;border:1px solid var(--line);border-radius:16px;background:var(--md-sys-color-surface);box-shadow:var(--elev-1);overflow:auto;overflow-x:auto;max-height:calc(100vh - 180px)}
.repo{
  position:sticky;
  bottom:0;
  z-index:20;
  padding:12px 16px;
  text-align:center;
  color:var(--muted);
  font-size:13px;
  display:flex;
  flex-direction:column;
  align-items:center;
  gap:4px;
  background:var(--md-sys-color-surface);
  border-top:1px solid var(--line);
  box-shadow:var(--elev-1);
}
.repo a{color:inherit;text-decoration:none;border-bottom:1px dashed var(--line)}
.repo a:hover{color:var(--accent);border-bottom-color:var(--accent)}
.repo .gh-ico{width:16px;height:16px;vertical-align:middle;margin-right:4px;fill:currentColor}

/* ========================= Data Table (M3) ========================= */
.tbl{width:100%;border-collapse:separate;border-spacing:0}
.tbl thead th{position:sticky;top:0;background:var(--md-sys-color-surface-container);color:var(--muted);padding:12px 12px;text-align:left;border-bottom:1px solid var(--line);z-index:1;font-weight:600}
/* Keep first header cells readable when table scrolls */
.tbl thead th:first-child{ position:sticky; left:0; z-index:2 }
.tbl tbody tr{background:var(--md-sys-color-surface);border-bottom:1px solid var(--line)}
.tbl tbody tr:hover{background:var(--md-sys-color-surface-container-high)}
.tbl tbody td{padding:14px 12px;vertical-align:top}
.col-id{width:72px}.col-time{width:150px}.col-kind{width:120px}.col-tag{width:140px}.col-method{width:92px}.col-status{width:92px}.col-actions{width:170px}

/* Status & kind colors */

:root{
  --ws-send:#06b6d4;   /* cyan for client → server */
  --ws-recv:#22c55e;   /* green for server → client */
  --ws-ping:#a3e635;   /* lime */
  --ws-pong:#f59e0b;   /* amber */
  --ws-close:#ef4444;  /* red   */
}
/* Table tinting for WebSocket rows */
.tbl tbody tr.ws-send  td.col-url .url{ color: color-mix(in srgb, var(--ws-send) 85%, currentColor); }
.tbl tbody tr.ws-recv  td.col-url .url{ color: color-mix(in srgb, var(--ws-recv) 85%, currentColor); }
.tbl tbody tr.ws-send  td.col-method{ color: var(--ws-send); }
.tbl tbody tr.ws-recv  td.col-method{ color: var(--ws-recv); }
/* Mini body preview tint */
.tbl tbody tr.ws-send  pre.code.mini{ border-left:4px solid var(--ws-send); }
.tbl tbody tr.ws-recv  pre.code.mini{ border-left:4px solid var(--ws-recv); }
/* Drawer tinting for WS frames */
#tab-request pre.code.ws-send,  #tab-response pre.code.ws-send{  border-left:4px solid var(--ws-send); }
#tab-request pre.code.ws-recv,  #tab-response pre.code.ws-recv{  border-left:4px solid var(--ws-recv); }
#tab-request pre.code.ws-ping,  #tab-response pre.code.ws-ping{  border-left:4px solid var(--ws-ping); }
#tab-request pre.code.ws-pong,  #tab-response pre.code.ws-pong{  border-left:4px solid var(--ws-pong); }
#tab-request pre.code.ws-close, #tab-response pre.code.ws-close{ border-left:4px solid var(--ws-close); }
/* WS icon colors */
.ws-send{ color: var(--ws-send); }
.ws-recv{ color: var(--ws-recv); }

:root{
  --m-get:#3b82f6;   /* blue  */
  --m-post:#22c55e;  /* green */
  --m-put:#f59e0b;   /* amber */
  --m-patch:#a855f7; /* violet*/
  --m-delete:#ef4444;/* red   */
  --m-ws:#06b6d4;    /* cyan  */
}
/* Method tints in table */
.tbl tbody tr .col-method.method-GET   { color: var(--m-get); }
.tbl tbody tr .col-method.method-POST  { color: var(--m-post); }
.tbl tbody tr .col-method.method-PUT   { color: var(--m-put); }
.tbl tbody tr .col-method.method-PATCH { color: var(--m-patch); }
.tbl tbody tr .col-method.method-DELETE{ color: var(--m-delete); }
.tbl tbody tr .col-method.method-WS    { color: var(--m-ws); }
/* URL primary line inherits method color slightly for quick scan */
.tbl tbody tr .col-url .url.method-GET   { color: color-mix(in srgb, var(--m-get) 85%, currentColor); }
.tbl tbody tr .col-url .url.method-POST  { color: color-mix(in srgb, var(--m-post) 85%, currentColor); }
.tbl tbody tr .col-url .url.method-PUT   { color: color-mix(in srgb, var(--m-put) 85%, currentColor); }
.tbl tbody tr .col-url .url.method-PATCH { color: color-mix(in srgb, var(--m-patch) 85%, currentColor); }
.tbl tbody tr .col-url .url.method-DELETE{ color: color-mix(in srgb, var(--m-delete) 85%, currentColor); }
.tbl tbody tr .col-url .url.method-WS    { color: color-mix(in srgb, var(--m-ws) 85%, currentColor); }
/* Drawer: method/status colored borders for code blocks */
#tab-request pre.code.method-GET,   #tab-response pre.code.method-GET   { border-left:4px solid var(--m-get); }
#tab-request pre.code.method-POST,  #tab-response pre.code.method-POST  { border-left:4px solid var(--m-post); }
#tab-request pre.code.method-PUT,   #tab-response pre.code.method-PUT   { border-left:4px solid var(--m-put); }
#tab-request pre.code.method-PATCH, #tab-response pre.code.method-PATCH { border-left:4px solid var(--m-patch); }
#tab-request pre.code.method-DELETE,#tab-response pre.code.method-DELETE{ border-left:4px solid var(--m-delete); }
#tab-request pre.code.method-WS,    #tab-response pre.code.method-WS    { border-left:4px solid var(--m-ws); }
/* Drawer: headers highlighted with method hue */
#tab-headers .code.headers.method-GET   .hk{ color: var(--m-get); }
#tab-headers .code.headers.method-POST  .hk{ color: var(--m-post); }
#tab-headers .code.headers.method-PUT   .hk{ color: var(--m-put); }
#tab-headers .code.headers.method-PATCH .hk{ color: var(--m-patch); }
#tab-headers .code.headers.method-DELETE .hk{ color: var(--m-delete); }
#tab-headers .code.headers.method-WS    .hk{ color: var(--m-ws); }

.kind-HTTP{color:#8ab4ff}.kind-WEBSOCKET{color:#7af59b}.kind-LOG{color:#eab308}
.status-2xx{color:#22c55e}.status-3xx{color:#fbbf24}.status-4xx{color:#fca5a5}.status-5xx{color:#fb7185}


:root{
  /* default scheme = android */
  --lv-v:#9E9E9E; /* VERBOSE */
  --lv-d:#2196F3; /* DEBUG   */
  --lv-i:#4CAF50; /* INFO    */
  --lv-w:#FFC107; /* WARN    */
  --lv-e:#F44336; /* ERROR   */
  --lv-a:#9C27B0; /* ASSERT  */
}
:root[data-scheme="android"]{ /* Android Studio */
  --lv-v:#9E9E9E; --lv-d:#2196F3; --lv-i:#4CAF50; --lv-w:#FFC107; --lv-e:#F44336; --lv-a:#9C27B0;
}
:root[data-scheme="xcode"]{ /* Xcode inspired */
  --lv-v:#8E8E93; --lv-d:#0A84FF; --lv-i:#34C759; --lv-w:#FF9F0A; --lv-e:#FF453A; --lv-a:#BF5AF2;
}
:root[data-scheme="vscode"]{ /* VS Code */
  --lv-v:#808080; --lv-d:#4FC1FF; --lv-i:#89D185; --lv-w:#CCA700; --lv-e:#F14C4C; --lv-a:#C586C0;
}
:root[data-scheme="grafana"]{ /* Grafana */
  --lv-v:#6b7280; --lv-d:#60a5fa; --lv-i:#22c55e; --lv-w:#f59e0b; --lv-e:#ef4444; --lv-a:#d946ef;
}
/* Text color for the Kind column when row has a level */
.tbl tbody tr.level-VERBOSE .col-kind{ color: var(--lv-v) }
.tbl tbody tr.level-DEBUG   .col-kind{ color: var(--lv-d) }
.tbl tbody tr.level-INFO    .col-kind{ color: var(--lv-i) }
.tbl tbody tr.level-WARN    .col-kind{ color: var(--lv-w) }
.tbl tbody tr.level-ERROR   .col-kind{ color: var(--lv-e) }
.tbl tbody tr.level-ASSERT  .col-kind{ color: var(--lv-a) }
/* Left accent bar tint using current scheme vars */
.tbl tbody tr.level-VERBOSE{ box-shadow: inset 4px 0 0 color-mix(in srgb, var(--lv-v) 55%, transparent) }
.tbl tbody tr.level-DEBUG  { box-shadow: inset 4px 0 0 color-mix(in srgb, var(--lv-d) 55%, transparent) }
.tbl tbody tr.level-INFO   { box-shadow: inset 4px 0 0 color-mix(in srgb, var(--lv-i) 55%, transparent) }
.tbl tbody tr.level-WARN   { box-shadow: inset 4px 0 0 color-mix(in srgb, var(--lv-w) 55%, transparent) }
.tbl tbody tr.level-ERROR  { box-shadow: inset 4px 0 0 color-mix(in srgb, var(--lv-e) 55%, transparent) }
.tbl tbody tr.level-ASSERT { box-shadow: inset 4px 0 0 color-mix(in srgb, var(--lv-a) 55%, transparent) }
/* Preserve accent on hover */
.tbl tbody tr.level-VERBOSE:hover{ box-shadow: inset 4px 0 0 color-mix(in srgb, var(--lv-v) 70%, transparent) }
.tbl tbody tr.level-DEBUG:hover  { box-shadow: inset 4px 0 0 color-mix(in srgb, var(--lv-d) 70%, transparent) }
.tbl tbody tr.level-INFO:hover   { box-shadow: inset 4px 0 0 color-mix(in srgb, var(--lv-i) 70%, transparent) }
.tbl tbody tr.level-WARN:hover   { box-shadow: inset 4px 0 0 color-mix(in srgb, var(--lv-w) 70%, transparent) }
.tbl tbody tr.level-ERROR:hover  { box-shadow: inset 4px 0 0 color-mix(in srgb, var(--lv-e) 70%, transparent) }
.tbl tbody tr.level-ASSERT:hover { box-shadow: inset 4px 0 0 color-mix(in srgb, var(--lv-a) 70%, transparent) }

:/* ===== Color URL/Summary & Time columns ===== */
/* Color by LOG level (for logger rows) */
.tbl tbody tr.level-VERBOSE .col-time,
.tbl tbody tr.level-VERBOSE .col-url { color: var(--lv-v); }
.tbl tbody tr.level-DEBUG   .col-time,
.tbl tbody tr.level-DEBUG   .col-url { color: var(--lv-d); }
.tbl tbody tr.level-INFO    .col-time,
.tbl tbody tr.level-INFO    .col-url { color: var(--lv-i); }
.tbl tbody tr.level-WARN    .col-time,
.tbl tbody tr.level-WARN    .col-url { color: var(--lv-w); }
.tbl tbody tr.level-ERROR   .col-time,
.tbl tbody tr.level-ERROR   .col-url { color: var(--lv-e); }
.tbl tbody tr.level-ASSERT  .col-time,
.tbl tbody tr.level-ASSERT  .col-url { color: var(--lv-a); }

/* Color by HTTP status class (for network rows) */
.tbl tbody tr.status-2xx .col-time, .tbl tbody tr.status-2xx .col-url .url { color:#22c55e; }
.tbl tbody tr.status-3xx .col-time, .tbl tbody tr.status-3xx .col-url .url { color:#fbbf24; }
.tbl tbody tr.status-4xx .col-time, .tbl tbody tr.status-4xx .col-url .url { color:#fca5a5; }
.tbl tbody tr.status-5xx .col-time, .tbl tbody tr.status-5xx .col-url .url { color:#fb7185; }

/* ===== Make all table column values colorful by row context ===== */
/* Logger rows: color all cells by level */
.tbl tbody tr.level-VERBOSE td:not(.col-actions){ color: var(--lv-v); }
.tbl tbody tr.level-DEBUG   td:not(.col-actions){ color: var(--lv-d); }
.tbl tbody tr.level-INFO    td:not(.col-actions){ color: var(--lv-i); }
.tbl tbody tr.level-WARN    td:not(.col-actions){ color: var(--lv-w); }
.tbl tbody tr.level-ERROR   td:not(.col-actions){ color: var(--lv-e); }
.tbl tbody tr.level-ASSERT  td:not(.col-actions){ color: var(--lv-a); }

/* HTTP rows: color all cells by status class */
:root{ --st-2xx:#22c55e; --st-3xx:#fbbf24; --st-4xx:#fca5a5; --st-5xx:#fb7185; }
.tbl tbody tr.status-2xx td:not(.col-actions){ color: var(--st-2xx); }
.tbl tbody tr.status-3xx td:not(.col-actions){ color: var(--st-3xx); }
.tbl tbody tr.status-4xx td:not(.col-actions){ color: var(--st-4xx); }
.tbl tbody tr.status-5xx td:not(.col-actions){ color: var(--st-5xx); }

/* Keep chips, icons and code readable (don’t inherit the tint) */
.tbl tbody tr td .muted,
.tbl tbody tr td .badge,
.tbl tbody tr td .material-symbols-outlined,
.tbl tbody tr td pre.code{ color: inherit; opacity: 0.95; }
:root{
  /* default scheme = android */
  --lv-v:#9E9E9E; /* VERBOSE */
  --lv-d:#2196F3; /* DEBUG   */
  --lv-i:#4CAF50; /* INFO    */
  --lv-w:#FFC107; /* WARN    */
  --lv-e:#F44336; /* ERROR   */
  --lv-a:#9C27B0; /* ASSERT  */
}
:root[data-scheme="android"]{ /* Android Studio */
  --lv-v:#9E9E9E; --lv-d:#2196F3; --lv-i:#4CAF50; --lv-w:#FFC107; --lv-e:#F44336; --lv-a:#9C27B0;
}
:root[data-scheme="xcode"]{ /* Xcode inspired */
  --lv-v:#8E8E93; --lv-d:#0A84FF; --lv-i:#34C759; --lv-w:#FF9F0A; --lv-e:#FF453A; --lv-a:#BF5AF2;
}
:root[data-scheme="vscode"]{ /* VS Code */
  --lv-v:#808080; --lv-d:#4FC1FF; --lv-i:#89D185; --lv-w:#CCA700; --lv-e:#F14C4C; --lv-a:#C586C0;
}
:root[data-scheme="grafana"]{ /* Grafana */
  --lv-v:#6b7280; --lv-d:#60a5fa; --lv-i:#22c55e; --lv-w:#f59e0b; --lv-e:#ef4444; --lv-a:#d946ef;
}
/* Text color for the Kind column when row has a level */
.tbl tbody tr.level-VERBOSE .col-kind{ color: var(--lv-v) }
.tbl tbody tr.level-DEBUG   .col-kind{ color: var(--lv-d) }
.tbl tbody tr.level-INFO    .col-kind{ color: var(--lv-i) }
.tbl tbody tr.level-WARN    .col-kind{ color: var(--lv-w) }
.tbl tbody tr.level-ERROR   .col-kind{ color: var(--lv-e) }
.tbl tbody tr.level-ASSERT  .col-kind{ color: var(--lv-a) }
/* Left accent bar tint using current scheme vars */
.tbl tbody tr.level-VERBOSE{ box-shadow: inset 4px 0 0 color-mix(in srgb, var(--lv-v) 55%, transparent) }
.tbl tbody tr.level-DEBUG  { box-shadow: inset 4px 0 0 color-mix(in srgb, var(--lv-d) 55%, transparent) }
.tbl tbody tr.level-INFO   { box-shadow: inset 4px 0 0 color-mix(in srgb, var(--lv-i) 55%, transparent) }
.tbl tbody tr.level-WARN   { box-shadow: inset 4px 0 0 color-mix(in srgb, var(--lv-w) 55%, transparent) }
.tbl tbody tr.level-ERROR  { box-shadow: inset 4px 0 0 color-mix(in srgb, var(--lv-e) 55%, transparent) }
.tbl tbody tr.level-ASSERT { box-shadow: inset 4px 0 0 color-mix(in srgb, var(--lv-a) 55%, transparent) }
/* Preserve accent on hover */
.tbl tbody tr.level-VERBOSE:hover{ box-shadow: inset 4px 0 0 color-mix(in srgb, var(--lv-v) 70%, transparent) }
.tbl tbody tr.level-DEBUG:hover  { box-shadow: inset 4px 0 0 color-mix(in srgb, var(--lv-d) 70%, transparent) }
.tbl tbody tr.level-INFO:hover   { box-shadow: inset 4px 0 0 color-mix(in srgb, var(--lv-i) 70%, transparent) }
.tbl tbody tr.level-WARN:hover   { box-shadow: inset 4px 0 0 color-mix(in srgb, var(--lv-w) 70%, transparent) }
.tbl tbody tr.level-ERROR:hover  { box-shadow: inset 4px 0 0 color-mix(in srgb, var(--lv-e) 70%, transparent) }
.tbl tbody tr.level-ASSERT:hover { box-shadow: inset 4px 0 0 color-mix(in srgb, var(--lv-a) 70%, transparent) }

/* Drawer styles (hidden by default) */
.drawer{
  position:relative;
  border:1px solid var(--line);
  border-radius:16px;
  height:calc(100vh - 180px);
  overflow:auto;
  flex:0 0 0;
  width:0;
  opacity:0;
  pointer-events:none;
  transition:flex-basis .2s ease,width .2s ease,opacity .2s ease,border-color .2s ease;
  background:var(--md-sys-color-surface);
  box-shadow:var(--elev-2);
}
body.drawer-open .drawer{ flex-basis:var(--drawer-w); width:var(--drawer-w); opacity:1; pointer-events:auto }

/* Drawer resizer */
.d-resize{ position:absolute; left:-6px; top:0; bottom:0; width:12px; cursor:col-resize; z-index:2; }
.d-resize::after{ content:""; position:absolute; inset:0; background:transparent; }
.d-resize:hover::after{ background:color-mix(in srgb, var(--md-sys-color-primary) 12%, transparent); }
body.resizing{ cursor:col-resize !important; }
body.resizing *{ user-select:none !important; }
.d-head{display:flex;justify-content:space-between;align-items:center;padding:16px 16px;border-bottom:1px solid var(--line)}
.d-title{font-weight:700}.d-sub{color:var(--muted);font-size:12px;margin-top:4px}
.tabs{display:flex;gap:8px;padding:10px 12px;border-bottom:1px solid var(--line)}
.tab{background:transparent;color:var(--text);border:1px solid var(--line);border-radius:999px;padding:6px 12px}
.tab.active{background:var(--surface)}
.panes{padding:12px}
.pane{display:none}
.pane.active{display:block}
.kv{display:grid;grid-template-columns:160px 1fr;gap:12px 16px}
.kv dt{color:var(--muted)} .kv dd{margin:0}
.kv .full{grid-column:1 / -1}

.cols{display:grid;grid-template-columns:1fr 1fr;gap:12px}

/* Colorful drawer content */
#drawer .d-title{ color: var(--md-sys-color-primary); }
#drawer .d-sub{ color: var(--md-sys-color-secondary); }
#drawer .kv dt{ color: var(--md-sys-color-on-surface-variant); font-weight:600; }
#drawer .kv dd{ color: var(--md-sys-color-on-surface); }
#drawer .badge{ background: var(--md-sys-color-primary-container); color: var(--md-sys-color-on-primary-container); border:none; }
#drawer pre.code{ background: var(--md-sys-color-surface-container-high); color: var(--md-sys-color-on-surface); }

/* Colorful panes */
.panes h4{ color: var(--md-sys-color-primary); margin: 6px 0 8px; }
.panes h5{ color: var(--md-sys-color-secondary); margin: 4px 0 6px; }
#tab-request pre.code,
#tab-response pre.code,
#tab-headers pre.code{ background: var(--md-sys-color-surface-container-high); border-color: var(--md-sys-color-outline-variant); }

/* Extra color accents inside panes */
.panes a{ color: var(--md-sys-color-primary); text-decoration: none; }
.panes a:hover{ text-decoration: underline; }
.panes .badge{ background: var(--md-sys-color-primary-container); color: var(--md-sys-color-on-primary-container); border: none; }
.panes pre.code{ border-left: 3px solid var(--md-sys-color-primary); }
.panes .muted{ color: var(--md-sys-color-on-surface-variant); }
.panes .callout{ border:1px solid var(--md-sys-color-outline-variant); background: var(--md-sys-color-surface-container); border-radius: 12px; padding: 10px 12px; }

/* Headers highlighting inside code blocks */
.code.headers { background: var(--md-sys-color-surface-container-high); }
.code.headers .hk{ color: var(--md-sys-color-primary); font-weight:600; }
.code.headers .hv{ color: var(--md-sys-color-on-surface); }

/* Colorful JSON syntax highlighting (applies in drawer + table) */
.json .k { color:#d19a66; }   /* keys - orange */
.json .s { color:#98c379; }   /* strings - green */
.json .n { color:#61afef; }   /* numbers - blue */
.json .b { color:#c678dd; }   /* booleans - purple */
.json .null { color:#e06c75; } /* null - red */

/* Colorful summary block container */
#drawer .sum {
  padding: 8px;
}
#drawer .sum pre.code {
  background: transparent;
  font-family: ui-monospace, Menlo, monospace;
  font-size: 13px;
  line-height: 1.4;
}

/* Code blocks */
.code{background:var(--codebg);color:var(--code);border:1px solid var(--line);border-radius:12px;padding:12px;overflow:auto;max-height:22vh;white-space:pre-wrap;word-break:break-word}
#ov-summary{white-space:pre-wrap;word-break:break-word;width:100%;max-height:50vh;overflow:auto}
.curl{display:flex;gap:8px;align-items:flex-start;width:100%}
.curl .code{flex:1;min-height:160px}
#ov-curl{white-space:pre-wrap;word-break:break-all;overflow:auto;max-height:70vh;width:100%}

/* WebSocket direction glyphs */
.ws-ico{margin-left:6px;font:12px ui-monospace,Menlo,monospace;vertical-align:middle}
.ws-send{ color: var(--ws-send); }
.ws-recv{ color: var(--ws-recv); }

/* Logcat line styling */
.lc{font:12px ui-monospace,Menlo,monospace; white-space:pre-wrap; word-break:break-word}
.lc-ts{color:var(--muted)}
.lc-prio{font-weight:700}
.lc-tag{color:#00bcd4}

/* Modes */
body.mode-network .col-tag{display:none}
body.mode-log .col-method,body.mode-log .col-status,body.mode-log .col-actions{display:none}
body.mode-log .col-url .url{display:none}

/* Helpers */
.muted{color:var(--muted)} .badge{border:1px solid var(--line);border-radius:6px;padding:2px 6px;background:transparent;font:12px ui-monospace,Menlo,monospace}
.hidden{display:none !important}

/* Material Symbols font setup */
.material-symbols-outlined{font-family:'Material Symbols Outlined';font-weight:normal;font-style:normal;font-size:20px;line-height:1;letter-spacing:normal;text-transform:none;display:inline-block;white-space:nowrap;word-wrap:normal;direction:ltr;-webkit-font-feature-settings:'liga';-webkit-font-smoothing:antialiased;font-variation-settings:'FILL' 0,'wght' 400,'GRAD' 0,'opsz' 24}

/* Theme toggle icon visibility (default: hide both, then show correct for theme) */
#themeToggle .ico-sun,
#themeToggle .ico-moon { display:none }
:root[data-theme="light"] #themeToggle .ico-sun{ display:block }
:root[data-theme="light"] #themeToggle .ico-moon{ display:none }
:root[data-theme="dark"] #themeToggle .ico-sun{ display:none }
:root[data-theme="dark"] #themeToggle .ico-moon{ display:block }

/* Responsive */
@media (max-width:1024px){ .tbl thead th,.tbl tbody td{padding:10px} .col-actions{width:140px} }
@media (max-width:900px){ #logtbl thead .col-id,#logtbl tbody .col-id{display:none} #logtbl thead .col-kind,#logtbl tbody .col-kind{display:none} .col-time{width:96px}.col-method{width:80px}.col-status{width:80px}.col-actions{width:120px} .panel{max-height:calc(100vh - 220px)} .kv{grid-template-columns:140px 1fr} }
@media (max-width:700px){
  .shell{ flex-direction:column; }
  /* Hide less critical columns by default on narrow viewports */
  #logtbl thead .col-method, #logtbl tbody .col-method{ display:none }
  #logtbl thead .col-status, #logtbl tbody .col-status{ display:none }
  /* Make URL/Summary breathe */
  .col-url{ width:auto }
}
@media (max-width:768px){
  .tbl{ font-size:13px }
  .hdr{padding:10px} .bar>*{flex:1 1 100%} .field{width:100%} .input,.select{width:100%} .stats{padding:8px 10px} .shell{padding:8px} .kv{grid-template-columns:1fr} .kv .full{grid-column:1 / -1} .cols{grid-template-columns:1fr} #ov-curl{max-height:50vh} #ov-summary{max-height:40vh}
}
@media (max-width:600px){
  .drawer{
    position:fixed;
    left:0; right:0;
    top:96px; /* below header + stats */
    bottom:0;
    z-index:60;
    width:auto; flex:0 0 auto;
    opacity:0; pointer-events:none;
    max-width:none;
    border-radius:16px 16px 0 0;
    height:auto;
  }
  body.drawer-open .drawer{ opacity:1; pointer-events:auto; }
  .d-head{position:sticky;top:0;background:var(--md-sys-color-surface);z-index:5}
  .tabs{position:sticky;top:48px;background:var(--md-sys-color-surface);z-index:4}
  .panel{max-height:calc(100vh - 260px)}
  .tbl thead th,.tbl tbody td{padding:9px}
  .col-actions{display:none}
  /* Compact table on phones */
  #logtbl thead .col-tag, #logtbl tbody .col-tag{ display:none }
  .tbl thead th, .tbl tbody td{ padding:8px }
  .hdr{ flex-wrap:wrap }
  .bar{ gap:6px }
  .stats{ padding:6px 8px }
  /* Popovers snap to left for small screens to avoid clipping */
  .popover{ left:0; right:auto; }
  .fp{ width: calc(100vw - 24px); max-width:none; }
}
@media (max-width:420px){
  .titles{display:none} .chip{font-size:12px} .btn{padding:7px 10px} .icon{width:32px;height:32px}
  #logtbl thead .col-time, #logtbl tbody .col-time{ display:none }
  .tbl thead th, .tbl tbody td{ padding:7px }
}
""".trimIndent()

    val appJs = """
        // ========================= LogTap Viewer (fixed runtime JS) =========================
        // Changes:
        // - Fixed cURL builder (no nested template literal confusions)
        // - Removed fragile ${'$'}{JSON.stringify(...)} nesting inside Kotlin
        // - Stronger error logging so JS errors don't silently stop rendering
        const colorScheme = document.querySelector('#colorScheme');

        function applyScheme(s){
          const scheme = (s||'').toLowerCase();
          const valid = ['android','xcode','vscode','grafana'];
          const pick = valid.includes(scheme) ? scheme : 'android';
          document.documentElement.setAttribute('data-scheme', pick);
          if(colorScheme) colorScheme.value = pick;
          try{ localStorage.setItem('logtap:scheme', pick); }catch{}
        }
        function initScheme(){
          let s = 'android';
          try{ s = localStorage.getItem('logtap:scheme') || 'android'; }catch{}
          applyScheme(s);
        }
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
        
        const chipTotal = document.querySelector('#chipTotal');
        const chipHttp  = document.querySelector('#chipHttp');
        const chipWs    = document.querySelector('#chipWs');
        const chipLog   = document.querySelector('#chipLog');
        const chipGet   = document.querySelector('#chipGet');
        const chipPost  = document.querySelector('#chipPost');

        // Column toggles
        const colId = document.querySelector('#colId');
        const colTime = document.querySelector('#colTime');
        const colKind = document.querySelector('#colKind');
        const colTag = document.querySelector('#colTag');
        const colMethod = document.querySelector('#colMethod');
        const colStatus = document.querySelector('#colStatus');
        const colUrl = document.querySelector('#colUrl');
        const colActions = document.querySelector('#colActions');
        
        const drawer = document.querySelector('#drawer');
        const drawerClose = document.querySelector('#drawerClose');
        const tabs = Array.from(document.querySelectorAll('.tab'));
        const drawerResizer = document.querySelector('#drawerResizer');
        const DRAWER_MIN = 360, DRAWER_MAX = 1000;
        // ---- Drawer width persistence & drag-resize ----
        function loadDrawerWidth(){
          try{
            const v = Number(localStorage.getItem('logtap:drawerW')||'');
            if(v && !Number.isNaN(v)) document.documentElement.style.setProperty('--drawer-w', v+'px');
          }catch{}
        }
        function saveDrawerWidth(px){ try{ localStorage.setItem('logtap:drawerW', String(px)); }catch{} }
        let dragStartX=0, dragStartW=0, dragging=false;
        function startResize(e){ if(!drawer) return; dragging=true; document.body.classList.add('resizing'); const r=drawer.getBoundingClientRect(); dragStartW=r.width; dragStartX=e.clientX; window.addEventListener('mousemove', onResizeMove); window.addEventListener('mouseup', endResize); }
        function onResizeMove(e){ if(!dragging) return; const dx = dragStartX - e.clientX; let nw = Math.round(dragStartW + dx); nw = Math.max(DRAWER_MIN, Math.min(DRAWER_MAX, nw)); document.documentElement.style.setProperty('--drawer-w', nw+'px'); }
        function endResize(){ if(!dragging) return; dragging=false; document.body.classList.remove('resizing'); const cur = getComputedStyle(document.documentElement).getPropertyValue('--drawer-w').trim(); const px = Number(cur.replace('px',''))||0; if(px>0) saveDrawerWidth(px); window.removeEventListener('mousemove', onResizeMove); window.removeEventListener('mouseup', endResize); }
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
          if (themeToggle) { const next = (theme==='dark'?'light':'dark'); themeToggle.setAttribute('title', 'Switch to '+next+' mode'); themeToggle.setAttribute('aria-label', 'Switch to '+next+' mode'); }
        }
        function initTheme(){
          let t = localStorage.getItem('logtap:theme');
          if (!t) t = (window.matchMedia && window.matchMedia('(prefers-color-scheme: light)').matches) ? 'light' : 'dark';
          applyTheme(t);
        }

        // ---- Preferences ----
        function initPrefs(){
          try{
            const v = localStorage.getItem('logtap:jsonPretty');
            if(jsonPretty && v!==null){ jsonPretty.checked = (v === '1'); }
          }catch{}
        }

        // ---- Column visibility persistence ----
        function loadCols(){
          try{ return JSON.parse(localStorage.getItem('logtap:cols')||'{}') }catch{ return {} }
        }
        function saveCols(cfg){ try{ localStorage.setItem('logtap:cols', JSON.stringify(cfg||{})) }catch{}
        }
        function applyCols(cfg){
          const m = Object.assign({
            id:true,time:true,kind:true,tag:true,method:true,status:true,url:true,actions:true
          }, cfg||{});
          // set checkbox states
          if(colId) colId.checked = !!m.id;
          if(colTime) colTime.checked = !!m.time;
          if(colKind) colKind.checked = !!m.kind;
          if(colTag) colTag.checked = !!m.tag;
          if(colMethod) colMethod.checked = !!m.method;
          if(colStatus) colStatus.checked = !!m.status;
          if(colUrl) colUrl.checked = !!m.url;
          if(colActions) colActions.checked = !!m.actions;
          // toggle body classes
          bodyEl.classList.toggle('hide-col-id',      !m.id);
          bodyEl.classList.toggle('hide-col-time',    !m.time);
          bodyEl.classList.toggle('hide-col-kind',    !m.kind);
          bodyEl.classList.toggle('hide-col-tag',     !m.tag);
          bodyEl.classList.toggle('hide-col-method',  !m.method);
          bodyEl.classList.toggle('hide-col-status',  !m.status);
          bodyEl.classList.toggle('hide-col-url',     !m.url);
          bodyEl.classList.toggle('hide-col-actions', !m.actions);
        }
        let colCfg = loadCols();

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

       function logcatLine(ev){
          // Return only the message content for table summary; strip duplicates like ts/level/tag/pid
          let msg = (ev.summary!=null && ev.summary!=='') ? String(ev.summary) : (ev.message || '');
          if (!msg) return '';
          try{
            // Strip leading timestamp like "MM-DD HH:mm:ss.SSS" or "YYYY-MM-DD HH:mm:ss.SSS"
            msg = msg.replace(/^\s*(\d{2}|\d{4})-\d{2}-\d{2}\s+\d{2}:\d{2}:\d{2}(?:\.\d{1,3})?\s*/,'');
            // Strip classic logcat prefix: "E/Tag(123): " or "D/Tag:" etc.
            msg = msg.replace(/^\s*[VDIWEA]\s*\/\s*[^:()]+(?:\([^)]*\))?\s*:\s*/,'');
            // If we know the level or tag, strip them if they appear at the start like "[INFO] Tag:"
            const lvl = (levelOf(ev)||'').toUpperCase();
            if(lvl) msg = msg.replace(new RegExp('^\\s*\\['+lvl+'\\]\\s*:?\\s*',''),'');
            const tag = (ev.tag||'');
            if(tag) msg = msg.replace(new RegExp('^\\s*'+tag.replace(/[.*+?^${'$'}}()|[\\]\\\\]/g,'\\$&')+'\\s*:?\\s*'),'');
          }catch{}
          return msg.trim();
       }

       function applyMode(){
          const m = viewMode?.value || 'mix';
          bodyEl.classList.remove('mode-mix','mode-network','mode-log');
          bodyEl.classList.add('mode-'+m);
       }

        // ---- Stat chip filtering (toggleable) ----
        const allChips = [];
        let activeChip = null; // currently selected chip element or null
        function highlightChip(el){
          allChips.forEach(c=>c?.classList.remove('active'));
          if(el){ el.classList.add('active'); activeChip = el; } else { activeChip = null; }
        }
        function resetFilters(){
          // Text & selects
          if(search){ search.value=''; filterText=''; }
          if(methodFilter) methodFilter.value='';
          if(statusFilter) statusFilter.value='';
          if(statusCodeFilter) statusCodeFilter.value='';
          if(levelFilter) levelFilter.value='';
          if(viewMode){ viewMode.value='mix'; applyMode(); }

          // Pretty JSON defaults to OFF
          if(jsonPretty){
            jsonPretty.checked = false;
            try{ localStorage.setItem('logtap:jsonPretty','0'); }catch{}
          }

          // Columns: show all
          const allTrue = {id:true,time:true,kind:true,tag:true,method:true,status:true,url:true,actions:true};
          try{ localStorage.setItem('logtap:cols', JSON.stringify(allTrue)); }catch{}
          applyCols(allTrue);

          // Clear active stat chip highlight
          highlightChip(null);
        }
        function applyStatFilter(kind, toggledOff){
          if (toggledOff) {
            // clear any visual/aria selection on chips
            allChips.forEach(ch=>{ ch?.setAttribute('aria-pressed','false'); ch?.classList.remove('active'); });
            highlightChip(null); // also sets activeChip = null
            // force view back to Mix and clear method/level/status filters
            if(viewMode) viewMode.value = 'mix';
            if(methodFilter) methodFilter.value = '';
            if(levelFilter) levelFilter.value = '';
            if(statusFilter) statusFilter.value = '';
            if(statusCodeFilter) statusCodeFilter.value = '';
            applyMode();
            // user clicked the active chip again -> clear selection and reset filters
            resetFilters();
            renderAll();
            return;
          }
          switch(kind){
            case 'TOTAL':
              resetFilters();
              if(viewMode) viewMode.value='mix';
              applyMode();
              break;
            case 'HTTP':
              resetFilters();
              if(viewMode) viewMode.value='network';
              if(methodFilter) methodFilter.value='';
              applyMode();
              break;
            case 'WS':
              resetFilters();
              if(viewMode) viewMode.value='network';
              if(methodFilter) methodFilter.value='WS';
              applyMode();
              break;
            case 'LOG':
              resetFilters();
              if(viewMode) viewMode.value='log';
              applyMode();
              break;
            case 'GET':
              resetFilters();
              if(viewMode) viewMode.value='network';
              if(methodFilter) methodFilter.value='GET';
              applyMode();
              break;
            case 'POST':
              resetFilters();
              if(viewMode) viewMode.value='network';
              if(methodFilter) methodFilter.value='POST';
              applyMode();
              break;
          }
          renderAll();
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
          // Add WS direction classes to row
          const isSend = (dir === 'OUTBOUND' || dir === 'REQUEST');
          const isRecv = (dir === 'INBOUND'  || dir === 'RESPONSE');
          if (kind === 'WEBSOCKET'){
            if (isSend) tr.classList.add('ws-send');
            else if (isRecv) tr.classList.add('ws-recv');
          }
          // Build WS direction icon (send/receive)
          let wsIconHtml = '';
          const mU = (ev.method ? String(ev.method).toUpperCase() : (kind==='WEBSOCKET' ? 'WS' : ''));
          if (kind === 'WEBSOCKET') {
            wsIconHtml = isSend
              ? '<span class="ws-ico ws-send" title="WebSocket send">⇧</span>'
              : isRecv
                ? '<span class="ws-ico ws-recv" title="WebSocket receive">⇩</span>'
                : '<span class="ws-ico" title="WebSocket">∿</span>';
          }
          const lvl = (kind==='LOG') ? levelOf(ev) : '';
          if(lvl) tr.classList.add('level-'+lvl);
          const stCls = classForStatus(ev.status);
          if (stCls) tr.classList.add(stCls);
          const tagTxt = ev.tag ? String(ev.tag) : '';
          tr.dataset.id = String(ev.id ?? '');
          if (mU) tr.classList.add('method-'+mU);
          const actions = document.createElement('div'); actions.className='action-row';
          if(kind==='HTTP') {
            const copyBtn = document.createElement('button');
            copyBtn.className = 'icon';
            copyBtn.title = 'Copy cURL';
            copyBtn.innerHTML = '<span class="material-symbols-outlined" aria-hidden="true">content_copy</span>';
            copyBtn.addEventListener('click', async (e)=>{
              e.preventDefault(); e.stopPropagation();
              const ok = await copyText(curlFor(ev));
              if(ok){ copyBtn.classList.add('active'); setTimeout(()=> copyBtn.classList.remove('active'), 800); }
            });
            actions.appendChild(copyBtn);
          }
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
            `<td class="col-method method-${'$'}{mU}">${'$'}{escapeHtml(ev.method || (kind==='WEBSOCKET'?'WS':''))}</td>`+
            `<td class="col-status ${'$'}{classForStatus(ev.status)}">${'$'}{ev.status ?? ''}</td>`+
            (kind==='LOG'
              ? (`<td class="col-url"><div class="url"><div class="lc">${'$'}{logcatLine(ev)}</div></div></td>`)
              : (`<td class="col-url">`
                   + `<div class="url method-${'$'}{mU} ${'$'}{kind==='WEBSOCKET' ? (isSend?'ws ws-send': (isRecv?'ws ws-recv':'ws')) : ''}">${'$'}{escapeHtml(ev.url || '')}</div>`
                   + (ev.summary ? `<div class="muted">${'$'}{escapeHtml(ev.summary)}</div>` : '')
                 + `</td>`)
            )
          // pretty body preview under URL cell (respects global Pretty JSON toggle)
          if (ev.bodyPreview) {
            const pre = document.createElement('pre');
            pre.className = 'code mini body' + (jsonPretty?.checked ? ' json' : '');
            if (kind === 'WEBSOCKET'){
              pre.classList.add(isSend ? 'ws-send' : (isRecv ? 'ws-recv' : ''));
            }
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
        function headersToLines(map){
          try{ return Object.entries(map||{}).map(([k,v])=> k+': '+(Array.isArray(v)?v.join(', '): String(v))).join('\n'); }catch{ return ''; }
        }
        function hlHeaders(text){
          return String(text||'').split('\n').map(line=>{
            const m = line.match(/^([^:]+):\s*(.*)$/);
            if(m){
              return '<span class="hk">'+escapeHtml(m[1])+'</span>: <span class="hv">'+escapeHtml(m[2])+'</span>';
            }
            return escapeHtml(line);
          }).join('\n');
        }
        function setHeaders(id, map){
          const el = document.getElementById(id); if(!el) return;
          const txt = headersToLines(map);
          el.innerHTML = hlHeaders(txt);
        }
        function activateTab(name){ tabs.forEach(b=>b.classList.toggle('active', b.dataset.tab===name)); document.querySelectorAll('.pane').forEach(p=>p.classList.toggle('active', p.id==='tab-'+name)); }
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
          let title = '';
          if (kind === 'LOG') {
            // For logger entries: show the tag (fallback to summary or 'LOG')
            title = ev.tag || ev.summary || 'LOG';
          } else {
            // For interceptor entries (HTTP / WebSocket): show the URL only
            title = ev.url || ev.summary || '';
          }
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
          // Colorize drawer content by method
          const mU = (ev.method ? String(ev.method).toUpperCase() : (kind==='WEBSOCKET' ? 'WS' : ''));
          const reqPre = document.getElementById('req-body');
          const respPre = document.getElementById('resp-body');
          [reqPre, respPre].forEach(p=>{ if(!p) return; p.classList.remove('method-GET','method-POST','method-PUT','method-PATCH','method-DELETE','method-WS'); if(mU) p.classList.add('method-'+mU); });
          const reqH = document.getElementById('req-headers');
          const respH = document.getElementById('resp-headers');
          [reqH, respH].forEach(h=>{ if(!h) return; h.classList.add('headers'); h.classList.remove('method-GET','method-POST','method-PUT','method-PATCH','method-DELETE','method-WS'); if(mU) h.classList.add('method-'+mU); });
          setHeaders('req-headers', ev.headers || {});
          setHeaders('resp-headers', ev.responseHeaders || {});
          // --- WebSocket direction & opcode classes for drawer code/headers ---
          const isSend = (dir === 'OUTBOUND' || dir === 'REQUEST');
          const isRecv = (dir === 'INBOUND'  || dir === 'RESPONSE');
          const op = (ev.op || ev.opcode || '').toString().toLowerCase(); // 'text','binary','ping','pong','close'
          const wsClass = (kind==='WEBSOCKET') ? (op==='ping'?'ws-ping': op==='pong'?'ws-pong': op==='close'?'ws-close': (isSend?'ws-send': isRecv?'ws-recv':'')) : '';
          [reqPre, respPre].forEach(p=>{
            if(!p) return;
            p.classList.remove('ws-send','ws-recv','ws-ping','ws-pong','ws-close');
            if(wsClass) p.classList.add(wsClass);
          });
          [reqH, respH].forEach(h=>{
            if(!h) return;
            h.classList.remove('ws-send','ws-recv','ws-ping','ws-pong','ws-close');
            if(wsClass) h.classList.add(wsClass);
          });
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
        function onColChange(){
          colCfg = {
            id: !!(colId?.checked), time: !!(colTime?.checked), kind: !!(colKind?.checked), tag: !!(colTag?.checked),
            method: !!(colMethod?.checked), status: !!(colStatus?.checked), url: !!(colUrl?.checked), actions: !!(colActions?.checked)
          };
          saveCols(colCfg); applyCols(colCfg);
        }
        [colId,colTime,colKind,colTag,colMethod,colStatus,colUrl,colActions].forEach(el=> el?.addEventListener('change', onColChange));
        // Apply saved column visibility on startup
        applyCols(colCfg);
        search?.addEventListener('input', ()=>{ filterText = search.value.trim().toLowerCase(); renderAll(); });
        // Make stat chips clickable (toggle selection on re-click)
        allChips.push(chipTotal, chipHttp, chipWs, chipLog, chipGet, chipPost);
        // Set default selection to "Total" chip on boot
        highlightChip(chipTotal);
        chipTotal.setAttribute('aria-pressed','true');
        chipTotal.classList.add('active');
        allChips.forEach(c=>{
          if(!c) return;
          c.classList.add('clickable');
          c.setAttribute('role','button');
          c.setAttribute('tabindex','0');
          // Set all chips aria-pressed to false except chipTotal
          if (c !== chipTotal) c.setAttribute('aria-pressed','false');
          c.addEventListener('click', ()=>{
            const isSame = (activeChip === c);
            // update aria and visual state
            if(isSame){
              c.setAttribute('aria-pressed','false');
              highlightChip(null);
            } else {
              allChips.forEach(ch=>{ ch?.setAttribute('aria-pressed','false'); ch?.classList.remove('active'); });
              c.setAttribute('aria-pressed','true');
              c.classList.add('active');
              activeChip = c;
            }
            const kind = (c===chipTotal)?'TOTAL':(c===chipHttp)?'HTTP':(c===chipWs)?'WS':(c===chipLog)?'LOG':(c===chipGet)?'GET':'POST';
            applyStatFilter(kind, isSame);
          });
          c.addEventListener('keydown', (e)=>{ if(e.key==='Enter' || e.key===' '){ e.preventDefault(); c.click(); } });
        });
        methodFilter?.addEventListener('change', renderAll);
        viewMode?.addEventListener('change', ()=>{ applyMode(); renderAll(); });
        statusFilter?.addEventListener('change', renderAll);
        statusCodeFilter?.addEventListener('input', renderAll);
        levelFilter?.addEventListener('change', renderAll);
        jsonPretty?.addEventListener('change', ()=>{
          try{ localStorage.setItem('logtap:jsonPretty', jsonPretty.checked ? '1' : '0'); }catch{}
          renderAll();
          if (currentEv) openDrawer(currentEv);
        });
        colorScheme?.addEventListener('change', ()=> applyScheme(colorScheme.value));
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
        // Filters actions (Reset / Apply)
        const filtersReset = document.querySelector('#filtersReset');
        const filtersClose = document.querySelector('#filtersClose');
        filtersReset?.addEventListener('click', (e)=>{ e.preventDefault(); e.stopPropagation(); resetFilters(); renderAll(); });
        filtersClose?.addEventListener('click', (e)=>{ e.preventDefault(); e.stopPropagation(); filtersPanel?.classList.add('hidden'); });

        // Prevent clicks inside popovers from bubbling to document
        filtersPanel?.addEventListener('click', (e)=> e.stopPropagation());
        exportMenu?.addEventListener('click', (e)=> e.stopPropagation());
        document.addEventListener('click', (e)=> closePopovers(e));
        document.addEventListener('keydown', (e)=>{ if(e.key==='Escape') closePopovers(); });
        themeToggle?.addEventListener('click', ()=>{ const cur = document.documentElement.getAttribute('data-theme') || 'dark'; const next = cur==='dark'?'light':'dark'; applyTheme(next); localStorage.setItem('logtap:theme', next); });
        
        // ---- Bootstrap + WS status ----
        async function bootstrap(){
          initTheme();
          initPrefs();
          initScheme();
          try{ const res = await fetch('/api/logs?limit=1000'); if(!res.ok) throw new Error('HTTP '+res.status); rows = await res.json(); }
          catch(err){ console.error('[LogTap] failed to fetch /api/logs', err); rows=[]; }
          applyMode();
          renderAll();
          try{
            const setWs = (text, cls)=>{ if(wsStatus){ wsStatus.textContent = text; wsStatus.classList.remove('status-on','status-off','status-connecting'); if(cls) wsStatus.classList.add(cls); } };
            setWs('● Connecting…', 'status-connecting');
            const ws = new WebSocket((location.protocol==='https:'?'wss':'ws')+'://'+location.host+'/ws');
            const on = ()=> setWs('● Connected', 'status-on');
            const off = ()=> setWs('● Disconnected', 'status-off');
            ws.addEventListener('open', on);
            ws.addEventListener('close', off);
            ws.addEventListener('error', off);
            ws.onmessage = (e)=>{ try{ const ev = JSON.parse(e.data); rows.push(ev); if(matchesFilters(ev)){ tbody.appendChild(renderRow(ev)); if(autoScroll?.checked) tbody.lastElementChild?.scrollIntoView({block:'end'}); renderStats(); } }catch(parseErr){ console.warn('[LogTap] bad WS payload', parseErr); } };
          }catch(wsErr){ console.warn('[LogTap] WS setup failed', wsErr); if(wsStatus){ wsStatus.textContent='● Disconnected'; wsStatus.classList.remove('status-on','status-connecting'); wsStatus.classList.add('status-off'); } }
        }
        // Load saved drawer width and enable drag to resize
        loadDrawerWidth();
        drawerResizer?.addEventListener('mousedown', (e)=>{ e.preventDefault(); startResize(e); });
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