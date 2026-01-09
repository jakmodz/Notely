<template>
<NotesList class="mx-4" :notes="notes" @noteDeleted="handleNoteDeleted" />
<div class="mx-4">
    <div v-if="errorMessage" class="  bg-white/60 dark:bg-slate-800/60 backdrop-blur-xl rounded-[2rem] shadow-xl shadow-slate-200/30 dark:shadow-none p-12 border border-slate-100 dark:border-slate-700 text-center">
        <div  class="max-w-md mx-auto">
            <div class="w-24 h-24 bg-red-100 dark:bg-red-900/30 rounded-full flex items-center justify-center mx-auto mb-6">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-12 w-12 text-red-600 dark:text-red-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>
            </div>
            <h3 class="text-xl font-bold text-slate-900 dark:text-white mb-2">Error Loading Notes</h3>
            <p class="text-slate-500 dark:text-slate-400">{{ errorMessage }}</p>
        </div>
    </div>
    <div v-else-if="notes.length ==  0">
        <div class=" flex justify-center align-center bg-white/60 dark:bg-slate-800/60 backdrop-blur-xl rounded-[2rem] shadow-xl shadow-slate-200/30 dark:shadow-none p-12 border border-slate-100 dark:border-slate-700 text-center">
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
    </div>
</div>

</template>
<script setup>
import NotesList from "@/components/NotesList.vue";
import { ref, onMounted } from 'vue';
import notesService from '@/api/services/notesService.js';

const notes = ref([]);
const errorMessage = ref('');
const fetchNotes = async () => {
   notesService.getAllNotes()
    .then(response => {
      notes.value = response.data;
    })
    .catch(error => {
        errorMessage.value = 'Failed to load notes. Please try again later.';
    });
};

const handleNoteDeleted = (deletedNoteUuid) => {
    notes.value = notes.value.filter(note => note.uuid !== deletedNoteUuid);
};
onMounted(() => {
    fetchNotes();
});
</script>