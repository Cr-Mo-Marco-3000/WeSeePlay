<template>
  <div id="modal-content">
    <div v-if="!isAuthDone" class="auth-done-false">
        <div class="user-input">
          <span>이메일</span>
            <div class="email-input">
              <input type="text" name="email" placeholder="email@address.com">
              <button class="overlay__btn">회원확인</button>
            </div>
        </div>
        <span class="info-text error-message">{{ emailErrorMsg }}</span>
      </div>
    <div v-else class="auth-done-true">
      <span>임시비밀번호가 발송되었습니다</span>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'

export default {
  name: "ModalContent",
  setup() {
    const isAuthDone = ref(false)
    const emailErrorMsg = ref('가입시 인증한 이메일을 입력해주세요')

    return { isAuthDone, emailErrorMsg }
  }
}
</script>

<style scope>
@import "../../assets/startpage/modal.css";
</style>