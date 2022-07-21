<template>
  <div id="user-box">
    <h5>로그인</h5>
    <div class="user-input">
      <span>이메일</span>
      <input type="text" name="email" placeholder="email@address.com">
    </div>
    <div class="user-input">
      <span>비밀번호</span>
      <input type="password" name="email">
    </div>
    <span class="error-message">{{ loginErrorMsg }}</span>
    <button class="auth-btn overlay__btn">로그인</button>
    <span class="kakao-btn">
      <p>카카오계정으로 접속하기</p>
      <img src="../../assets/kakao-logo.png">
      </span>
    <span>비밀번호를 잊으셨습니까?</span>
  </div>
</template>

<script>
import { ref } from 'vue'

export default {
  name: "LoginInput",
  setup() {
    // 로그인 에러 메서지
    const loginErrorMsg = ref(' ')

    return { loginErrorMsg } 
  }
}
</script>

<style>

</style>