<template>
  <div class="video-item">
    <div class="user-video-box">
      <div v-if="user">
        <ov-video :stream-manager="user" />
      </div>
      <div>
        <p v-if="myEmail === videoUserEmail">이름: ???</p>
        <p v-else>이름: {{ suggestion }}</p>
      </div>
      <button
        v-if="myEmail === userEmail && myEmail === videoUserEmail"
        @click.stop="$emit('next')"
      >
        NEXT {{ gameIdx }}
      </button>
      <button
        v-if="
          myEmail === userEmail && coolDown <= 0 && myEmail === videoUserEmail
        "
        @click.stop="$emit('answer')"
      >
        What is your name?
      </button>
    </div>
  </div>
</template>

<script setup>
import store from "@/store"
import { defineProps } from "vue"
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
console.log("asdasdasdasdasdasdasdasdsa", myEmail, props.userEmail)
</script>

<style scoped>
.user-video-box {
  background-color: #c9c5f1;
  border-radius: 15px;
  width: 100%;
}
</style>
