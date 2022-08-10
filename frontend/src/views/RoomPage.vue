<template>
  <div class="main-area">
    <VideoArea
      v-if="isSide !== 0"
      class="video_area video_area_on_sidebar"
      :isSide="Boolean(isSide)"
    />
    <VideoArea
      v-if="isSide === 0"
      class="video_area video_area_off_sidebar"
      :isSide="Boolean(isSide)"
    />
    <SideArea class="self-center" v-if="isSide !== 0" />
  </div>
  <BottomBar @room-edit="isEditModal = !isEditModal" />
  <EditModal v-if="isEditModal" @close="editModalClose">
    <EditModalContent @close="editModalClose" />
  </EditModal>
  <LiarModal v-if="isLiarModal" @close="isLiarModal = false">
    <LiarModalContent />
  </LiarModal>
</template>

<script setup>
import { ref, watchEffect } from 'vue'
import VideoArea from '@/components/RoomPage/VideoArea.vue'
import SideArea from '@/components/RoomPage/SideArea.vue'
import BottomBar from '@/components/RoomPage/BottomBar.vue'
import EditModal from '@/components/RoomPage/meeting/EditModal.vue'
import EditModalContent from '@/components/RoomPage/meeting/EditModalContent.vue'
import LiarModal from '@/components/RoomPage/game/liargame/modal/LiarModal.vue'
import LiarModalContent from '@/components/RoomPage/game/liargame/modal/LiarModalContent.vue'
import { useRoute } from 'vue-router'
import { useStore } from 'vuex'

//  props 정보
const route = useRoute()
const roomId = route.params.roomId
const store = useStore()
store.dispatch('getRoomInfo', roomId)

const isSide = ref(0)
watchEffect(() => {
  /* eslint-disable */
  const roomInfo = store.getters.getRoomInfo
  const userInfo = store.getters.getUserInfo
  // SideArea Open 정보
  // 게터에 저장되어 있는 정보를 가져왔습니다.
  // 이미 문자열로 저장되어 있는 부분 처리하기 싫어서 여기서는 숫자로 처리해 주었습니다.
  isSide.value = parseInt(store.getters.get_sidebar)
  // console.log(roomInfo)
  // console.log(userInfo)
})

/* 방정보 수정 모달 */
const isEditModal = ref(false)

/* 라이어게임 모달 */
const isLiarModal = ref(false)

const editModalClose = function () {
  isEditModal.value = false
}
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
