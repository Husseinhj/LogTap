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
                <div id="wsStatus" class="status status-off" title="WebSocket status">● Disconnected</div>
                <div class="split"></div>
                <button id="exportJson" class="ghost" title="Download filtered logs as JSON">Export JSON</button>
                <button id="exportHtml" class="ghost" title="Download a self-contained HTML report">Export Report</button>
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
                      <th class="col-actions">Actions</th>
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
                      <div class="full"><dt>Summary</dt><dd><div class="summary-row"><button id="ov-summary-copy" class="xs ghost" title="Copy Summary">Copy</button><pre class="code" id="ov-summary"></pre></div></dd></div>
                      <div><dt>Took</dt><dd id="ov-took"></dd></div>
                      <div><dt>Thread</dt><dd id="ov-thread"></dd></div>
                      <div class="full"><dt>cURL</dt><dd><div class="curl-row"><button id="ov-curl-copy" class="xs ghost" title="Copy cURL">Copy</button><pre class="code" id="ov-curl"></pre></div></dd></div>
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
        .split{width:1px;height:24px;background:var(--border);margin:0 4px}
        .input-wrap{position:relative}
        .input-wrap .key{position:absolute;right:8px;top:50%;transform:translateY(-50%);opacity:.6;background:#0002;border:1px solid var(--border);border-radius:6px;padding:0 6px;font:11px ui-monospace,Menlo,monospace}
        input[type="search"], select{background:var(--row);color:var(--text);border:1px solid var(--border);border-radius:10px;padding:8px 10px}
        .chk{opacity:.9}
        button{background:var(--accent);color:#fff;border:0;border-radius:10px;padding:8px 12px;cursor:pointer}
        button.ghost{background:transparent;border:1px solid var(--border);color:var(--text)}
        button.icon-btn{width:28px;height:28px;border-radius:8px;background:transparent;border:1px solid var(--border);color:var(--text);font-size:18px;line-height:1}
        .status{border:1px solid var(--border);border-radius:999px;padding:4px 8px;font:12px ui-monospace,Menlo,monospace}
        .status-on{color:var(--ok);}
        .status-off{color:var(--err);}
        
        .stats{display:flex;gap:8px;flex-wrap:wrap;padding:8px 16px;border-bottom:1px solid var(--border);background:var(--panel)}
        .chip{background:var(--chip);border:1px solid var(--border);padding:6px 10px;border-radius:999px;color:var(--muted)}
        
        .layout{display:grid;grid-template-columns:1fr 460px;gap:0}
        .table-wrap{overflow:auto;max-height:calc(100vh - 122px)}
        .table-wrap::-webkit-scrollbar{height:10px;width:10px}
        .table-wrap::-webkit-scrollbar-thumb{background:var(--border);border-radius:10px}
        .table-wrap::-webkit-scrollbar-track{background:transparent}
        
        table{width:100%;border-collapse:collapse}
        th,td{padding:10px 12px;border-bottom:1px solid var(--border);vertical-align:top}
        thead th{position:sticky;top:0;background:var(--panel);z-index:5}
        tbody tr{background:var(--row);cursor:pointer}
        tbody tr:hover{background:var(--row-hover)}
        .col-id{width:72px}.col-time{width:120px}.col-kind{width:92px}.col-dir{width:96px}.col-method{width:84px}.col-status{width:84px}.col-url{width:auto}.col-actions{width:160px}
        
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
        .kv .full{grid-column:1 / -1}
        .summary-row{display:flex; gap:8px; align-items:flex-start; width:100%}
        .summary-row .code{flex:1; min-height:120px}
        #ov-summary{white-space:pre-wrap; word-break:break-word; width:100%; max-height:50vh; overflow:auto}
        .curl-row{width:100%}
        #ov-curl{width:100%; max-height:70vh}
        .curl-row .code{flex:1; min-height:160px}
        .columns{display:grid;grid-template-columns:1fr 1fr;gap:12px}
        
        .code{background:var(--code);border:1px solid var(--border);border-radius:10px;padding:10px;overflow:auto;max-height:48vh;white-space:pre-wrap;word-break:break-word}
        .code.json .k{color:#7aa2f7}.code.json .s{color:#a6e3a1}.code.json .n{color:#f2cdcd}.code.json .b{color:#f9e2af}.code.json .l{color:#f28fad}.code.json .null{color:#cdd6f4;opacity:.8}
        #ov-curl{white-space:pre-wrap; word-break:break-all; overflow:auto; max-height:60vh; width:100%;}
        .curl-row{display:flex; gap:8px; align-items:flex-start}
        .curl-row .code{flex:1;}
        
        .muted{color:var(--muted)}
        .badge{border:1px solid var(--border);border-radius:6px;padding:2px 6px;background:#0002;font:12px ui-monospace,Menlo,monospace}
        .action-row{display:flex;gap:6px;flex-wrap:wrap}
        button.xs{padding:4px 8px;border-radius:8px;font-size:12px}
        @media (max-width: 1100px){ .layout{grid-template-columns:1fr;} .drawer{height:auto;} }
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
        const statusFilter = document.querySelector('#statusFilter');
        const statusCodeFilter = document.querySelector('#statusCodeFilter');
        const wsStatus = document.querySelector('#wsStatus');
        const exportJsonBtn = document.querySelector('#exportJson');
        const exportHtmlBtn = document.querySelector('#exportHtml');
        
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
        
        // ---- Utils ----
        function escapeHtml(s){ return String(s).replace(/[&<>"']/g, c=>({"&":"&amp;","<":"&lt;",">":"&gt;","\"":"&quot;","'":"&#39;"}[c])); }
        function fmtTime(ts){ try { return new Date(ts).toLocaleTimeString(); } catch { return String(ts ?? ''); } }
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
          const m = methodFilter?.value || '';
          if(m){ if(m==='WS' && ev.kind!=='WEBSOCKET') return false; if(m!=='WS' && (ev.method||'').toUpperCase() !== m) return false; }
          const s = statusFilter?.value || '';
          if(s && ev.status){ const x = Math.floor(ev.status/100)+'xx'; if(x!==s) return false; }
          if (statusCodeFilter && statusCodeFilter.value && !statusMatches(ev.status, statusCodeFilter.value)) return false;
          return true;
        }
        function renderStats(){
          const total = rows.length; const http = rows.filter(r=>r.kind==='HTTP').length; const ws = rows.filter(r=>r.kind==='WEBSOCKET').length; const get = rows.filter(r=>(r.method||'').toUpperCase()==='GET').length; const post = rows.filter(r=>(r.method||'').toUpperCase()==='POST').length;
          const set=(id,txt)=>{ const el=document.getElementById(id); if(el) el.textContent = txt; };
          set('chipTotal','Total: '+total); set('chipHttp','HTTP: '+http); set('chipWs','WS: '+ws); set('chipGet','GET: '+get); set('chipPost','POST: '+post);
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
        function btn(label, on){ const b=document.createElement('button'); b.className='xs ghost'; b.textContent=label; b.addEventListener('click', (e)=>{ e.stopPropagation(); on();}); return b; }
        function renderRow(ev){
          const tr = document.createElement('tr');
          tr.dataset.id = String(ev.id ?? '');
          const actions = document.createElement('div'); actions.className='action-row';
          if(ev.kind==='HTTP') actions.appendChild(btn('Copy cURL', ()=>{ try{ navigator.clipboard?.writeText(curlFor(ev)); }catch(e){ console.warn('clipboard failed', e); } }));
          const tdActions = document.createElement('td'); tdActions.className='col-actions'; tdActions.appendChild(actions);
        
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
        function openDrawer(ev){
          if(!drawer) return;
          drawer.classList.remove('hidden');
          const title = (ev.method? (ev.method+' ') : (ev.kind==='WEBSOCKET'?'WS ':'') ) + (ev.url || ev.summary || '');
          const tEl = document.getElementById('drawerTitle'); tEl && tEl.replaceChildren(document.createTextNode(title));
          const sub = `<span class="badge">id ${'$'}{ev.id}</span> ` + (ev.status? `<span class="badge">status ${'$'}{ev.status}</span> ` : '') + (ev.tookMs? `<span class="badge">${'$'}{ev.tookMs} ms</span>` : '');
          const sEl = document.getElementById('drawerSub'); if(sEl) sEl.innerHTML = sub;
          setText('ov-id', ev.id); setText('ov-time', new Date(ev.ts).toLocaleString()); setText('ov-kind', ev.kind); setText('ov-dir', ev.direction);
          setText('ov-method', ev.method || (ev.kind==='WEBSOCKET'?'WS':'')); setText('ov-status', ev.status ?? ''); setText('ov-url', ev.url ?? ''); setText('ov-summary', ev.summary ?? ''); setText('ov-took', ev.tookMs? ev.tookMs+' ms' : ''); setText('ov-thread', ev.thread ?? '');
          setJson('req-body', bodyFor(ev,'request'));
          setJson('resp-body', bodyFor(ev,'response'));
          const rh = document.getElementById('req-headers'); if(rh) rh.textContent = ev.headers ? Object.entries(ev.headers).map(([k,v])=> k+': '+(Array.isArray(v)?v.join(', '):v)).join('\n') : '';
          const ph = document.getElementById('resp-headers'); if(ph) ph.textContent = '';
          const oc = document.getElementById('ov-curl'); if(oc) oc.textContent = curlFor(ev);
          if(curlCopyBtn){ curlCopyBtn.onclick = async (e)=>{ e.preventDefault(); e.stopPropagation(); const ocEl = document.getElementById('ov-curl'); const ok = await copyText(ocEl?.textContent || ''); if(ok){ const old = curlCopyBtn.textContent; curlCopyBtn.textContent = 'Copied!'; setTimeout(()=> curlCopyBtn.textContent = old, 1200); } }; }
          const os = document.getElementById('ov-summary');
          if(summaryCopyBtn){ summaryCopyBtn.onclick = async (e)=>{ e.preventDefault(); e.stopPropagation(); const osEl = document.getElementById('ov-summary'); const ok = await copyText(osEl?.textContent || ''); if(ok){ const old = summaryCopyBtn.textContent; summaryCopyBtn.textContent = 'Copied!'; setTimeout(()=> summaryCopyBtn.textContent = old, 1200); } }; }
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
        statusFilter?.addEventListener('change', renderAll);
        statusCodeFilter?.addEventListener('input', renderAll);
        clearBtn?.addEventListener('click', async ()=>{ try{ await fetch('/api/clear', {method:'POST'}); }catch{} rows=[]; renderAll(); });
        drawerClose?.addEventListener('click', ()=> drawer.classList.add('hidden'));
        
        // ---- Bootstrap + WS status ----
        async function bootstrap(){
          try{ const res = await fetch('/api/logs?limit=1000'); if(!res.ok) throw new Error('HTTP '+res.status); rows = await res.json(); }
          catch(err){ console.error('[LogTap] failed to fetch /api/logs', err); rows=[]; }
          renderAll();
          try{
            const ws = new WebSocket((location.protocol==='https:'?'wss':'ws')+'://'+location.host+'/ws');
            const on = ()=>{ if(wsStatus){ wsStatus.textContent = '● Connected'; wsStatus.classList.remove('status-off'); wsStatus.classList.add('status-on'); } };
            const off= ()=>{ if(wsStatus){ wsStatus.textContent = '● Disconnected'; wsStatus.classList.remove('status-on'); wsStatus.classList.add('status-off'); } };
            ws.addEventListener('open', on);
            ws.addEventListener('close', off);
            ws.addEventListener('error', off);
            ws.onmessage = (e)=>{ try{ const ev=JSON.parse(e.data); rows.push(ev); if(matchesFilters(ev)){ tbody.appendChild(renderRow(ev)); if(autoScroll?.checked) tbody.lastElementChild?.scrollIntoView({block:'end'}); renderStats(); } }catch(parseErr){ console.warn('[LogTap] bad WS payload', parseErr); } };
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