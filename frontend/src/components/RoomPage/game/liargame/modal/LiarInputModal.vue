<template>
  <div class="modal modal-overlay">
    <div class="modal-window">
      <div id="keyword-input-box">
        <h6>당신은 라이어로 지목되었습니다!</h6>
        <p>승리하고 싶다면 주어진 제시어를 맞혀주세요</p>
        <input v-model="inputKeyword" class="keyword-input" type="text" />
        <button @click="submitKeyword" class="overlay__btn" style="width: 100%">
          제출하기
        </button>
      </div>
      <div class="round-time-bar" data-style="smooth" style="--duration: 30">
        <div></div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from "vue"

export default {
  name: "LiarInputModal",
  emits: ["close"],
  props: [],
  setup(props, context) {
    setTimeout(function () {
      context.emit("answer")
    }, 15000)

    const inputKeyword = ref("")
    const submitKeyword = function () {
      context.emit("answer", inputKeyword.value)
    }
    return { inputKeyword, submitKeyword }
  },
}
</script>

<style scoped>
@import url("@/assets/roompage/Modal.css");

.modal-window {
  height: auto;
}
</style>
