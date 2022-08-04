<template>
  <TheBackground />
  <div id="first-page-box">
    <canvas class="orb-canvas"></canvas>
    <div class="overlay">
      <div id="state-box">
        <TheCarousel v-if="!isLoginOpen && !isSignupOpen" />
        <LoginInput v-if="isLoginOpen" />
        <SignupInput v-if="isSignupOpen" @signup-done="loginOpen" />
      </div>
      <div class="overlay__btns">
        <button
          @click="loginOpen"
          class="overlay__btn overlay__btn--transparent login-btn"
        >
          <span>{{ loginText }}</span>
        </button>
        <button
          @click="signupOpen"
          class="overlay__btn overlay__btn--colors signup-btn"
        >
          <span>{{ signupText }}</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from "vue"
import TheBackground from "@/components/TheBackground.vue"
import TheCarousel from "./TheCarousel.vue"
import LoginInput from "./LoginInput.vue"
import SignupInput from "./SignupInput.vue"

export default {
  name: "FisrtPage",
  components: {
    TheBackground,
    TheCarousel,
    LoginInput,
    SignupInput,
  },
  setup() {
    const isLoginOpen = ref(false)
    const isSignupOpen = ref(false)
    const loginText = ref("로그인")
    const signupText = ref("회원가입")

    const loginOpen = function () {
      if (isLoginOpen.value === false && isSignupOpen.value === false) {
        loginText.value = "뒤로가기"
        isLoginOpen.value = !isLoginOpen.value
      } else if (isSignupOpen.value === true) {
        signupText.value = "회원가입"
        loginText.value = "뒤로가기"
        isLoginOpen.value = !isLoginOpen.value
        isSignupOpen.value = !isSignupOpen.value
      } else if (loginText.value === "뒤로가기") {
        loginText.value = "로그인"
        isLoginOpen.value = !isLoginOpen.value
      }
    }

    const signupOpen = function () {
      if (isSignupOpen.value === false && isLoginOpen.value === false) {
        signupText.value = "뒤로가기"
        isSignupOpen.value = !isSignupOpen.value
      } else if (isLoginOpen.value === true) {
        loginText.value = "로그인"
        signupText.value = "뒤로가기"
        isLoginOpen.value = !isLoginOpen.value
        isSignupOpen.value = !isSignupOpen.value
      } else if (signupText.value === "뒤로가기") {
        signupText.value = "회원가입"
        isSignupOpen.value = !isSignupOpen.value
      }
    }

    return {
      isLoginOpen,
      isSignupOpen,
      loginOpen,
      signupOpen,
      loginText,
      signupText,
    }
  },
}
</script>

<style>
@import "../../assets/startpage/startpage.css";
</style>
