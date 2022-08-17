<template>
  <div class="modal modal-overlay">
    <div class="modal-window">
      <h6>주제 선택</h6>
      <div id="category-box">
        <div v-for="(category, key) in categoryList" :key="key">
          <div class="category-item">
            <button
              @click="selectCategory($event)"
              class="overlay__btn category-btn"
              :value="key"
            >
              {{ category }}
            </button>
          </div>
        </div>
      </div>
      <button
        @click="selectCategory($event)"
        class="overlay__btn category-btn random-btn"
        value="random"
      >
        랜덤선택
      </button>
      <button
        @click="submitCategory"
        class="overlay__btn overlay__btn--transparent start-btn"
      >
        게임시작!
      </button>
      <div class="round-time-bar" data-style="smooth" style="--duration: 30">
        <div></div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from "vue"
import api from "@/api/api"

export default {
  name: "CategoryModal",
  emits: ["close"],
  props: [],
  setup(props, context) {
    setTimeout(function () {
      // socket 보내는거
      // 만약 이때까지 start 버튼을 안눌렀다면 여기서 주제를 socket으로 보냄
      context.emit("close", "liarGameStart")
    }, 30000)

    const pickCategory = ref(0)

    // 주제 더미데이터
    const categoryList = [
      "주제1",
      "주제2",
      "주제3",
      "주제4",
      "주제5",
      "주제6",
      "주제7",
      "주제8",
      "주제9",
      "주제10",
      "주제11",
      "주제12",
    ]

    const selectCategory = function (event) {
      const categoryBtns = document.querySelectorAll(".category-btn")
      categoryBtns.forEach((btn) => {
        btn.classList.remove("active")
      })
      event.target.classList.add("active")
      pickCategory.value = parseInt(event.target.value) + 1
    }

    const submitCategory = async function () {
      const getLiarSuggestion = await api.game.getliarsuggestion(
        pickCategory.value
      )
      context.emit("close")
      context.emit("liarGameStart", getLiarSuggestion.data.suggestion)
    }
    return { categoryList, selectCategory, submitCategory }
  },
}
</script>

<style scoped>
@import url("@/assets/roompage/Modal.css");
.modal-window {
  height: auto;
}
</style>
