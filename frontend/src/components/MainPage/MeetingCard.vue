<template>
  <div id="card-box" class="flex flex-center">
    <div id="card-container" @click="icon = true">
      <div id="card-head">
        <span id="card-title">
          {{ info.title }}
        </span>
        <i
          id="isprivate-icon"
          class="fa-solid fa-lock"
          v-show="info.isPrivate"
        ></i>
      </div>

      <div id="card-host" class="col-12">host: {{ info.hostNickname }}</div>

      <div class="col-12 card-bottom">
        <div class="game-info" v-if="info.game == 2">
          <span><i class="fa-solid fa-gamepad"></i></span>
          <span>현재 게임 진행 중!</span>
        </div>
        <span id="people-number"> &nbsp; {{ userNum }}명/12명 </span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  components: {},
  props: {
    info: Object,
  },
  setup(props) {
    const userNum = props.info.joinUsers.length + 1

    return { userNum }
  },
}
</script>

<style scoped>
@import "../../assets/mainpage/card.css";
</style>
