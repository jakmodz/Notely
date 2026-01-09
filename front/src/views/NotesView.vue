<template>
<div class="mx-4">
    <div v-if="isLoading" class="bg-white/60 dark:bg-slate-800/60 backdrop-blur-xl rounded-[2rem] shadow-xl shadow-slate-200/30 dark:shadow-none p-12 border border-slate-100 dark:border-slate-700 text-center">
        <div class="max-w-md mx-auto">
            <div class="w-24 h-24 bg-emerald-100 dark:bg-emerald-900/30 rounded-full flex items-center justify-center mx-auto mb-6">
                <svg class="animate-spin h-12 w-12 text-emerald-600 dark:text-emerald-400" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                    <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                    <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                </svg>
            </div>
            <h3 class="text-xl font-bold text-slate-900 dark:text-white mb-2">Loading Notes...</h3>
            <p class="text-slate-500 dark:text-slate-400">Please wait while we fetch your notes</p>
        </div>
    </div>
    
    <div v-else-if="errorMessage" class="bg-white/60 dark:bg-slate-800/60 backdrop-blur-xl rounded-[2rem] shadow-xl shadow-slate-200/30 dark:shadow-none p-12 border border-slate-100 dark:border-slate-700 text-center">
        <div class="max-w-md mx-auto">
            <div class="w-24 h-24 bg-red-100 dark:bg-red-900/30 rounded-full flex items-center justify-center mx-auto mb-6">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-12 w-12 text-red-600 dark:text-red-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>
            </div>
            <h3 class="text-xl font-bold text-slate-900 dark:text-white mb-2">Error Loading Notes</h3>
            <p class="text-slate-500 dark:text-slate-400 mb-4">{{ errorMessage }}</p>
            <button 
                @click="fetchNotes" 
                class="px-6 py-3 bg-emerald-600 hover:bg-emerald-700 text-white font-medium rounded-xl transition-colors duration-200">
                Try Again
            </button>
        </div>
    </div>
    
    <div v-else-if="notes.length === 0" class="bg-white/60 dark:bg-slate-800/60 backdrop-blur-xl rounded-[2rem] shadow-xl shadow-slate-200/30 dark:shadow-none p-12 border border-slate-100 dark:border-slate-700 text-center">
        <div class="max-w-md mx-auto">
            <div class="w-24 h-24 bg-slate-100 dark:bg-slate-700 rounded-full flex items-center justify-center mx-auto mb-6">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-12 w-12 text-slate-400 dark:text-slate-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z" />
                </svg>
            </div>
            <h3 class="text-xl font-bold text-slate-900 dark:text-white mb-2">No notes yet</h3>
            <p class="text-slate-500 dark:text-slate-400">Create your first note to get started on your journey of organized thoughts!</p>
        </div>
    </div>
    
    <NotesList v-else :notes="notes" @noteDeleted="handleNoteDeleted" />
</div>
</template>

<script setup>
import NotesList from "@/components/NotesList.vue";
import { ref, onMounted } from 'vue';
import notesService from '@/api/services/notesService.js';

const notes = ref([]);
const errorMessage = ref('');
const isLoading = ref(false);

const fetchNotes = async () => {
    isLoading.value = true;
    errorMessage.value = '';
    
    try {
        const response = await notesService.getAllNotes();
        notes.value = response.data;
    } catch (error) {
        console.error('Error fetching notes:', error);
        errorMessage.value = 'Failed to load notes. Please try again later.';
        notes.value = [];
    } finally {
        isLoading.value = false;
    }
};

const handleNoteDeleted = (deletedNoteUuid) => {
    notes.value = notes.value.filter(note => note.uuid !== deletedNoteUuid);
};

onMounted(() => {
    fetchNotes();
});
</script>
