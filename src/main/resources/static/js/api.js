// js/api.js

document.addEventListener("DOMContentLoaded", function () {
  atualizarNavbarDinamica();
});

function atualizarNavbarDinamica() {
  const navActions = document.getElementById('navActions');
  if (!navActions) return;

  const utilizador = JSON.parse(sessionStorage.getItem('utilizador'));

  if (utilizador && sessionStorage.getItem('token')) {
    navActions.innerHTML = `
      <span class="navbar-user" style="margin-right: 12px;">👤 ${utilizador.nome}</span>
      <a href="perfil.html" class="btn-outline-custom" style="margin-right: 8px;">Perfil</a>
      <button class="btn-danger-custom" onclick="sair()">Terminar Sessão</button>
    `;
  } else {
    navActions.innerHTML = `
      <a href="login.html"    class="btn-outline-custom" style="margin-right: 8px;">Login</a>
      <a href="register.html" class="btn-orange-custom">Registar</a>
    `;
  }
}

/**
 * Função Mágica: Substitui o fetch global do teu projeto.
 * Garante que o Token JWT vai sempre agarrado no cabeçalho dos teus pedidos ao Spring Boot.
 */
function requisicaoSegura(url, opcoes = {}) {
  const token = sessionStorage.getItem('token');

  if (!opcoes.headers) opcoes.headers = {};

  if (token) {
    opcoes.headers['Authorization'] = `Bearer ${token}`;
  }

  // Se enviares dados, garante o content-type correto
  if (opcoes.body && typeof opcoes.body === 'object' && !(opcoes.body instanceof FormData)) {
    opcoes.headers['Content-Type'] = 'application/json';
    opcoes.body = JSON.stringify(opcoes.body);
  }

  // Faz o pedido apontando para a tua porta 8081 automaticamente
  const urlCompleta = url.startsWith('http') ? url : `http://localhost:8081${url}`;

  return fetch(urlCompleta, opcoes).then(res => {
    // Se o Spring Security interceptar falta de permissão ou token inválido
    if (res.status === 401 || res.status === 403) {
       console.warn("Acesso negado ou Token inválido/expirado.");
      if (!window.location.pathname.includes('login.html')) {
        sessionStorage.clear();
        window.location.href = 'login.html';
      }
    throw new Error("Sessão expirada. Por favor volte a fazer login.");
    }
    return res;
  });
}

function formatData(dt) {
  if (!dt) return '—';
  return new Date(dt).toLocaleDateString('pt-PT');
}

function mostrarMsg(id, msg) {
  const el = document.getElementById(id);
  if (el) {
    el.innerText = msg;
    el.style.display = 'block';
    setTimeout(() => { el.style.display = 'none'; }, 3000);
  }
}

function sair() {
  sessionStorage.clear();
  window.location.href = 'index.html';
}

/**
 * Proteção de páginas administrativas do Dashboard
 */
function verificarSessao() {
  const utilizador = JSON.parse(sessionStorage.getItem('utilizador'));
  const token = sessionStorage.getItem('token');

  if (!utilizador || !token || (utilizador.cargo !== 'ADMIN' && utilizador.cargo !== 'FUNCIONARIO')) {
    alert('Acesso Restrito! Área exclusiva para os funcionários.');
    sessionStorage.clear();
    window.location.href = 'login.html';
    return null;
  }
  return utilizador;
}
