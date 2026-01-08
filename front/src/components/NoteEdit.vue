<template>
    <vue-monaco-editor
        v-model:value="localContent"
        language="markdown"
        theme="vs-dark"
        width="100%"
        height="600px"
        :options="editorOptions"
        @change="handleChange"
    />
</template>

<script setup>
import { ref, watch } from 'vue';  
import { VueMonacoEditor } from '@guolao/vue-monaco-editor';

const props = defineProps({
    content: {
        type: String,
        default: ''
    }
});

const emit = defineEmits(['update:content']);

const localContent = ref(props.content || '');

const editorOptions = {
    automaticLayout: true,
    formatOnType: true,
    formatOnPaste: true,
    minimap: { enabled: false },
    lineNumbers: 'on',
    wordWrap: 'on',
};

const handleChange = (newValue) => {
    emit('update:content', newValue);
};

watch(() => props.content, (newContent) => {
    if (newContent !== localContent.value) {
        localContent.value = newContent || '';
    }
});
</script>
