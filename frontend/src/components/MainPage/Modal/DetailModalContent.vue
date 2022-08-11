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

<script>
import Swal from "sweetalert2"
import { ref, watchEffect } from "vue"
import axios from "axios"
import api from "@/api/api"
import { useStore } from "vuex"
import { useRouter } from "vue-router"

export default {
  name: "DetailModalContent",
  props: ["roomId"],
  setup(props) {
    let info = ref({})
    const store = useStore()
    const router = useRouter()
    const token = store.state.users.token
    const passwordInput = ref("")
    const userNum = ref(0)

    watchEffect(async () => {
      try {
        const response = await axios({
          url: api.room.roomInfo(props.roomId),
          method: "GET",
          headers: { authorization: "Bearer " + token },
        })
        if (response.status === 200) {
          info.value = response.data
          userNum.value = 1 + info.value.joinUsers.length
        }
      } catch (error) {
        Swal.fire({
          icon: "error",
          text: "해당 방 정보가 존재하지 않습니다!",
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
      try {
        if (info.value.game === 2) {
          Swal.fire({
            icon: "error",
            text: "게임 중인 방에는 입장할 수 없습니다",
          })
          return
        } else if (userInList(store.getters.me.userNickname) === true) {
          Swal.fire({
            icon: "error",
            html: "이미 참가 중인 방 입니다! <br><br> 2초 후, Room으로 이동합니다.",
            timer: 2000,
          })
          await setTimeout(() => {
            router.push({ name: "roompage", params: { roomId: props.roomId } })
          }, 2000)
          return
        }
        const data = ref({})

        data.value = {
          roomId: props.roomId,
        }

        if (info.value.isPrivate === 1) {
          data.value.inputPassword = passwordInput.value
        }

        const response = await axios({
          url: api.room.enterRoom(),
          method: "POST",
          headers: { authorization: "Bearer " + token },
          data: data.value,
        })

        if (response.status === 200) {
          router.push({ name: "roompage", params: { roomId: props.roomId } })
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
          } else if (
            error.response.data.message ===
            "Bad Request - Can't Enter Game Room"
          ) {
            Swal.fire({
              icon: "error",
              text: "게임 중인 방에는 입장할 수 없습니다",
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
    return { info, joinRoom, passwordInput, userNum }
  },
}
</script>

<style></style>
