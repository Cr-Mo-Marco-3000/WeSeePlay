<template>
  <div id="speaker" class="video-box">
    Main Video
    <button @click.stop="nextPlayer">Next</button>
    <p>id: {{ speaker.id }} / nickname: {{ speaker.nickname }}</p>
    <p v-if="speaker.onVideo">
      <img
        src="https://cdn3.iconfinder.com/data/icons/vector-icons-6/96/256-512.png"
        style="width: 100px; height: 100px"
      />
    </p>
    <p v-else>Video가 꺼져있습니다.</p>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from "vue"

defineProps({
  speaker: {
    type: Object,
    required: true,
  },
})

const emits = defineEmits(["nextPlayer"])

const nextPlayer = function () {
  emits("nextPlayer")
}
</script>

<style>
.video-box {
  border: solid 1px black;
}
</style>
