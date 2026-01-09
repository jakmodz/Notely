<template>
  <VueFinalModal
    class="flex items-center justify-center p-4"
    content-class="max-w-md w-full"
    overlay-transition="vfm-fade"
    content-transition="vfm-fade"
    @update:model-value="handleClose"
  >
    <div class="bg-white dark:bg-slate-800 rounded-2xl shadow-2xl border border-slate-200 dark:border-slate-700 overflow-hidden">
      <div class="p-6 border-b border-slate-200 dark:border-slate-700">
        <div class="flex items-center justify-between">
          <div class="flex items-center gap-3">
            <div class="p-3 rounded-xl bg-emerald-100 dark:bg-emerald-900/30">
              <svg 
                xmlns="http://www.w3.org/2000/svg" 
                class="h-6 w-6 text-emerald-600 dark:text-emerald-400" 
                fill="none" 
                viewBox="0 0 24 24" 
                stroke="currentColor"
              >
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 7a2 2 0 012 2m4 0a6 6 0 01-7.743 5.743L11 17H9v2H7v2H4a1 1 0 01-1-1v-2.586a1 1 0 01.293-.707l5.964-5.964A6 6 0 1121 9z" />
              </svg>
            </div>
            <h3 class="text-xl font-bold text-slate-900 dark:text-white">
              Reset Password
            </h3>
          </div>
          <button
            @click="handleClose"
            class="text-slate-400 hover:text-slate-600 dark:hover:text-slate-300 transition-colors"
          >
            <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
            </svg>
          </button>
        </div>
      </div>
      <form @submit.prevent="handleSubmit" class="p-6 space-y-4">
        <div 
          v-if="errorMessage" 
          class="p-4 bg-red-50 dark:bg-red-900/20 border border-red-200 dark:border-red-800 rounded-xl"
        >
          <div class="flex items-start gap-3">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-red-600 dark:text-red-400 flex-shrink-0 mt-0.5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
            </svg>
            <p class="text-sm text-red-800 dark:text-red-300">{{ errorMessage }}</p>
          </div>
        </div>

        <div 
          v-if="successMessage" 
          class="p-4 bg-emerald-50 dark:bg-emerald-900/20 border border-emerald-200 dark:border-emerald-800 rounded-xl"
        >
          <div class="flex items-start gap-3">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-emerald-600 dark:text-emerald-400 flex-shrink-0 mt-0.5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
            </svg>
            <p class="text-sm text-emerald-800 dark:text-emerald-300">{{ successMessage }}</p>
          </div>
        </div>
        <div>
          <label for="password" class="block text-sm font-semibold text-slate-700 dark:text-slate-300 mb-2">
            New Password
          </label>
          <PasswordWithShow
          v-model="formData.password"
          required
          />
          <p v-if="errors.password" class="mt-1.5 text-sm text-red-600 dark:text-red-400">
            {{ errors.password }}
          </p>
          <p class="mt-1.5 text-xs text-slate-500 dark:text-slate-400">
            Must be at least 8 characters with uppercase, lowercase, number, and special character
          </p>
        </div>

        <div>
          <label for="confirmPassword" class="block text-sm font-semibold text-slate-700 dark:text-slate-300 mb-2">
            Confirm Password
          </label>
          <PasswordWithShow
          v-model="formData.confirmPassword"
          required
          />
          <p v-if="errors.confirmPassword" class="mt-1.5 text-sm text-red-600 dark:text-red-400">
            {{ errors.confirmPassword }}
          </p>
        </div>

        <div class="flex justify-end gap-3 pt-2">
          <button
            type="button"
            @click="handleClose"
            :disabled="isLoading"
            class="px-6 py-2.5 bg-white dark:bg-slate-700 border border-slate-300 dark:border-slate-600 text-slate-700 dark:text-slate-300 font-medium rounded-xl hover:bg-slate-50 dark:hover:bg-slate-600 transition-all duration-200 disabled:opacity-50"
          >
            Cancel
          </button>
          <button
            type="submit"
            :disabled="isLoading"
            class="px-6 py-2.5 bg-emerald-600 hover:bg-emerald-700 text-white font-medium rounded-xl transition-all duration-200 shadow-lg shadow-emerald-500/30 hover:shadow-emerald-500/40 disabled:opacity-50 disabled:cursor-not-allowed flex items-center gap-2"
          >
            <svg v-if="isLoading" class="animate-spin h-5 w-5" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
              <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
              <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
            </svg>
            <span>{{ isLoading ? 'Resetting...' : 'Reset Password' }}</span>
          </button>
        </div>
      </form>
    </div>
  </VueFinalModal>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { useModal } from 'vue-final-modal';
import { VueFinalModal } from 'vue-final-modal';
import authService from '@/api/services/authService';
import PasswordWithShow from '@/components/PasswordWithShow.vue';
import handleApiError from '@/util/apiError.js';
const emit = defineEmits(['close', 'success']);

const formData = reactive({
  password: '',
  confirmPassword: ''
});

const errors = reactive({
  password: '',
  confirmPassword: ''
});

const showPassword = ref(false);
const showConfirmPassword = ref(false);
const isLoading = ref(false);
const errorMessage = ref('');
const successMessage = ref('');


const validatePassword = () => {
  errors.password = '';
  
  if (!formData.password) {
    errors.password = 'Password is required';
    return false;
  }
  
  
  
  return true;
};

const validateConfirmPassword = () => {
  errors.confirmPassword = '';
  
  if (!formData.confirmPassword) {
    errors.confirmPassword = 'Please confirm your password';
    return false;
  }
  
  if (formData.password !== formData.confirmPassword) {
    errors.confirmPassword = 'Passwords do not match';
    return false;
  }
  
  return true;
};

const validateForm = () => {
  const isPasswordValid = validatePassword();
  const isConfirmPasswordValid = validateConfirmPassword();
  
  return isPasswordValid && isConfirmPasswordValid;
};

const handleSubmit = async () => {
  errorMessage.value = '';
  successMessage.value = '';
  
  if (!validateForm()) {
    return;
  }
  
  isLoading.value = true;
  
  try {
    await authService.resetPassword({
      password: formData.password
    });
    
    successMessage.value = 'Password reset successfully!';
    
    formData.password = '';
    formData.confirmPassword = '';
    
    setTimeout(() => {
      emit('success');
      emit('close');
    }, 1500);
    
  } catch (error) {
    console.error('Failed to reset password:', error);
    
    errorMessage.value = handleApiError(error);
  } finally {
    isLoading.value = false;
  }
};

const handleClose = () => {
  formData.password = '';
  formData.confirmPassword = '';
  errors.password = '';
  errors.confirmPassword = '';
  errorMessage.value = '';
  successMessage.value = '';
  
  emit('close');
};
</script>

<style scoped>
.vfm-fade-enter-active,
.vfm-fade-leave-active {
  transition: opacity 0.2s ease;
}

.vfm-fade-enter-from,
.vfm-fade-leave-to {
  opacity: 0;
}
</style>
