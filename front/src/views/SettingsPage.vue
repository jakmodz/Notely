<template>
  <div class="min-h-screen bg-slate-50 dark:bg-slate-900 py-12 px-4 transition-colors duration-300">
    <div class="max-w-4xl mx-auto">

      <div class="mb-8">
        <h1 class="text-4xl font-black text-slate-900 dark:text-white mb-2">Settings</h1>
        <p class="text-slate-600 dark:text-slate-400">Manage your account preferences and application settings</p>
      </div>


      <div class="space-y-6">
        <div class="bg-white dark:bg-slate-800 rounded-2xl shadow-lg border border-slate-200 dark:border-slate-700 overflow-hidden transition-colors duration-300">
          <div class="p-6 border-b border-slate-200 dark:border-slate-700">
            <div class="flex items-center gap-3">
              <div class="w-10 h-10 bg-emerald-100 dark:bg-emerald-900/30 rounded-xl flex items-center justify-center">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-emerald-600 dark:text-emerald-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 21a4 4 0 01-4-4V5a2 2 0 012-2h4a2 2 0 012 2v12a4 4 0 01-4 4zm0 0h12a2 2 0 002-2v-4a2 2 0 00-2-2h-2.343M11 7.343l1.657-1.657a2 2 0 012.828 0l2.829 2.829a2 2 0 010 2.828l-8.486 8.485M7 17h.01" />
                </svg>
              </div>
              <div>
                <h2 class="text-xl font-bold text-slate-900 dark:text-white">Appearance</h2>
                <p class="text-sm text-slate-600 dark:text-slate-400">Customize how Notely looks</p>
              </div>
            </div>
          </div>
          
          <div class="p-6 space-y-4">
            <div class="flex items-center justify-between">
              <div>
                <label class="text-sm font-bold text-slate-900 dark:text-white">Theme</label>
                <p class="text-sm text-slate-600 dark:text-slate-400">Choose your preferred color scheme</p>
              </div>
              <div class="flex gap-2">
                <button
                  @click="themeStore.setTheme('light')"
                  :class="[
                    'px-4 py-2 rounded-xl font-semibold text-sm transition-all duration-200',
                    themeStore.theme === 'light'
                      ? 'bg-emerald-600 text-white shadow-lg shadow-emerald-500/30'
                      : 'bg-slate-100 dark:bg-slate-700 text-slate-700 dark:text-slate-300 hover:bg-slate-200 dark:hover:bg-slate-600'
                  ]"
                >
                  <span class="flex items-center gap-2">
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 3v1m0 16v1m9-9h-1M4 12H3m15.364 6.364l-.707-.707M6.343 6.343l-.707-.707m12.728 0l-.707.707M6.343 17.657l-.707.707M16 12a4 4 0 11-8 0 4 4 0 018 0z" />
                    </svg>
                    Light
                  </span>
                </button>
                <button
                  @click="themeStore.setTheme('dark')"
                  :class="[
                    'px-4 py-2 rounded-xl font-semibold text-sm transition-all duration-200',
                    themeStore.theme === 'dark'
                      ? 'bg-emerald-600 text-white shadow-lg shadow-emerald-500/30'
                      : 'bg-slate-100 dark:bg-slate-700 text-slate-700 dark:text-slate-300 hover:bg-slate-200 dark:hover:bg-slate-600'
                  ]"
                >
                  <span class="flex items-center gap-2">
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20.354 15.354A9 9 0 018.646 3.646 9.003 9.003 0 0012 21a9.003 9.003 0 008.354-5.646z" />
                    </svg>
                    Dark
                  </span>
                </button>
              </div>
            </div>
          </div>
        </div>

        <div class="bg-white dark:bg-slate-800 rounded-2xl shadow-lg border border-slate-200 dark:border-slate-700 overflow-hidden transition-colors duration-300">
          <div class="p-6 border-b border-slate-200 dark:border-slate-700">
            <div class="flex items-center gap-3">
              <div class="w-10 h-10 bg-blue-100 dark:bg-blue-900/30 rounded-xl flex items-center justify-center">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-blue-600 dark:text-blue-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
                </svg>
              </div>
              <div>
                <h2 class="text-xl font-bold text-slate-900 dark:text-white">Account</h2>
                <p class="text-sm text-slate-600 dark:text-slate-400">Manage your account information</p>
              </div>
            </div>
          </div>
          
          <div class="p-6 space-y-4">
            <div class="flex items-center justify-between py-3">
              <div>
                <label class="text-sm font-bold text-slate-900 dark:text-white">Username</label>
                <p class="text-sm text-slate-600 dark:text-slate-400">{{ authStore.user?.username || 'Not available' }}</p>
              </div>
            </div>
            
            <div class="flex items-center justify-between py-3 border-t border-slate-200 dark:border-slate-700">
              <div>
                <label class="text-sm font-bold text-slate-900 dark:text-white">Change Password</label>
                <p class="text-sm text-slate-600 dark:text-slate-400">Update your password</p>
              </div>
              <button class="px-4 py-2 bg-slate-100 dark:bg-slate-700 text-slate-700 dark:text-slate-300 hover:bg-slate-200 dark:hover:bg-slate-600 rounded-xl font-semibold text-sm transition-all duration-200">
                Change
              </button>
            </div>
          </div>
        </div>


      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useThemeStore } from '@/stores/themes';
import { useAuthStore } from '@/stores/auth';

const themeStore = useThemeStore();
const authStore = useAuthStore();

const emailNotifications = ref(true);
const pushNotifications = ref(false);
</script>

