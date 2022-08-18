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
        <div class="padding-area">
          <div class="radius-area row" :class="non - talk - box">
            <ov-video class="ov-area" :stream-manager="winnerVideo" />
          </div>
        </div>
        <div v-if="win === 2" class="word">
          <p>최고의 눈치를 가졌군요!</p>
        </div>
        <div v-else-if="win === 3" class="word">
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
import { ref, watchEffect } from "vue"
import OvVideo from "@/components/RoomPage/game/callmyname/OvVideo.vue"

export default {
  name: "CallmynameResultModal",
  props: ["winner", "isWin", "suggestion", "winnerVideo", "myEmail"],
  components: {
    OvVideo,
  },
  setup(props) {
    const win = ref(1)
    const winnerNickname = ref(props.winner)
    const word = ref(props.suggestion)
    watchEffect(() => {
      win.value = props.isWin
      winnerNickname.value = JSON.parse(
        props.winnerVideo.stream.connection.data
      ).userNickname
    })
    console.log(props.winner, props.isWin, props.suggestion, props.winnerVideo)
    return { win, winnerNickname, word }
  },
}
</script>

<style scoped>
@import url("@/assets/roompage/Modal.css");
@import url("@/assets/roompage/videoBox.css");
.modal-window {
  height: auto;
}
</style>
