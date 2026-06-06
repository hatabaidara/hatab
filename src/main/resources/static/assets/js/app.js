/* ===========================
   SHAOUME GLOBAL BUSINESS
   JavaScript Principal
   =========================== */

const API_BASE = '';

const Auth = {
  getToken: () => localStorage.getItem('token'),
  getRefreshToken: () => localStorage.getItem('refreshToken'),
  getUser: () => JSON.parse(localStorage.getItem('user') || '{}'),
  setSession: (data) => {
    localStorage.setItem('token', data.accessToken);
    if (data.refreshToken) localStorage.setItem('refreshToken', data.refreshToken);
    localStorage.setItem('user', JSON.stringify(data.user || {}));
  },
  clearSession: () => {
    localStorage.removeItem('token');
    localStorage.removeItem('refreshToken');
    localStorage.removeItem('user');
  },
  isLogged: () => !!localStorage.getItem('token'),
  isAdmin: () => {
    const user = JSON.parse(localStorage.getItem('user') || '{}');
    return user.role === 'ADMIN';
  },
  isTokenExpired: () => {
    const token = localStorage.getItem('token');
    if (!token) return true;
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload.exp * 1000 < Date.now();
    } catch(e) { return true; }
  },
  async refresh() {
    const refreshToken = Auth.getRefreshToken();
    if (!refreshToken) return false;
    try {
      const res = await fetch('/api/auth/refresh-token?refreshToken=' + refreshToken, { method: 'POST' });
      if (!res.ok) return false;
      const data = await res.json();
      if (data.data?.accessToken) {
        localStorage.setItem('token', data.data.accessToken);
        if (data.data.refreshToken) localStorage.setItem('refreshToken', data.data.refreshToken);
        return true;
      }
      return false;
    } catch(e) { return false; }
  }
};

const Api = {
  async request(method, url, body = null) {
    if (Auth.isTokenExpired() && Auth.getRefreshToken()) {
      const refreshed = await Auth.refresh();
      if (!refreshed) {
        Auth.clearSession();
        if (!url.includes('/auth/login') && !url.includes('/auth/register')) {
          window.location.href = 'login.html';
        }
        return null;
      }
    }
    const headers = { 'Content-Type': 'application/json' };
    if (Auth.getToken()) headers['Authorization'] = 'Bearer ' + Auth.getToken();
    const options = { method, headers };
    if (body) options.body = JSON.stringify(body);
    try {
      const res = await fetch(url, options);
      if (res.status === 401) {
        const refreshed = await Auth.refresh();
        if (refreshed) {
          headers['Authorization'] = 'Bearer ' + Auth.getToken();
          const retryRes = await fetch(url, { method, headers, body: options.body });
          const retryData = await retryRes.json();
          if (!retryRes.ok) throw new Error(retryData.message || 'Erreur serveur');
          return retryData;
        } else {
          Auth.clearSession();
          Toast.error('Session expirée. Reconnexion...');
          setTimeout(() => { window.location.href = 'login.html'; }, 1500);
          return;
        }
      }
      const data = await res.json();
      if (!res.ok) throw new Error(data.message || 'Erreur serveur');
      return data;
    } catch (e) { throw e; }
  },
  get:    (url)       => Api.request('GET',    url),
  post:   (url, body) => Api.request('POST',   url, body),
  put:    (url, body) => Api.request('PUT',    url, body),
  delete: (url)       => Api.request('DELETE', url),
};

const Toast = {
  show(message, type = 'success') {
    const id = 'toast-' + Date.now();
    const icons = { success: 'check-circle-fill', danger: 'x-circle-fill', warning: 'exclamation-triangle-fill', info: 'info-circle-fill' };
    const html = `<div id="${id}" class="toast align-items-center text-bg-${type} border-0 mb-2" role="alert" style="min-width:300px">
      <div class="d-flex">
        <div class="toast-body d-flex align-items-center gap-2">
          <i class="bi bi-${icons[type] || 'info-circle-fill'}"></i> ${message}
        </div>
        <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
      </div>
    </div>`;
    let container = document.getElementById('toast-container');
    if (!container) {
      container = document.createElement('div');
      container.id = 'toast-container';
      container.className = 'position-fixed top-0 end-0 p-3';
      container.style.zIndex = '9999';
      document.body.appendChild(container);
    }
    container.insertAdjacentHTML('beforeend', html);
    const toastEl = document.getElementById(id);
    const toast = new bootstrap.Toast(toastEl, { delay: 4000 });
    toast.show();
    toastEl.addEventListener('hidden.bs.toast', () => toastEl.remove());
  },
  success: (msg) => Toast.show(msg, 'success'),
  error:   (msg) => Toast.show(msg, 'danger'),
  warning: (msg) => Toast.show(msg, 'warning'),
  info:    (msg) => Toast.show(msg, 'info'),
};

const Loader = {
  show(btn) {
    if (!btn) return;
    btn.dataset.originalText = btn.innerHTML;
    btn.disabled = true;
    btn.innerHTML = '<span class="spinner-border spinner-border-sm me-2"></span>Chargement...';
  },
  hide(btn) {
    if (!btn) return;
    btn.disabled = false;
    btn.innerHTML = btn.dataset.originalText || 'Envoyer';
  }
};

const Format = {
  currency: (amount) => {
    if (!amount && amount !== 0) return '—';
    return new Intl.NumberFormat('fr-FR', { style: 'currency', currency: 'XOF' }).format(amount);
  },
  date: (date) => {
    if (!date) return '—';
    return new Date(date).toLocaleDateString('fr-FR', { day: '2-digit', month: 'short', year: 'numeric' });
  },
  dateTime: (date) => {
    if (!date) return '—';
    return new Date(date).toLocaleString('fr-FR', { day: '2-digit', month: 'short', year: 'numeric', hour: '2-digit', minute: '2-digit' });
  },
  statusBadge(status) {
    const map = {
      PENDING: ['warning','En attente'], CONFIRMED: ['info','Confirmée'],
      PROCESSING: ['primary','En cours'], SHIPPED: ['secondary','Expédiée'],
      DELIVERED: ['success','Livrée'], CANCELLED: ['danger','Annulée'], REFUNDED: ['dark','Remboursée'],
    };
    const [color, label] = map[status] || ['secondary', status];
    return `<span class="badge bg-${color}-subtle text-${color} badge-status">${label}</span>`;
  },
  roleBadge(role) {
    const map = { ADMIN: ['danger','Admin'], USER: ['primary','Client'] };
    const [color, label] = map[role] || ['secondary', role];
    return `<span class="badge bg-${color}-subtle text-${color}">${label}</span>`;
  }
};

function checkAuth() {
  document.body.style.opacity = '0';
  if (!Auth.isLogged()) {
    window.location.href = 'login.html';
    return false;
  }
  document.body.style.opacity = '1';
  return true;
}

function checkAdmin() {
  if (!Auth.isLogged()) {
    window.location.href = 'admin-login.html';
    return false;
  }
  if (!Auth.isAdmin()) {
    window.location.href = 'home.html';
    return false;
  }
  document.body.style.opacity = '1';
  return true;
}

function initUserInfo() {
  const user = Auth.getUser();
  const nameEl = document.getElementById('user-name');
  const avatarEl = document.getElementById('user-avatar');
  if (nameEl) nameEl.textContent = (user.firstName || '') + ' ' + (user.lastName || '');
  if (avatarEl) avatarEl.textContent = (user.firstName?.[0] || 'U') + (user.lastName?.[0] || '');
}

async function logout() {
  try { await Api.post('/api/auth/logout'); } catch(e) {}
  Auth.clearSession();
  window.location.href = 'login.html';
}

function buildPagination(containerId, totalPages, currentPage, onPageChange) {
  const ul = document.getElementById(containerId);
  if (!ul) return;
  ul.innerHTML = '';
  if (totalPages <= 1) return;
  ul.innerHTML += `<li class="page-item ${currentPage === 0 ? 'disabled' : ''}"><a class="page-link" href="#" onclick="(${onPageChange})(${currentPage - 1})">‹</a></li>`;
  for (let i = 0; i < totalPages; i++) {
    if (totalPages > 7 && Math.abs(i - currentPage) > 2 && i !== 0 && i !== totalPages - 1) {
      if (i === 1 || i === totalPages - 2) ul.innerHTML += `<li class="page-item disabled"><a class="page-link">…</a></li>`;
      continue;
    }
    ul.innerHTML += `<li class="page-item ${i === currentPage ? 'active' : ''}"><a class="page-link" href="#" onclick="(${onPageChange})(${i})">${i + 1}</a></li>`;
  }
  ul.innerHTML += `<li class="page-item ${currentPage === totalPages - 1 ? 'disabled' : ''}"><a class="page-link" href="#" onclick="(${onPageChange})(${currentPage + 1})">›</a></li>`;
}

function confirmDialog(message) {
  return new Promise(resolve => { resolve(window.confirm(message)); });
}