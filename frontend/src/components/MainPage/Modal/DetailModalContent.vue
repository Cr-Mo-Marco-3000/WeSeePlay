<template>
  <div class="detail-text">
    <span>제목</span>
    <p>{{ info.title }}</p>
  </div>
  <div class="host-info">
    <span>호스트 </span>
    <p>{{ info.hostNickname }}</p>
  </div>
  <div class="time-info">
    <span>방 개설 시간</span>
    <p>{{ info.callStartTime }}</p>
  </div>
  <div class="discript-info detail-text">
    <span>내용</span>
    <p>{{ info.descript }}</p>
  </div>
  <div v-if="!info.private" class="detail-text">
    <span>참여자 목록</span>
    <div class="user-list">
      <div v-for="user, idx in info.joinUsers" :key="idx">{{ user }}</div>
    </div>
  </div>
  <div v-else class="user-input room-pw-input">
    <span>비밀번호</span>
    <p>비공개 방입니다.</p>
    <input type="password">
  </div>
  
  <button class="overlay__btn join-btn">방 입장</button>
</template>

<script>
// import { ref } from 'vue'

export default {
  name: "DetailModalContent",
  props: { 
    info: Object 
    },
}
</script>

<style>

</style>