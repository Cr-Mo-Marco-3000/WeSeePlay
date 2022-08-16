<template>
  <div class="padding-area">
    <div class="radius-area row">
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
const nickname = store.getters.getNickname(props.userEmail)

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
.padding-area {
  padding: 5px;
  background-color: #c9c5f1;
  border-radius: 15px;
}
.radius-area {
  border-radius: 15px;
  overflow: hidden;
  position: relative;
}

.talk-now {
  position: absolute;
  top: 15px;
  left: 15px;
  background: rgb(94, 144, 219);
  color: rgba(255, 255, 255, 0.8);
  font-size: 18px;
  padding: 2px 12px;
  border-radius: 5px;
}

.non-talk {
  position: absolute;
  top: 15px;
  left: 15px;
  color: rgb(0, 0, 0);
  background: rgba(255, 255, 255, 0.8);
  font-size: 18px;
  padding: 2px 12px;
  border-radius: 5px;
}

.next-btn {
  position: absolute;
  bottom: 10px;
  right: 10px;
  border-radius: 5px;
  padding: 2px 12px;
  z-index: 2;
  cursor: pointer;
}
</style>
