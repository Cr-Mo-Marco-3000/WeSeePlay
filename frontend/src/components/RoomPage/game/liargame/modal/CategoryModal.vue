<template>
  <div class="modal modal-overlay" @mousedown.self="$emit('close')">
    <div class="modal-window">
      <div class="top">
        <button @click="$emit('close')" class="close-button">X</button>
      </div>
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
        @click="submitCategory"
        class="overlay__btn overlay__btn--transparent start-btn"
      >
        게임시작!
      </button>
    </div>
  </div>
</template>

<script>
import Swal from "sweetalert2"
import { ref } from "vue"
import api from "@/api/api"

export default {
  name: "CategoryModal",
  emits: ["close"],
  props: [],
  setup(props, context) {
    const pickCategory = ref(0)

    // 주제 더미데이터
    const categoryList = ["음식", "위인", "동물", "직업", "영화", "스포츠"]

    const selectCategory = function (event) {
      const categoryBtns = document.querySelectorAll(".category-btn")
      categoryBtns.forEach((btn) => {
        btn.classList.remove("active")
      })
      event.target.classList.add("active")
      pickCategory.value = parseInt(event.target.value) + 1
    }

    const submitCategory = async function () {
      console.log("dkffkfkdsfsdkjfldsjflkdsjflkjdsflk", pickCategory.value)
      if (pickCategory.value == 0) {
        Swal.fire({
          icon: "error",
          text: "주제를 선택하지 않았습니다!",
        })
        return
      } else {
        const getLiarSuggestion = await api.game.getliarsuggestion(
          pickCategory.value
        )
        const gameData = {
          category: categoryList[pickCategory.value],
          suggestion: getLiarSuggestion.data.suggestion,
        }
        context.emit("close")
        context.emit("liarGameStart", gameData)
      }
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

.close-button {
  background: rgba(0, 0, 0, 0);
  font-size: 18px;
}

.close-button:hover {
  cursor: pointer;
}
</style>
