<template>
  <div class="row full-height justify-center items-center video-list-area">
    <a class="col-1" @click.stop="getPageUser(currentPage - 1)">
      <img src="@/assets/leftArrow.png" />
    </a>
    <div class="row col-10">
      <video-item
        v-for="(user, idx) in PageUserVideo"
        :key="idx"
        :user="user"
        :class="grid"
        class="self-center"
      />
    </div>
    <a class="col-1" @click.stop="getPageUser(currentPage + 1)">
      <img src="@/assets/rightArrow.png" />
    </a>
  </div>
  <div>{{ currentPage }}</div>
</template>

<script setup>
import VideoItem from "./VideoItem.vue"
import { ref, defineProps, watchEffect } from "vue"

const props = defineProps({
  users: {
    type: Array,
    required: true,
  },
  isSide: {
    type: Boolean,
    required: true,
  },
  publisher: {
    type: Object,
  },
  subscribers: {
    type: Array,
  },
})

// pagination 관련
const currentPage = ref(0)
const maxPages = ref(0)
const pageLimit = ref(6)

const getPageUser = function (page) {
  // console.log(
  //   "page number다 짜식드라",
  //   currentPage.value,
  //   "입력 값: ",
  //   page,
  //   "maxpage: ",
  //   maxPages.value
  // )
  if (page > maxPages.value || page < 0) {
    return
  }
  currentPage.value = page
}

// 비디오 사용자들(subscribers)
const UserVideo = ref([])
const PageUserVideo = ref([])

// const getPageUserVideo = function (page) {
//   if (props.publisher) {
//     if (page > maxPages.value || page < 0) {
//       return
//     } else if (page === maxPages.value) {
//       PageUserVideo.value = UserVideo.value.slice(
//         UserVideo.value.length - pageLimit.value,
//         UserVideo.value.length
//       )
//     } else {
//       PageUserVideo.value = UserVideo.value.slice(
//         page * pageLimit.value,
//         (page + 1) * pageLimit.value
//       )
//     }
//     currentPage.value = page
//   }
// }

getPageUser(0)
// getPageUserVideo(0)

// grid 나누는 거 관련
const grid = ref("col-6")

watchEffect(() => {
  currentPage.value
  // 비디오 유저들
  UserVideo.value = []
  if (props.publisher) {
    console.log(props.subscribers.length)
    UserVideo.value.push(props.publisher)
    UserVideo.value.push(...props.subscribers)
  }
  console.log(
    "유저 비디오 입니다",
    UserVideo.value.length,
    props.subscribers.length
  )
  // getPageUserVideo(currentPage)
  console.log("hahahahahahah", PageUserVideo.value)
  if (props.isSide === true || UserVideo.value.length < 5) {
    grid.value = "col-6"
    pageLimit.value = 4
  } else {
    grid.value = "col-4"
    pageLimit.value = 6
  }
  console.log(
    UserVideo.value.length,
    grid.value,
    pageLimit.value,
    UserVideo.value,
    props.publisher,
    props.subscribers
  )
  maxPages.value = parseInt((UserVideo.value.length - 1) / pageLimit.value)
  console.log(
    "여기가 인덱스 입니다~",
    0 + currentPage.value * pageLimit.value,
    pageLimit.value + currentPage.value * pageLimit.value
  )
  PageUserVideo.value = UserVideo.value.slice(
    0 + currentPage.value * pageLimit.value,
    pageLimit.value + currentPage.value * pageLimit.value
  )
})
</script>

<style>
.video-list-area {
  width: 100%;
}
</style>
