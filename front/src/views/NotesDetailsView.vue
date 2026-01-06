<template>
    <div class="min-h-screen bg-[radial-gradient(circle_at_top_right,_var(--tw-gradient-stops))] from-emerald-50 via-slate-50 to-slate-100 dark:from-slate-900 dark:via-slate-900 dark:to-slate-800 py-12 px-4 transition-colors duration-300 relative overflow-hidden">
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

            <div v-else-if="error" class="bg-white/80 dark:bg-slate-800/80 backdrop-blur-xl rounded-2xl shadow-xl shadow-slate-200/30 dark:shadow-none p-12 border border-red-200 dark:border-red-800 text-center">
                <div class="w-16 h-16 bg-red-100 dark:bg-red-900/30 rounded-full flex items-center justify-center mx-auto mb-4">
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-8 w-8 text-red-600 dark:text-red-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                    </svg>
                </div>
                <h3 class="text-xl font-bold text-slate-900 dark:text-white mb-2">Error Loading Note</h3>
                <p class="text-slate-600 dark:text-slate-400">{{ error }}</p>
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
                            @click="handleDelete"
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
                        <div class="flex items-center gap-2">
                            <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z" />
                            </svg>
                            <span>Modified: {{ formatDate(note.modified) }}</span>
                        </div>
                    </div>
                    
                    <NoteView :content="note.content"/>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import BackButton from '@/components/BackButton.vue';
import VueMarkdown from 'vue-markdown-render'
import NoteView from '@/components/NoteView.vue';
const route = useRoute();
const router = useRouter();

const note = ref(null);
const loading = ref(true);
const error = ref(null);

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
    
    try {
        // TODO: Replace this with actual API call
        
        
        // Mock data for now - matches the HomePage mock data
        const mockNotes = [
          {
              uuid: '1',
              title: 'Complete Markdown Test',
              content: `# Heading 1
This is a paragraph with **bold text** and *italic text* and ***bold italic***.

## Heading 2
Here's a [link to Google](https://google.com) and some \`inline code\`.

### Heading 3
#### Heading 4

### Lists

**Unordered list:**
- Item one
- Item two
- Nested item
- Another nested item
- Item three

**Ordered list:**
1. First item
2. Second item
3. Third item

### Code Block

\`\`\`javascript
function hello() {
console.log("Hello, World!");
return true;
}
\`\`\`

### Blockquote

> This is a blockquote.
> It can span multiple lines.

### Task List

- [x] Completed task
- [ ] Incomplete task
- [ ] Another task

### Table

| Feature | Status |Priority |
|---------|--------|----------|
| Dark Mode | ✅ | High |
| Search | 🚧 | Medium |
| Export | ❌ | Low |

### Horizontal Rule

---

### Emphasis

This is **bold**, this is *italic*, and this is ~~strikethrough~~.
**Random Photo:**
### Images
![Random](https://picsum.photos/600/300)

![Alt text](https://via.placeholder.com/150)
---

That's it! If you can see all these elements properly styled, markdown is working correctly.`,
              created: '2024-01-15T10:30:00',
              modified: '2024-01-20T15:45:00'
          },

            {
                uuid: '2',
                title: 'Meeting Notes',
                content: '## Q1 2024 Planning\n\nDiscuss project timeline and milestones for Q1 2024.\n\n### Action Items\n1. Review requirements\n2. Set deadlines\n3. Assign tasks',
                created: '2024-01-18T09:00:00',
                modified: '2024-01-18T09:30:00'
            },
            {
                uuid: '3',
                title: 'Ideas',
                content: '# New Feature Ideas\n\n- Dark mode ✅\n- Search functionality\n- Tags\n- Sharing capabilities\n- Export to PDF',
                created: '2024-01-20T14:20:00',
                modified: '2024-01-22T11:15:00'
            },
            {
                uuid: '4',
                title: 'Grocery List',
                content: '## Shopping List\n\n- Milk\n- Eggs\n- Bread\n- Coffee\n- Vegetables\n  - Carrots\n  - Broccoli\n  - Spinach',
                created: '2024-01-21T08:00:00',
                modified: '2024-01-21T08:05:00'
            },
            {
                uuid: '5',
                title: 'Book Recommendations',
                content: '## Must Read Books\n\n### Software Engineering\n- *Clean Code* by Robert Martin\n- *Design Patterns* by Gang of Four\n- *The Pragmatic Programmer*\n\n### Personal Development\n- *Atomic Habits*\n- *Deep Work*',
                created: '2024-01-19T16:30:00',
                modified: '2024-01-23T10:00:00'
            }
        ];
        
        const foundNote = mockNotes.find(n => n.uuid === route.params.id);
        
        if (!foundNote) {
            error.value = 'Note not found';
        } else {
            note.value = foundNote;
        }
    } catch (err) {
        error.value = 'Failed to load note. Please try again.';
        console.error('Error fetching note:', err);
    } finally {
        loading.value = false;
    }
};

const handleEdit = () => {
    // TODO: Navigate to edit view or open edit modal
};

const handleDelete = () => {
    // TODO: Show confirmation dialog and delete note
    if (confirm('Are you sure you want to delete this note?')) {
        console.log('Delete note:', note.value.uuid);
        // After deleting, navigate back to home
        // router.push({ name: 'Home' });
    }
};

onMounted(() => {
    fetchNote();
});
</script>
