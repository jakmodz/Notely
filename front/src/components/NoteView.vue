<template>
<div class="p-8">
    <div class="prose dark:prose-invert max-w-none">
        <VueMarkdown :source="content" />
    </div>
</div>
</template>
<script setup>
import VueMarkdown from 'vue-markdown-render'

const props = defineProps({
    content: {
        type: String,
        required: true
    }
});
</script>