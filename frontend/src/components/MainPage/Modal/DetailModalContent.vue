<template>
  <div class="detail-text">
    <div class="title-game-info">
      <span>제목</span>
      <div v-if="info.game == 2" class="game-info">
        <span><i class="fa-solid fa-gamepad"></i></span>
        <span>현재 게임 진행 중!</span>
      </div>
    </div>
    <p>{{ info.title }}</p>
  </div>
  <div class="host-info">
    <span>호스트 </span>
    <p>{{ info.hostNickname }}</p>
  </div>
  <div class="time-info">
    <span>방 개설 시간</span>
    <p>{{ info.callStartTime }}</p>
  </div>
  <div class="discript-info detail-text">
    <span>내용</span>
    <p>{{ info.descript }}</p>
  </div>
  <div v-if="!info.isPrivate" class="detail-text">
    <span>참여자 목록 ({{ userNum }})</span>
    <div class="user-list">
      <div>{{ info.hostNickname }}</div>
      <div v-for="(user, idx) in info.joinUsers" :key="idx">
        {{ user.userNickname }}
      </div>
    </div>
  </div>
  <div v-else class="user-input room-pw-input">
    <span>비밀번호</span>
    <span> 비공개 방입니다.</span>
    <input v-model="passwordInput" type="password" />
  </div>

  <button @click="joinRoom" class="overlay__btn join-btn">방 입장</button>
</template>

<script setup>
import Swal from "sweetalert2"
import { ref, watchEffect, defineProps } from "vue"
import api from "@/api/api"
import { useStore } from "vuex"
import { useRouter } from "vue-router"

const props = defineProps({ roomId: Number })
const info = ref({})
const store = useStore()
const router = useRouter()
const passwordInput = ref("")
const userNum = ref(0)

watchEffect(async () => {
  const response = await api.room.roomInfo(props.roomId)
  console.log("ROOM 정보", response)
  const { status, data } = response
  if (status === 200) {
    console.log("loading 성공", data)
    info.value = data
  } else {
    Swal.fire({
      title: "방 정보가 존재하지 않습니다.",
      icon: "error",
    })
  }
})

const userInList = function (nickname) {
  const userList = info.value.joinUsers
  const rst = ref(false)
  if (info.value.hostNickname === nickname) {
    return true
  }
  userList.forEach((user) => {
    if (user.userNickname === nickname) {
      rst.value = true
    }
  })
  console.log(
    `> userInList Function => 이 유저는 해당 룸 안에 있습니까?: ${rst.value}`
  )
  return rst.value
}

const joinRoom = async function () {
  console.log("joinRoom Function Start")
  // 게임 중인 방 or 이미 참가 중인 방에 입장하는 경우
  if (info.value.game === 2) {
    Swal.fire({
      icon: "error",
      html: "이미 참가 중인 방 입니다! <br><br> 1초 후, Room으로 이동합니다.",
      timer: 1000,
    })
  } else if (userInList(store.getters.me.userNickname) === true) {
    Swal.fire({
      icon: "error",
      html: "이미 참가 중인 방 입니다! <br><br> 1초 후, Room으로 이동합니다.",
      timer: 1000,
    })
    await setTimeout(() => {
      router.push({ name: "roompage", params: { roomId: props.roomId } })
    }, 1000)
    return
  }

  // api.js 에 데이터(req) 넘기기
  const req = {
    roomId: info.value.roomId,
    inputPassword: passwordInput.value,
  }

  const { status, data } = await api.room.enterRoom(req)
  console.log("enterRoom", status, data)
  if (status === 200) {
    router.push({ name: "roompage", params: { roomId: props.roomId } })
  } else {
    console.log("enterRoom > Error Data > ", data)
  }
}
</script>

<style></style>
