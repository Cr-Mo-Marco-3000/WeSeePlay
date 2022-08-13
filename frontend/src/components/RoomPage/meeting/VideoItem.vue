<template>
  <div class="video-item">
    <div class="user-video-box">
      <!-- <p>{{ user.nickname }}</p> -->
      <p>{{ talkNow }}</p>
      <div v-if="user">
        <ov-video :stream-manager="user" />
      </div>
    </div>
  </div>
</template>

<script setup>
import OvVideo from "./OvVideo.vue"
import { defineProps, ref } from "vue"

const props = defineProps({
  user: {
    type: Object,
    required: true,
  },
})

const talkNow = ref(false)
if (props.user) {
  props.user.on("publisherStartSpeaking", () => {
    talkNow.value = true
  })

  props.user.on("publisherStopSpeaking", () => {
    talkNow.value = false
  })
}
</script>

<style>
.video-item {
  padding: 10px;
}

.user-video-box {
  background-color: #c9c5f1;
  border-radius: 15px;
  width: 100%;
}
</style>
