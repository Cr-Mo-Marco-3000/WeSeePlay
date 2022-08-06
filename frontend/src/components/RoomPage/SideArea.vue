<template>
  <div>{{ signalSideArea }}</div>
  <div v-if="!(signalSideArea === 'close')" class="sideArea">
    <button class="exit">
      <i
        @click="emit('click-close', 'close')"
        class="fa-solid fa-x fa-lg exit-label"
      ></i>
    </button>
    <!-- <ParticipantArea /> -->
    <ChattingArea />
    <!-- <GameArea /> -->
  </div>
</template>

<script setup>
// import GameArea from './sidearea/GameArea.vue'
import ChattingArea from './sidearea/ChattingArea.vue'
// import ParticipantArea from './sidearea/ParticipantArea.vue'
// eslint-disable-next-line
const props = defineProps({
  signalSideArea: Object,
})

/* eslint-disable */
const emit = defineEmits(['click-close'])

const tempList = []

for (let i = 0; i < 5; i++) {
  const user_id = i
  const user_nickname = `참여자${i}`
  const is_player = 2 % i
  const is_host = 0
  const user = {
    userId: user_id,
    userNickname: user_nickname,
    isPlayer: is_player,
    isHost: is_host,
  }
  tempList.push(user)
}
console.log(tempList)
</script>

<style scoped>
@import url('../../../src/assets/roompage/sidearea.css');
</style>
