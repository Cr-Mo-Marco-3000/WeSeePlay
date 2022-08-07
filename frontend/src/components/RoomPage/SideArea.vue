<template>
  <div v-if="!(sideBarStatus === '0')" class="sideArea">
    <button class="exit">
      <i @click="closeSidebar" class="fa-solid fa-x fa-lg exit-label"></i>
    </button>
    <div>{{ sideBar }}</div>
    <ParticipantArea v-if="sideBarStatus === '1'" :userList="userList" />
    <ChattingArea v-if="sideBarStatus === '2'" />
    <GameArea v-if="sideBarStatus === '3'" />
  </div>
</template>

<script setup>
// eslint-disable-next-line
import { ref, watchEffect } from 'vue'
import { useStore } from 'vuex'
import GameArea from './sidearea/GameArea.vue'
import ChattingArea from './sidearea/ChattingArea.vue'
import ParticipantArea from './sidearea/ParticipantArea.vue'

// 사이드바 열고 닫는 부분
const store = useStore()

const closeSidebar = () => {
  store.dispatch('openSidebar', '0')
}
const sideBarStatus = ref('0')

watchEffect(() => {
  sideBarStatus.value = store.getters.get_sidebar
})

// 여기부터 아래까지 임시 userList
const userList = ref([])

const nameList = ['아무무', '야스오', '유리아쥬']

userList.value.push({
  userId: 1,
  userNickname: '에드워드 컬렌',
  isPlayer: 1,
  isHost: 1,
})

for (let i = 2; i < 5; i++) {
  const user_id = i
  const user_nickname = nameList[i - 2]
  const is_player = 2 % i
  const is_host = 0
  const user = {
    userId: user_id,
    userNickname: user_nickname,
    isPlayer: is_player,
    isHost: is_host,
  }
  userList.value.push(user)
}

// 유저 참가, 제거를 해 보기 위한 함수 => 버튼으로 실험해봄
// eslint-disable-next-line
const userAdd = function () {
  const user = {
    userId: 1,
    userNickname: '임시유저',
    isPlayer: 1,
    isHost: 0,
  }
  userList.value.push(user)
}

// eslint-disable-next-line
const userDelete = function () {
  userList.value.pop()
}
</script>

<style scoped>
@import url('../../../src/assets/roompage/sidearea.css');
</style>
