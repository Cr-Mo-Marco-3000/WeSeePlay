<template>
  <!-- 채팅 중일 때 참가자 리스트 -->
  <div class="title">참가자</div>
  <div id="participant-list-area" class="participant-list-area">
    <div class="participant-item">
      <div>{{ roomInfo.hostNickname }}</div>
      <div>호스트</div>
    </div>
    <div
      class="participant-item"
      v-for="(user, idx) in roomUserInfo"
      :key="idx"
    >
      <div>
        {{ user.userNickname }}
      </div>
    </div>
  </div>
</template>

<script setup>
// eslint-disable-next-line
const props = defineProps({
  roomUserInfo: Array,
  roomInfo: Object,
})
console.log(props.roomInfo)
</script>

<style scoped>
@import url('../../../../src/assets/roompage/sidearea.css');
</style>
