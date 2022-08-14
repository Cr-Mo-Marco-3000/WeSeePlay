<template>
  <div class="modal modal-overlay">
    <div class="modal-window"></div>
    결과창창
  </div>
</template>

<script>
import { ref } from "vue"

export default {
  name: "ResultModal",
  emits: ["close"],
  props: [],
  setup(props, context) {
    setTimeout(function () {
      // socket 보내는거
      context.emit("close")
    }, 30000)

    const inputKeyword = ref("")
    const submitKeyword = function () {
      console.log(inputKeyword.value)
      context.emit("close")
      // socket 통신 코드 작성
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
