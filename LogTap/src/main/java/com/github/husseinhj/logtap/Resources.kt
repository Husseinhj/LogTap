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
  <body>
    <header class="topbar">
      <div class="brand">LogTap</div>
      <div class="controls">
        <div class="input-wrap">
          <input id="search" type="search" placeholder="Search (url, method, headers, body)…  ⌘/Ctrl + K" />
          <kbd class="key">K</kbd>
        </div>
        <select id="methodFilter" title="Method filter">
          <option value="">All</option>
          <option>GET</option><option>POST</option><option>PUT</option>
          <option>PATCH</option><option>DELETE</option><option>WS</option>
        </select>
        <select id="statusFilter" title="Status filter (HTTP)">
          <option value="">Any</option>
          <option value="2xx">2xx</option>
          <option value="3xx">3xx</option>
          <option value="4xx">4xx</option>
          <option value="5xx">5xx</option>
        </select>
        <label class="chk"><input type="checkbox" id="autoScroll" checked/> Auto-scroll</label>
        <button id="clearBtn" class="ghost">Clear</button>
      </div>
    </header>

    <section class="stats">
      <div class="chip" id="chipTotal">Total: 0</div>
      <div class="chip" id="chipHttp">HTTP: 0</div>
      <div class="chip" id="chipWs">WS: 0</div>
      <div class="chip" id="chipGet">GET: 0</div>
      <div class="chip" id="chipPost">POST: 0</div>
    </section>

    <main class="layout">
      <div class="table-wrap">
        <table id="logtbl">
          <thead>
            <tr>
              <th class="col-id">ID</th>
              <th class="col-time">Time</th>
              <th class="col-kind">Kind</th>
              <th class="col-dir">Dir</th>
              <th class="col-method">Method</th>
              <th class="col-status">Status</th>
              <th class="col-url">URL / Summary</th>
            </tr>
          </thead>
          <tbody></tbody>
        </table>
      </div>

      <aside id="drawer" class="drawer hidden">
        <header class="drawer-head">
          <div>
            <div id="drawerTitle" class="drawer-title">Details</div>
            <div id="drawerSub" class="drawer-sub"></div>
          </div>
          <button id="drawerClose" class="icon-btn" title="Close (Esc)">×</button>
        </header>
        <nav class="tabs">
          <button class="tab active" data-tab="overview">Overview</button>
          <button class="tab" data-tab="request">Request</button>
          <button class="tab" data-tab="response">Response</button>
          <button class="tab" data-tab="headers">Headers</button>
        </nav>
        <section class="tabpanes">
          <div class="tabpane active" id="tab-overview">
            <dl class="kv">
              <div><dt>ID</dt><dd id="ov-id"></dd></div>
              <div><dt>Time</dt><dd id="ov-time"></dd></div>
              <div><dt>Kind</dt><dd id="ov-kind"></dd></div>
              <div><dt>Direction</dt><dd id="ov-dir"></dd></div>
              <div><dt>Method</dt><dd id="ov-method"></dd></div>
              <div><dt>Status</dt><dd id="ov-status"></dd></div>
              <div><dt>URL</dt><dd id="ov-url"></dd></div>
              <div><dt>Summary</dt><dd id="ov-summary"></dd></div>
              <div><dt>Took</dt><dd id="ov-took"></dd></div>
              <div><dt>Thread</dt><dd id="ov-thread"></dd></div>
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
:root{
  --bg:#0b0d10; --panel:#0f1318; --muted:#9aa4b2; --text:#e6edf3;
  --accent:#7aa2f7; --ok:#a6e3a1; --warn:#ffd479; --err:#ff6b6b; --chip:#1c232b;
  --border:#1e2630; --row:#121820; --row-hover:#141c25; --code:#0d1117;
}
*{box-sizing:border-box}
html,body{height:100%}
body{margin:0;background:var(--bg);color:var(--text);font:14px system-ui,Segoe UI,Roboto,Helvetica,Arial,sans-serif}

.topbar{position:sticky;top:0;z-index:10;display:flex;gap:16px;align-items:center;
  padding:12px 16px;background:var(--panel);border-bottom:1px solid var(--border)}
.brand{font-weight:700;letter-spacing:.3px}
.controls{display:flex;gap:8px;align-items:center;margin-left:auto}
.input-wrap{position:relative}
.input-wrap .key{position:absolute;right:8px;top:50%;transform:translateY(-50%);opacity:.6;background:#0002;border:1px solid var(--border);border-radius:6px;padding:0 6px;font:11px ui-monospace,Menlo,monospace}
input[type="search"], select{background:var(--row);color:var(--text);border:1px solid var(--border);border-radius:10px;padding:8px 10px}
.chk{opacity:.9}
button{background:var(--accent);color:#fff;border:0;border-radius:10px;padding:8px 12px;cursor:pointer}
button.ghost{background:transparent;border:1px solid var(--border);color:var(--text)}
button.icon-btn{width:28px;height:28px;border-radius:8px;background:transparent;border:1px solid var(--border);color:var(--text);font-size:18px;line-height:1}

.stats{display:flex;gap:8px;flex-wrap:wrap;padding:8px 16px;border-bottom:1px solid var(--border);background:var(--panel)}
.chip{background:var(--chip);border:1px solid var(--border);padding:6px 10px;border-radius:999px;color:var(--muted)}

.layout{display:grid;grid-template-columns:1fr 420px;gap:0}
.table-wrap{overflow:auto;max-height:calc(100vh - 122px)}
table{width:100%;border-collapse:collapse}
th,td{padding:10px 12px;border-bottom:1px solid var(--border);vertical-align:top}
thead th{position:sticky;top:0;background:var(--panel);z-index:5}
tbody tr{background:var(--row);cursor:pointer}
tbody tr:hover{background:var(--row-hover)}
.col-id{width:72px}.col-time{width:120px}.col-kind{width:92px}.col-dir{width:96px}.col-method{width:84px}.col-status{width:84px}.col-url{width:auto}

.kind-HTTP{color:#8ab4ff}.kind-WEBSOCKET{color:#7af59b}
.dir-REQUEST,.dir-OUTBOUND{color:var(--warn)}.dir-RESPONSE,.dir-INBOUND{color:var(--ok)}.dir-ERROR{color:var(--err)}.dir-STATE{color:#9bb}
.status-2xx{color:var(--ok)}.status-3xx{color:#ffd479}.status-4xx{color:#ffb4a2}.status-5xx{color:#ff8787}

.drawer{border-left:1px solid var(--border);background:var(--panel);height:calc(100vh - 122px);overflow:auto}
.drawer.hidden{display:none}
.drawer-head{display:flex;justify-content:space-between;align-items:center;padding:12px 14px;border-bottom:1px solid var(--border)}
.drawer-title{font-weight:600}.drawer-sub{color:var(--muted);font-size:12px;margin-top:4px}

.tabs{display:flex;gap:6px;padding:10px 12px;border-bottom:1px solid var(--border)}
.tab{background:transparent;color:var(--text);border:1px solid var(--border);border-radius:10px;padding:6px 10px}
.tab.active{background:var(--row)}

.tabpanes{padding:12px}
.tabpane{display:none}
.tabpane.active{display:block}
.kv{display:grid;grid-template-columns:120px 1fr;gap:10px 14px}
.kv dt{color:var(--muted)} .kv dd{margin:0}
.columns{display:grid;grid-template-columns:1fr 1fr;gap:12px}

.code{background:var(--code);border:1px solid var(--border);border-radius:10px;padding:10px;overflow:auto;max-height:48vh;white-space:pre-wrap;word-break:break-word}
.code.json .k{color:#7aa2f7}.code.json .s{color:#a6e3a1}.code.json .n{color:#f2cdcd}.code.json .b{color:#f9e2af}.code.json .l{color:#f28fad}.code.json .null{color:#cdd6f4;opacity:.8}

.muted{color:var(--muted)}
.badge{border:1px solid var(--border);border-radius:6px;padding:2px 6px;background:#0002;font:12px ui-monospace,Menlo,monospace}
""".trimIndent()

    val appJs = """
// ========================= LogTap Viewer (fixed) =========================
// This script matches current indexHtml and renders logs reliably.
// Key: use Kotlin's ${'$'} to emit real JS template slots (no backslashes).

// ---- DOM ----
const tbody = document.querySelector('#logtbl tbody');
const search = document.querySelector('#search');
const autoScroll = document.querySelector('#autoScroll');
const clearBtn = document.querySelector('#clearBtn');
const methodFilter = document.querySelector('#methodFilter');
const statusFilter = document.querySelector('#statusFilter');

const drawer = document.querySelector('#drawer');
const drawerClose = document.querySelector('#drawerClose');
const tabs = Array.from(document.querySelectorAll('.tab'));

// ---- State ----
let rows = [];            // all events
let filtered = [];        // events passing filters
let filterText = '';
let selectedIdx = -1;

// ---- Utils ----
function safe(q){ const el = document.querySelector(q); if(!el) console.warn('[LogTap] missing', q); return el; }
function escapeHtml(s){ return String(s).replace(/[&<>"']/g, c=>({"&":"&amp;","<":"&lt;",">":"&gt;","\"":"&quot;","'":"&#39;"}[c])); }
function fmtTime(ts){ try { return new Date(ts).toLocaleTimeString(); } catch { return String(ts ?? ''); } }
function classForStatus(code){ if(!code) return ''; const x=Math.floor(code/100); return x===2?'status-2xx':x===3?'status-3xx':x===4?'status-4xx':x===5?'status-5xx':''; }
function hlJson(raw){
  try {
    const obj = typeof raw === 'string' ? JSON.parse(raw) : raw;
    const json = JSON.stringify(obj, null, 2);
    return json.replace(/"(\w+)":|"(.*?)"|\b(true|false)\b|\b(-?\d+(?:\.\d+)?)\b|null/g,(m,k,s,b,n)=>{
      if(k) return '<span class="k">"'+escapeHtml(k)+'"</span>:';
      if(s!==undefined) return '<span class="s">"'+escapeHtml(s)+'"</span>';
      if(b) return '<span class="b">'+b+'</span>';
      if(n) return '<span class="n">'+n+'</span>';
      return '<span class="null">null</span>';
    });
  } catch { return escapeHtml(String(raw ?? '')); }
}

// ---- Filters & Stats ----
function matchesFilters(ev){
  if(filterText){ const hay = JSON.stringify(ev).toLowerCase(); if(!hay.includes(filterText)) return false; }
  const m = methodFilter?.value || '';
  if(m){
    if(m==='WS' && ev.kind!=='WEBSOCKET') return false;
    if(m!=='WS' && (ev.method||'').toUpperCase() !== m) return false;
  }
  const s = statusFilter?.value || '';
  if(s && ev.status){ const x = Math.floor(ev.status/100)+'xx'; if(x!==s) return false; }
  return true;
}
function renderStats(){
  const total = rows.length;
  const http = rows.filter(r=>r.kind==='HTTP').length;
  const ws   = rows.filter(r=>r.kind==='WEBSOCKET').length;
  const get  = rows.filter(r=>(r.method||'').toUpperCase()==='GET').length;
  const post = rows.filter(r=>(r.method||'').toUpperCase()==='POST').length;
  const set = (id,txt)=>{ const el=document.getElementById(id); if(el) el.textContent = txt; };
  set('chipTotal', 'Total: '+total);
  set('chipHttp',  'HTTP: '+http);
  set('chipWs',    'WS: '+ws);
  set('chipGet',   'GET: '+get);
  set('chipPost',  'POST: '+post);
}

// ---- Table ----
function renderAll(){
  filtered = rows.filter(matchesFilters);
  tbody.innerHTML='';
  const fr = document.createDocumentFragment();
  for(const ev of filtered) fr.appendChild(renderRow(ev));
  tbody.appendChild(fr);
  if(autoScroll?.checked) tbody.lastElementChild?.scrollIntoView({block:'end'});
  renderStats();
}
function renderRow(ev){
  const tr = document.createElement('tr');
  tr.dataset.id = String(ev.id ?? '');
  tr.innerHTML =
    `<td class="col-id">${'$'}{ev.id ?? ''}</td>`+
    `<td class="col-time">${'$'}{fmtTime(ev.ts)}</td>`+
    `<td class="col-kind kind-${'$'}{ev.kind}">${'$'}{ev.kind ?? ''}</td>`+
    `<td class="col-dir dir-${'$'}{ev.direction}">${'$'}{ev.direction ?? ''}</td>`+
    `<td class="col-method">${'$'}{escapeHtml(ev.method || (ev.kind==='WEBSOCKET'?'WS':''))}</td>`+
    `<td class="col-status ${'$'}{classForStatus(ev.status)}">${'$'}{ev.status ?? ''}</td>`+
    `<td class="col-url">`+
      `<div class="url">${'$'}{escapeHtml(ev.url || '')}</div>`+
      (ev.summary ? `<div class="muted">${'$'}{escapeHtml(ev.summary)}</div>` : '')+
      (ev.bodyPreview ? `<pre class="body">${'$'}{escapeHtml(String(ev.bodyPreview))}</pre>` : '')+
    `</td>`;
  tr.addEventListener('click', ()=> openDrawer(ev));
  return tr;
}

// ---- Drawer ----
function setText(id,v){ const el=document.getElementById(id); if(el) el.textContent = v==null?'':String(v); }
function setJson(id,raw){ const el=document.getElementById(id); if(!el) return; if(!raw){ el.textContent=''; return;} el.innerHTML = hlJson(raw); }
function activateTab(name){ tabs.forEach(b=>b.classList.toggle('active', b.dataset.tab===name)); document.querySelectorAll('.tabpane').forEach(p=>p.classList.toggle('active', p.id==='tab-'+name)); }

function openDrawer(ev){
  if(!drawer) return;
  drawer.classList.remove('hidden');
  const title = (ev.method? (ev.method+' ') : (ev.kind==='WEBSOCKET'?'WS ':'') ) + (ev.url || ev.summary || '');
  const tEl = document.getElementById('drawerTitle'); tEl && tEl.replaceChildren(document.createTextNode(title));
  const sub = `<span class="badge">id ${'$'}{ev.id}</span> ` + (ev.status? `<span class="badge">status ${'$'}{ev.status}</span> ` : '') + (ev.tookMs? `<span class="badge">${'$'}{ev.tookMs} ms</span>` : '');
  const sEl = document.getElementById('drawerSub'); if(sEl) sEl.innerHTML = sub;

  setText('ov-id', ev.id);
  setText('ov-time', new Date(ev.ts).toLocaleString());
  setText('ov-kind', ev.kind);
  setText('ov-dir', ev.direction);
  setText('ov-method', ev.method || (ev.kind==='WEBSOCKET'?'WS':''));
  setText('ov-status', ev.status ?? '');
  setText('ov-url', ev.url ?? '');
  setText('ov-summary', ev.summary ?? '');
  setText('ov-took', ev.tookMs? ev.tookMs+' ms' : '');
  setText('ov-thread', ev.thread ?? '');

  // request/response payloads (we log one bodyPreview per event; show in both when appropriate)
  setJson('req-body', ev.direction==='REQUEST' ? (ev.bodyPreview ?? '') : '');
  setJson('resp-body', (ev.direction==='RESPONSE' || ev.status) ? (ev.bodyPreview ?? '') : '');

  const rh = document.getElementById('req-headers');
  if(rh) rh.textContent = ev.headers ? Object.entries(ev.headers).map(([k,v])=> k+': '+(Array.isArray(v)?v.join(', '):v)).join('\n') : '';
  const ph = document.getElementById('resp-headers');
  if(ph) ph.textContent = '';

  activateTab('overview');
}

// ---- Events ----
search?.addEventListener('input', ()=>{ filterText = search.value.trim().toLowerCase(); renderAll(); });
methodFilter?.addEventListener('change', renderAll);
statusFilter?.addEventListener('change', renderAll);
clearBtn?.addEventListener('click', async ()=>{ try{ await fetch('/api/clear', {method:'POST'}); }catch{} rows=[]; renderAll(); });
drawerClose?.addEventListener('click', ()=> drawer.classList.add('hidden'));

document.addEventListener('keydown',(e)=>{
  if((e.metaKey||e.ctrlKey) && (e.key==='k'||e.key==='K')){ e.preventDefault(); search?.focus(); }
  if(e.key==='Escape') drawer?.classList.add('hidden');
});

// ---- Bootstrap ----
async function bootstrap(){
  // initial load
  try{
    const res = await fetch('/api/logs?limit=1000');
    if(!res.ok) throw new Error('HTTP '+res.status);
    rows = await res.json();
  } catch(err){ console.error('[LogTap] failed to fetch /api/logs', err); rows = []; }
  renderAll();

  // live updates
  try{
    const ws = new WebSocket((location.protocol==='https:'?'wss':'ws')+'://'+location.host+'/ws');
    ws.onmessage = (e)=>{
      try{
        const ev = JSON.parse(e.data);
        rows.push(ev);
        if(matchesFilters(ev)){
          tbody.appendChild(renderRow(ev));
          if(autoScroll?.checked) tbody.lastElementChild?.scrollIntoView({block:'end'});
          renderStats();
        }
      }catch(parseErr){ console.warn('[LogTap] bad WS payload', parseErr); }
    };
    ws.onerror = (e)=> console.warn('[LogTap] WS error', e);
  }catch(wsErr){ console.warn('[LogTap] WS setup failed', wsErr); }
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