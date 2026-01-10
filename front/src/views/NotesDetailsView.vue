<template>
    <div class="min-h-screen bg-[radial-gradient(circle_at_top_right,var(--tw-gradient-stops))] from-emerald-50 via-slate-50 to-slate-100 dark:from-slate-900 dark:via-slate-900 dark:to-slate-800 py-12 px-4 transition-colors duration-300 relative overflow-hidden">
        <div class="absolute top-0 left-0 w-full h-full overflow-hidden -z-10 pointer-events-none">
            <div class="absolute -top-[10%] -left-[10%] w-[40%] h-[40%] rounded-full bg-emerald-100/50 dark:bg-emerald-900/20 blur-3xl"></div>
            <div class="absolute top-[30%] right-[10%] w-[30%] h-[30%] rounded-full bg-blue-100/40 dark:bg-blue-900/20 blur-3xl"></div>
            <div class="absolute -bottom-[10%] -right-[10%] w-[35%] h-[35%] rounded-full bg-purple-100/30 dark:bg-purple-900/20 blur-3xl"></div>
        </div>

        <div class="max-w-4xl mx-auto">
            <BackButton />
            <div v-if="loading" class="bg-white/80 dark:bg-slate-800/80 backdrop-blur-xl rounded-2xl shadow-xl shadow-slate-200/30 dark:shadow-none p-12 border border-slate-100 dark:border-slate-700 text-center">
                <div class="animate-spin rounded-full h-16 w-16 border-b-2 border-emerald-600 mx-auto"></div>
                <p class="mt-4 text-slate-600 dark:text-slate-400">Loading note...</p>
            </div>
            <div v-else-if="error" class="bg-white/80 dark:bg-slate-800/80 backdrop-blur-xl rounded-2xl shadow-xl shadow-slate-200/30 dark:shadow-none p-12 border border-red-200 dark:border-red-700 text-center">
                <div class="flex flex-col items-center gap-4">
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-16 w-16 text-red-500 dark:text-red-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z" />
                    </svg>
                    <div>
                        <h2 class="text-2xl font-bold text-red-600 dark:text-red-400 mb-2">Error Loading Note</h2>
                        <p class="text-lg text-red-700 dark:text-red-300">{{ error }}</p>
                    </div>
                    <button 
                        @click="router.push({ name: 'Home' })"
                        class="mt-4 px-6 py-3 bg-emerald-600 hover:bg-emerald-700 text-white font-medium rounded-xl transition-all duration-200 shadow-lg shadow-emerald-500/30 hover:shadow-emerald-500/40"
                    >
                        Back to Notes
                    </button>
                </div>
            </div>

            <div v-else-if="note" class="bg-white/80 dark:bg-slate-800/80 backdrop-blur-xl rounded-2xl shadow-xl shadow-slate-200/30 dark:shadow-none border border-slate-100 dark:border-slate-700 overflow-hidden">
                <div class="p-2 border-b border-slate-200 dark:border-slate-700">
                    <h1 class="text-4xl text-center font-bold text-slate-900 dark:text-white mb-4">
                        {{ note.title || 'Untitled' }}
                    </h1>
                    <div class="p-6 border-t border-slate-200 dark:border-slate-700 flex justify-center gap-4">
                        <button 
                            @click="handleEdit"
                            class="flex items-center gap-2 px-6 py-3 bg-emerald-600 hover:bg-emerald-700 text-white font-medium rounded-xl transition-all duration-200 shadow-lg shadow-emerald-500/30 hover:shadow-emerald-500/40"
                        >
                            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z" />
                            </svg>
                            Edit
                        </button>
                        <button 
                            @click="openDeleteModal"
                            class="flex items-center gap-2 px-6 py-3 bg-red-600 hover:bg-red-700 text-white font-medium rounded-xl transition-all duration-200 shadow-lg shadow-red-500/30 hover:shadow-red-500/40"
                        >
                            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
                            </svg>
                            Delete
                        </button>
                    </div>
                    <div class="flex flex-wrap gap-4 text-sm text-slate-500 dark:text-slate-400">
                        <div class="flex items-center gap-2">
                            <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
                            </svg>
                            <span>Created: {{ formatDate(note.created) }}</span>
                        </div>
                    </div>
                    
                    <NoteView :content="note.content"/>
                </div>
            </div>
        </div>

        <ConfirmModal
            :isOpen="showDeleteModal"
            title="Delete Note"
            :message="`Are you sure you want to delete '${note?.title || 'this note'}'? This action cannot be undone.`"
            confirmText="Delete"
            cancelText="Cancel"
            variant="danger"
            @confirm="confirmDelete"
            @cancel="closeDeleteModal"
        />
    </div>
</template>
<script setup>
import { ref, onMounted, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import BackButton from '@/components/BackButton.vue';
import NoteView from '@/components/NoteView.vue';
import ConfirmModal from '@/components/ConfirmModal.vue';
import notesService from '@/api/services/notesService.js';

const route = useRoute();
const router = useRouter();

const note = ref(null);
const loading = ref(true);
const error = ref(null);
const showDeleteModal = ref(false);

const formatDate = (dateString) => {
    if (!dateString) return 'No date';
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', { 
        year: 'numeric',
        month: 'long', 
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    });
};

const fetchNote = async () => {
    loading.value = true;
    error.value = null;
    note.value = null;
    
    try {
        const response = await notesService.getNote(route.params.id);
        note.value = response.data;
    } catch (err) {
        console.error('Error fetching note:', err);
        
        const backendMessage = err.response?.data?.message;
        
        if (err.response?.status === 404) {
            error.value = backendMessage || 'Note not found.';
        } else if (err.response?.status === 403) {
            error.value = backendMessage || 'You do not have permission to view this note.';
        } else {
            error.value = backendMessage || 'Failed to load note. Please try again.';
        }
    } finally {
        loading.value = false;
    }
};

const handleEdit = () => {
    router.push({ name: 'NoteEdit', params: { id: note.value.uuid } });
};

const openDeleteModal = () => {
    showDeleteModal.value = true;
};

const closeDeleteModal = () => {
    showDeleteModal.value = false;
};

const confirmDelete = async () => {
    try {
        await notesService.deleteNote(note.value.uuid);
        closeDeleteModal(); 
        router.push({ name: 'Home' }); 
    } catch (err) {
        console.error('Error deleting note:', err);
        error.value = err.response?.data?.message || 'Failed to delete note';
        closeDeleteModal();
    }
};

watch(() => route.params.id, (newId) => {
    if (newId) {
        fetchNote();
    }
});

onMounted(() => {
    fetchNote();
});
</script>
