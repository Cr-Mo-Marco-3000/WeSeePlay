<template>
  <div @click="closeSideArea" v-if="isSideAreaOpen" class="sideArea">
    <button class="exit"><i class="fa-solid fa-x fa-lg"></i></button>
    <GameArea v-if="sideAreas.isGameAreaOpen" />
    <ChattingArea v-if="sideAreas.isChattingAreaOpen" />
    <ParticipantArea v-if="sideAreas.isParticipantAreaOpen" />
  </div>
</template>

<script setup>
import GameArea from './sidearea/GameArea.vue'
import ChattingArea from './sidearea/ChattingArea.vue'
import ParticipantArea from './sidearea/ParticipantArea.vue'
import { ref, reactive } from 'vue'

// sideArea 자체를 띄워주는 변수 => 상위 요소에서 그리드와 같이 사용 필요
const isSideAreaOpen = ref(false)

// 각각 컴포넌트들을 띄워주는 변수들
const sideAreas = reactive({
  isGameAreaOpen: false,
  isChattingAreaOpen: false,
  isParticipantAreaOpen: false,
})

// X 누르면 창이 꺼진다.
const closeSideArea = function () {
  isSideAreaOpen.value = false
}
</script>

<style scoped>
@import url('../../../src/assets/roompage/sidearea.css');
</style>
