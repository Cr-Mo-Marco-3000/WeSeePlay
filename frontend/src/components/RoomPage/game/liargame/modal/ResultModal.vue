<template>
  <div class="modal modal-overlay" @mousedown.self="$emit('close')">
    <div v-if="!waiting" class="confetti">
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
    <div class="modal-window">
      <div class="top">
        <button @click="$emit('close')">X</button>
      </div>
      <div v-if="waiting" class="count-box">
        <h5>결과 발표</h5>
        <div class="timer" for="timer">
          <span class="timer-num">{{ time }}</span>
        </div>
      </div>
      <div v-if="!waiting && liarWin" class="winner-box">
        <div class="winner"><span>라이어</span> 승리!</div>
        <div class="video-box">대충 여기에 화면 띄워주기</div>
        <span>라이어 닉네임</span>
      </div>
      <div v-if="!waiting && !liarWin">
        <div class="winner"><span>참가자</span> 승리!</div>
      </div>
    </div>
  </div>
</template>
<script>
import { ref } from "vue"

export default {
  name: "ResultModal",
  setup() {
    const time = ref(3)
    const waiting = ref(true)
    const liarWin = ref(false)

    setInterval(function () {
      if (time.value > 0) {
        time.value -= 1
        const timer = document.querySelector(".timer")
        let color = ["", "#3c358a", "#695eef"]
        timer.style.background = `${color[time.value]}`
      }

      if (time.value == 0) {
        waiting.value = false
        // 라이어 승리시
        liarWin.value = true
        return
      }
    }, 1000)
    return { time, waiting, liarWin }
  },
}
</script>

<style scoped>
@import url("@/assets/roompage/Modal.css");

.modal-window {
  height: auto;
}
</style>
