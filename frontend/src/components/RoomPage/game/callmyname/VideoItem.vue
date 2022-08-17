<template>
  <div class="padding-area">
    <div
      class="radius-area row"
      :class="[talkNow ? 'talk-now-box' : 'non-talk-box']"
    >
      <div :class="[talkNow ? 'talk-now' : 'non-talk']">
        {{ nickname }}
        <br />
        <span v-if="myEmail === videoUserEmail">이름: ???</span>
        <span v-else>이름: {{ suggestion }}</span>
      </div>
      <ov-video class="ov-area" :stream-manager="user" />
      <button
        class="next-btn"
        v-if="myEmail === userEmail && myEmail === videoUserEmail"
        @click.stop="$emit('next')"
      >
        NEXT
      </button>
    </div>
  </div>
</template>

<script setup>
import store from "@/store"
import { defineProps, ref } from "vue"
import OvVideo from "./OvVideo.vue"

const props = defineProps({
  user: {
    type: Object,
    required: true,
  },
  gameIdx: {
    type: Number,
  },
  userEmail: {
    type: String,
  },
  suggestion: {
    type: String,
  },
  coolDown: {
    type: Number,
  },
  videoUserEmail: {
    type: String,
  },
})

const myEmail = store.getters.me.userEmail
let data = { ...props.user }
console.log("%c Main Video ", "color: black;background: brown", data)
data = JSON.parse(data.stream.connection.data)
const nickname = data.userNickname

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

<style scoped>
@import url("@/assets/roompage/videoBox.css");
</style>
