<template>
  <div class="participant-item">
    <div>{{ userInfo.userNickname }}</div>
    <div>{{ userInfo.userStatus }}</div>
    <div>게임 중</div>
    <div>대기 중</div>
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
