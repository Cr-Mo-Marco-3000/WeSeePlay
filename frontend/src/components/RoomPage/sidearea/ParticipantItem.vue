<template>
  <div class="participant-item">
    <div>{{ user.userNickname }}</div>
    <div v-if="user.isHost">호스트</div>
    <div v-if="!user.isHost"></div>
    <div v-if="user.isPlayer">
      <q-icon
        title="게임 중"
        size="2rem"
        name="sports_esports"
        color="deep-purple-3"
      ></q-icon>
    </div>
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
