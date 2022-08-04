<template>
  <div>RoomPage</div>
  <VideoArea />
  <SideArea />
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

/* 방정보 수정 모달 */
const isEditModal = ref(false)

/* 라이어게임 모달 */
const isLiarModal = ref(true)
</script>

<style></style>
