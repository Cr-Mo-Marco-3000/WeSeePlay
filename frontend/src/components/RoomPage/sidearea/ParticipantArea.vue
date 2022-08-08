<template>
  <!-- 채팅 중일 때 참가자 리스트 -->
  <div class="title">참가자</div>
  <div id="participant-list-area" class="participant-list-area">
    <!-- 아래 양식 그대로 가져가면 됩니다. => class 순서가 아니라 nth-child로 적용 -->
    <ParticipantItem v-for="(user, idx) in userList" :key="idx" :user="user" />
  </div>
</template>

<script setup>
import ParticipantItem from './ParticipantItem.vue'
// eslint-disable-next-line
const props = defineProps({
  userList: Array,
})
</script>

<style scoped>
@import url('../../../../src/assets/roompage/sidearea.css');
</style>
