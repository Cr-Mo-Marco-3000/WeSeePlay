<template>
  <div class="user-input">
    <span>방 제목</span>
    <input type="text" name="title" v-model="roomInfo.title" />
  </div>

  <div class="user-input">
    <span>호스트</span>
    <select>
      <option disabled value="">호스트를 위임할 사용자를 정해주세요</option>
      <option v-for="(user, key) in userInfo" :key="key"></option>
    </select>
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
      placeholder="비밀번호"
      :disabled="roomInfo.isPrivate == false"
      v-model="roomInfo.roomPassword"
    />
  </div>

  <span class="edit-error-msg">{{ editErrorMsg }}</span>

  <div class="btns-box">
    <button class="overlay__btn room_edit_btn" @click="editRoom">수정</button>
  </div>
</template>

<script>
import Swal from "sweetalert2"
import { ref, watchEffect } from "vue"
import { useStore } from "vuex"
import { useRoute } from "vue-router"
import api from "@/api/api"
import axios from "axios"

export default {
  name: "EditModalContent",
  emits: ["close"],
  props: [],
  setup(props, context) {
    const editErrorMsg = ref("")
    const store = useStore()
    // const router = useRouter()
    const route = useRoute()
    const token = store.state.users.token
    const roomId = route.params.roomId

    const roomInfo = ref({})
    const userInfo = ref({})
    store.dispatch("getRoomInfo", roomId)
    watchEffect(() => {
      roomInfo.value = store.getters.getRoomInfo
      userInfo.value = store.getters.getUserInfo
      if (roomInfo.value.isPrivate == 1) {
        roomInfo.value.isPrivate = true
      } else {
        roomInfo.value.isPrivate = false
      }
    })

    const editRoom = async function () {
      try {
        let errorFlag = 0

        if (roomInfo.value.isPrivate == true) {
          roomInfo.value.isPrivate = 1
        } else {
          roomInfo.value.isPrivate = 0
        }
        if (roomInfo.value.title == "") {
          editErrorMsg.value = "방 이름을 정해 주세요"
          errorFlag = 1
        }
        if (roomInfo.value.isPrivate) {
          if (roomInfo.value.roomPassword.length != 4) {
            editErrorMsg.value = "4자리의 비밀번호를 정해주세요"
            errorFlag = 1
          }
        }

        if (errorFlag) {
          roomInfo.value.isPrivate = Boolean(roomInfo.value.isPrivate)
          errorFlag = 0
          return
        }

        const response = await axios({
          url: api.room.editRoom(roomId),
          method: "PATCH",
          headers: { Authorization: "Bearer " + token },
          data: roomInfo.value,
        })
        if (response.data.statusCode === 200) {
          Swal.fire({
            icon: "success",
            text: "방 정보 수정이 완료되었습니다",
          })
          context.emit("close")
        }
      } catch (err) {
        console.log("실패")
      }
    }

    return { editErrorMsg, roomInfo, userInfo, editRoom }
  },
}
</script>

<style></style>
