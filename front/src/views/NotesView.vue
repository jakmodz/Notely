<template>
<NotesList class="mx-4" :notes="notes" @noteDeleted="handleNoteDeleted" />
</template>
<script setup>
import NotesList from "@/components/NotesList.vue";
import { ref, onMounted } from 'vue';
import notesService from '@/api/services/notesService.js';

const notes = ref([]);

const fetchNotes = async () => {
   notesService.getAllNotes()
    .then(response => {
        notes.value = response.data;
        console.log("Fetched notes:", notes.value);
    })
    .catch(error => {
        console.error("Error fetching notes:", error);
    });
};

const handleNoteDeleted = (deletedNoteUuid) => {
    notes.value = notes.value.filter(note => note.uuid !== deletedNoteUuid);
};

onMounted(() => {
    fetchNotes();
});
</script>
