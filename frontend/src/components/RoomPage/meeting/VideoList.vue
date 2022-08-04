<template>
  <div class="row items-center" style="height: 100%">
    <button class="moveBtn" @click="move(-1)" :disabled="currentPage === 0">
      --
    </button>
    <div class="row col justify-center video-list-case">
      <VideoItem
        class="video"
        v-for="user in filteredUsers"
        :key="user.id"
        :user="user"
        :class="filteredUsers.length < 5 ? 'col-5' : 'col-4'"
      />
    </div>
    <button
      class="moveBtn"
      @click="move(1)"
      :disabled="currentPage === maxPages"
    >
      ++
    </button>
  </div>
</template>

<script setup>
import { ref, defineProps } from "vue"
import VideoItem from "./VideoItem.vue"

const props = defineProps({
  users: {
    type: Array,
    required: true,
  },
})

// user video pagination용
const currentPage = ref(0)
const maxPages = ref(0)
const filteredUsers = ref([])

maxPages.value = parseInt((props.users.length - 1) / 6)

const getUserList = function () {
  const cp = currentPage.value
  const mp = maxPages.value
  if (cp === mp && mp !== 0) {
    filteredUsers.value = props.users.slice(
      props.users.length - 6,
      props.users.length
    )
  } else {
    filteredUsers.value = props.users.slice(cp * 6, cp * 6 + 6)
  }
}

getUserList()

const move = function (cal) {
  currentPage.value = currentPage.value + cal
  getUserList()
}
</script>

<style>
.video {
  max-width: 350px;
}

.video-list-case {
  height: 100%;
  padding-bottom: 20px;
}

.moveBtn {
  height: 30px;
}
</style>
