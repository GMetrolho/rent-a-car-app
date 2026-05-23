// js/dashboard.js

// ─────────────────────────────────────────────
// GESTÃO DE ALUGUERES (Máquina de Estados)
// ─────────────────────────────────────────────
function carregarAlugueres() {
  fetch('/alugueres')
    .then(r => r.json())
    .then(lista => {
      // Atualizar contadores se eles existirem na página
      if(document.getElementById('statAlugueres')) document.getElementById('statAlugueres').textContent = lista.length;
      if(document.getElementById('statAtivos')) document.getElementById('statAtivos').textContent = lista.filter(a => a.statusAluguer === 'ATIVA').length;
      if(document.getElementById('statPendentes')) document.getElementById('statPendentes').textContent = lista.filter(a => a.statusAluguer === 'PENDENTE').length;

      const body = document.getElementById('bodyAlugueres');
      if (!body) return;

      body.innerHTML = lista.map(a => {
        let botoesAcao = '';

        if (a.statusAluguer === 'PENDENTE') {
          botoesAcao += `<button class="btn-tbl-ok" onclick="acaoAluguer(${a.id}, 'confirmar')">Confirmar Reserva</button>`;
        }
        if (a.statusAluguer === 'LEVANTAR') {
          botoesAcao += `<button class="btn-tbl-ok" style="background:#e0e7ff; color:#3730a3;" onclick="acaoAluguer(${a.id}, 'levantar')">Registar Levantamento</button>`;
        }
        if (a.statusAluguer === 'ATIVA') {
          botoesAcao += `<button class="btn-tbl-ok" style="background:#fff7ed; color:#c2410c;" onclick="acaoAluguer(${a.id}, 'devolver')">Pedir Devolução</button>`;
        }
        if (a.statusAluguer === 'DEVOLVER') {
          botoesAcao += `<button class="btn-tbl-ok" onclick="acaoAluguer(${a.id}, 'concluir')">Concluir Aluguer</button>`;
          botoesAcao += `<button class="btn-tbl-edit" style="background:#fee2e2; color:#991b1b;" onclick="acaoAluguer(${a.id}, 'manutencao')">Enviar p/ Oficina</button>`;
        }
        if (a.statusAluguer === 'MANUTENCAO') {
          botoesAcao += `<button class="btn-tbl-ok" onclick="acaoAluguer(${a.id}, 'concluir')">Liberar da Oficina</button>`;
        }
        if (a.statusAluguer !== 'CONCLUIDA' && a.statusAluguer !== 'CANCELADO') {
          botoesAcao += `<button class="btn-tbl-del" onclick="acaoAluguer(${a.id}, 'cancelar')">Cancelar</button>`;
        }

        return `
          <tr>
            <td>#${a.id}</td>
            <td>${a.cliente?.nome || '—'}</td>
            <td>${a.veiculo?.marca || ''} ${a.veiculo?.modelo || ''}</td>
            <td>${formatData(a.dataLevantamento)}</td>
            <td>${formatData(a.dataDevolucao)}</td>
            <td>${(a.valorFinalPrevisto || 0).toFixed(2)}€</td>
            <td><span class="badge-${a.statusAluguer?.toLowerCase()}">${a.statusAluguer}</span></td>
            <td><div class="acoes">${botoesAcao}</div></td>
          </tr>
        `;
      }).join('') || '<tr><td colspan="8" style="text-align:center;color:var(--text-muted)">Sem alugueres.</td></tr>';
    });
}

function acaoAluguer(id, acao) {
  fetch(`/alugueres/${id}/${acao}`, { method: 'PUT' })
    .then(r => {
      if (!r.ok) return r.text().then(text => { throw new Error(text) });
      return r.json();
    })
    .then(() => {
      mostrarMsg('aluguerSucesso', 'Estado do aluguer atualizado!');
      carregarAlugueres();
      if (typeof carregarOverview === 'function') carregarOverview();
    })
    .catch(err => mostrarMsg('aluguerErro', err.message || 'Erro ao executar operação!'));
}

// ─────────────────────────────────────────────
// GESTÃO DE VEÍCULOS
// ─────────────────────────────────────────────
let categorias = [];

function carregarVeiculos() {
  fetch('/veiculos')
    .then(r => r.json())
    .then(lista => {
      if(document.getElementById('statVeiculos')) document.getElementById('statVeiculos').textContent = lista.length;

      const body = document.getElementById('bodyVeiculos');
      if (!body) return;

      body.innerHTML = lista.map(v => `
        <tr>
          <td>${v.matricula}</td>
          <td>${v.marca}</td>
          <td>${v.modelo}</td>
          <td>${v.ano}</td>
          <td>${v.precoDiario}€</td>
          <td><span class="badge-${v.status?.toLowerCase()}">${v.status}</span></td>
          <td><div class="acoes">
            <button class="btn-tbl-edit" onclick='abrirModalVeiculo(${JSON.stringify(v)})'>Editar</button>
            <button class="btn-tbl-del"  onclick="apagarVeiculo(${v.id})">Apagar</button>
          </div></td>
        </tr>
      `).join('') || '<tr><td colspan="7" style="text-align:center;color:var(--text-muted)">Sem veículos.</td></tr>';
    });
}

function abrirModalVeiculo(v) {
  document.getElementById('modalVeiculoErro').style.display = 'none';
  fetch('/categorias').then(r => r.json()).then(cats => {
    categorias = cats;
    const sel = document.getElementById('vCategoria');
    if (sel) sel.innerHTML = '<option value="">Sem categoria</option>' + cats.map(c => `<option value="${c.id}">${c.nome}</option>`).join('');
  });

  if (v) {
    document.getElementById('modalVeiculoTitulo').textContent = 'Editar Veículo';
    document.getElementById('veiculoId').value   = v.id;
    document.getElementById('vMatricula').value  = v.matricula;
    document.getElementById('vMarca').value      = v.marca;
    document.getElementById('vModelo').value     = v.modelo;
    document.getElementById('vAno').value        = v.ano;
    document.getElementById('vPreco').value      = v.precoDiario;
    document.getElementById('vStatus').value     = v.status;
    document.getElementById('vMotor').value      = v.tipoMotor;
    document.getElementById('vCaixa').value      = v.tipoCaixa;
    document.getElementById('vCombustao').value  = v.tipoCombustao;
    setTimeout(() => { if (v.categoria && document.getElementById('vCategoria')) document.getElementById('vCategoria').value = v.categoria.id; }, 300);
  } else {
    document.getElementById('modalVeiculoTitulo').textContent = 'Adicionar Veículo';
    document.getElementById('veiculoId').value = '';
    ['vMatricula','vMarca','vModelo','vAno','vPreco'].forEach(id => { if(document.getElementById(id)) document.getElementById(id).value = ''; });
  }
  document.getElementById('modalVeiculo').classList.add('open');
}

function fecharModalVeiculo() { document.getElementById('modalVeiculo').classList.remove('open'); }

function guardarVeiculo() {
  const id = document.getElementById('veiculoId').value;
  const catId = document.getElementById('vCategoria').value;
  const body = {
    matricula:     document.getElementById('vMatricula').value,
    marca:         document.getElementById('vMarca').value,
    modelo:        document.getElementById('vModelo').value,
    ano:           parseInt(document.getElementById('vAno').value),
    precoDiario:   parseFloat(document.getElementById('vPreco').value),
    status:        document.getElementById('vStatus').value,
    tipoMotor:     document.getElementById('vMotor').value,
    tipoCaixa:     document.getElementById('vCaixa').value,
    tipoCombustao: document.getElementById('vCombustao').value,
    categoria:     catId ? { id: parseInt(catId) } : null
  };

  const url    = id ? `/veiculos/${id}` : '/veiculos';
  const method = id ? 'PUT' : 'POST';

  fetch(url, { method, headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(body) })
    .then(r => { if (!r.ok) return r.text().then(m => { throw new Error(m); }); return r.json(); })
    .then(veiculo => {
      // Upload de imagem se foi selecionada
      const ficheiro = document.getElementById('vImagem')?.files[0];
      if (ficheiro) {
        const formData = new FormData();
        formData.append('ficheiro', ficheiro);
        return fetch(`/veiculos/${veiculo.id}/imagem`, { method: 'POST', body: formData })
          .then(r => { if (!r.ok) throw new Error('Veículo guardado mas erro no upload da imagem!'); });
      }
    })
    .then(() => { fecharModalVeiculo(); carregarVeiculos(); mostrarMsg('veiculoSucesso', 'Veículo guardado!'); })
    .catch(err => { document.getElementById('modalVeiculoErro').innerText = err.message; document.getElementById('modalVeiculoErro').style.display = 'block'; });
}

function apagarVeiculo(id) {
  if (!confirm('Tens a certeza que queres apagar este veículo?')) return;
  fetch(`/veiculos/${id}`, { method: 'DELETE' })
    .then(r => { if (!r.ok) throw new Error(); })
    .then(() => { carregarVeiculos(); mostrarMsg('veiculoSucesso', 'Veículo apagado!'); })
    .catch(() => mostrarMsg('veiculoErro', 'Erro ao apagar veículo!'));
}

// ─────────────────────────────────────────────
// GESTÃO DE CATEGORIAS
// ─────────────────────────────────────────────
function carregarCategorias() {
  const body = document.getElementById('bodyCategorias');
  if (!body) return;

  fetch('/categorias')
    .then(r => r.json())
    .then(lista => {
      body.innerHTML = lista.map(c => `
        <tr>
          <td>#${c.id}</td>
          <td>${c.nome}</td>
          <td>${c.descricao || '—'}</td>
          <td><div class="acoes">
            <button class="btn-tbl-del" onclick="apagarCategoria(${c.id})">Apagar</button>
          </div></td>
        </tr>
      `).join('') || '<tr><td colspan="4" style="text-align:center;color:var(--text-muted)">Sem categorias.</td></tr>';
    });
}

function abrirModalCategoria() {
  document.getElementById('catNome').value = '';
  document.getElementById('catDescricao').value = '';
  document.getElementById('modalCategoriaErro').style.display = 'none';
  document.getElementById('modalCategoria').classList.add('open');
}
function fecharModalCategoria() { document.getElementById('modalCategoria').classList.remove('open'); }

function guardarCategoria() {
  const nome      = document.getElementById('catNome').value.trim();
  const descricao = document.getElementById('catDescricao').value.trim();
  if (!nome) {
    document.getElementById('modalCategoriaErro').innerText = 'Nome é obrigatório!';
    document.getElementById('modalCategoriaErro').style.display = 'block';
    return;
  }
  fetch('/categorias', { method:'POST', headers:{'Content-Type':'application/json'}, body: JSON.stringify({ nome, descricao }) })
    .then(r => { if (!r.ok) throw new Error(); return r.json(); })
    .then(() => { fecharModalCategoria(); carregarCategorias(); mostrarMsg('categoriaSucesso', 'Categoria adicionada!'); })
    .catch(() => { document.getElementById('modalCategoriaErro').innerText = 'Erro ao guardar!'; document.getElementById('modalCategoriaErro').style.display='block'; });
}

function apagarCategoria(id) {
  if (!confirm('Apagar esta categoria?')) return;
  fetch(`/categorias/${id}`, { method:'DELETE' })
    .then(r => { if (!r.ok) throw new Error(); })
    .then(() => { carregarCategorias(); mostrarMsg('categoriaSucesso', 'Categoria apagada!'); })
    .catch(() => mostrarMsg('categoriaErro', 'Erro ao apagar!'));
}

// ─────────────────────────────────────────────
// GESTÃO DE UTILIZADORES
// ─────────────────────────────────────────────
function carregarUtilizadores() {
  const body = document.getElementById('bodyUtilizadores');
  if (!body) return;

  fetch('/utilizadores')
    .then(r => r.json())
    .then(lista => {
      body.innerHTML = lista.map(u => `
        <tr>
          <td>${u.nome}</td>
          <td>${u.email}</td>
          <td><span class="badge-${u.cargo?.toLowerCase()}">${u.cargo}</span></td>
          <td><div class="acoes">
            <button class="btn-tbl-edit" onclick="abrirModalCargo(${u.id}, '${u.cargo}')">Editar Cargo</button>
            <button class="btn-tbl-del"  onclick="apagarUtilizador(${u.id})">Remover</button>
          </div></td>
        </tr>
      `).join('') || '<tr><td colspan="4" style="text-align:center;color:var(--text-muted)">Sem utilizadores.</td></tr>';
    });
}

function abrirModalCargo(id, cargo) {
  document.getElementById('cargoUtilizadorId').value = id;
  document.getElementById('novoCargo').value = cargo;
  document.getElementById('modalCargo').classList.add('open');
}
function fecharModalCargo() { document.getElementById('modalCargo').classList.remove('open'); }

function guardarCargo() {
  const id    = document.getElementById('cargoUtilizadorId').value;
  const cargo = document.getElementById('novoCargo').value;
  fetch(`/utilizadores/${id}`, { method:'PUT', headers:{'Content-Type':'application/json'}, body: JSON.stringify({ cargo }) })
    .then(r => { if (!r.ok) throw new Error(); return r.json(); })
    .then(() => { fecharModalCargo(); carregarUtilizadores(); mostrarMsg('utilizadorSucesso', 'Cargo atualizado!'); })
    .catch(() => mostrarMsg('utilizadorErro', 'Erro ao atualizar cargo!'));
}

function apagarUtilizador(id) {
  if (!confirm('Tens a certeza que queres remover este utilizador?')) return;
  fetch(`/utilizadores/${id}`, { method:'DELETE' })
    .then(r => { if (!r.ok) throw new Error(); })
    .then(() => { carregarUtilizadores(); mostrarMsg('utilizadorSucesso', 'Utilizador removido!'); })
    .catch(() => mostrarMsg('utilizadorErro', 'Erro ao remover!'));
}

// Alternar visualização de secções nas Sidebars
function mostrarSecao(nome, el) {
  document.querySelectorAll('.secao').forEach(s => s.classList.remove('active'));
  document.querySelectorAll('.sidebar-link').forEach(l => l.classList.remove('active'));
  document.getElementById('secao-' + nome).classList.add('active');
  el.classList.add('active');

  if (nome === 'overview')     carregarOverview();
  if (nome === 'alugueres')    carregarAlugueres();
  if (nome === 'veiculos')     carregarVeiculos();
  if (nome === 'categorias')   carregarCategorias();
  if (nome === 'utilizadores') carregarUtilizadores();
}
