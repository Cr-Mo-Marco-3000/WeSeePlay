<template>
  <div class="row full-height justify-center items-center video-list-area">
    <div class="row" :class="[less ? 'col-9' : 'col-12']">
      <video-item
        v-for="(user, idx) in UserVideo"
        :key="idx"
        :user="user"
        :class="grid"
        class="self-center q-mb-xs"
      />
    </div>
  </div>
</template>

<script setup>
import VideoItem from "./VideoItem.vue"
import { ref, defineProps, watchEffect } from "vue"

const props = defineProps({
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

// 비디오 사용자들(subscribers)
const UserVideo = ref([])
const numUser = ref(0)
const grid = ref("col-6")
const less = ref(true)
watchEffect(() => {
  UserVideo.value = []
  if (props.publisher) {
    console.log(props.subscribers.length)
    UserVideo.value.push(props.publisher)
    UserVideo.value.push(...props.subscribers)
  }
  numUser.value = UserVideo.value.length
  if (numUser.value == 1) {
    grid.value = "col-12"
  } else if (numUser.value > 6) {
    grid.value = "col-3"
  } else if (numUser.value > 4) {
    grid.value = "col-4"
  } else {
    grid.value = "col-6"
  }
  if (numUser.value <= 4) {
    less.value = true
  } else {
    less.value = false
  }
})
</script>

<style>
.video-list-area {
  width: 100%;
}
</style>
