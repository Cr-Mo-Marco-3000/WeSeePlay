<template>
  <div class="modal modal-overlay">
    <div v-if="win" class="confetti">
      <div class="confetti-piece"></div>
      <div class="confetti-piece"></div>
      <div class="confetti-piece"></div>
      <div class="confetti-piece"></div>
      <div class="confetti-piece"></div>
      <div class="confetti-piece"></div>
      <div class="confetti-piece"></div>
      <div class="confetti-piece"></div>
      <div class="confetti-piece"></div>
      <div class="confetti-piece"></div>
      <div class="confetti-piece"></div>
      <div class="confetti-piece"></div>
      <div class="confetti-piece"></div>
    </div>
    <div v-if="win" class="face-icon" style="color: #ff5194">
      <i class="fa-solid fa-face-laugh-squint"></i>
    </div>
    <div v-else class="face-icon" style="color: #625eef">
      <i class="fa-solid fa-face-sad-cry"></i>
    </div>
    <div class="modal-window">
      <div class="top">
        <button @click="$emit('close')">X</button>
      </div>
      <div class="winner-box">
        <div class="winner">
          <span>{{ winnerNickname }}</span> 승리!
        </div>
        <div class="video-box">대충 여기에 화면 띄워주기</div>
        <div v-if="win" class="word">
          <p>최고의 눈치를 가졌군요!</p>
        </div>
        <div v-else class="word">
          <div>
            당신의 제시어: <span>{{ word }}</span>
          </div>
          <p style="margin-top: 0.5rem">안타까운 패배군요!</p>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { ref } from "vue"

export default {
  name: "CallmynameResultModal",
  props: ["winner", "isWin", "suggestion"],
  setup(props) {
    console.log(props.winner, props.isWin, props.suggestion)
    const win = ref(props.isWin)
    const winnerNickname = ref(props.winner)
    const word = ref(props.suggestion)
    return { win, winnerNickname, word }
  },
}
</script>

<style scoped>
@import url("@/assets/roompage/Modal.css");

.modal-window {
  height: auto;
}
</style>
