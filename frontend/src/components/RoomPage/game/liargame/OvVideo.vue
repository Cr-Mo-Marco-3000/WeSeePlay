<template>
  <video id="uervido" ref="root" autoplay />
</template>

<script setup>
import { defineProps, onMounted, ref, watchEffect } from "vue"
const props = defineProps({
  streamManager: Object,
})
const root = ref(null)
onMounted(() => {
  console.log("Ovvideo this is", props.streamManager)
  props.streamManager.addVideoElement(root.value)
})
watchEffect(() => {
  // console.log(
  //   "여기 정보에요",
  //   JSON.parse(props.streamManager.stream.connection.data).clientData
  // )
})
</script>

<style scoped>
#uervido {
  width: 100%;
}
</style>
