<template>
  <div id="signup-box">
    <div id="user-box">
      <h5>회원가입</h5>
      <div class="user-input signup-email-input">
        <span>이메일</span>
        <div>
          <div class="email-input">
            <input
              :disabled="isAuthOpen"
              type="text"
              name="email"
              placeholder="email@address.com"
              v-model="credentials.email"
            />
            <q-icon
              fontSize="large"
              @click="cancelEmail"
              class="email-cancel"
              style="position: absolute; top: 50%"
              name="close"
            />
          </div>
          <div class="email-auth-btns">
            <div v-if="!isAuthOpen" class="email-auth-btns-false">
              <button @click="checkEmail">이메일 인증</button>
            </div>
            <div v-else class="email-auth-btns-true">
              <button class="resend-btn" @click="sendEmail">재발송</button>
            </div>
          </div>
        </div>
      </div>
      <span class="info-text error-message">{{ emailErrorMsg }}</span>
      <div class="signup-password-input">
        <div class="user-input">
          <span>비밀번호</span>
          <input
            type="password"
            name="password"
            v-model="credentials.password"
          />
        </div>
        <div class="user-input" style="margin-right: 0">
          <span>비밀번호 확인</span>
          <input
            type="password"
            name="passwordConfirm"
            v-model="credentials.passwordConfirm"
          />
        </div>
      </div>
      <span class="info-text">{{ passwordErrorMsg }}</span>
      <div class="user-input">
        <span>닉네임</span>
        <input type="text" name="nickname" v-model="credentials.nickname" />
      </div>
      <span class="info-text">{{ nicknameErrorMsg }}</span>
      <button @click="checkSignup" class="auth-btn overlay__btn">
        회원가입
      </button>
    </div>
    <div id="clause-box">
      <ClauseText />
    </div>
  </div>
</template>

<script>
import Swal from 'sweetalert2'
import { ref, reactive } from 'vue'
import axios from 'axios'
import ClauseText from './ClauseText.vue'
import api from '@/api/api'
import { useRouter } from 'vue-router'

export default {
  name: 'SignupInput',
  components: {
    ClauseText,
  },
  // eslint-disable-next-line
  setup(props, context) {
    // 이메일 인증 관련 변수
    const isAuthOpen = ref(false)

    const router = useRouter()
    // 오류 메시지
    // 오류 발생시 해당 문자열을 오류메세지로 변경
    const emailErrorMsg = ref('')
    const passwordErrorMsg = ref(
      '영문, 숫자, 특수문자를 각각 1개 이상 포함(8~16자)'
    )
    const nicknameErrorMsg = ref('한글 2~8자, 영문 6~24자(6~24 byte 이내)')

    // 회원정보 반응형
    let credentials = reactive({
      email: '',
      password: '',
      passwordConfirm: '',
      nickname: '',
    })

    // 이메일 중복 확인
    const checkEmail = async function () {
      try {
        const emailRegex = /^[a-zA-Z0-9.^$*?=!:/_-]+@[a-zA-Z]+\.[A-Za-z]{2,4}$/
        if (!emailRegex.test(credentials.email)) {
          emailErrorMsg.value = '이메일 형식이 올바르지 않습니다'
          return
        }
        let response = await axios({
          url: api.users.duplicateEmail(credentials.email),
          method: 'GET',
        })
        console.log(response.data)
        if (response.status === 200) {
          isAuthOpen.value = true
          response = sendEmail()
          // 중복확인 완료
          return
        }
      } catch (err) {
        if (err.response.data.statusCode === 409) {
          emailErrorMsg.value = '이미 가입한 이메일 주소입니다'
        } else if (err.response.data.statusCode === 500) {
          router.push({ name: 'errorpage', params: { errorname: 500 } })
        }
      }
    }

    // 이메일 캔슬
    const cancelEmail = function () {
      credentials.email = ''
      emailErrorMsg.value = ''
      isAuthOpen.value = false
    }

    // 이메일 전송 & 재전송 함수
    const sendEmail = async function () {
      try {
        emailErrorMsg.value = '인증 이메일이 발송 중 입니다'
        const response = await axios({
          url: api.users.sendEmail(),
          method: 'POST',
          data: {
            userEmail: credentials.email,
          },
        })
        if (response.data.statusCode === 200) {
          emailErrorMsg.value = '인증 이메일이 발송되었습니다'
        }
      } catch (err) {
        if (err.response.data.statusCode === 400) {
          emailErrorMsg.value = '이메일 주소에 문제가 있습니다.'
        } else if (err.response.data.statusCode === 500) {
          router.push({ name: 'errorpage', params: { errorname: 500 } })
        }
      }
    }

    // 회원가입 함수
    const checkSignup = async function () {
      try {
        let errorFlag = true
        // 검증용 정규식
        const passwordRegex =
          /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!#$%&'*+-/=?^_`{|}~."(),:;<>@[\]\\])[A-Za-z\d!#$%&'*+-/=?^_`{|}~."(),:;<>@[\]\\]{8,16}$/
        // 이메일 인증 로직
        // 이메일 인증을 보낸 상태에서
        if (isAuthOpen.value === true) {
          const response = await axios.get(
            api.users.verifyEmail(credentials.email)
          )
          console.log(response)
          if (!(response.data.statusCode === 200)) {
            emailErrorMsg.value = '이메일 인증이 완료되지 않았습니다'
            errorFlag = false
          } else if (response.data.statusCode === 200) {
            emailErrorMsg.value = '이메일 인증이 완료되었습니다'
          }
        }
        // 비밀번호
        if (!credentials.password || !credentials.passwordConfirm) {
          passwordErrorMsg.value = '비밀번호를 입력하세요'
          setTimeout(() => {
            passwordErrorMsg.value =
              '영문, 숫자, 특수문자를 각각 1개 이상 포함(8~16자)'
          }, 3000)
          errorFlag = false
        } else if (
          errorFlag === true &&
          !passwordRegex.test(credentials.password)
        ) {
          passwordErrorMsg.value = '비밀번호 형식이 잘못되었습니다.'
          setTimeout(() => {
            passwordErrorMsg.value =
              '영문, 숫자, 특수문자를 각각 1개 이상 포함(8~16자)'
          }, 3000)
          errorFlag = false
        } else if (
          errorFlag === true &&
          credentials.password !== credentials.passwordConfirm
        ) {
          passwordErrorMsg.value = '두 비밀번호가 일치하지 않습니다'
          setTimeout(() => {
            passwordErrorMsg.value =
              '영문, 숫자, 특수문자를 각각 1개 이상 포함(8~16자)'
          }, 3000)
          errorFlag = false
        }
        // 닉네임
        if (!credentials.nickname) {
          nicknameErrorMsg.value = '닉네임을 입력하세요'
          errorFlag = false
        } else if (
          errorFlag === true &&
          (!(
            6 <=
            credentials.nickname.replace(
              /[\0-\x7f]|([0-\u07ff]|(.))/g,
              '$&$1$2'
            ).length
          ) ||
            !(
              credentials.nickname.replace(
                /[\0-\x7f]|([0-\u07ff]|(.))/g,
                '$&$1$2'
              ).length <= 24
            ) ||
            !/^[ㄱ-ㅎ|가-힣|a-z|A-Z|]+$/.test(credentials.nickname))
        ) {
          nicknameErrorMsg.value = '닉네임 형식이 잘못되었습니다.'
          setTimeout(() => {
            nicknameErrorMsg.value = '한글 2~8자, 영문 6~24자(6~24 byte 이내)'
          }, 3000)
          errorFlag = false
        }
        // 하나라도 false라면 여기서 걸려서 return
        if (errorFlag === false) {
          return
        }
        const response = await axios({
          url: api.users.signup(),
          method: 'POST',
          data: {
            userEmail: credentials.email,
            userPassword: credentials.password,
            userNickname: credentials.nickname,
          },
        })
        if (response.status === 201) {
          Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: '회원가입이 완료되었습니다',
            showConfirmButton: false,
            timer: 1500
          })
          // 페이지 로그인 페이지로 전환
          context.emit('signup-done')
          return
        }
      } catch (err) {
        if (err.response.status === 401 || err.response.status === 400) {
          console.log()
          Swal.fire({
            icon: 'error',
            text: '입력하신 정보를 다시 한 번 확인해 주세요',
          })
        } else {
          router.push({ name: 'errorpage', params: { errorname: 500 } })
        }
      }
    }

    return {
      isAuthOpen,
      emailErrorMsg,
      passwordErrorMsg,
      nicknameErrorMsg,
      credentials,
      sendEmail,
      checkEmail,
      checkSignup,
      cancelEmail,
    }
  },
}
</script>

<style></style>
