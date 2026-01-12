<template>
    <div class="my-2">
        <div v-if="!viewOnly" class="flex flex-wrap items-center gap-3 mb-6 p-4 bg-white/60 dark:bg-slate-800/60 backdrop-blur-xl rounded-2xl border border-slate-100 dark:border-slate-700">
            <label class="text-sm font-medium text-slate-600 dark:text-slate-400">Sort by:</label>
            <select 
                v-model="sortBy"
                @change="onSortChange"
                class="px-4 py-2 bg-white dark:bg-slate-800 border border-slate-200 dark:border-slate-700 rounded-xl text-sm font-medium text-slate-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-emerald-500 transition-all cursor-pointer hover:border-emerald-500"
            >
                <option value="modified">Last Modified</option>
                <option value="created">Date Created</option>
                <option value="title">Title (A-Z)</option>
            </select>
            
            <button 
                @click="toggleSortOrder"
                class="p-2 bg-white dark:bg-slate-800 border border-slate-200 dark:border-slate-700 rounded-xl hover:bg-slate-50 dark:hover:bg-slate-700 hover:border-emerald-500 transition-all focus:outline-none focus:ring-2 focus:ring-emerald-500"
                :title="sortOrder === 'ASC' ? 'Ascending order' : 'Descending order'"
            >
                <svg v-if="sortOrder === 'DESC'" xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-slate-700 dark:text-slate-300" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7" />
                </svg>
                <svg v-else xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-slate-700 dark:text-slate-300" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 15l7-7 7 7" />
                </svg>
            </button>

            <label class="text-sm font-medium text-slate-600 dark:text-slate-400 ml-4">Per page:</label>
            <select 
                :value="pagination.size"
                @change="$emit('pageSizeChange', Number($event.target.value))"
                class="px-4 py-2 bg-white dark:bg-slate-800 border border-slate-200 dark:border-slate-700 rounded-xl text-sm font-medium text-slate-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-emerald-500 transition-all cursor-pointer hover:border-emerald-500"
            >
                <option value="6">6</option>
                <option value="12">12</option>
                <option value="24">24</option>
                <option value="48">48</option>
            </select>
            <input
                class="text-center text-slate-600 dark:text-slate-400 ml-4 
                px-4 py-2 bg-white dark:bg-slate-800 border border-slate-200 dark:border-slate-700 rounded-xl text-sm font-medium text-slate-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-emerald-500 transition-all cursor-pointer hover:border-emerald-500
                "
                v-model="localSearch"
                @input="onSearchInput"
                placeholder="Search"
            />

            <div class="text-xs text-slate-500 dark:text-slate-500 ml-auto">
                {{ pagination.totalElements }} {{ pagination.totalElements === 1 ? 'note' : 'notes' }}
            </div>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            <Note 
                v-for="note in notes" 
                :key="note.uuid"
                :note="note"
                @view="handleView"
                @edit="handleEdit"
                @delete="openDeleteModal"
            />
        </div>

        <!-- Pagination Controls -->
        <div v-if="pagination.totalPages > 1" class="mt-8 flex items-center justify-center gap-2">
            <button
                @click="goToPage(0)"
                :disabled="pagination.page === 0"
                class="px-3 py-2 bg-white dark:bg-slate-800 border border-slate-200 dark:border-slate-700 rounded-xl hover:bg-slate-50 dark:hover:bg-slate-700 disabled:opacity-50 disabled:cursor-not-allowed transition-all"
                title="First page"
            >
                <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-slate-700 dark:text-slate-300" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 19l-7-7 7-7m8 14l-7-7 7-7" />
                </svg>
            </button>

            <button
                @click="goToPage(pagination.page - 1)"
                :disabled="pagination.page === 0"
                class="px-3 py-2 bg-white dark:bg-slate-800 border border-slate-200 dark:border-slate-700 rounded-xl hover:bg-slate-50 dark:hover:bg-slate-700 disabled:opacity-50 disabled:cursor-not-allowed transition-all"
                title="Previous page"
            >
                <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-slate-700 dark:text-slate-300" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7" />
                </svg>
            </button>

            <!-- Page Numbers -->
            <div class="flex gap-2">
                <button
                    v-for="pageNum in visiblePages"
                    :key="pageNum"
                    @click="goToPage(pageNum)"
                    :class="[
                        'px-4 py-2 rounded-xl font-medium transition-all',
                        pageNum === pagination.page
                            ? 'bg-emerald-600 text-white'
                            : 'bg-white dark:bg-slate-800 border border-slate-200 dark:border-slate-700 text-slate-700 dark:text-slate-300 hover:bg-slate-50 dark:hover:bg-slate-700'
                    ]"
                >
                    {{ pageNum + 1 }}
                </button>
            </div>

            <button
                @click="goToPage(pagination.page + 1)"
                :disabled="pagination.page >= pagination.totalPages - 1"
                class="px-3 py-2 bg-white dark:bg-slate-800 border border-slate-200 dark:border-slate-700 rounded-xl hover:bg-slate-50 dark:hover:bg-slate-700 disabled:opacity-50 disabled:cursor-not-allowed transition-all"
                title="Next page"
            >
                <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-slate-700 dark:text-slate-300" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
                </svg>
            </button>

            <button
                @click="goToPage(pagination.totalPages - 1)"
                :disabled="pagination.page >= pagination.totalPages - 1"
                class="px-3 py-2 bg-white dark:bg-slate-800 border border-slate-200 dark:border-slate-700 rounded-xl hover:bg-slate-50 dark:hover:bg-slate-700 disabled:opacity-50 disabled:cursor-not-allowed transition-all"
                title="Last page"
            >
                <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-slate-700 dark:text-slate-300" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 5l7 7-7 7M5 5l7 7-7 7" />
                </svg>
            </button>
        </div>

        <div v-if="pagination.totalPages > 1" class="mt-4 text-center text-sm text-slate-500 dark:text-slate-400">
            Page {{ pagination.page + 1 }} of {{ pagination.totalPages }}
            <span class="mx-2">•</span>
            Showing {{ notes.length }} of {{ pagination.totalElements }} notes
        </div>
    </div>
</template>
<script setup>
import { ref, computed, onMounted, watch } from 'vue';
import { useRouter } from 'vue-router';
import Note from './Note.vue';
import ConfirmModal from './ConfirmModal.vue';
import notesService from '@/api/services/notesService.js';
import handleApiError from '@/util/apiError.js';

const router = useRouter();

const props = defineProps({
    notes: {
        type: Array,
        required: true
    },
    pagination: {
        type: Object,
        required: true
    },
    currentSort: {
        type: Object,
        default: () => ({ sortBy: 'created', sortDirection: 'DESC' })
    },
    viewOnly: {
        type: Boolean,
        default: false
    },
    search: {
        type: String,
        default: ''
    }
});

const DELAY = import.meta.env.VITE_SEARCH_DELAY || 300; 
const emit = defineEmits(['noteDeleted', 'pageChange', 'sortChange', 'pageSizeChange','searchChange']);

const localSearch = ref(props.search || '');
const sortBy = ref(props.currentSort.sortBy);
const sortOrder = ref(props.currentSort.sortDirection);
const showDeleteModal = ref(false);
const noteToDelete = ref(null);

watch(() => props.search, (newValue) => {
    localSearch.value = newValue || '';
});

let timeout;
const onSearchInput = (event) => {
    localSearch.value = event.target.value;
    clearTimeout(timeout);
    timeout = setTimeout(() => {
        emit('searchChange', event.target.value);
    }, DELAY);
};

watch(() => props.currentSort, (newSort) => {
    sortBy.value = newSort.sortBy;
    sortOrder.value = newSort.sortDirection;
}, { deep: true });

const visiblePages = computed(() => {
    const totalPages = props.pagination.totalPages;
    const currentPage = props.pagination.page;
    const maxVisible = 5;
    
    if (totalPages <= maxVisible) {
        return Array.from({ length: totalPages }, (_, i) => i);
    }
    
    let start = Math.max(0, currentPage - Math.floor(maxVisible / 2));
    let end = Math.min(totalPages, start + maxVisible);
    
    if (end - start < maxVisible) {
        start = Math.max(0, end - maxVisible);
    }
    
    return Array.from({ length: end - start }, (_, i) => start + i);
});

const goToPage = (page) => {
    if (page >= 0 && page < props.pagination.totalPages) {
        emit('pageChange', page);
    }
};

const toggleSortOrder = () => {
    sortOrder.value = sortOrder.value === 'ASC' ? 'DESC' : 'ASC';
    onSortChange();
};

const onSortChange = () => {
    emit('sortChange', {
        sortBy: sortBy.value,
        sortDirection: sortOrder.value
    });
};

const handleView = (note) => {
    router.push({ name: 'NoteDetails', params: { id: note.uuid } });
};

const handleEdit = (note) => {
    router.push({ name: 'NoteEdit', params: { id: note.uuid } });
};

const openDeleteModal = (note) => {
    noteToDelete.value = note;
    showDeleteModal.value = true;
};

const closeDeleteModal = () => {
    showDeleteModal.value = false;
    noteToDelete.value = null;
};

const confirmDelete = async () => {
    if (!noteToDelete.value) return;
    
    try {
        await notesService.deleteNote(noteToDelete.value.uuid);
        emit('noteDeleted', noteToDelete.value.uuid);
        closeDeleteModal();
    } catch (err) {
        const errorMessage = handleApiError(err);
        closeDeleteModal();
    }
};

onMounted(() => {
    sortBy.value = props.currentSort.sortBy;
    sortOrder.value = props.currentSort.sortDirection;
});
</script>
