<template>
  <div class="participant-item">
    <div>{{ userInfo.userNickname }}</div>
    <div>{{ userInfo.userStatus }}</div>
    <div>
      <i
        v-if="userInfo.isMicOpen"
        class="fa-solid fa-microphone-lines fa-xl"
      ></i>
      <i
        v-if="!userInfo.isMicOpen"
        style="color: rgba(255, 255, 255, 0.75)"
        class="fa-solid fa-microphone-lines-slash fa-xl"
      ></i>
    </div>
    <div>
      <i v-if="userInfo.isVideoOpen" class="fa-solid fa-video fa-xl"> </i>
      <i
        v-if="!userInfo.isVideoOpen"
        style="color: rgba(255, 255, 255, 0.75)"
        class="fa-solid fa-video-slash fa-xl"
      ></i>
    </div>
  </div>
</template>
<script setup>
// eslint-disable-next-line
defineProps({
  userInfo: Object,
})
</script>
<style>
@import url('../../../../src/assets/roompage/sidearea.css');
</style>
