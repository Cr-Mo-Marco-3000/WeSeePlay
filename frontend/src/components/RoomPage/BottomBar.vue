<template>
  <div class="bottombar">
    <div class="triColumn"></div>
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
        <label @click="$emit('room-edit')" class="btn" for="setttingInput">
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
        <label class="btn exit-btn" for="exitInput"> </label>
      </div>
    </div>
    <!-- 오른쪽 div 공간 -->
    <div class="triColumn">
      <div class="rightIconDiv">
        <label id="1" @click="clickSidebarIcon" class="btn">
          <i id="1" class="fa-solid fa-users fa-2x"></i>
        </label>
      </div>
      <div class="rightIconDiv">
        <label id="2" @click="clickSidebarIcon" class="btn">
          <i id="2" class="fa-solid fa-comments fa-2x"></i>
        </label>
      </div>
      <div class="rightIconDiv">
        <label id="3" @click="clickSidebarIcon" class="btn">
          <i id="3" class="fa-solid fa-gamepad fa-2x"></i>
        </label>
      </div>
      <!-- right icon bundle -->
      <div class="rightIconBundle">
        <input
          v-model="sideAreaBundle"
          class="sideAreaBundleInput"
          id="sideAreaBundleInput"
          type="checkbox"
        />
        <label class="btn sideAreaBundleOpen" for="sideAreaBundleInput">
          <i class="fa-solid fa-angles-up fa-2x"></i>
        </label>

        <label id="3" @click="clickSidebarIcon" class="sideAreaBundleItem">
          <i id="3" class="fa-solid fa-gamepad fa-2x"></i>
        </label>
        <label id="2" @click="clickSidebarIcon" class="sideAreaBundleItem">
          <i id="2" class="fa-solid fa-comments fa-2x"></i>
        </label>
        <label id="1" @click="clickSidebarIcon" class="sideAreaBundleItem">
          <i id="1" class="fa-solid fa-users fa-2x"></i>
        </label>
      </div>
    </div>
  </div>
</template>

<script setup>
import store from "@/store"
import { ref } from "vue"

// 마이크가 오픈되어 있지 않음 => 기본값
const isMicOpen = ref(false)

// 비디오도 오픈되어 있지 않음 => 기본값
const isVideoOpen = ref(false)

// 세팅창도 오픈되어 있지 않음 => 기본값
const isSettingOpen = ref(false)

// 빠져나가는 버튼
const backToLounge = function () {
  console.log("라운지로 숑숑숑")
}

// sideArea 묶음 버튼
const sideAreaBundle = ref(false)

const clickSidebarIcon = function (event) {
  store.dispatch("openSidebar", event.target.id)
}
</script>

<style scoped>
@import url("../../../src/assets/roompage/bottombar.css");
</style>
