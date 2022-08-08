<template>
  <div class="detail-text">
    <span>제목</span>
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
    <span>참여자 목록</span>
    <div class="user-list">
      <div v-for="(user, idx) in info.joinUsers" :key="idx">{{ user }}</div>
    </div>
  </div>
  <div v-else class="user-input room-pw-input">
    <span>비밀번호</span>
    <span> 비공개 방입니다.</span>
    <input v-model="passwordInput" type="password" />
  </div>

  <button @click="joinRoom" class="overlay__btn join-btn">방 입장</button>
</template>

<script>
import Swal from "sweetalert2"
import { ref, watchEffect } from "vue"
import axios from "axios"
import api from "@/api/api"
import { useStore } from "vuex"
import { useRouter } from "vue-router"

export default {
  name: "DetailModalContent",
  props: ["roomID"],
  setup(props) {
    let info = ref({})
    const store = useStore()
    const router = useRouter()
    const token = store.state.users.token
    const passwordInput = ref("")

    watchEffect(async () => {
      try {
        const response = await axios({
          url: api.room.roomInfo(props.roomID),
          method: "GET",
          headers: { authorization: "Bearer " + token },
        })
        if (response.status === 200) {
          info.value = response.data
        }
      } catch (error) {
        Swal.fire({
          icon: "error",
          text: "해당 방 정보가 존재하지 않습니다!",
        })
      }
    })

    const joinRoom = async function () {
      try {
        const data = ref({})
        if (info.value.isPrivate) {
          data.value = {
            roomID: props.roomID,
            inputPassword: passwordInput.value,
          }
        } else {
          data.value = {
            roomID: props.roomID,
          }
        }

        const response = await axios({
          url: api.room.enterRoom(),
          method: "POST",
          headers: { authorization: "Bearer " + token },
          data: data.value,
        })

        if (response.status === 200) {
          router.push({ name: "roompage", params: { roomID: props.roomID } })
        }
      } catch (error) {
        if (error.response.status === 400) {
          if (error.response.data.message === "Bad Request - Bad Password") {
            Swal.fire({
              icon: "error",
              text: "비밀번호가 틀렸습니다",
            })
          } else if (
            error.response.data.message === "Bad Request - Too Many People"
          ) {
            Swal.fire({
              icon: "error",
              text: "수용 인원을 초과하였습니다!",
            })
          } else {
            Swal.fire({
              icon: "error",
              text: "존재하지 않는 계정입니다. 다시 시도해 주세요.",
            })
            store.dispatch("logout")
          }
        } else {
          router.push({ name: "errorpage", params: { errorname: 500 } })
        }
      }
    }
    return { info, joinRoom, passwordInput }
  },
}
</script>

<style></style>
