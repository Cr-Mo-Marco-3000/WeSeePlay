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
      placeholder="4자리의 비밀번호를 정해주세요"
      :disabled="roomInfo.isPrivate == false"
    />
  </div>
  <span class="room-create-error-msg">{{ roomCreateInputError }}</span>
  <div class="btns-box">
    <button class="overlay__btn" @click="createRoom">생성</button>
  </div>
</template>

<script>
import { ref, reactive } from "vue"
// import { computed } from "vue"
import { useStore } from "vuex"
import { useRouter } from "vue-router"
import api from "@/api/api"
import axios from "axios"

export default {
  name: "CreateRoomModalContent",
  setup() {
    const store = useStore()
    const router = useRouter()
    const token = store.state.users.token
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
      try {
        let errorFlag = 0

        if (roomInfo.isPrivate == true) {
          roomInfo.isPrivate = 1
        } else {
          roomInfo.isPrivate = 0
        }
        if (roomInfo.title == "") {
          roomCreateInputError.value = "방 이름을 정해 주세요"
          errorFlag = 1
        }
        if (roomInfo.isPrivate) {
          if (roomInfo.roomPassword.length != 4) {
            roomCreateInputError.value = "4자리의 비밀번호를 정해주세요"
            errorFlag = 1
          }
        }

        if (errorFlag) {
          roomInfo.isPrivate = Boolean(roomInfo.isPrivate)
          errorFlag = 0
          return
        }

        const response = await axios({
          url: api.room.createRoom(),
          method: "POST",
          headers: { Authorization: "Bearer " + token },
          data: roomInfo,
        })
        if (response.data.statusCode === 201) {
          const roomId = response.data.roomId
          router.push({ name: "roompage", params: { roomId: roomId } })
        }
      } catch (err) {
        console.log("실패")
      }
    }

    return {
      roomInfo,
      createRoom,
      roomCreateInputError,
    }
  },
}
</script>

<style></style>
