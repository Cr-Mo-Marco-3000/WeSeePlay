<template>
  <div id="signup-box">
    <div id="user-box">
      <h5>회원가입</h5> 
      <div class="user-input signup-email-input">
        <span>이메일</span>
        <div>
          <input type="text" name="email" placeholder="email@address.com">
          <button>중복확인</button>
        </div>
      </div>
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
      <span class="info-text">영문, 숫자, 특수문자를 각각 1개 이상 포함해주세요(9~16자)</span>
      <div class="user-input">
        <span>닉네임</span>
        <input type="text" name="nickname">
      </div>
      <span class="error-message">{{ signupErrorMsg }}</span>
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
    const signupErrorMsg = ref('')

    return { signupErrorMsg }
  }
}
</script>

<style>

</style>