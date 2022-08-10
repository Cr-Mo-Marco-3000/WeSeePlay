<template>
  <div class="participant-item">
    <div>{{ user.userNickname }}</div>
  </div>
</template>
<script setup>
// eslint-disable-next-line
const props = defineProps({
  user: Object,
})
</script>
<style>
@import url('../../../../src/assets/roompage/sidearea.css');
</style>
