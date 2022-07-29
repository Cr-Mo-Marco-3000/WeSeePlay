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
          <input type="text" name="email" placeholder="email@address.com" />
          <div class="email-auth-btns" style="">
            <div v-if="!isAuthOpen" class="email-auth-btns-false">
              <button style="background: black" class="modal-email-btn">
                회원확인
              </button>
            </div>
            <div v-else class="email-auth-btns-true">
              <button class="resend-btn">재발송</button>
              <button style="background: black">완료</button>
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
import { ref } from 'vue'

export default {
  name: 'ModalContent',
  setup() {
    const isAuthDone = ref(false)
    const isAuthOpen = ref(false)
    const emailErrorMsg = ref('이메일 인증 후 비밀번호가 변경됩니다')
    const changePassword = ref('DAS0DA2')

    return { isAuthDone, isAuthOpen, emailErrorMsg, changePassword }
  },
}
</script>

<style>
@import '../../assets/startpage/modal.css';
</style>
