<template>
  <nav class="navbar">
    <ul class="navbar-content">
      <div class="logo-box">
        <div class="name">WeSeePlay</div>
        <img src="../../assets/logo_nontext.png" />
      </div>
      <li class="user-text nav-item">
        <span class="welcome-text"
          >반가워요,
          <p style="margin-left: 0.5rem; margin-right: 0.1rem">
            {{ username }}
          </p>
          님 +</span
        >
        <ul class="dropdown-box">
          <li v-if="!isChangeNickname" @click="onChangeNickname">
            닉네임 변경
          </li>
          <li v-else class="nickname-input">
            <input
              v-model="credentials.nicknameInput"
              type="text"
              :placeholder="username"
            />
            <span @click="onChangeNickname">X</span>
            <button @click.prevent="changeNickname">변경</button>
          </li>
          <li @click="$emit('change-pw')">비밀번호 변경</li>
          <li @click="$emit('delete-user')">회원 삭제</li>
        </ul>
      </li>
      <li @click="logout" class="nav-item logout-box">
        <span>로그아웃</span>
        <i class="fa-solid fa-arrow-right-from-bracket"></i>
      </li>
    </ul>
  </nav>
</template>

<script>
import Swal from "sweetalert2"
import { ref, reactive, watchEffect } from "vue"
import { useStore } from "vuex"
import axios from "axios"
import api from "@/api/api"
import { useRouter } from "vue-router"

export default {
  name: "NavBar",
  setup() {
    const isChangeNickname = ref(false)
    let credentials = reactive({
      nicknameInput: "",
    })

    const onChangeNickname = function () {
      isChangeNickname.value = !isChangeNickname.value
    }

    const store = useStore()
    const router = useRouter()

    // 수정한 부분
    const userEmail = ref("")
    const username = ref("")

    watchEffect(() => {
      username.value = store.getters.me.userNickname
      userEmail.value = store.getters.me.userEmail
    })

    const logout = function () {
      store.dispatch("logout")
    }

    const changeNickname = async function () {
      try {
        /* 닉네임 형식 검사 */
        let errorFlag = true
        if (!credentials.nicknameInput) {
          Swal.fire({
            icon: "error",
            text: "닉네임을 입력하세요",
          })

          errorFlag = false
        } else if (credentials.nicknameInput === username.value) {
          Swal.fire({
            icon: "error",
            text: "현재 사용하고 계신 닉네임과 다른 닉네임을 입력해 주세요",
          })
          errorFlag = false
        } else if (
          errorFlag === true &&
          (!(
            6 <=
            credentials.nicknameInput.replace(
              /[\0-\x7f]|([0-\u07ff]|(.))/g,
              "$&$1$2"
            ).length
          ) ||
            !(
              credentials.nicknameInput.replace(
                /[\0-\x7f]|([0-\u07ff]|(.))/g,
                "$&$1$2"
              ).length <= 24
            ) ||
            !/^[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9]+$/.test(credentials.nicknameInput))
        ) {
          Swal.fire({
            icon: "error",
            title: "닉네임 형식이 잘못되었습니다.",
            text: "한글 2~8자, 영문 6~24자(6~24 byte 이내)",
          })
          errorFlag = false
        }
        // 하나라도 false라면 여기서 걸려서 return
        if (errorFlag === false) {
          return
        }
        const response = await axios({
          url: api.users.changeNickname(),
          method: "PATCH",
          headers: store.getters.authHeader,
          data: {
            userEmail: userEmail.value,
            userNewNickname: credentials.nicknameInput,
          },
        })

        if (response.status === 200) {
          credentials.nicknameInput = ""
          store.dispatch("fetchMe")
        }
      } catch (error) {
        if (error.response.status === 404) {
          Swal.fire({
            icon: "error",
            text: "존재하지 않는 계정입니다. 다시 시도해 주세요.",
          })
          store.dispatch("logout")
        } else {
          router.push({ name: "errorpage", params: { errorname: 500 } })
        }
      }
    }

    return {
      username,
      isChangeNickname,
      onChangeNickname,
      credentials,
      changeNickname,
      logout,
    }
  },
}
</script>

<style scoped>
@import url("../../assets/mainpage/navbar.css");
</style>
