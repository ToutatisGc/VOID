<template>
  <!-- eslint-disable vue/no-v-html -->
  <div v-html="getHtmlData" :class="$props.class" class="markdown-viewer"></div>
</template>

<script lang="ts" setup>
  import { computed } from '../../../../node_modules/vue';
  import showdown from '../../../../node_modules/@types/showdown';

  const converter = new showdown.Converter();
  converter.setOption('tables', true);
  const props = defineProps({
    value: { type: String },
    class: { type: String },
  });
  const getHtmlData = computed(() => converter.makeHtml(props.value || ''));
</script>

<style scoped>
  .markdown-viewer {
    width: 100%;
  }
</style>
