<template>
  <div class="modal modal-overlay">
    <div class="modal-window">
      <h6>의심되는 참가자를 지목해주세요!</h6>
      <div id="user-vote-box">
        <button
          v-for="(user, key) in userInfo"
          :key="key"
          class="overlay__btn user-item"
          @click="check(user)"
          :id="user"
        >
          <div class="name-box">
            <span class="nickname-text">{{ user }}</span>
            <span class="order-text">발표자 {{ key + 1 }}</span>
          </div>
        </button>
      </div>
      <button class="overlay__btn skip-btn" @click="clearCheck">skip</button>
      <div class="round-time-bar" data-style="smooth" style="--duration: 10">
        <div></div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from "vue"

export default {
  name: "VoteModal",
  emits: ["close"],
  props: ["userList"],
  setup(props, context) {
    const suspect = ref("")

    let userInfo = [...props.userList]

    const check = function (user) {
      const checkBtns = document.querySelectorAll(".user-item")
      const skipBtn = document.querySelector(".skip-btn")
      checkBtns.forEach((cb) => {
        cb.classList.remove("active")
        if (cb.id === user) {
          cb.classList.add("active")
          suspect.value = user
        }
      })
      skipBtn.classList.remove("active")
    }

    const clearCheck = function () {
      const checkBtns = document.querySelectorAll(".user-item")
      const skipBtn = document.querySelector(".skip-btn")
      checkBtns.forEach((cb) => {
        cb.classList.remove("active")
      })
      suspect.value = "skip"
      skipBtn.classList.add("active")
    }

    setTimeout(function () {
      // socket 보내는거
      context.emit("vote", suspect.value)
    }, 10000)

    return { userInfo, check, clearCheck }
  },
}
</script>

<style scoped>
@import url("@/assets/roompage/Modal.css");

.modal-window {
  height: auto;
}
</style>
