// js/api.js

document.addEventListener("DOMContentLoaded", function () {
  atualizarNavbarDinamica();
});

function atualizarNavbarDinamica() {
  const navActions = document.getElementById('navActions');
  if (!navActions) return;

  const utilizador = JSON.parse(sessionStorage.getItem('utilizador'));

  if (utilizador) {
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
 * Função auxiliar para chamadas à API Spring Boot.
 * Aponta automaticamente para localhost:8081.
 */
function apiFetch(url, opcoes = {}) {
  const urlCompleta = url.startsWith('http') ? url : `http://localhost:8081${url}`;

  return window.fetch(urlCompleta, opcoes).then(async (res) => {
    // 1. Tratamento imediato para problemas de segurança (sessão expirada/acesso negado)
    if (res.status === 401 || res.status === 403) {
      console.warn("Acesso negado.");
      if (!window.location.pathname.includes('login.html')) {
        sessionStorage.clear();
        window.location.href = 'login.html';
      }
      throw new Error("Sessão inválida. Por favor, volte a fazer login.");
    }

    // 2. Se a resposta for um erro (Status 4xx ou 5xx)
    if (!res.ok) {
      const text = await res.text(); // Extrair o texto cru da resposta de erro
      let mensagemErro = "Ocorreu um erro inesperado ao comunicar com o servidor."; // Fallback seguro

      if (text) {
        try {
          // Tentar processar a resposta caso o Spring Boot devolva um objeto JSON
          const errorObj = JSON.parse(text);

          if (errorObj.message) {
            // Se o backend enviar um campo "message" bonitinho, nós usamo-lo!
            mensagemErro = errorObj.message;
          } else if (errorObj.error === "Internal Server Error") {
            // Se for o erro padrão de "rebentamento" do Spring Boot
            mensagemErro = "Erro interno no servidor. Por favor, tenta novamente.";
          } else {
             // Tentar usar algo do corpo caso as propriedades acima falhem
             mensagemErro = errorObj.error || text;
          }
        } catch (e) {
          // Se falhar o "JSON.parse", significa que o servidor mandou texto simples, usamos isso
          mensagemErro = text;
        }
      }

      // Lança um erro real em JavaScript para ser capturado no ".catch()" de quem chamou a função
      throw new Error(mensagemErro);
    }

    // 3. Se tudo correu bem, devolve a resposta normal para ser processada
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

  if (!utilizador || (utilizador.cargo !== 'ADMIN' && utilizador.cargo !== 'FUNCIONARIO')) {
    alert('Acesso Restrito! Área exclusiva para os funcionários.');
    sessionStorage.clear();
    window.location.href = 'login.html';
    return null;
  }
  return utilizador;
}
