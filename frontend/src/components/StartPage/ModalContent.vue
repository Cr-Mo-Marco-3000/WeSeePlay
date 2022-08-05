<template>
  <div id="modal-content">
    <div
      v-if="!isAuthDone"
      id="user-box"
      class="auth-done-false"
      style="width: 100%"
    >
      <div class="user-input signup-email-input">
        <span>이메일</span>
        <div class="email-input">
          <input
            :disabled="isAuthOpen"
            type="text"
            name="email"
            placeholder="email@address.com"
            v-model="email"
          />
          <q-icon
            fontSize="large"
            @click="cancelEmail"
            class="email-cancel"
            style="position: absolute; top: 50; left: 70%"
            name="close"
          />
          <div class="email-auth-btns" style="">
            <div v-if="!isAuthOpen" class="email-auth-btns-false">
              <button
                @click="sendEmail"
                style="background: black"
                class="modal-email-btn"
              >
                회원확인
              </button>
            </div>
            <div v-else class="email-auth-btns-true">
              <button class="resend-btn" @click="sendEmail">재발송</button>
              <button style="background: black" @click="changePw">완료</button>
            </div>
          </div>
        </div>
      </div>
      <span class="info-text error-message">{{ emailErrorMsg }}</span>
    </div>
    <div v-else class="auth-done-true">
      <span
        >변경된 비밀번호
        <p>{{ changePassword }}</p>
      </span>
    </div>
  </div>
</template>

<script>
// import Swal from "sweetalert2"
import { ref } from "vue"
import axios from "axios"
import api from "@/api/api"
import { useRouter } from "vue-router"

export default {
  name: "ModalContent",
  setup() {
    const isAuthDone = ref(false)
    const isAuthOpen = ref(false)
    const emailErrorMsg = ref("이메일 인증 후 비밀번호가 변경됩니다")
    const changePassword = ref("")
    const router = useRouter()

    const email = ref("")

    const cancelEmail = function () {
      email.value = ""
      emailErrorMsg.value = "이메일 인증 후 비밀번호가 변경됩니다"
      isAuthOpen.value = false
    }

    const sendEmail = async function () {
      try {
        const emailRegex = /^[a-zA-Z0-9.^$*?=!:/_-]+@[a-zA-Z]+\.[A-Za-z]{2,4}$/
        if (!emailRegex.test(email.value)) {
          emailErrorMsg.value = "이메일 형식이 올바르지 않습니다"
          return
        }
        let response = await axios({
          url: api.users.sendEmailForPW,
          method: "POST",
          data: {
            userEmail: email.value,
          },
        })
        if (response.status === 200) {
          isAuthOpen.value = true
          emailErrorMsg.value = "인증 이메일이 발송 중 입니다"
          setTimeout(() => {
            emailErrorMsg.value =
              "인증 이메일이 발송되었습니다. 인증 후 완료 버튼을 눌러주세요"
          }, 3000)
          return
        }
      } catch (error) {
        if (error.response.status === 404) {
          emailErrorMsg.value = "해당 이메일로 가입된 계정이 없습니다"
        } else if (error.response.status === 500) {
          router.push({ name: "errorpage", params: { errorname: 500 } })
        }
      }
    }

    const changePw = async function () {
      try {
        let response = await axios({
          method: "POST",
          data: {
            userEmail: email.value,
          },
        })
        if (response.state === 200) {
          changePassword.value = response.password
          isAuthDone.value = true
        }
      } catch (error) {
        if (error.response.status === 404) {
          emailErrorMsg.value = "이메일 인증이 완료되지 않았습니다"
        } else if (error.response.status === 500) {
          router.push({ name: "errorpage", params: { errorname: 500 } })
        }
      }
    }

    return {
      isAuthDone,
      isAuthOpen,
      emailErrorMsg,
      changePassword,
      email,
      cancelEmail,
      sendEmail,
      changePw,
    }
  },
}
</script>

<style>
@import "../../assets/startpage/modal.css";
</style>
