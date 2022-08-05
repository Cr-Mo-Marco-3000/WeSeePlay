<template>
  <div class="video-area-case" :class="true ? 'non-side' : 'on-side'">
    <button @click="isGameMode = !isGameMode">Game On/Off</button>
    <!-- isGameMode가 참이면 GameVideo가 나오게 하고, false라면 MeetingVideo가 나오게 짰어-->
    <LiarGameVideo v-if="isGameMode" :users="userList" :isSide="isSide" />
    <MeetingVideo v-else :users="userList" :isSide="isSide" />
  </div>
</template>

<script setup>
import LiarGameVideo from "./game/liargame/VideoList.vue"
import MeetingVideo from "./meeting/VideoList.vue"
import { ref, defineProps } from "vue"

const isGameMode = ref(true)
const userList = ref([]) // Component에 넘겨줄 user list
defineProps({
  isSide: {
    type: Boolean,
    required: true,
  },
})

// 이쪽은 샘플로 넣을 User 수 정하는 거 (최종본에는 지울 내용)
const customNumber = ref(10)
for (let i = 1; i < customNumber.value; i++) {
  const id = i
  const nickname = String.fromCharCode(i + 64)
  const onVideo = false
  userList.value.push({ id, nickname, onVideo })
}
</script>

<style>
.non-side {
  height: 100%;
}
</style>
