<template>
  <div class="row video-list-area">
    <MainVideo
      class="self-center"
      :class="isSide ? 'col-7' : 'col-5'"
      :user="users[speaker]"
      @next="nextPlayer"
    />
    <AllVideo
      class="self-center"
      :class="isSide ? 'col-5' : 'col-7'"
      :users="users"
      :isSide="isSide"
    />
  </div>
</template>

<script setup>
import { ref, defineProps } from 'vue'
import MainVideo from './MainVideo.vue'
import AllVideo from './AllVideo.vue'

const props = defineProps({
  isSide: {
    type: Boolean,
    required: true,
  },
  users: {
    type: Array,
    required: true,
  },
})

const speaker = ref(0)

const nextPlayer = function () {
  if (speaker.value + 1 === props.users.length) {
    speaker.value = 0
    return
  }
  speaker.value += 1
}
</script>

<style scoped>
.video-list-area {
  width: 100%;
  min-width: 35rem;
}
</style>
