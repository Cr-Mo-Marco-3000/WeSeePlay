<template>
  <div class="row justify-center items-center">
    <a class="col-1" @click.stop="getPageUser(currentPage - 1)">
      <img class="arrow" src="@/assets/leftArrow.png" />
    </a>
    <!-- min width를 줘서 일정 크기 이하로 줄어들었을 때만 겹치게 함 -->
    <div class="row col-10 justify-center">
      <video-item
        v-for="(user, idx) in pageUsers"
        :key="idx"
        :user="user"
        :userEmail="emailData[idx]"
        class="self-center"
        :class="isSide ? 'col-12' : 'col-6'"
      />
    </div>
    <a class="col-1" @click.stop="getPageUser(currentPage + 1)">
      <img class="arrow" src="@/assets/rightArrow.png" />
    </a>
  </div>
</template>

<script setup>
import { defineProps, ref, watchEffect } from "vue"
import VideoItem from "./VideoItem.vue"
const props = defineProps({
  isSide: {
    type: Boolean,
    required: true,
  },
  users: {
    type: Array,
    required: true,
  },
  gameUserOrder: {
    type: Array,
    required: true,
  },
  emailData: {
    type: Array,
    required: true,
  },
})

// pagination 관련
const userStream = [...props.gameUserOrder]
const currentPage = ref(0)
const userLimit = ref(6)
const maxPage = ref(0)
const pageUsers = ref()
const sInd = ref(0)
const eInd = ref(0)

const getPageUser = function (page) {
  if (page > maxPage.value || page < 0) {
    console.log("유효한 페이지 범위를 벗어났습니다.", page)
    return
  }
  if (page == maxPage.value) {
    sInd.value = userStream.length - userLimit.value
    sInd.value = sInd.value < 0 ? 0 : sInd.value
    eInd.value = userStream.length
  } else {
    sInd.value = page * currentPage.value
    eInd.value = sInd.value + userLimit.value
  }
  console.log("유효한 페이지 범위!.", page)
  pageUsers.value = userStream.slice(sInd.value, eInd.value)
  currentPage.value = page
  console.log("새로운 유저 리스트: ", pageUsers.value)
}
getPageUser(0)

watchEffect(() => {
  props.isSide
  userLimit.value = props.isSide ? 3 : 6
  maxPage.value = parseInt((userStream.length - 1) / userLimit.value)
  getPageUser(0)
})
</script>

<style scoped>
.all-video-box {
  padding-bottom: 25%;
}

.arrow {
  width: 100%;
}

.row-min-width {
  min-width: 35rem;
}
</style>
