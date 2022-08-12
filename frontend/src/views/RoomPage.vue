<template>
  <div class="main-area">
    <VideoArea
      class="video_area"
      :class="isSide ? 'video_area_on_sidebar' : 'video_area_off_sidebar'"
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
  <LiarModal v-if="isLiarModal" @close="isLiarModal = false">
    <LiarModalContent />
  </LiarModal>
</template>

<script setup>
import { ref, watchEffect, onBeforeUnmount, onMounted } from "vue"
import VideoArea from "@/components/RoomPage/VideoArea.vue"
import SideArea from "@/components/RoomPage/SideArea.vue"
import BottomBar from "@/components/RoomPage/BottomBar.vue"
import EditModal from "@/components/RoomPage/meeting/EditModal.vue"
import EditModalContent from "@/components/RoomPage/meeting/EditModalContent.vue"
import LiarModal from "@/components/RoomPage/game/liargame/modal/LiarModal.vue"
import LiarModalContent from "@/components/RoomPage/game/liargame/modal/LiarModalContent.vue"
import { useRoute } from "vue-router"
import { useStore } from "vuex"
import api from "@/api/api"
import Swal from "sweetalert2"

// video toggle
const isVideoOpen = ref(true)
const isAudeoOpen = ref(true)

//  props 정보
const route = useRoute()
const roomId = route.params.roomId
const store = useStore()
store.dispatch("getRoomInfo", roomId)
const hostEmail = store.getters.getRoomInfo.hostEmail
const userEmail = store.getters.me.userEmail

const chatData = ref("")
// 채팅 정보
const sideFromRoom = function (e) {
  console.log("메세지가 도착할까요?", e)
  chatData.value = e
}

const isSide = ref(0)
watchEffect(() => {
  /* eslint-disable */
  // 지금 방 안에 있다가 유저가 입장하면 정보를 새로 불러오는 로직이 작성되어 있나요?
  // 그거 인식하게 해야 합니다.

  // SideArea Open 정보
  // 게터에 저장되어 있는 정보를 가져왔습니다.
  // 이미 문자열로 저장되어 있는 부분 처리하기 싫어서 여기서는 숫자로 처리해 주었습니다.
  isSide.value = parseInt(store.getters.get_sidebar)
})

/* 방정보 수정 모달 */
const isEditModal = ref(false)

/* 라이어게임 모달 */
const isLiarModal = ref(false)

const editModalClose = function () {
  isEditModal.value = false
}

const leaveOrKill = async function () {
  const isHost = hostEmail === userEmail

  const data = { roomId }
  console.log(`isHost: ${hostEmail} ${userEmail}`, `roomId: ${roomId}`)
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
onMounted(() => {
  window.addEventListener("beforeunload", leaveOrKill)
})

// 해당 페이지에서 나갈 때
onBeforeUnmount(async () => {
  window.removeEventListener("beforeunload", leaveOrKill)
})
</script>

<style scoped>
.temp {
  border: solid 1px black;
}
.main-area {
  top: 1rem;
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

.video_area {
  position: absolute;
  left: 3rem;
  top: 3rem;
  bottom: 5rem;
  display: flex;
  align-items: center;
}

/* sidebar가 켜지고 꺼짐에 따라서, position absolute로 지정한 오른쪽에 여백이 증가하고 감소한다 */
/* col-3을 쓰면, 화면 사이즈의 전체 비율에 따라 조절하게 되기 때문에 레이아웃이 어그러지더라 */
.video_area_on_sidebar {
  right: 26rem;
}

.video_area_off_sidebar {
  right: 3rem;
}
</style>
