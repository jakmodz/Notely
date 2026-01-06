<template>
  <div 
    class="bg-white/80 dark:bg-slate-800/80 backdrop-blur-xl rounded-2xl shadow-xl shadow-slate-200/30 dark:shadow-none p-6 border border-slate-100 dark:border-slate-700 hover:shadow-emerald-500/10 transition-all duration-300 hover:-translate-y-1 cursor-pointer"
    @click="$emit('view', note)"
  >
    <h3 class="text-xl font-bold text-slate-900 dark:text-white mb-3 truncate">
      {{ note.title || 'Untitled' }}
    </h3>
    <p class="text-slate-600 dark:text-slate-400 mb-4 line-clamp-3">
      {{ note.content || 'No content' }}
    </p>
    <div class="flex justify-between items-center text-sm text-slate-500 dark:text-slate-500">
      <span>{{ formatDate(note.created) }}</span>
      <div class="flex gap-2">
        <button 
          @click.stop="$emit('edit', note)"
          class="hover:text-emerald-600 transition-colors"
          title="Edit note"
        >
          <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z" />
          </svg>
        </button>
        <button 
          @click.stop="$emit('delete', note)"
          class="hover:text-red-600 transition-colors"
          title="Delete note"
        >
          <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
          </svg>
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  note: {
    type: Object,
    required: true
  }
});

defineEmits(['view', 'edit', 'delete']);

const formatDate = (dateString) => {
  if (!dateString) return 'No date';
  const date = new Date(dateString);
  return date.toLocaleDateString('en-US', { month: 'short', day: 'numeric', year: 'numeric' });
};
</script>
