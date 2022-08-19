<template>
  <div class="row justify-center items-center q-mt-xl">
    <div class="row justify-center" :class="isSide ? 'col-12' : 'col-10'">
      <video-item
        v-for="(user, idx) in userList"
        :key="idx"
        :user="user"
        :userEmail="emailData[idx]"
        class="self-center q-mb-md"
        :class="grid"
      />
    </div>
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
  gameUserOrder: {
    type: Array,
    required: true,
  },
  emailData: {
    type: Array,
    required: true,
  },
})
const userList = ref([])
const numUser = ref(0)
const grid = ref("col-6")

if (numUser.value < 6) {
  grid.value = "col-6"
} else {
  grid.value = "col-4"
}
watchEffect(() => {
  userList.value = []
  userList.value = [...props.gameUserOrder]
  numUser.value = userList.value.length
  if (numUser.value === 2) {
    grid.value = "col-7"
  } else if (numUser.value < 7) {
    grid.value = "col-5"
  } else if (numUser.value > 9 && props.isSide === false) {
    grid.value = "col-3"
  } else {
    grid.value = "col-4"
  }
})
</script>

<style scoped>
.all-video-box {
  padding-bottom: 25%;
}

.too-many {
  width: 50%;
}

.arrow {
  width: 100%;
}

.row-min-width {
  min-width: 35rem;
}
</style>
