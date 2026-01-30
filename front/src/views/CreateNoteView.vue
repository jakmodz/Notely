<template>
  <div class="min-h-screen bg-[radial-gradient(circle_at_top_right,var(--tw-gradient-stops))] from-emerald-50 via-slate-50 to-slate-100 dark:from-slate-900 dark:via-slate-900 dark:to-slate-800 py-12 px-4 transition-colors duration-300">
    <div class="max-w-6xl mx-auto">
      <BackButton class="mb-4"/>
      
      <div class="bg-white/80 dark:bg-slate-800/80 backdrop-blur-xl rounded-2xl shadow-xl p-6 border border-slate-100 dark:border-slate-700">
        <div class="mb-6">
          <h2 class="text-2xl font-bold text-slate-900 dark:text-white mb-4">Create New Note</h2>
          <label class="block text-sm font-bold text-slate-700 dark:text-slate-300 mb-2">
            Title
          </label>
          <input
            v-model="noteData.title"
            type="text"
            placeholder="Enter note title..."
            class="w-full px-4 py-3 bg-white dark:bg-slate-800 border border-slate-200 dark:border-slate-700 text-slate-900 dark:text-white rounded-xl focus:outline-none focus:ring-2 focus:ring-emerald-500 transition-all text-lg font-semibold"
            required
            autofocus
          />
        </div>

        <div class="editor-tabs mb-4 flex justify-center gap-2 border-b border-slate-200 dark:border-slate-700 pb-2">
          <button
            @click="activeTab = 'edit'"
            :class="['tab-button', activeTab === 'edit' ? 'active' : '']"
          >
            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z" />
            </svg>
            Edit
          </button>
          <button
            @click="activeTab = 'preview'"
            :class="['tab-button', activeTab === 'preview' ? 'active' : '']"
          >
            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z" />
            </svg>
            Preview
          </button>
          <button
            @click="activeTab = 'split'"
            :class="['tab-button', activeTab === 'split' ? 'active' : '']"
          >
            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 4H5a2 2 0 00-2 2v14a2 2 0 002 2h4m0-18v18m0-18l6-6m-6 6L3 10m6 8l6 6m-6-6l6-6" />
            </svg>
            Split
          </button>
        </div>

        <div class="editor-container" :class="activeTab">
          <div v-show="activeTab === 'edit' || activeTab === 'split'" class="editor-panel">
            <textarea
              v-model="noteData.content"
              class="markdown-textarea"
              placeholder="# Write your markdown here...
## Features
- Lists
- **Bold** and *italic*
- [Links](https://example.com)
- \`code\`
> Blockquotes"
            ></textarea>
          </div>
          <div v-show="activeTab === 'preview' || activeTab === 'split'" class="preview-panel">
            <NoteView :content="noteData.content || '*No content yet...*'" />
          </div>
        </div>

        <div v-if="errorMessage" class="mt-6">
          <div class="p-4 bg-red-50 dark:bg-red-900/20 border border-red-200 dark:border-red-800 rounded-xl">
            <div class="flex items-start gap-3">
              <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-red-600 dark:text-red-400 flex-shrink-0 mt-0.5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
              </svg>
              <p class="text-sm text-red-800 dark:text-red-300">{{ errorMessage }}</p>
            </div>
          </div>
        </div>

        <div class="mt-6 flex justify-end gap-3">
          <button
            @click="handleCancel"
            :disabled="isLoading"
            class="px-6 py-3 bg-slate-200 hover:bg-slate-300 dark:bg-slate-700 dark:hover:bg-slate-600 text-slate-900 dark:text-white font-medium rounded-xl transition-all duration-200 disabled:opacity-50"
          >
            Cancel
          </button>
          <button
            @click="handleCreateNote"
            :disabled="isLoading || !noteData.title.trim() || !noteData.content.trim()"
            class="flex items-center gap-2 px-6 py-3 bg-emerald-600 hover:bg-emerald-700 disabled:bg-emerald-400 text-white font-medium rounded-xl transition-all duration-200 shadow-lg shadow-emerald-500/30 hover:shadow-emerald-500/40 disabled:cursor-not-allowed"
          >
            <svg v-if="isLoading" class="animate-spin h-5 w-5" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
              <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
              <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
            </svg>
            <svg v-else xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4" />
            </svg>
            {{ isLoading ? 'Creating...' : 'Create Note' }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import NoteView from '@/components/NoteView.vue';
import BackButton from '@/components/BackButton.vue';
import notesService from '@/api/services/notesService.js';
import { useNotebooksStore } from "@/stores/notebooks.js";
import handleApiError from '@/util/apiError.js';
const router = useRouter();
const route = useRoute();

const notebooksStore = useNotebooksStore();

const noteData = ref({
  title: '',
  content: ''
});

const isLoading = ref(false);
const errorMessage = ref('');
const activeTab = ref('split');

const handleCreateNote = async () => {
  errorMessage.value = '';
  
  if (!noteData.value.title.trim()) {
    errorMessage.value = 'Please enter a title for your note';
    return;
  }
  
  if (!noteData.value.content.trim()) {
    errorMessage.value = 'Please add some content to your note';
    return;
  }

  isLoading.value = true;

  try {
    const response = await notesService.createNote({
      title: noteData.value.title.trim(),
      content: noteData.value.content.trim()
    }, route.query.notebookId || null);
    await notebooksStore.reloadTree();
    router.push({ name: 'NoteDetails', params: { id: response.data.uuid } });
  } catch (error) {
    errorMessage.value = handleApiError(error);
  } finally {
    isLoading.value = false;
  }
};

const handleCancel = () => {
  if (noteData.value.title || noteData.value.content) {
    if (confirm('Are you sure you want to discard this note?')) {
      router.push({ path: '/notes' });
    }
  } else {
    router.push({ path: '/notes' });
  }
};
</script>

<style scoped>
.editor-tabs {
  display: flex;
  gap: 0.5rem;
}

.tab-button {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.5rem 1rem;
  border-bottom: 2px solid transparent;
  color: #64748b;
  font-weight: 500;
  transition: all 0.2s;
  background: none;
  border-radius: 0.375rem 0.375rem 0 0;
}

.tab-button:hover {
  color: #10b981;
  background-color: rgba(16, 185, 129, 0.05);
}

.tab-button.active {
  color: #10b981;
  border-bottom-color: #10b981;
  background-color: rgba(16, 185, 129, 0.1);
}

.dark .tab-button {
  color: #94a3b8;
}

.dark .tab-button:hover {
  color: #10b981;
}

.dark .tab-button.active {
  color: #10b981;
}

.editor-container {
  display: grid;
  gap: 1rem;
  min-height: 500px;
  border: 1px solid #e2e8f0;
  border-radius: 0.5rem;
  overflow: hidden;
}

.dark .editor-container {
  border-color: #334155;
}

.editor-container.edit {
  grid-template-columns: 1fr;
}

.editor-container.preview {
  grid-template-columns: 1fr;
}

.editor-container.split {
  grid-template-columns: 1fr 1fr;
}

@media (max-width: 768px) {
  .editor-container.split {
    grid-template-columns: 1fr;
  }
  
  .editor-container.split .editor-panel {
    border-bottom: 1px solid #e2e8f0;
  }
  
  .dark .editor-container.split .editor-panel {
    border-bottom-color: #334155;
  }
}

.editor-panel,
.preview-panel {
  overflow-y: auto;
  max-height: 600px;
}

.editor-panel {
  background-color: #ffffff;
  border-right: 1px solid #e2e8f0;
}

.dark .editor-panel {
  background-color: #1e293b;
  border-right-color: #334155;
}

.preview-panel {
  background-color: #f8fafc;
  padding: 0;
}

.dark .preview-panel {
  background-color: #0f172a;
}

.markdown-textarea {
  width: 100%;
  height: 100%;
  min-height: 500px;
  padding: 1.5rem;
  border: none;
  outline: none;
  font-family: 'Monaco', 'Menlo', 'Ubuntu Mono', 'Consolas', monospace;
  font-size: 1rem;
  line-height: 1.6;
  resize: vertical;
  background-color: transparent;
  color: inherit;
}

.markdown-textarea::placeholder {
  color: #94a3b8;
}

.dark .markdown-textarea {
  color: #e2e8f0;
}

.dark .markdown-textarea::placeholder {
  color: #64748b;
}
</style>
