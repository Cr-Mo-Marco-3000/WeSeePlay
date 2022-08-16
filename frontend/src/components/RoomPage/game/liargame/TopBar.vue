<template>
  <div class="top-bar">
    <div></div>
    <div>
      <!-- 라이어일때 뜨는 부분 -->
      <div v-if="isLiar">
        <span class="notify-span">당신은 Liar입니다</span>
      </div>
      <!-- 일반 참가자일때 뜨는 부분 -->
      <div v-if="!isLiar">
        <span class="notify-span">제시어: ㅇㅇㅇ</span>
      </div>
    </div>
    <div>
      <button v-if="isHost" class="end-button">
        <div>
          <i class="fa-solid fa-person-running fa-xl"></i>
        </div>
        <div>
          <span>게임 종료</span>
        </div>
      </button>
    </div>
  </div>
</template>
<script setup>
import { ref } from "vue"
// eslint-disable-next-line
defineProps({
  isHost: Boolean,
})
const isLiar = ref(false)
</script>
<style scoped>
@import url("../../../../assets/roompage/topbar.css");
</style>
