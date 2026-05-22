// js/api.js

document.addEventListener("DOMContentLoaded", function () {
  atualizarNavbarDinamica();
});

function atualizarNavbarDinamica() {
  const navActions = document.getElementById('navActions');
  if (!navActions) return;

  const utilizador = JSON.parse(sessionStorage.getItem('utilizador'));

  if (utilizador) {
    // 👤 CASO LOGADO: Bate 100% com o teu protótipo visual
    const isStaff = utilizador.cargo === 'ADMIN' || utilizador.cargo === 'FUNCIONARIO';

    navActions.innerHTML = `
      <span class="navbar-user" style="margin-right: 12px;">👤 ${utilizador.nome}</span>
      <a href="perfil.html" class="btn-outline-custom" style="margin-right: 8px;">Perfil</a>
      <button class="btn-danger-custom" onclick="sair()">Terminar Sessão</button>
    `;
  } else {
    // 🔓 CASO ANÓNIMO: Mantém o teu padrão original de botões
    navActions.innerHTML = `
      <a href="login.html"    class="btn-outline-custom" style="margin-right: 8px;">Login</a>
      <a href="register.html" class="btn-orange-custom">Registar</a>
    `;
  }
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
  sessionStorage.removeItem('utilizador');
  window.location.href = 'index.html';
}

function verificarSessao() {
  const utilizador = JSON.parse(sessionStorage.getItem('utilizador'));
  if (!utilizador || (utilizador.cargo !== 'ADMIN' && utilizador.cargo !== 'FUNCIONARIO')) {
    alert('Acesso Restrito! Área exclusiva para os funcionários.');
    window.location.href = 'login.html';
    return null;
  }
  return utilizador;
}
