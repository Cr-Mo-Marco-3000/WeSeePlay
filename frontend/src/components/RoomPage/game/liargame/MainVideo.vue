<template>
  <div class="padding-area">
    <div
      class="radius-area row"
      :class="[talkNow ? 'talk-now-box' : 'non-talk-box']"
    >
      <div :class="[talkNow ? 'talk-now' : 'non-talk']">
        {{ nickname }}
      </div>
      <ov-video class="ov-area" :stream-manager="user" />
      <button
        v-if="myEmail === userEmail"
        @click="$emit('next')"
        class="next-btn"
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
})

const myEmail = store.getters.me.userEmail
let data = { ...props.user }
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
