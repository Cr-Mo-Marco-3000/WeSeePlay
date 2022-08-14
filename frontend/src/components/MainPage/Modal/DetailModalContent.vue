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
    <span>참여자 목록 ({{ info.joinUsers.length + 1 }})</span>
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
const info = ref({ joinUsers: [] })
const store = useStore()
const router = useRouter()
const passwordInput = ref("")

watchEffect(async () => {
  const response = await api.room.roomInfo(props.roomId)
  const { status, data } = response
  if (status === 200) {
    info.value = data
  } else {
    Swal.fire({
      title: "방 정보가 존재하지 않습니다.",
      icon: "error",
    })
  }
})

const errMsg = {
  inGame: "게임 중인 방에는 입장할 수 없습니다",
  inRoom: "이미 참가 중인 방 입니다! <br><br> 1초 후, Room으로 이동합니다.",
  "Bad Request - Bad Password": "비밀번호가 틀렸습니다",
  "Bad Request - Too Many People": "수용 인원을 초과하였습니다!",
  "Bad Request - Can't Enter Game Room": "게임 중인 방에는 입장할 수 없습니다",
  "Bad Request - No User": "존재하지 않는 계정입니다. 다시 시도해 주세요.",
  "Bad Request - No Room": "해당 Room은 존재하지 않습니다.",
}

const joinError = async function (key) {
  await Swal.fire({ icon: "error", html: `${errMsg[key]}`, timer: 1000 })
}

const joinRoom = async function () {
  // api.js 에 데이터(req) 넘기기
  const req = {
    roomId: info.value.roomId,
    inputPassword: passwordInput.value,
  }

  const { status, data } = await api.room.enterRoom(req)
  if (status === 200) {
    router.push({ name: "roompage", params: { roomId: props.roomId } })
    return
  } else if (status === 400) {
    await joinError(data.message)
    if (data.message === "Bad Request - No User") {
      store.dispatch("logout")
    } else if (data.message === "Bad Request - No Room") {
      location.reload()
    }
  } else {
    router.push({ name: "errorpage", params: { errorname: 500 } })
  }
}
</script>

<style></style>
