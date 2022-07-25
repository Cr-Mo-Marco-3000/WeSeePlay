<template>
  <form @submit.prevent="login">
    <div id="user-box">
      <h5>로그인</h5>
      <div class="user-input">
        <span>이메일</span>
        <input
          type="text"
          name="email"
          placeholder="email@address.com"
          v-model="credentials.email"
        />
      </div>
      <div class="user-input">
        <span>비밀번호</span>
        <input type="password" name="email" v-model="credentials.password" />
      </div>
      <span class="error-message">{{ loginErrorMsg }}</span>
      <button class="auth-btn overlay__btn">로그인</button>
      <span class="kakao-btn">
        <p>카카오계정으로 접속하기</p>
        <img src="../../assets/kakao-logo.png" />
      </span>
      <span @click="openModal" class="forget-pw-text"
        >비밀번호를 잊으셨습니까?</span
      >
    </div>
    <div>
      <TheModal v-if="modal" @close="openModal">
        <ModalContent />
      </TheModal>
    </div>
  </form>
</template>

<script>
import { ref } from 'vue'
import { reactive } from 'vue'
import { useStore } from 'vuex'
import router from '@/router'
import axios from 'axios'
import TheModal from './TheModal.vue'
import ModalContent from './ModalContent.vue'
import api from '@/api/api'

export default {
  name: 'LoginInput',
  components: {
    TheModal,
    ModalContent,
  },
  setup() {
    // 스토어 사용
    const store = useStore()

    // 모달
    const modal = ref(false)
    const openModal = function () {
      modal.value = !modal.value
    }

    // 로그인 에러 메서지
    const loginErrorMsg = ref('')

    // 로그인 정보 객체
    const credentials = reactive({
      email: '',
      password: '',
    })

    // 로그인 함수
    const login = async function () {
      try {
        if (credentials.email && credentials.password) {
          const response = await axios({
            url: api.users.login(),
            method: 'POST',
            data: {
              userEmail: credentials.email,
              userPassword: credentials.password,
            },
          })
          // 모임 이거 토큰 몰로 옴?
          store.dispatch('saveToken', response.data.key)
          store.dispatch('fetchMe')
          // 로그인이 끝나면 메인페이지로 전송
          router.push({ name: 'mainpage' })
          return
        } else {
          loginErrorMsg.value = '아이디와 비밀번호를 입력하세요'
          return
        }
      } catch (err) {
        console.log(err.response.staus)
        loginErrorMsg.value = '입력한 정보를 다시 확인해 주세요'
        return
      }
    }
    return { modal, loginErrorMsg, openModal, credentials, login }
  },
}
</script>

<style></style>
