<template>
  <div id="signup-box">
    <div id="user-box">
      <h5>회원가입</h5>
      <div class="user-input signup-email-input">
        <span>이메일</span>
        <div>
          <div class="email-input">
            <input
              :readonly="isAuthDone"
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
          <div v-if="!isAuthDone" class="email-auth-btns">
            <div v-if="!isAuthOpen" class="email-auth-btns-false">
              <button @click="checkEmail">중복확인</button>
            </div>
            <div v-else class="email-auth-btns-true">
              <button class="resend-btn" @click="sendEmail">재발송</button>
              <button @click="confirmEmail">완료</button>
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
        <input type="text" name="nickname" v-model="nickname" />
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
import { ref, reactive } from 'vue'
import axios from 'axios'
import ClauseText from './ClauseText.vue'
import api from '@/api/api'

export default {
  name: 'SignupInput',
  components: {
    ClauseText,
  },
  // eslint-disable-next-line
  setup(props, context) {
    // 이메일 인증 관련 변수
    const isAuthDone = ref(false)
    const isAuthOpen = ref(false)

    // 오류 메시지
    // 오류 발생시 해당 문자열을 오류메세지로 변경
    const emailErrorMsg = ref('')
    const passwordErrorMsg = ref(
      '영문, 숫자, 특수문자를 각각 1개 이상 포함(9~16자)'
    )
    const nicknameErrorMsg = ref('한글 2~8자, 영문 4~16자(4~16 byte 이내)')

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
          url: api.users.duplicateEmail(),
          method: 'GET',
          data: {
            userEmail: credentials.email,
          },
        })
        if (response.statusCode === 200) {
          response = sendEmail()
          // 중복확인 완료
          isAuthOpen.value = true
          return
        }
      } catch (err) {
        if (err.response.statusCode === 409) {
          emailErrorMsg.value = '중복된 이메일이 존재합니다'
        }
      }
    }

    // 이메일 캔슬
    const cancelEmail = function () {
      credentials.email = ''
      isAuthDone.value = false
      isAuthOpen.value = false
    }

    // 이메일 전송 & 재전송 함수
    const sendEmail = async function () {
      try {
        const response = await axios({
          url: api.users.sendEmail(),
          method: 'POST',
          data: {
            userEmail: credentials.email,
          },
        })
        if (response.data.statusCode === 200) {
          console.log('이메일 전송 성공!')
          emailErrorMsg.value = '입력하신 주소로 인증 이메일이 발송되었습니다.'
        }
      } catch (err) {
        emailErrorMsg.value =
          '서버 문제로 인해 이메일을 보낼 수 없습니다. 나중에 다시 시도해 주세요.'
      }
    }

    // 이메일 인증 확인 함수
    const confirmEmail = async function () {
      try {
        const response = await axios.get({
          url: api.users.verifyEmail(credentials.email),
        })
        if (response.data.statusCode === 200) {
          isAuthDone.value = true
          alert('이메일 인증이 완료되었습니다.')
          emailErrorMsg.value = '이메일 인증이 완료되었습니다.'
        }
      } catch (err) {
        alert('이메일 인증이 완료되지 않았습니다')
      }
    }

    // 회원가입 함수
    const checkSignup = async function () {
      try {
        // 검증용 정규식
        const passwordRegex =
          /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{9,16}$/
        // 이메일
        if (!isAuthDone.value) {
          emailErrorMsg.value = '이메일 인증을 완료해 주세요'
          return
        }
        // 비밀번호
        if (!credentials.password || !credentials.passwordConfirm) {
          passwordErrorMsg.value = '비밀번호를 입력하세요'
          return
        } else if (!passwordRegex.test(credentials.password)) {
          passwordErrorMsg.value = '비밀번호 형식이 잘못되었습니다.'
          setTimeout(() => {
            passwordErrorMsg.value =
              '영문, 숫자, 특수문자를 각각 1개 이상 포함(9~16자)'
          }, 3000)
          return
        } else if (credentials.password !== credentials.passwordConfirm) {
          passwordErrorMsg.value = '두 비밀번호가 일치하지 않습니다'
          return
        }
        // 닉네임
        if (!credentials.nickname) {
          nicknameErrorMsg.value = '닉네임을 입력하세요'
          return
          // UTF-8에서는 한글이 3바이트라는데?
        } else if (
          !(
            4 <=
              credentials.nickname.replace(
                /[\0-\x7f]|([0-\u07ff]|(.))/g,
                '$&$1$2'
              ).length <=
              16 && /^[ㄱ-ㅎ|가-힣|a-z|A-Z|]+$/.test(credentials.nickname)
          )
        ) {
          nicknameErrorMsg.value = '닉네임 형식이 잘못되었습니다.'
          setTimeout(() => {
            nicknameErrorMsg.value = '한글 2~8자, 영문 4~16자(4~16 byte 이내)'
          }, 3000)
          return
        }
        // console.log('회원가입 후 로그인 완료')
        const response = await axios({
          url: api.users.signup(),
          method: 'POST',
          body: {
            userEmail: credentials.email,
            userPassword: credentials.password,
            userNickname: credentials.nickname,
          },
        })
        if (response.data.statusCode === 'Created') {
          alert('회원가입이 완료되었습니다')
          // 회원가입 완료되면 변수들 초기화
          isAuthDone.value = false
          isAuthOpen.value = false
          emailErrorMsg.value = ''
          passwordErrorMsg.value =
            '영문, 숫자, 특수문자를 각각 1개 이상 포함(9~16자)'
          nicknameErrorMsg.value = '한글 2~8자, 영문 4~16자(4~16 byte 이내)'
          credentials = reactive({
            email: '',
            password: '',
            passwordConfirm: '',
            nickname: '',
          })
          // 페이지 로그인 페이지로 전환
          context.emit('signup-done')
          return
        }
      } catch (err) {
        if (err.response.statusCode === 400) {
          emailErrorMsg.value = '이메일 인증을 완료해 주세요'
        } else if (err.response.statusCode === 500) {
          emailErrorMsg.value =
            '서버 문제로 회원가입이 실패하였습니다. 나중에 다시 시도해 주세요'
        }
      }
    }

    return {
      isAuthDone,
      isAuthOpen,
      emailErrorMsg,
      passwordErrorMsg,
      nicknameErrorMsg,
      credentials,
      sendEmail,
      confirmEmail,
      checkEmail,
      checkSignup,
      cancelEmail,
    }
  },
}
</script>

<style></style>
