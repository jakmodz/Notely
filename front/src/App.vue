<script setup>
import { computed, onMounted } from "vue";
import { useAuthStore } from "@/stores/auth.js";
import { useThemeStore } from "@/stores/themes.js";
import { useRouter } from "vue-router";

const authStore = useAuthStore();
const themeStore = useThemeStore();
const router = useRouter();

const isAuthenticated = computed(() => authStore.isLoggedIn());

onMounted(async () => {
  themeStore.initTheme();
});

const handleLogout = async () => {
  await authStore.logout();
  router.push('/login');
};

</script>
<template>
  <div class="min-h-screen bg-slate-50 dark:bg-slate-900 transition-colors duration-300">
    <nav v-if="isAuthenticated" class="bg-white/80 dark:bg-slate-800/80 backdrop-blur-xl border-b border-slate-200 dark:border-slate-700 sticky top-0 z-50 transition-colors duration-300">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex items-center justify-between h-16">
          
          <div class="flex items-center gap-8 flex-1">
            <div class="flex items-center gap-3">
              <div class="w-10 h-10 bg-emerald-600 rounded-xl flex items-center justify-center shadow-lg shadow-emerald-500/20 hover:shadow-emerald-500/40 transition-all duration-300">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2" />
                </svg>
              </div>
              <span class="text-xl font-black text-slate-900 dark:text-white tracking-tight">Notely</span>
            </div>

            <div class="flex items-center gap-2">
              <router-link 
                to="/" 
                class="px-4 py-2 text-sm font-semibold text-slate-700 dark:text-slate-300 hover:text-emerald-600 dark:hover:text-emerald-400 hover:bg-emerald-50 dark:hover:bg-emerald-900/20 rounded-xl transition-all duration-200"
              >
                Home
              </router-link>
              <router-link 
                to="/notes" 
                class="px-4 py-2 text-sm font-semibold text-slate-700 dark:text-slate-300 hover:text-emerald-600 dark:hover:text-emerald-400 hover:bg-emerald-50 dark:hover:bg-emerald-900/20 rounded-xl transition-all duration-200">
                    Notes
                </router-link>
              <router-link 
                to="/settings" 
                class="px-4 py-2 text-sm font-semibold text-slate-700 dark:text-slate-300 hover:text-emerald-600 dark:hover:text-emerald-400 hover:bg-emerald-50 dark:hover:bg-emerald-900/20 rounded-xl transition-all duration-200"
              >
                Settings
              </router-link>
            </div>
          </div>
        </div>
      </div>
    </nav>
    
    <router-view />
  </div>
</template>

<style>
@import "tailwindcss";
@plugin "@tailwindcss/typography";
@custom-variant dark (&:where(.dark, .dark *));
@theme {
  --color-background-light: #ffffff;
  --color-background-dark: #0f172a;
}
</style>
