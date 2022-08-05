<template>
  <div class="video-item">
    <div class="user-box">
      <h3>{{ user.nickname }}</h3>
      <p v-if="user.onVideo">
        <img
          class="sample"
          src="https://cdn3.iconfinder.com/data/icons/vector-icons-6/96/256-512.png"
        />
      </p>
      <p v-else>Video가 꺼져있습니다.</p>
      <button @click.stop="$emit('next')">NEXT</button>
    </div>
  </div>
</template>

<script setup>
import { defineProps } from "vue"

defineProps({
  user: {
    type: Object,
    required: true,
  },
})
</script>

<style scoped>
.user-box {
  background-color: #c9c5f1;
  border-radius: 15px;
  width: 100%;
}
</style>
