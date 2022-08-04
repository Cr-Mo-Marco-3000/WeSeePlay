<template>
  <div class="video-area-case" :class="true ? 'non-side' : 'on-side'">
    <!-- isGameMode가 참이면 GameVideo가 나오게 하고, false라면 MeetingVideo가 나오게 짰어-->
    <LiarGameVideo v-if="isGameMode" :users="userList" />
    <MeetingVideo v-else :users="userList" />
  </div>
</template>

<script setup>
import LiarGameVideo from "./game/liargame/VideoList.vue"
import MeetingVideo from "./meeting/VideoList.vue"
import { ref } from "vue"

const isGameMode = ref(false)
const userList = ref([]) // Component에 넘겨줄 user list

// 이쪽은 샘플로 넣을 User 수 정하는 거 (최종본에는 지울 내용)
const customNumber = ref(13)
for (let i = 1; i < customNumber.value; i++) {
  const id = i
  const nickname = String.fromCharCode(i + 64)
  const onVideo = true
  userList.value.push({ id, nickname, onVideo })
}
</script>

<style>
.video-area-case {
  padding: 15px 15px 95px 15px;
  position: relative;
}

.non-side {
  height: 100%;
}
</style>
