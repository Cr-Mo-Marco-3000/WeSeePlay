<template>
  <!-- 채팅 중일 때 참가자 리스트 -->
  <div class="title">참가자</div>
  <div id="participant-list-area" class="participant-list-area">
    <!-- 아래 양식 그대로 가져가면 됩니다. => class 순서가 아니라 nth-child로 적용 -->
    <div id="1" class="participant-item">
      <div>참가자 이름</div>
      <div>참가자 권한</div>
      <div>
        <i class="fa-solid fa-microphone-lines fa-xl"></i>
      </div>
      <div>
        <i class="fa-solid fa-video fa-xl"></i>
      </div>
    </div>
    <div id="2" class="participant-item">
      <div>바닐라</div>
      <div>호스트</div>
      <div>
        <i class="fa-solid fa-microphone-lines fa-xl"></i>
      </div>
      <div>
        <i class="fa-solid fa-video fa-xl"></i>
      </div>
    </div>
    <div id="3" class="participant-item">
      <div>초콜릿</div>
      <div>참여자</div>
      <div>
        <i
          style="color: rgba(255, 255, 255, 0.75)"
          class="fa-solid fa-microphone-lines-slash fa-xl"
        ></i>
      </div>
      <div>
        <i
          style="color: rgba(255, 255, 255, 0.75)"
          class="fa-solid fa-video-slash fa-xl"
        ></i>
      </div>
    </div>
    <button id="temp-button">임시버튼</button>
  </div>
</template>

<script setup>
import { watchEffect } from 'vue'

watchEffect(() => {})
</script>

<style scoped>
@import url('../../../../src/assets/roompage/sidearea.css');
</style>
