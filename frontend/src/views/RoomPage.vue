<template>
  <TopBar v-if="isGamoMode !== 1" :isHost="isHost" />
  <div class="main-area">
    <VideoArea
      :isSide="Boolean(isSide)"
      :roomId="roomId"
      :isVideoOpen="isVideoOpen"
      :isAudeoOpen="isAudeoOpen"
      :chatData="chatData"
    />
    <SideArea
      class="self-center"
      v-if="isSide !== 0"
      @send-message="sideFromRoom"
      :isGameMode="isGameMode"
    />
  </div>
  <BottomBar
    @room-edit="isEditModal = !isEditModal"
    @video-toggle="isVideoOpen = !isVideoOpen"
    @audio-toggle="isAudeoOpen = !isAudeoOpen"
  />
  <EditModal v-if="isEditModal" @close="editModalClose">
    <EditModalContent @close="editModalClose" />
  </EditModal>

  <button @click="isLiarWaiting = true">waiting</button>
  <LiarWaitingModal v-if="isLiarWaiting" @close="isLiarWaiting = false" />
</template>

<script setup>
import { ref, watchEffect, onBeforeUnmount, onMounted } from "vue"
import VideoArea from "@/components/RoomPage/VideoArea.vue"
import SideArea from "@/components/RoomPage/SideArea.vue"
import BottomBar from "@/components/RoomPage/BottomBar.vue"
import EditModal from "@/components/RoomPage/meeting/EditModal.vue"
import EditModalContent from "@/components/RoomPage/meeting/EditModalContent.vue"
import TopBar from "@/components/RoomPage/game/liargame/TopBar.vue"
//modal 임시 위치
import { useRoute } from "vue-router"
import { useStore } from "vuex"
import api from "@/api/api"
import Swal from "sweetalert2"
/* 라이어게임 모달 */
import LiarWaitingModal from "@/components/RoomPage/game/liargame/modal/LiarWaitingModal.vue"
const isLiarWaiting = ref(false)

// video toggle
const isVideoOpen = ref(true)
const isAudeoOpen = ref(true)

//  props 정보
const route = useRoute()
const roomId = route.params.roomId
const store = useStore()

const chatData = ref({
  chatData: "",
  chatCount: 0,
})

// 채팅이 오면 받아줍니다.
const sideFromRoom = function (e) {
  chatData.value.chatData = e
  chatData.value.chatCount++
}

// 게임 모드 판별하는 변수
const isGameMode = ref(store.getters.getRoomInfo.game)

// 본인이 호스트인지 판별하는 변수
const isHost = ref(false)

const isSide = ref(0)
watchEffect(() => {
  /* eslint-disable */
  // 지금 방 안에 있다가 유저가 입장하면 정보를 새로 불러오는 로직이 작성되어 있나요?
  // 그거 인식하게 해야 합니다.

  // SideArea Open 정보
  // 게터에 저장되어 있는 정보를 가져왔습니다.
  // 이미 문자열로 저장되어 있는 부분 처리하기 싫어서 여기서는 숫자로 처리해 주었습니다.

  // 방정보의 호스트와 내 이메일 주소가 일치하면
  if (store.getters.me.userEmail === store.getters.getRoomInfo.hostEmail) {
    isHost.value = true
  } else {
    isHost.value = false
  }

  isSide.value = parseInt(store.getters.get_sidebar)
  isGameMode.value = store.getters.getRoomInfo.game
})

/* 방정보 수정 모달 */
const isEditModal = ref(false)

const editModalClose = function () {
  isEditModal.value = false
}

const leaveOrKill = async function () {
  const userEmail = store.getters.me.userEmail
  const hostEmail = store.getters.getRoomInfo.hostEmail
  const isHost = hostEmail === userEmail

  const data = { roomId }
  if (isHost) {
    await api.room.killRoom(data)
    await setTimeout(() => {
      Swal.fire({
        title: "당신은 Host 군요",
        text: "방을 삭제했습니다.",
      })
    }, 1000)
    return
  } else {
    await api.room.leaveRoom(data)
    await setTimeout(() => {
      Swal.fire({
        title: "당신은 User 군요",
        text: "방에서 나갔습니다.",
      })
    }, 1000)
    return
  }
}
// 해당 페이지 열 때
onMounted(async () => {
  await store.dispatch("getRoomInfo", roomId)
  await store.dispatch("checkToken")
  await api.room.enterRoom({ roomId })
})

// 해당 페이지에서 나갈 때
onBeforeUnmount(async () => {
  if (
    store.getters.me.userEmail !== undefined &&
    store.getters.getRoomInfo.hostEmail !== undefined
  ) {
    await leaveOrKill()
  }
  store.dispatch("initChatting")
})
</script>

<style scoped>
.temp {
  border: solid 1px black;
}
.main-area {
  top: 5rem;
  bottom: 5rem;
  position: absolute;
  left: 3vw;
  right: 3vw;
  padding-top: 10px;
  padding-bottom: 70px;
  min-width: 794px;
  display: flex;
  align-items: center;
}
</style>
