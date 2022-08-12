<template>
  <div class="user-input">
    <span>방 제목</span>
    <input type="text" name="title" v-model="roomInfo.title" />
  </div>
  <div class="user-input">
    <span>방 설명</span>
    <textarea
      type="text"
      rows="5"
      name="descript"
      v-model="roomInfo.descript"
    />
  </div>
  <div class="private-check">
    <span>비공개</span>
    <input type="checkbox" name="is_private" v-model="roomInfo.isPrivate" />
  </div>
  <div class="user-input">
    <input
      type="password"
      name="roomPassword"
      v-model="roomInfo.roomPassword"
      placeholder="4 ~ 12자리의 비밀번호를 정해주세요"
      :disabled="roomInfo.isPrivate == false"
    />
  </div>
  <span class="room-create-error-msg">{{ roomCreateInputError }}</span>
  <div class="btns-box">
    <button class="overlay__btn" @click="createRoom">생성</button>
  </div>
</template>

<script setup>
import { ref, reactive } from "vue"
import { useRouter } from "vue-router"
import api from "@/api/api"
import Swal from "sweetalert2"

const router = useRouter()

// 방 생성 정보
let roomInfo = reactive({
  title: "",
  descript: "어서와요",
  roomPassword: "",
  game: 1,
  isPrivate: false,
})

// 오류 메시지
const roomCreateInputError = ref("")
const createRoom = async function () {
  const data = { ...roomInfo }
  if (!roomInfo.isPrivate) {
    data.roomPassword = ""
  }

  data.isPrivate = Number(data.isPrivate)

  if (roomInfo.title == "") {
    roomCreateInputError.value = "방 이름을 정해 주세요"
    return
  } else if (
    roomInfo.isPrivate &&
    (roomInfo.roomPassword.length < 4 || 12 < roomInfo.roomPassword.length)
  ) {
    roomCreateInputError.value = "4 ~ 12자리의 비밀번호를 정해주세요"
    return
  }

  const response = await api.room.createRoom(data)

  if (response.status === 201) {
    const roomId = response.data.roomId
    router.push({ name: "roompage", params: { roomId: roomId } })
  } else {
    Swal.fire({
      title: "방 생성에 실패했습니다",
      text: `Errorcode: ${response.status}`,
    })
  }
}
</script>

<style></style>
