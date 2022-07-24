<template>
  <div id="signup-box">
    <div id="user-box">
      <h5>회원가입</h5> 
      <div class="user-input signup-email-input">
        <span>이메일</span>
        <div>
          <div class="email-input">
            <input type="text" name="email" placeholder="email@address.com">
          </div>
          <div v-if="!isAuthDone" class="email-auth-btns">
            <div v-if="!isAuthOpen" class="email-auth-btns-false">
              <button>중복확인</button>
            </div>
            <div v-else class="email-auth-btns-true">
              <button class="resend-btn">재발송</button>
              <button>완료</button>
            </div>
          </div>
        </div>
      </div>
      <span class="info-text error-message">{{ emailErrorMsg }}</span>
      <div class="signup-password-input">
        <div class="user-input">
          <span>비밀번호</span>
          <input type="password" name="email">
        </div>
        <div class="user-input" style="margin-right: 0;">
          <span>비밀번호 확인</span>
          <input type="password" name="email">
        </div>
      </div>
      <span class="info-text">{{ passwordErrorMsg }}</span>
      <div class="user-input">
        <span>닉네임</span>
        <input type="text" name="nickname">
      </div>
      <span class="info-text">{{ nicknameErrorMsg }}</span>
      <button class="auth-btn overlay__btn">회원가입</button>
    </div>
    <div id="clause-box">
      <ClauseText />
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'
import ClauseText from './ClauseText.vue'

export default {
  name: "SignupInput",
  components: {
    ClauseText
  },
  setup() {
    const isAuthDone = ref(true)
    const isAuthOpen = ref(true)
    // 오류 발생시 해당 문자열을 오류메세지로 변경 
    const emailErrorMsg = ref('')
    const passwordErrorMsg = ref('영문, 숫자, 특수문자를 각각 1개 이상 포함(9~16자)')
    const nicknameErrorMsg = ref('한글 2~8자, 영문 4~16자(4~16 byte 이내)')

    return { isAuthDone, isAuthOpen, emailErrorMsg, passwordErrorMsg, nicknameErrorMsg }
  }
}
</script>

<style>

</style>