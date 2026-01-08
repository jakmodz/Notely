<template>
    <div class="min-h-screen bg-[radial-gradient(circle_at_top_right,var(--tw-gradient-stops))] from-emerald-50 via-slate-50 to-slate-100 dark:from-slate-900 dark:via-slate-900 dark:to-slate-800 py-12 px-4 transition-colors duration-300 relative overflow-hidden">
        <div class="absolute top-0 left-0 w-full h-full overflow-hidden -z-10 pointer-events-none">
            <div class="absolute -top-[10%] -left-[10%] w-[40%] h-[40%] rounded-full bg-emerald-100/50 dark:bg-emerald-900/20 blur-3xl"></div>
            <div class="absolute top-[30%] right-[10%] w-[30%] h-[30%] rounded-full bg-blue-100/40 dark:bg-blue-900/20 blur-3xl"></div>
            <div class="absolute -bottom-[10%] -right-[10%] w-[35%] h-[35%] rounded-full bg-purple-100/30 dark:bg-purple-900/20 blur-3xl"></div>
        </div>

        <div class="max-w-6xl mx-auto">
            <div class="mb-12 text-center">
                <div class="inline-flex items-center justify-center w-20 h-20 bg-white dark:bg-slate-800 rounded-3xl shadow-xl shadow-slate-200/50 dark:shadow-none mb-6 border border-slate-100 dark:border-slate-700">
                    <div class="w-14 h-14 bg-linear-to-br from-emerald-500 to-emerald-600 rounded-2xl flex items-center justify-center shadow-inner">
                        <svg xmlns="http://www.w3.org/2000/svg" class="h-7 w-7 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2" />
                        </svg>
                    </div>
                </div>
                <h1 class="text-5xl md:text-6xl font-black text-slate-900 dark:text-white mb-3 tracking-tight">
                    Dashboard
                </h1>
                <p class="text-lg text-slate-600 dark:text-slate-400 font-medium">
                    Create, organize, and manage all your thoughts in one place
                </p>
            </div>

            <div class="mb-12 group">
                <div class="bg-white/80 dark:bg-slate-800/80 backdrop-blur-xl rounded-[2rem] shadow-2xl shadow-slate-200/50 dark:shadow-none p-8 md:p-10 border border-white dark:border-slate-700 hover:shadow-emerald-500/10 transition-all duration-300 hover:-translate-y-1">
                    <div class="flex items-center gap-4 mb-6">
                        <div class="w-12 h-12 bg-linear-to-br from-emerald-500 to-emerald-600 rounded-2xl flex items-center justify-center shadow-lg shadow-emerald-500/30">
                            <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4" />
                            </svg>
                        </div>
                        <h2 class="text-2xl font-bold text-slate-900 dark:text-white">Create New Note</h2>
                    </div>
                    
                    <button class="group/btn w-full py-5 px-6 bg-slate-900 hover:bg-emerald-600 dark:bg-slate-700 dark:hover:bg-emerald-600 text-white font-bold rounded-2xl shadow-lg shadow-slate-900/20 hover:shadow-emerald-500/30 transition-all duration-300 active:scale-[0.98] flex items-center justify-center gap-3">
                        <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z" />
                        </svg>
                        <span class="tracking-wide">Start Writing</span>
                        <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 group-hover/btn:translate-x-1 transition-transform" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 7l5 5m0 0l-5 5m5-5H6" />
                        </svg>
                    </button>
                </div>
            </div>
            <div>
                <div class="flex items-center gap-3 mb-6">
                    <div class="w-10 h-10 bg-slate-900 dark:bg-slate-700 rounded-xl flex items-center justify-center">
                        <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
                        </svg>
                    </div>
                    <h2 class="text-3xl font-bold text-slate-900 dark:text-white">Recent Notes</h2>
                </div>
                
                <div v-if="!notes" class="bg-white/60 dark:bg-slate-800/60 backdrop-blur-xl rounded-[2rem] shadow-xl shadow-slate-200/30 dark:shadow-none p-12 border border-slate-100 dark:border-slate-700 text-center">
                    <div  class="max-w-md mx-auto">
                        <div class="w-24 h-24 bg-slate-100 dark:bg-slate-700 rounded-full flex items-center justify-center mx-auto mb-6">
                            <svg xmlns="http://www.w3.org/2000/svg" class="h-12 w-12 text-slate-400 dark:text-slate-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z" />
                            </svg>
                        </div>
                        <h3 class="text-xl font-bold text-slate-900 dark:text-white mb-2">No notes yet</h3>
                        <p class="text-slate-500 dark:text-slate-400">Create your first note to get started on your journey of organized thoughts!</p>
                    </div>
                </div>
                <div v-else>
                  <NotesList :notes="notes" @noteDeleted="handleNoteDeleted"/>
                </div>
            </div>
        </div>
    </div>
</template>


<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import NotesList from '@/components/NotesList.vue';
import notesService from '@/api/services/notesService';
const router = useRouter();
const notes = ref(null);

const fetchNotes = async () => {
   notesService.getAllNotes()
    .then(response => {
        notes.value = response.data;
    })
    .catch(error => {
        console.error("Error fetching notes:", error);
        notes.value = null;
    });
};
const handleNoteDeleted = (deletedNoteUuid) => {
    notes.value = notes.value.filter(note => note.uuid !== deletedNoteUuid);
};

onMounted(() => {
    fetchNotes();
});
</script>
