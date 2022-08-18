<template>
  <video id="uervido" ref="root" autoplay />
</template>

<script setup>
import { defineProps, onMounted, ref } from "vue"
const props = defineProps({
  streamManager: Object,
})
const root = ref(null)
onMounted(() => {
  console.log("Ovvideo this is", props.streamManager)
  props.streamManager.addVideoElement(root.value)
})
</script>

<style scoped>
#uervido {
  width: 100%;
}
</style>
