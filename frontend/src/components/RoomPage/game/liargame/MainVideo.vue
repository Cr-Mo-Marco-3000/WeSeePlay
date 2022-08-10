<template>
  <div class="video-item">
    <div class="user-box">
      <div v-if="user">
        <ov-video :stream-manager="user" />
      </div>
      <button @click.stop="$emit('next')">NEXT</button>
    </div>
  </div>
</template>

<script setup>
import { defineProps } from "vue"
import OvVideo from "./OvVideo.vue"

defineProps({
  user: {
    type: Object,
    required: true,
  },
})
</script>

<style scoped>
.user-box {
  background-color: #c9c5f1;
  border-radius: 15px;
  width: 100%;
}
</style>
