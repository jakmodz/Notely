<template>
<NotesList class="mx-4" :notes="mockNotes" />
</template>
<script setup>
import NotesList from "@/components/NotesList.vue";
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

</script>