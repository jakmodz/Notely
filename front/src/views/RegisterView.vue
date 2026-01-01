<template>
  <div class="min-h-screen flex items-center justify-center bg-[radial-gradient(circle_at_top_right,_var(--tw-gradient-stops))] from-emerald-50 via-slate-50 to-slate-100 px-4">
    
    <div class="absolute top-0 left-0 w-full h-full overflow-hidden -z-10 pointer-events-none">
      <div class="absolute -top-[10%] -left-[10%] w-[40%] h-[40%] rounded-full bg-emerald-100/50 blur-3xl"></div>
      <div class="absolute -bottom-[10%] -right-[10%] w-[30%] h-[30%] rounded-full bg-blue-100/40 blur-3xl"></div>
    </div>

    <div class="max-w-md w-full py-12">
      <div class="text-center mb-8">
        <div class="inline-flex items-center justify-center w-20 h-20 bg-white rounded-3xl shadow-xl shadow-slate-200/50 mb-6 border border-slate-100">
          <div class="w-14 h-14 bg-emerald-600 rounded-2xl flex items-center justify-center shadow-inner">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-7 w-7 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M18 9v3m0 0v3m0-3h3m-3 0h-3m-2-5a4 4 0 11-8 0 4 4 0 018 0zM3 20a6 6 0 0112 0v1H3v-1z" />
            </svg>
          </div>
        </div>
        <h2 class="text-4xl font-black text-slate-900 tracking-tight mb-2">Create Account</h2>
        <p class="text-slate-500 font-medium">Join us to start managing your dashboard</p>
      </div>

      <div class="bg-white/80 backdrop-blur-xl rounded-[2rem] shadow-2xl shadow-slate-200/50 p-10 border border-white">
        <form @submit.prevent="handleRegister" class="space-y-5">
          
          <div class="space-y-2">
            <label class="block text-sm font-bold text-slate-700 ml-1">Username</label>
            <input 
              v-model="username"  
              required 
              placeholder="John Doe"
              class="block w-full px-5 py-4 bg-white border border-slate-200 rounded-2xl text-slate-900 placeholder-slate-400 focus:outline-none focus:ring-4 focus:ring-emerald-500/10 focus:border-emerald-500 transition-all duration-300"
            />
          </div>

          <div class="space-y-2">
            <label class="block text-sm font-bold text-slate-700 ml-1">Password</label>
            <input 
              v-model="password" 
              type="password" 
              required 
              placeholder="••••••••"
              class="block w-full px-5 py-4 bg-white border border-slate-200 rounded-2xl text-slate-900 placeholder-slate-400 focus:outline-none focus:ring-4 focus:ring-emerald-500/10 focus:border-emerald-500 transition-all duration-300"
            />
          </div>

          <div class="space-y-2">
            <label class="block text-sm font-bold text-slate-700 ml-1">Confirm Password</label>
            <input 
              v-model="confirmPassword" 
              type="password" 
              required 
              placeholder="••••••••"
              class="block w-full px-5 py-4 bg-white border border-slate-200 rounded-2xl text-slate-900 placeholder-slate-400 focus:outline-none focus:ring-4 focus:ring-emerald-500/10 focus:border-emerald-500 transition-all duration-300"
            />
          </div>

          

          <button 
            type="submit" 
            :disabled="isLoading"
            class="group relative w-full py-4 px-6 bg-slate-900 hover:bg-emerald-600 text-white font-bold rounded-2xl shadow-lg shadow-slate-900/20 hover:shadow-emerald-500/30 transition-all duration-300 active:scale-[0.97] disabled:opacity-70 disabled:cursor-not-allowed flex items-center justify-center gap-3"
          >
            <span v-if="isLoading" class="w-5 h-5 border-2 border-white/30 border-t-white rounded-full animate-spin"></span>
            <span class="tracking-wide">{{ isLoading ? 'Creating account...' : 'Create Account' }}</span>
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
          Already have an account?
          <router-link to="/login" class="text-emerald-600 font-bold hover:text-emerald-700 transition-colors ml-1">Sign in</router-link>
      </p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import authService from '@/api/services/authService';

const router = useRouter();
const username = ref('');
const password = ref('');
const confirmPassword = ref('');
const isLoading = ref(false);
const errorMessage = ref('');

const handleRegister = () => {
  if(confirmPassword.value !== password.value) {
    errorMessage.value = "Passwords do not match.";
    return;
  }
  let credentials = {
    username: username.value,
    password: password.value,
  };
  isLoading.value = true;
  authService.register(credentials
  ).then(response => {
    isLoading.value = false;
    errorMessage.value = '';
    router.push('/login');
  }).catch(error => {
    isLoading.value = false;
    if (error.response && error.response.data && error.response.data.message) {
      errorMessage.value = error.response.data.message;
    } else {
      errorMessage.value = 'An error occurred during registration. Please try again.';
    }
  });
};
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

input[type="checkbox"] {
  accent-color: #059669;
}
</style>