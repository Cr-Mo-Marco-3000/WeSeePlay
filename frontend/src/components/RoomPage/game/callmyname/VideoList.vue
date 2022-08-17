<template>
  <div class="row full-height justify-center items-center video-list-area">
    <div class="row col-9 justify-center">
      <video-item
        v-for="(user, idx) in callMyNamePlayers"
        class="col-6"
        :key="idx"
        :user="user"
        :gameIdx="tmpNum"
        @next="gameIdxUp"
        @answer="userInputModal = true"
        :userEmail="gameSet.userList[tmpNum]"
        :suggestion="suggestions[idx]"
        :videoUserEmail="gameSet.userList[idx]"
        :coolDown="tmpCoolDown"
      />
    </div>
  </div>
  <CallmynameInputModal
    v-if="userInputModal"
    @close="userInputModal = false"
    @gameIdxUp="chooseIncorrectCoolDown += 6"
    :suggestion="suggestions[tmpNum]"
    :gameIdx="tmpNum"
    :session="session"
  />
</template>

<script setup>
import VideoItem from "./VideoItem.vue"
import CallmynameInputModal from "@/components/RoomPage/game/callmyname/modal/CallmynameInputModal.vue"

import { reactive, ref, defineProps, watchEffect } from "vue"

const props = defineProps({
  setting: {
    type: Object,
    required: true,
  },
  session: {
    type: Object,
    required: true,
  },
  gameIdx: {
    type: Number,
    required: true,
  },
  coolDown: {
    type: Number,
    required: true,
  },
})

const gameSet = reactive({ ...props.setting })
const tmpNum = ref(props.gameIdx)
const tmpCoolDown = ref(0)
const callMyNamePlayers = ref(gameSet.gameUserOrder.slice(0, 3))
const nonPlayers = ref(gameSet.gameUserOrder.slice(3))
const suggestions = ref(gameSet.suggestion)
const userInputModal = ref(false)
const chooseIncorrectCoolDown = ref(0)
console.log("여기까지는 들어왔나", callMyNamePlayers.value)
console.log("이게 문제인가", gameSet.userList)
console.log(nonPlayers)
watchEffect(() => {
  tmpNum.value = props.gameIdx
  console.log("tmpNum")
  tmpCoolDown.value = props.coolDown + chooseIncorrectCoolDown.value
  console.log("tmpCoolDown", tmpCoolDown.value)
})

const gameIdxUp = function () {
  props.session
    .signal({
      data: 1,
      to: [],
      type: "Call my name, idx up",
    })
    .then(() => {
      console.log("Call my name, idx up")
    })
    .catch((error) => {
      console.error(error)
    })
}
</script>

<style>
.video-list-area {
  width: 100%;
}
</style>
