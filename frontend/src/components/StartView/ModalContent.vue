<template>
  <div v-if="!isAuthDone">
    <div class="user-input signup-email-input">
      <span>이메일</span>
        <div class="email-input">
          <input type="text" name="email" placeholder="email@address.com">
          <button>회원확인</button>
        </div>
    </div>
    <span class="info-text error-message">{{ emailErrorMsg }}</span>
  </div>
  <div v-else>
    <span>임시비밀번호가 발송되었습니다</span>
  </div>
</template>

<script>
import { ref } from 'vue'

export default {
  name: "ModalContent",
  setup() {
    const isAuthDone = ref(true)
    const emailErrorMsg = ref('')

    return { isAuthDone, emailErrorMsg }
  }
}
</script>

<style>

</style>