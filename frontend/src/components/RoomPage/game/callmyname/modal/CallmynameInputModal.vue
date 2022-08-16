<template>
  <div class="modal modal-overlay" @mousedown.self="$emit('close')">
    <div class="modal-window">
      <div class="top">
        <button @click="$emit('close')">X</button>
      </div>
      <div id="keyword-input-box">
        <h6>당신의 제시어를 맞혀주세요!</h6>
        <p>주어진 답변을 가지고 제시어를 유추해 보세요</p>
        <input v-model="inputKeyword" class="keyword-input" type="text" />
        <button @click="submitKeyword" class="overlay__btn" style="width: 100%">
          제출하기
        </button>
      </div>
      <!-- <div class="round-time-bar" data-style="smooth" style="--duration: 30">
        <div></div>
      </div> -->
    </div>
  </div>
</template>

<script>
import Swal from "sweetalert2"
import { ref } from "vue"

export default {
  name: "CallmynameInputModal",
  emits: ["close", "gameIdxUp"],
  props: ["suggestion", "gameIdx", "session"],

  setup(props, context) {
    const inputKeyword = ref("")
    const submitKeyword = function () {
      console.log(inputKeyword.value)
      context.emit("close")
      // socet 통신 코드 작성
      if (props.suggestion == inputKeyword.value) {
        props.session
          .signal({
            data: props.gameIdx,
            to: [],
            type: "callMynameChooseCorrect",
          })
          .then(() => {
            console.log("callMynameChooseCorrect")
          })
          .catch((error) => {
            console.error(error)
          })
      } else {
        props.session
          .signal({
            data: inputKeyword.value,
            to: [],
            type: "callMynamechooseIncorrect",
          })
          .then(() => {
            console.log("callMynamechooseIncorrect")
          })
          .catch((error) => {
            console.error(error)
          })
        // 틀렸을 경우
        Swal.fire({
          icon: "error",
          title: "틀렸습니다!",
          showConfirmButton: false,
          timer: 1500,
        })
        context.emit("gameIdxUp")
        props.session
          .signal({
            data: 1,
            to: [],
            type: "Call my name, idx up",
          })
          .then(() => {
            console.log("Call my name, idx up")
          })
          .catch((error) => {
            console.error(error)
          })
      }
      inputKeyword.value = ""
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
