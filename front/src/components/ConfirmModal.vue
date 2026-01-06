<template>
  <Teleport to="body">
    <Transition
      enter-active-class="transition-opacity duration-200"
      leave-active-class="transition-opacity duration-200"
      enter-from-class="opacity-0"
      leave-to-class="opacity-0"
    >
      <div
        v-if="isOpen"
        class="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/50 backdrop-blur-sm"
        @click.self="onCancel"
      >
        <Transition
          enter-active-class="transition-all duration-200"
          leave-active-class="transition-all duration-200"
          enter-from-class="opacity-0 scale-95"
          leave-to-class="opacity-0 scale-95"
        >
          <div
            v-if="isOpen"
            class="bg-white dark:bg-slate-800 rounded-2xl shadow-2xl border border-slate-200 dark:border-slate-700 max-w-md w-full overflow-hidden"
          >
            <div class="p-6 border-b border-slate-200 dark:border-slate-700">
              <div class="flex items-center gap-3">
                <div :class="[
                  'p-3 rounded-xl',
                  variant === 'danger' ? 'bg-red-100 dark:bg-red-900/30' : 'bg-emerald-100 dark:bg-emerald-900/30'
                ]">
                  <svg 
                    v-if="variant === 'danger'"
                    xmlns="http://www.w3.org/2000/svg" 
                    class="h-6 w-6 text-red-600 dark:text-red-400" 
                    fill="none" 
                    viewBox="0 0 24 24" 
                    stroke="currentColor"
                  >
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z" />
                  </svg>
                  <svg 
                    v-else
                    xmlns="http://www.w3.org/2000/svg" 
                    class="h-6 w-6 text-emerald-600 dark:text-emerald-400" 
                    fill="none" 
                    viewBox="0 0 24 24" 
                    stroke="currentColor"
                  >
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                  </svg>
                </div>
                <h3 class="text-xl font-bold text-slate-900 dark:text-white">
                  {{ title }}
                </h3>
              </div>
            </div>

            
            <div class="p-6">
              <p class="text-slate-600 dark:text-slate-400">
                {{ message }}
              </p>
            </div>

            <div class="p-6 bg-slate-50 dark:bg-slate-900/50 flex justify-end gap-3">
              <button
                @click="onCancel"
                class="px-6 py-2.5 bg-white dark:bg-slate-700 border border-slate-300 dark:border-slate-600 text-slate-700 dark:text-slate-300 font-medium rounded-xl hover:bg-slate-50 dark:hover:bg-slate-600 transition-all duration-200"
              >
                {{ cancelText }}
              </button>
              <button
                @click="onConfirm"
                :class="[
                  'px-6 py-2.5 font-medium rounded-xl transition-all duration-200 shadow-lg',
                  variant === 'danger' 
                    ? 'bg-red-600 hover:bg-red-700 text-white shadow-red-500/30 hover:shadow-red-500/40' 
                    : 'bg-emerald-600 hover:bg-emerald-700 text-white shadow-emerald-500/30 hover:shadow-emerald-500/40'
                ]"
              >
                {{ confirmText }}
              </button>
            </div>
          </div>
        </Transition>
      </div>
    </Transition>
  </Teleport>
</template>

<script setup>
const props = defineProps({
  isOpen: {
    type: Boolean,
    default: false
  },
  title: {
    type: String,
    default: 'Confirm Action'
  },
  message: {
    type: String,
    required: true
  },
  confirmText: {
    type: String,
    default: 'Confirm'
  },
  cancelText: {
    type: String,
    default: 'Cancel'
  },
  variant: {
    type: String,
    default: 'danger',
    validator: (value) => ['danger', 'info'].includes(value)
  }
});

const emit = defineEmits(['confirm', 'cancel']);

const onConfirm = () => {
  emit('confirm');
};

const onCancel = () => {
  emit('cancel');
};
</script>
