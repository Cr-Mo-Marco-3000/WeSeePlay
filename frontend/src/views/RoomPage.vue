<template>
  <div style="position: absolute; z-index: 500">
    RoomPage <button @click="isSide = !isSide">Side On/Off</button>
  </div>
  <div class="row main-area">
    <VideoArea
      class="self-center"
      :class="isSide ? 'col-9' : 'col-12'"
      :isSide="isSide"
    />
    <SideArea
      class="self-center"
      :class="isSide ? 'col-3' : ''"
      v-if="isSide"
    />
  </div>
  <BottomBar @room-edit="isEditModal = !isEditModal" />
  <EditModal v-if="isEditModal" @close="isEditModal = false">
    <EditModalContent />
  </EditModal>
  <LiarModal v-if="isLiarModal" @close="isLiarModal = false">
    <LiarModalContent />
  </LiarModal>
</template>

<script setup>
import { ref } from "vue"
import VideoArea from "@/components/RoomPage/VideoArea.vue"
import SideArea from "@/components/RoomPage/SideArea.vue"
import BottomBar from "@/components/RoomPage/BottomBar.vue"
import EditModal from "@/components/RoomPage/meeting/EditModal.vue"
import EditModalContent from "@/components/RoomPage/meeting/EditModalContent.vue"
import LiarModal from "@/components/RoomPage/game/liargame/modal/LiarModal.vue"
import LiarModalContent from "@/components/RoomPage/game/liargame/modal/LiarModalContent.vue"
import { useRoute } from "vue-router"

//  props 정보
const router = useRoute()
const roomID = router.params.roomID
console.log(roomID)

// SideArea Open 정보
const isSide = ref(false)

/* 방정보 수정 모달 */
const isEditModal = ref(false)

/* 라이어게임 모달 */
const isLiarModal = ref(false)
</script>

<style>
.temp {
  border: solid 1px black;
}
.main-area {
  height: 100%;
  position: relative;
  padding-top: 10px;
  padding-bottom: 70px;
  min-width: 794px;
}
</style>
