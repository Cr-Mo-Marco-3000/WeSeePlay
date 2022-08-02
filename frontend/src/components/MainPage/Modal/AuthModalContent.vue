<template>
  <div class="user-input">
    <span>기존 비밀번호</span>
    <input v-model="credentials.password" type="password" />
  </div>
  <div class="user-input auth-input">
    <span>새로운 비밀번호</span>
    <input v-model="credentials.newPassword" type="password" />
  </div>
  <div class="user-input auth-input">
    <span>비밀번호 확인</span>
    <input v-model="credentials.passwordConfirm" type="password" />
  </div>
  <span class="auth-error-msg">{{ passwordErrorMsg }}</span>
  <button class="overlay__btn pw-change-btn" @click="changePassword">
    비밀번호 변경
  </button>
</template>

<script>
import { ref, reactive } from 'vue'
import { useStore } from 'vuex'
import axios from 'axios'
import api from '@/api/api'
import { useRouter } from 'vue-router'

export default {
  name: 'AuthModalContent',
  setup() {
    const passwordErrorMsg = ref(
      '영문, 숫자, 특수문자를 각각 1개 이상 포함(8~16자)'
    )

    let credentials = reactive({
      password: '',
      newPassword: '',
      passwordConfirm: '',
    })

    const store = useStore()
    const router = useRouter()

    const changePassword = async function () {
      try {
        // token으로 사용자 정보 다시 불러오기
        store.dispatch('fetchMe')
        // getters로 불러온 사용자 정보 중 userEmail을 사용
        const userEmail = store.getters.me.userEmail

        let errorFlag = true
        // 검증용 정규식
        const passwordRegex =
          /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#()?&]{8,16}$/

        if (!credentials.newPassword || !credentials.passwordConfirm) {
          passwordErrorMsg.value = '비밀번호를 입력하세요'
          setTimeout(() => {
            passwordErrorMsg.value =
              '영문, 숫자, 특수문자를 각각 1개 이상 포함(8~16자)'
          }, 3000)
          errorFlag = false
        } else if (
          errorFlag === true &&
          !passwordRegex.test(credentials.newPassword)
        ) {
          passwordErrorMsg.value = '비밀번호 형식이 잘못되었습니다.'
          setTimeout(() => {
            passwordErrorMsg.value =
              '영문, 숫자, 특수문자를 각각 1개 이상 포함(8~16자)'
          }, 3000)
          errorFlag = false
        } else if (
          errorFlag === true &&
          credentials.newPassword !== credentials.passwordConfirm
        ) {
          passwordErrorMsg.value = '두 비밀번호가 일치하지 않습니다'
          setTimeout(() => {
            passwordErrorMsg.value =
              '영문, 숫자, 특수문자를 각각 1개 이상 포함(8~16자)'
          }, 3000)
          errorFlag = false
        }

        if (errorFlag === false) {
          return
        }

        const response = await axios({
          url: api.users.changePassword(),
          method: 'PATCH',
          headers: store.getters.authHeader,
          data: {
            userEmail: userEmail,
            userPassword: credentials.password,
            userNewPassword: credentials.newPassword,
          },
        })
        if (response.status === 200) {
          /* 성공, 로그아웃 */
          alert('비밀번호 변경이 완료되었습니다. 다시 한번 로그인 해주세요.')
          store.dispatch('logout')
        }
      } catch (error) {
        if (error.response.status === 401) {
          passwordErrorMsg.value = '비밀번호가 틀렸습니다.'
        } else if (error.response.status === 404) {
          alert('존재하지 않는 계정입니다.')
          store.dispatch('logout')
        } else {
          router.push({ name: 'errorpage', params: { errorname: 500 } })
        }
      }
    }

    return { passwordErrorMsg, credentials, changePassword }
  },
}
</script>

<style></style>
