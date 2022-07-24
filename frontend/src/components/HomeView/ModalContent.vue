<template>
  <div>
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
  </div>
</template>

<script>
export default {
  name: "ModalContent"
}
</script>

<style>

</style>