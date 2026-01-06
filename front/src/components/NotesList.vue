<template>
    <div class="my-2">
        <div class="flex flex-wrap items-center gap-3 mb-6 p-4 bg-white/60 dark:bg-slate-800/60 backdrop-blur-xl rounded-2xl border border-slate-100 dark:border-slate-700">
            <label class="text-sm font-medium text-slate-600 dark:text-slate-400">Sort by:</label>
            <select 
                v-model="sortBy"
                class="px-4 py-2 bg-white dark:bg-slate-800 border border-slate-200 dark:border-slate-700 rounded-xl text-sm font-medium text-slate-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-emerald-500 transition-all cursor-pointer hover:border-emerald-500"
            >
                <option value="modified">Last Modified</option>
                <option value="created">Date Created</option>
                <option value="title">Title (A-Z)</option>
                <option value="content">Content</option>
            </select>
            
            <button 
                @click="toggleSortOrder"
                class="p-2 bg-white dark:bg-slate-800 border border-slate-200 dark:border-slate-700 rounded-xl hover:bg-slate-50 dark:hover:bg-slate-700 hover:border-emerald-500 transition-all focus:outline-none focus:ring-2 focus:ring-emerald-500"
                :title="sortOrder === 'asc' ? 'Ascending order' : 'Descending order'"
            >
                <svg v-if="sortOrder === 'desc'" xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-slate-700 dark:text-slate-300" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7" />
                </svg>
                <svg v-else xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-slate-700 dark:text-slate-300" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 15l7-7 7 7" />
                </svg>
            </button>

            <div class="text-xs text-slate-500 dark:text-slate-500 ml-auto">
                {{ notes.length }} {{ notes.length === 1 ? 'note' : 'notes' }}
            </div>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            <Note 
                v-for="note in sortedNotes" 
                :key="note.uuid"
                :note="note"
                @view="handleView"
                @edit="handleEdit"
                @delete="handleDelete"
            />
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue';
import Note from './Note.vue';
import router from '@/router/index.js';
const props = defineProps({
    notes: {
        type: Array,
        required: true
    }
});

const emit = defineEmits(['view', 'edit', 'delete']);

const sortBy = ref('modified');
const sortOrder = ref('desc');


const sortedNotes = computed(() => {
    if (!props.notes || props.notes.length === 0) return [];
    
    const notesCopy = [...props.notes];
    
    return notesCopy.sort((a, b) => {
        let comparison = 0;
        
        switch (sortBy.value) {
            case 'title':
                comparison = (a.title || '').localeCompare(b.title || '', undefined, { 
                    sensitivity: 'base',
                    ignorePunctuation: true 
                });
                break;
            case 'created':
                comparison = new Date(a.created) - new Date(b.created);
                break;
            case 'modified':
                comparison = new Date(a.modified) - new Date(b.modified);
                break;
            case 'content':
                comparison = (a.content || '').localeCompare(b.content || '', undefined, { 
                    sensitivity: 'base',
                    ignorePunctuation: true 
                });
                break;
            default:
                comparison = new Date(a.modified) - new Date(b.modified);
        }
        
        return sortOrder.value === 'asc' ? comparison : -comparison;
    });
});

const toggleSortOrder = () => {
    sortOrder.value = sortOrder.value === 'asc' ? 'desc' : 'asc';
    saveSortPreference();
};

const saveSortPreference = () => {
    localStorage.setItem('notesSortBy', sortBy.value);
    localStorage.setItem('notesSortOrder', sortOrder.value);
};

const loadSortPreference = () => {
    const savedSortBy = localStorage.getItem('notesSortBy');
    const savedSortOrder = localStorage.getItem('notesSortOrder');
    
    if (savedSortBy) {
        sortBy.value = savedSortBy;
    }
    if (savedSortOrder) {
        sortOrder.value = savedSortOrder;
    }
};


const handleEdit = (note) => emit('edit', note);
const handleDelete = (note) => emit('delete', note);

const handleView = (note) => {
    router.push({ name: 'NoteDetails', params: { id: note.uuid } });
};
watch(sortBy, () => {
    saveSortPreference();
});

onMounted(() => {
    loadSortPreference();
});
</script>
