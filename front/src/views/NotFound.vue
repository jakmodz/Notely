<template>
  <div class="flex flex-col items-center justify-center h-screen">
    <h1 class="text-6xl font-bold text-slate-800 dark:text-slate-200 mb-4">404</h1>
    <p class="text-xl text-slate-600 dark:text-slate-400 mb-8">Page not found</p>
    <router-link 
      to="/" 
      class="px-6 py-3 bg-emerald-600 text-white font-semibold rounded-xl hover:bg-emerald-700 transition-colors duration-200"
    >
      Go back home
    </router-link>
  </div>
</template>

<script setup>
</script>
