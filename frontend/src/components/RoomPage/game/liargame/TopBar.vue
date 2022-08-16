<template>
  <div class="top-bar">
    <div></div>
    <div>
      <!-- 라이어일때 뜨는 부분 -->
      <div v-if="isLiar">
        <span class="notify-span">당신은 Liar입니다</span>
      </div>
      <!-- 일반 참가자일때 뜨는 부분 -->
      <div v-if="!isLiar">
        <span class="notify-span">제시어: {{ gameSet.suggestion }}</span>
      </div>
    </div>
    <div>
      <button v-if="isHost" class="end-button">
        <div>
          <i class="fa-solid fa-person-running fa-xl"></i>
        </div>
        <div>
          <span>게임 종료</span>
        </div>
      </button>
    </div>
  </div>
</template>
<script setup>
import store from "@/store"
import { defineProps, watchEffect, ref } from "vue"
// eslint-disable-next-line

defineProps({
  isHost: Boolean,
})
const gameSet = ref(store.getters.getLiarGameSet)
const isLiar = ref(true)
const myMail = store.getters.me.userEmail
watchEffect(() => {
  gameSet.value = store.getters.getLiarGameSet
  console.log(
    "%c gameSet 정보 불러옴 (from TopBar.vue) ",
    "color: white;background: pink",
    store.getters.getLiarGameSet
  )
  if (myMail === gameSet.value.liar) {
    isLiar.value = true
  } else {
    isLiar.value = false
  }
})
</script>
<style scoped>
@import url("../../../../assets/roompage/topbar.css");
</style>
