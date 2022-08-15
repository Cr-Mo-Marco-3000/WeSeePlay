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
  emits: ["close"],
  props: [],
  setup(props, context) {
    const inputKeyword = ref("")
    const submitKeyword = function () {
      console.log(inputKeyword.value)
      context.emit("close")
      // socet 통신 코드 작성

      // 틀렸을 경우
      Swal.fire({
        icon: "error",
        title: "틀렸습니다!",
        showConfirmButton: false,
        timer: 1500,
      })
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
