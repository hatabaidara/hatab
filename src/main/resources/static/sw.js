const CACHE_NAME = 'shaoume-v2';
const STATIC_CACHE = 'shaoume-v3';
const DYNAMIC_CACHE = 'shaoume-dynamic-v3';

const staticAssets = [
  '/pages/home.html',
  '/pages/search.html',
  '/pages/login.html',
  '/pages/register.html',
  '/manifest.json',
  '/assets/icons/icon-192.png',
  '/assets/icons/icon-512.png'
];

// Installation - mise en cache des fichiers statiques
self.addEventListener('install', e => {
  e.waitUntil(
    caches.open(STATIC_CACHE).then(cache => cache.addAll(staticAssets))
  );
  self.skipWaiting();
});

// Activation - supprime les anciens caches
self.addEventListener('activate', e => {
  e.waitUntil(
    caches.keys().then(keys =>
      Promise.all(keys.filter(k => k !== STATIC_CACHE && k !== DYNAMIC_CACHE).map(k => caches.delete(k)))
    )
  );
  self.clients.claim();
});

// Fetch - cache first pour statique, network first pour API
self.addEventListener('fetch', e => {
  // Ne pas intercepter PUT, POST, DELETE
  if (e.request.method !== 'GET') return;

  const url = new URL(e.request.url);

  // Ne pas cacher les appels API
  if (url.pathname.startsWith('/api/')) {
    e.respondWith(fetch(e.request).catch(() => new Response('{"error":"offline"}', {headers:{'Content-Type':'application/json'}})));
    return;
  }

  // Cache first pour les assets statiques
  e.respondWith(
    caches.match(e.request).then(cached => {
      if (cached) return cached;
      return fetch(e.request).then(response => {
        if (response.status === 200) {
          const clone = response.clone();
          caches.open(DYNAMIC_CACHE).then(cache => cache.put(e.request, clone));
        }
        return response;
      }).catch(() => caches.match('/pages/home.html'));
    })
  );
});
