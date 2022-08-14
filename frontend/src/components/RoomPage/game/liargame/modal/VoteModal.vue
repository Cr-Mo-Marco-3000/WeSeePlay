<template>
  <div class="modal modal-overlay">
    <div class="modal-window">
      <h6>의심되는 참가자를 지목해주세요!</h6>
      <div id="user-vote-box">
        <div v-for="(user, key) in userInfo" :key="key" class="user-item">
          <div class="name-box">
            <span class="nickname-text">{{ user.userNickname }}</span>
            <span class="order-text">발표자 {{ key + 1 }}</span>
          </div>
          <input
            @click="check($event)"
            class="check-box"
            type="checkbox"
            :value="user.userEmail"
          />
        </div>
      </div>
      <button class="skip-btn" @click="clearCheck">skip</button>
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
  props: [],
  setup(props, context) {
    const suspect = ref("")

    let userInfo = [
      { userEmail: "aaa@aaa", userId: 1, userNickname: "에이에이" },
      { userEmail: "bbb@bbb", userId: 2, userNickname: "비비비비" },
      { userEmail: "ccc@ccc", userId: 3, userNickname: "씨씨씨씨" },
      { userEmail: "aaa@aaa", userId: 1, userNickname: "에이에이" },
      { userEmail: "bbb@bbb", userId: 2, userNickname: "비비비비" },
      { userEmail: "ccc@ccc", userId: 3, userNickname: "씨씨씨씨" },
      { userEmail: "aaa@aaa", userId: 1, userNickname: "에이에이" },
      { userEmail: "bbb@bbb", userId: 2, userNickname: "비비비비" },
      { userEmail: "ccc@ccc", userId: 3, userNickname: "씨씨씨씨" },
      { userEmail: "aaa@aaa", userId: 1, userNickname: "에이에이" },
      { userEmail: "bbb@bbb", userId: 2, userNickname: "비비비비" },
      { userEmail: "ccc@ccc", userId: 3, userNickname: "씨씨씨씨" },
    ]

    const check = function (event) {
      const checkboxes = document.querySelectorAll(".check-box")
      const skipBtn = document.querySelector(".skip-btn")
      checkboxes.forEach((cb) => {
        cb.checked = false
      })
      skipBtn.classList.remove("active")
      event.target.checked = true
      suspect.value = event.target.value
    }

    const clearCheck = function () {
      const checkboxes = document.querySelectorAll(".check-box")
      const skipBtn = document.querySelector(".skip-btn")
      checkboxes.forEach((cb) => {
        cb.checked = false
      })
      suspect.value = "skip"
      skipBtn.classList.add("active")
    }

    setTimeout(function () {
      // socket 보내는거
      context.emit("close")
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
