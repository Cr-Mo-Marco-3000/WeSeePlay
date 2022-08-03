<template>
  <div class="bottombar">
    <div class="triColumn">.</div>
    <div class="triColumn">
      <div class="middleIconDiv">
        <input
          v-model="isMicOpen"
          class="input"
          id="micInput"
          type="checkbox"
        />
        <label class="btn" for="micInput">
          <i v-if="isMicOpen" class="fa-solid fa-microphone-lines fa-2x"></i>
          <i
            v-if="!isMicOpen"
            class="icon fa-solid fa-microphone-lines-slash fa-2x slash"
          ></i>
        </label>
      </div>
      <div class="middleIconDiv">
        <input
          v-model="isVideoOpen"
          class="input videoInput"
          id="videoInput"
          type="checkbox"
        />
        <label class="btn" for="videoInput">
          <i v-if="isVideoOpen" class="fa-solid fa-video fa-2x"></i>
          <i
            v-if="!isVideoOpen"
            class="fa-solid fa-video-slash fa-2x slash"
          ></i>
        </label>
      </div>
      <div class="middleIconDiv">
        <input
          v-model="isSettingOpen"
          class="input"
          id="setttingInput"
          type="checkbox"
        />
        <label class="btn" for="setttingInput">
          <i class="fa-solid fa-gear fa-2x"></i>
        </label>
      </div>
      <div class="middleIconDiv">
        <input
          @click="backToLounge"
          class="input exitInput"
          id="exitInput"
          type="checkbox"
        />
        <label class="btn" for="exitInput">
          <i class="fa-solid fa-circle-xmark fa-2x"></i>
        </label>
      </div>
    </div>
    <div class="triColumn">
      <div class="rightIconDiv">
        <input
          v-model="participantArea"
          class="input"
          id="participantAreaInput"
          type="checkbox"
        />
        <label class="btn" for="participantAreaInput">
          <i class="fa-solid fa-users fa-2x"></i>
        </label>
      </div>
      <div class="rightIconDiv">
        <input
          v-model="chattingArea"
          class="input"
          id="chattingAreaInput"
          type="checkbox"
        />
        <label class="btn" for="chattingAreaInput">
          <i class="fa-solid fa-comments fa-2x"></i>
        </label>
      </div>
      <div class="rightIconDiv">
        <input
          v-model="gameArea"
          class="input"
          id="gameAreaInput"
          type="checkbox"
        />
        <label class="btn" for="gameAreaInput">
          <i class="fa-solid fa-gamepad fa-2x"></i>
        </label>
      </div>
      <!-- right icon bundle -->
      <div class="rightIconBundle">
        <ul class="sideAreaBundleList" v-if="sideAreaBundle">
          <li class="sideAreaBundleItem">참여자</li>
          <li class="sideAreaBundleItem">채팅</li>
          <li class="sideAreaBundleItem">게임</li>
        </ul>
        <input
          v-model="sideAreaBundle"
          class="input"
          id="sideAreaBundleInput"
          type="checkbox"
        />
        <label class="btn" for="sideAreaBundleInput">
          <i class="fa-solid fa-angles-up fa-2x"></i>
        </label>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
// 마이크가 오픈되어 있지 않음 => 기본값
const isMicOpen = ref(false)

// 비디오도 오픈되어 있지 않음 => 기본값
const isVideoOpen = ref(false)

// 세팅창도 오픈되어 있지 않음 => 기본값
const isSettingOpen = ref(false)

// 빠져나가는 버튼
const backToLounge = function () {
  console.log('라운지로 숑숑숑')
}

// 아래 세 개는 sideArea
const participantArea = ref(false)

const chattingArea = ref(false)

const gameArea = ref(false)

// sideArea 묶음

const sideAreaBundle = ref(false)
</script>

<style scoped>
@import url('../../../src/assets/roompage/bottombar.css');
</style>
