<template>
  <div class="modal modal-overlay">
    <div class="modal-window">
      <div>
        <div class="winner">
          여러분의 선택, <span>{{ liarNickname }}</span>
        </div>
        <div style="font-size: 1.5rem">그는 라이어가 맞았습니다!</div>
      </div>
      <span style="margin-top: 1rem; font-size: 1.1rem"
        >현재 라이어가 제시어를 입력 중입니다...</span
      >
      <div class="waiting-icon">
        <i class="fa-solid fa-face-grin-stars"></i>
      </div>
      <div class="round-time-bar" data-style="smooth" style="--duration: 30">
        <div></div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, watchEffect } from "vue"

export default {
  name: "LiarWaitingModal",
  emits: ["close"],
  props: ["liar", "liarStream"],
  setup(props, context) {
    setTimeout(function () {
      context.emit("close")
    }, 30000)

    const liarNickname = ref("")
    watchEffect(() => {
      liarNickname.value = JSON.parse(
        props.liarStream.stream.connection.data
      ).userNickname
    })

    return { liarNickname }
  },
}
</script>

<style>
@import url("@/assets/roompage/Modal.css");

.modal-window {
  height: auto;
}
</style>
