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
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4" />
              </svg>
            </div>
            <h3 class="text-xl font-bold text-slate-900 dark:text-white">
              Create New Item
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

      <form @submit.prevent="handleSubmit" class="p-6 space-y-5">
        <!-- Item Type Selection -->
        <div>
          <label class="block text-sm font-semibold text-slate-700 dark:text-slate-300 mb-3">
            What would you like to create?
          </label>
          <div class="grid grid-cols-2 gap-3">
            <button
              type="button"
              @click="formData.type = 'notebook'"
              :class="[
                'p-4 rounded-xl border-2 transition-all duration-200 flex flex-col items-center gap-2',
                formData.type === 'notebook'
                  ? 'border-emerald-600 bg-emerald-50 dark:bg-emerald-900/20'
                  : 'border-slate-200 dark:border-slate-700 hover:border-emerald-400 dark:hover:border-emerald-600'
              ]"
            >
              <svg 
                xmlns="http://www.w3.org/2000/svg" 
                :class="[
                  'h-8 w-8',
                  formData.type === 'notebook'
                    ? 'text-emerald-600 dark:text-emerald-400'
                    : 'text-slate-400 dark:text-slate-500'
                ]"
                fill="none" 
                viewBox="0 0 24 24" 
                stroke="currentColor"
              >
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 7v10a2 2 0 002 2h14a2 2 0 002-2V9a2 2 0 00-2-2h-6l-2-2H5a2 2 0 00-2 2z" />
              </svg>
              <span 
                :class="[
                  'text-sm font-semibold',
                    formData.type === 'notebook'
                    ? 'text-emerald-700 dark:text-emerald-300'
                    : 'text-slate-600 dark:text-slate-400'
                ]"
              >
                Notebook
              </span>
            </button>

            <button
              type="button"
              @click="formData.type = 'note'"
              :class="[
                'p-4 rounded-xl border-2 transition-all duration-200 flex flex-col items-center gap-2',
                formData.type === 'note'
                  ? 'border-emerald-600 bg-emerald-50 dark:bg-emerald-900/20'
                  : 'border-slate-200 dark:border-slate-700 hover:border-emerald-400 dark:hover:border-emerald-600'
              ]"
            >
              <svg 
                xmlns="http://www.w3.org/2000/svg" 
                :class="[
                  'h-8 w-8',
                  formData.type === 'note'
                    ? 'text-emerald-600 dark:text-emerald-400'
                    : 'text-slate-400 dark:text-slate-500'
                ]"
                fill="none" 
                viewBox="0 0 24 24" 
                stroke="currentColor"
              >
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z" />
              </svg>
              <span 
                :class="[
                  'text-sm font-semibold',
                  formData.type === 'note'
                    ? 'text-emerald-700 dark:text-emerald-300'
                    : 'text-slate-600 dark:text-slate-400'
                ]"
              >
                Note
              </span>
            </button>
          </div>
        </div>

        <!-- Name/Title Input -->
        <div v-if="formData.type === 'notebook'">
          <label for="itemName" class="block text-sm font-semibold text-slate-700 dark:text-slate-300 mb-2">
            {{ formData.type === 'notebook' ? 'Notebook Name' : 'Note Title' }}
          </label>
          <input
            id="itemName"
            v-model="formData.name"
            type="text"
            placeholder="Notebook name"
            class="w-full px-4 py-3 bg-white dark:bg-slate-900 border border-slate-300 dark:border-slate-600 rounded-xl text-slate-900 dark:text-white placeholder-slate-400 dark:placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-emerald-500 focus:border-transparent transition-all duration-200"
            required
          />
          <p v-if="errors.name" class="mt-1.5 text-sm text-red-600 dark:text-red-400">
            {{ errors.name }}
          </p>
        </div>

        <!-- Error Message -->
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

        <!-- Success Message -->
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

        <!-- Action Buttons -->
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
            :disabled="isLoading || (!formData.name.trim() && formData.type === 'notebook')"
            class="px-6 py-2.5 bg-emerald-600 hover:bg-emerald-700 text-white font-medium rounded-xl transition-all duration-200 shadow-lg shadow-emerald-500/30 hover:shadow-emerald-500/40 disabled:opacity-50 disabled:cursor-not-allowed flex items-center gap-2"
          >
            <svg v-if="isLoading" class="animate-spin h-5 w-5" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
              <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
              <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
            </svg>
            <span>{{ isLoading ? 'Creating...' : `Create ${formData.type === 'notebook' ? 'Notebook' : 'Note'}` }}</span>
          </button>
        </div>
      </form>
    </div>
  </VueFinalModal>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { VueFinalModal } from 'vue-final-modal';
import { useRouter} from 'vue-router';
import apiHandleError from "@/util/apiError.js";
import notebooksService from '@/api/services/notebookService.js';
const props = defineProps({
  parentData: {
    type: Object,
    default: null
  }
});
const router = useRouter();
const emit = defineEmits(['close', 'success']);

const formData = reactive({
  type: 'notebook',
  name: ''
});

const errors = reactive({
  name: ''
});

const isLoading = ref(false);
const errorMessage = ref('');
const successMessage = ref('');

const validateForm = () => {
  errors.name = '';
  
  if (!formData.name.trim()) {
    errors.name = `${formData.type === 'notebook' ? 'Notebook name' : 'Note title'} is required`;
    return false;
  }
  
  if (formData.name.trim().length < 2) {
    errors.name = `${formData.type === 'notebook' ? 'Notebook name' : 'Note title'} must be at least 2 characters`;
    return false;
  }
  
  return true;
};

const handleSubmit = async () => {
  errorMessage.value = '';
  successMessage.value = '';
  
  if (formData.type === 'note') {
    router.push({
      name: 'CreateNote',
      query: {
        notebookId: props.parentData?.id || '',
      }
    });
    emit('success', {
      type: 'note',
    });
  } else {

    if (!validateForm()) {
      return;
    }

    isLoading.value = true;

    try {
      const response = await notebooksService.createNotebook({
        name: formData.name.trim(),
        parentNotebookId: props.parentData?.id || null
      });

      successMessage.value = 'Notebook created successfully!';

      setTimeout(() => {
        emit('success', response.data);
        emit('close');
      }, 500);
    } catch (err) {
      errorMessage.value = apiHandleError(err);
    } finally {
      isLoading.value = false;
    }
  }
};
const handleClose = () => {
  formData.type = 'notebook';
  formData.name = '';
  errors.name = '';
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
