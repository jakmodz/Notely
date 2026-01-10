<template>
  <div class="min-h-screen flex items-center justify-center bg-[radial-gradient(circle_at_top_right,var(--tw-gradient-stops))] from-emerald-50 via-slate-50 to-slate-100 px-4">
    <div class="absolute top-0 left-0 w-full h-full overflow-hidden -z-10 pointer-events-none">
      <div class="absolute -top-[10%] -left-[10%] w-[40%] h-[40%] rounded-full bg-emerald-100/50 blur-3xl"></div>
      <div class="absolute -bottom-[10%] -right-[10%] w-[30%] h-[30%] rounded-full bg-blue-100/40 blur-3xl"></div>
    </div>

    <div class="max-w-md w-full">
      <div class="text-center mb-8">
        <div class="inline-flex items-center justify-center w-20 h-20 bg-white dark:bg-slate-800 rounded-3xl shadow-xl shadow-slate-200/50 dark:shadow-none mb-6 border border-slate-100 dark:border-slate-700">
          <div class="w-14 h-14 bg-emerald-600 rounded-2xl flex items-center justify-center shadow-inner">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-7 w-7 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2" />
            </svg>
          </div>
        </div>
        <h2 class="text-4xl font-black text-slate-900 tracking-tight mb-2">Welcome back</h2>
        <p class="text-slate-500 font-medium">Please enter your details to sign in</p>
      </div>

      <div class="bg-white/80 dark:bg-slate-800/80 backdrop-blur-xl rounded-[2rem] shadow-2xl shadow-slate-200/50 p-10 border border-white dark:border-slate-700">
        <form @submit.prevent="handleLogin" class="space-y-6">
          
          <div class="space-y-2">
            <label class="block text-sm font-bold text-slate-700 ml-1">Username</label>
            <div class="group relative">
              <input 
                v-model="username"  
                required 
                placeholder="username"
                class="block w-full px-5 py-4 bg-white dark:bg-slate-900 border border-slate-200 dark:border-slate-700 text-slate-900 dark:text-white rounded-2xl text-slate-900 placeholder-slate-400 focus:outline-none focus:ring-4 focus:ring-emerald-500/10 focus:border-emerald-500 transition-all duration-300"
              />
            </div>
          </div>
          
          <div class="space-y-2">
            <label class="block text-sm font-bold text-slate-700 ml-1">Password</label>
            <PasswordWithShow 
            v-model="password"
            required
            />
          </div>

          <button 
            type="submit" 
            :disabled="isLoading"
            class="group relative w-full py-4 px-6 bg-slate-900 hover:bg-emerald-600 text-white font-bold rounded-2xl shadow-lg shadow-slate-900/20 hover:shadow-emerald-500/30 transition-all duration-300 active:scale-[0.97] disabled:opacity-70 disabled:cursor-not-allowed flex items-center justify-center gap-3"
          >
            <span v-if="isLoading" class="w-5 h-5 border-2 border-white/30 border-t-white rounded-full animate-spin"></span>
            <span class="tracking-wide">{{ isLoading ? 'Authenticating...' : 'Sign in to Dashboard' }}</span>
            <svg v-if="!isLoading" xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 group-hover:translate-x-1 transition-transform" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 7l5 5m0 0l-5 5m5-5H6" />
            </svg>
          </button>
          
          <div v-if="errorMessage" class="p-4 bg-red-50 border border-red-100 rounded-2xl animate-shake">
            <p class="text-sm text-red-600 text-center font-semibold flex items-center justify-center gap-2">
              <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4" viewBox="0 0 20 20" fill="currentColor">
                <path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7 4a1 1 0 11-2 0 1 1 0 012 0zm-1-9a1 1 0 00-1 1v4a1 1 0 102 0V6a1 1 0 00-1-1z" clip-rule="evenodd" />
              </svg>
              {{ errorMessage }}
            </p>
          </div>
        </form>
      </div>

      <p class="text-center mt-10 text-sm text-slate-500 font-medium"> 
          Doen't have an account?
          <router-link to="/register" class="text-emerald-600 font-bold hover:text-emerald-700 transition-colors ml-1" >Register account</router-link>
      </p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import apiClient from '@/api/services/authService';
import { useAuthStore } from '@/stores/auth.js';
import PasswordWithShow from '@/components/PasswordWithShow.vue';
import  handleApiError  from '@/util/apiError.js';
const router = useRouter();
const username = ref('');
const password = ref('');
const isLoading = ref(false);
const errorMessage = ref('');
const authStore = useAuthStore();

const  handleLogin = async () =>{
  isLoading.value = true;
  apiClient.login({
    username: username.value,
    password: password.value
  }).then(response => {
    isLoading.value = false;
    errorMessage.value = '';
    authStore.setAccessToken(response.data.accessToken);
    authStore.setUser(response.data.user);
    router.push('/');
  }).catch(error => {
    isLoading.value = false;
    errorMessage.value = handleApiError(error);
  });
}
</script>
<style scoped>
@keyframes shake {
  0%, 100% { transform: translateX(0); }
  25% { transform: translateX(-4px); }
  75% { transform: translateX(4px); }
}

.animate-shake {
  animation: shake 0.2s ease-in-out 0s 2;
}
</style>
