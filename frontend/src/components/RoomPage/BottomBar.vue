<template>
  <div class="bottombar">
    <!-- <button @click="">임시버튼</button> -->
    <div class="triColumn"></div>
    <div class="triColumn">
      <div class="middleIconDiv" @click="$emit('audio-toggle')">
        <label @click="onOffMic" class="btn">
          <i v-if="isMicOpen" class="fa-solid fa-microphone-lines fa-2x"></i>
          <i
            v-if="!isMicOpen"
            class="icon fa-solid fa-microphone-lines-slash fa-2x slash"
          ></i>
        </label>
      </div>
      <div class="middleIconDiv" @click="$emit('video-toggle')">
        <label @click="onOffVideo" class="btn">
          <i v-if="isVideoOpen" class="fa-solid fa-video fa-2x"></i>
          <i
            v-if="!isVideoOpen"
            class="fa-solid fa-video-slash fa-2x slash"
          ></i>
        </label>
      </div>
      <div v-if="isGameAndSettingOpen" class="middleIconDiv">
        <input
          v-model="isSettingOpen"
          class="input"
          id="setttingInput"
          type="checkbox"
        />
        <label @click="$emit('room-edit')" class="btn" for="setttingInput">
          <i class="fa-solid fa-gear fa-2x"></i>
        </label>
      </div>
      <div class="middleIconDiv">
        <input
          @click="leaveOrKill"
          class="input exitInput"
          id="exitInput"
          type="checkbox"
        />
        <label class="btn exit-btn" for="exitInput"> </label>
      </div>
    </div>
    <!-- 오른쪽 div 공간 -->
    <div class="triColumn">
      <div class="rightIconDiv">
        <label id="1" @click="clickSidebarIcon" class="btn">
          <i id="1" class="fa-solid fa-users fa-2x"></i>
        </label>
      </div>
      <div class="rightIconDiv">
        <label id="2" @click="clickSidebarIcon" class="btn">
          <i id="2" class="fa-solid fa-comments fa-2x"></i>
        </label>
      </div>
      <div v-if="isGameAndSettingOpen" class="rightIconDiv">
        <label id="3" @click="clickSidebarIcon" class="btn">
          <i id="3" class="fa-solid fa-gamepad fa-2x"></i>
        </label>
      </div>
      <!-- right icon bundle -->
      <div class="rightIconBundle">
        <input
          v-model="sideAreaBundle"
          class="sideAreaBundleInput"
          id="sideAreaBundleInput"
          type="checkbox"
        />
        <label
          v-if="isGameAndSettingOpen"
          class="btn sideAreaBundleOpen"
          for="sideAreaBundleInput"
        >
          <i class="fa-solid fa-angles-up fa-2x"></i>
        </label>

        <label id="3" @click="clickSidebarIcon" class="sideAreaBundleItem">
          <i id="3" class="fa-solid fa-gamepad fa-2x"></i>
        </label>
        <label id="2" @click="clickSidebarIcon" class="sideAreaBundleItem">
          <i id="2" class="fa-solid fa-comments fa-2x"></i>
        </label>
        <label id="1" @click="clickSidebarIcon" class="sideAreaBundleItem">
          <i id="1" class="fa-solid fa-users fa-2x"></i>
        </label>
      </div>
    </div>
  </div>
</template>

<script setup>
import store from "@/store"
import api from "@/api/api"
import { useRouter } from "vue-router"
import { ref, watchEffect } from "vue"
import Swal from "sweetalert2"
const router = useRouter()

// 마이크가 오픈되어 있지 않음 => 기본값
const isMicOpen = ref(false)

// 비디오도 오픈되어 있지 않음 => 기본값
const isVideoOpen = ref(false)

const onOffMic = function () {
  isMicOpen.value = !isMicOpen.value
  if (isMicOpen.value === true) {
    Swal.fire({
      position: "top",
      title: "마이크가 켜졌습니다.",
      color: "#695eef",
      backdrop: false,
      showConfirmButton: false,
      timer: 700,
      width: "25rem",
    })
  } else {
    Swal.fire({
      position: "top",
      title: "마이크가 꺼졌습니다.",
      color: "rgba(246, 30, 30, 0.75)",
      backdrop: false,
      showConfirmButton: false,
      timer: 700,
      width: "25rem",
    })
  }
}

const onOffVideo = function () {
  isVideoOpen.value = !isVideoOpen.value
  if (isVideoOpen.value === true) {
    Swal.fire({
      position: "top",
      title: "화면이 켜졌습니다.",
      color: "#695eef",
      backdrop: false,
      showConfirmButton: false,
      timer: 700,
      width: "25rem",
    })
  } else {
    Swal.fire({
      position: "top",
      title: "화면이 꺼졌습니다.",
      color: "rgba(246, 30, 30, 0.75)",
      backdrop: false,
      showConfirmButton: false,
      timer: 700,
      width: "25rem",
    })
  }
}

// 세팅창도 오픈되어 있지 않음 => 기본값
const isSettingOpen = ref(false)

// 빠져나가는 버튼 (방 나가기 or 방 삭제)

const leaveConfirm = async function () {
  let a = await Swal.fire({
    title: "방을 나가시겠습니까?",
    icon: "question",
    showCancelButton: true,
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33",
    confirmButtonText: "Room 나가기",
    cancelButtonText: "취소",
  }).then((result) => {
    if (result.isConfirmed) {
      return true
    } else {
      return false
    }
  })

  return a
}
const leaveRoom = async function () {
  const rst = await leaveConfirm()

  if (rst === true) {
    Swal.fire("방 나가기 완료!", "", "success")
    const data = { roomId: store.getters.getRoomInfo.roomId }
    const res = await api.room.leaveRoom(data)
    console.log(res)
    if (res.status === 200) {
      router.push({ name: "mainpage" })
    }
  }
}

const killConfirm = async function () {
  const rst = await Swal.fire({
    title: "당신은 호스트라서 나갈 수 없어요",
    text: "하지만 Room 삭제는 가능합니다",
    icon: "question",
    showCancelButton: true,
    confirmButtonColor: "red",
    cancelButtonColor: "#d33",
    confirmButtonText: "Room 삭제하기",
    cancelButtonText: "취소",
  }).then((result) => {
    if (result.isConfirmed) {
      return true
    }
    return false
  })
  return rst
}
const killRoom = async function () {
  const rst = await killConfirm()
  if (rst === true) {
    const data = { roomId: store.getters.getRoomInfo.roomId }
    const res = await api.room.killRoom(data)
    console.log("kill-room", res)
    if (res.status === 200) {
      Swal.fire("방 삭제 완료!", "", "success")
      router.push({ name: "mainpage" })
    } else {
      Swal.fire({
        title: "방 삭제 실패",
        html: `ErrorCode(${res.status})<br><br>지속 발생 시 관리자에게 문의해주세요`,
      })
    }
  }
}

const leaveOrKill = async function () {
  const hostNickname = store.getters.getRoomInfo.hostNickname
  const myNickname = store.getters.me.userNickname
  if (hostNickname !== myNickname) {
    leaveRoom()
  } else {
    killRoom()
  }
}

// sideArea 묶음 버튼
const sideAreaBundle = ref(false)

const clickSidebarIcon = function (event) {
  store.dispatch("openSidebar", event.target.id)
}

// 게임 & 방설정 언더바에서 보이게 하기 => 나중에 다시 활용할수도
const isGameAndSettingOpen = ref(true)

watchEffect(() => {
  if (store.getters.getRoomInfo.hostEmail === store.getters.me.userEmail) {
    isGameAndSettingOpen.value = true
  } else {
    isGameAndSettingOpen.value = false
  }
})
</script>

<style scoped>
@import url("../../../src/assets/roompage/bottombar.css");
</style>
