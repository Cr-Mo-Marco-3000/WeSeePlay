<template>
  <div class="row justify-center items-center">
    <a class="col-1" @click.stop="getPage(currentPage - 1)">
      <img class="arrow" src="@/assets/leftArrow.png" />
    </a>
    <!-- min width를 줘서 일정 크기 이하로 줄어들었을 때만 겹치게 함 -->
    <div class="row col-10 row-min-width">
      <video-item
        v-for="(user, idx) in showUsers"
        :key="idx"
        :user="user"
        :class="gridNum[side]"
        class="self-center"
      />
    </div>
    <a class="col-1" @click.stop="getPage(currentPage + 1)">
      <img class="arrow" src="@/assets/rightArrow.png" />
    </a>
  </div>
</template>

<script setup>
import { ref, defineProps, watchEffect } from "vue"
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
})
// pagination 관련
const currentPage = ref(0)
const maxPages = ref([0, 0])
const gridNum = ref(["col-6", "col-12"])
const pageLimit = ref([6, 3])
const showUsers = ref([])
const side = ref(0)

const getPage = function (page) {
  let s
  let e
  if (page > maxPages.value[side.value] || page < 0) {
    return
  } else if (page === maxPages.value[side.value]) {
    e = props.gameUserOrder.length
    s = props.gameUserOrder.length - pageLimit.value[side.value]
  } else {
    s = page * pageLimit.value[side.value]
    e = (page + 1) * pageLimit.value[side.value]
  }
  showUsers.value = props.gameUserOrder.slice(s, e)
  currentPage.value = page
  return
}

watchEffect(() => {
  props.isSide
  side.value = props.isSide ? 1 : 0
  getPage(0)
})

watchEffect(() => {
  props.gameUserOrder
  maxPages.value[0] = parseInt((props.gameUserOrder.length - 1) / 6)
  maxPages.value[1] = parseInt((props.gameUserOrder.length - 1) / 3)
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
