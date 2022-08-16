<template>
  <div
    class="padding-area"
    :class="[talkNow ? 'talk-now-box' : 'non-talk-box']"
  >
    <div class="radius-area row">
      <div :class="[talkNow ? 'talk-now' : 'non-talk']">{{ nickname }}</div>
      <ov-video class="ov-area" :stream-manager="user" />
    </div>
  </div>
</template>

<script setup>
import OvVideo from "./OvVideo.vue"
import { defineProps, ref, watchEffect } from "vue"

const props = defineProps({
  user: {
    type: Object,
    required: true,
  },
})

let data = { ...props.user }
console.log("%c Main Video ", "color: black;background: brown", data)
data = JSON.parse(data.stream.connection.data)
const nickname = ref(data.userNickname)

const talkNow = ref(false)

if (props.user) {
  props.user.on("publisherStartSpeaking", () => {
    talkNow.value = true
  })

  props.user.on("publisherStopSpeaking", () => {
    talkNow.value = false
  })
}

watchEffect(() => {
  props.user
  let data = { ...props.user }
  console.log("%c Main Video ", "color: black;background: brown", data)
  data = JSON.parse(data.stream.connection.data)
  nickname.value = data.userNickname
})
</script>

<style scoped>
@import url("@/assets/roompage/videoBox.css");
</style>
