<template>
  <div
    v-if="!(sideBarStatus === '0')"
    class="sideArea"
    :class="{ 'sideArea-on-game': isGameMode }"
  >
    <button class="exit">
      <i @click="closeSidebar" class="fa-solid fa-x fa-lg exit-label"></i>
    </button>
    <ParticipantArea
      v-if="sideBarStatus === '1'"
      :roomUserInfo="roomUserInfo"
      :roomInfo="roomInfo"
    />
    <ChattingArea
      v-if="sideBarStatus === '2'"
      :roomUserInfo="roomUserInfo"
      :roomInfo="roomInfo"
      @send-message-from-me="chatFromSide"
    />
    <GameArea
      v-if="sideBarStatus === '3'"
      @click-liargame="$emit('click-liargame')"
      @click-callmyname="$emit('click-callmyname')"
    />
  </div>
</template>

<script setup>
// eslint-disable-next-line
import { ref, watchEffect, defineEmits, defineProps } from "vue"
import { useStore } from "vuex"
import api from "@/api/api"
import GameArea from "./sidearea/GameArea.vue"
import ChattingArea from "./sidearea/ChattingArea.vue"
import ParticipantArea from "./sidearea/ParticipantArea.vue"

const emit = defineEmits(["chatFromSide"])
defineProps({
  isGameMode: Number,
})
const msgData = {}
const chatFromSide = function (msg) {
  console.log("this is isOkay", msg)
  msgData.message = msg
  msgData.nickname = store.getters.me.userNickname
  emit("send-message", msgData)
}

// 사이드바 열고 닫는 부분
const store = useStore()

const closeSidebar = () => {
  store.dispatch("openSidebar", "0")
}
const sideBarStatus = ref("0")

// 유저 정보 출력
const roomUserInfo = ref([])
const roomInfo = ref({})

watchEffect(() => {
  roomInfo.value = store.getters.getRoomInfo
})

watchEffect(async () => {
  const res = await api.room.roomInfo(roomInfo.value.roomId)
  sideBarStatus.value = store.getters.get_sidebar
  roomUserInfo.value = res.data.joinUsers
})
</script>

<style scoped>
@import url("../../../src/assets/roompage/sidearea.css");
</style>
