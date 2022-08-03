<template>
  <div class='video-box'>
    <p>id: {{ user.id }} / nickname: {{ user.nickname }}</p>
    <p v-if="user.onVideo">
        <img src="https://cdn3.iconfinder.com/data/icons/vector-icons-6/96/256-512.png" style='width: 150px; height: 150px;'>
    </p>
    <p v-else>Video가 꺼져있습니다.</p>
  </div>
</template>

<script>
export default {
    props: {
        user: {
            type: Object,
            required: true,
        }
    },
}
</script>

<style>
.video-box {
  border: solid 1px black;
}
</style>