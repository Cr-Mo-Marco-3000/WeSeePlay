<template>
 <nav class="navbar">
  <ul class="navbar-content">
    <div class="logo-box">
      <div class="name">WeSeePlay</div>
      <img src="../../assets/logo_nontext.png">
    </div>
      <li class="user-text nav-item"><span class="welcome-text">반가워요,<p>{{ username }}</p>님 +</span>
        <ul class="dropdown-box">
          <li v-if="!isChangeNickname" @click="onChangeNickname">닉네임 변경</li>
          <li v-else class="nickname-input">
            <input type="text" :placeholder="username">
            <span @click="onChangeNickname">X</span>
            <button>변경</button>
          </li>
          <li>비밀번호 변경</li>
          <li>회원 삭제</li>
        </ul>
      </li>
      <li class="nav-item logout-box">
        <span>로그아웃</span>
        <i class="fa-solid fa-arrow-right-from-bracket"></i>
      </li>
    </ul>
   </nav>
</template>


<script>
import { ref } from 'vue' 

export default {
  name: "NavBar",
  setup() {
    const username = ref('WeSeePlay')
    const isChangeNickname = ref(false)

    const onChangeNickname = function() {
      isChangeNickname.value = !isChangeNickname.value
    }

    return { username, isChangeNickname, onChangeNickname }
  }
}
</script>

<style>
@import url('../../assets/mainpage/navbar.css');
</style>