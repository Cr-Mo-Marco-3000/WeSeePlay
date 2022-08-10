<template>
  <div class="video-item">
    <div class="user-box">
      <!-- <p>{{ user.nickname }}</p> -->
      <div v-if="user">
        <ov-video :stream-manager="user" />
      </div>
    </div>
  </div>
</template>

<script setup>
import OvVideo from "./OvVideo.vue"
import { defineProps } from "vue"

defineProps({
  user: {
    type: Object,
    required: true,
  },
})
</script>

<style>
.video-item {
  padding: 10px;
}

.sample {
}

.user-box {
  background-color: #c9c5f1;
  border-radius: 15px;
  width: 100%;
}
</style>
