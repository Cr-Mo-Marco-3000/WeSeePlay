<template>
  <div class="row video-list-area justify-center">
    <div class="row" :class="isSide ? 'col-4' : 'col-4'">
      <MainVideo
        v-if="tmpNum == 0"
        class="self-center col-12"
        :user="gameSet.gameUserOrder[tmpNum]"
        :gameIdx="tmpNum"
        @next="gameIdxUp"
        :userEmail="gameSet.gameUserList.value[tmpNum]"
      />
      <MainVideo
        v-if="tmpNum == 1"
        class="self-center col-12"
        :user="gameSet.gameUserOrder[tmpNum]"
        :gameIdx="tmpNum"
        @next="gameIdxUp"
        :userEmail="gameSet.gameUserList.value[tmpNum]"
      />
      <MainVideo
        v-if="tmpNum == 2"
        class="self-center col-12"
        :user="gameSet.gameUserOrder[tmpNum]"
        :gameIdx="tmpNum"
        @next="gameIdxUp"
        :userEmail="gameSet.gameUserList.value[tmpNum]"
      />
      <MainVideo
        v-if="tmpNum == 3"
        class="self-center col-12"
        :user="gameSet.gameUserOrder[tmpNum]"
        :gameIdx="tmpNum"
        @next="gameIdxUp"
        :userEmail="gameSet.gameUserList.value[tmpNum]"
      />
      <MainVideo
        v-if="tmpNum == 4"
        class="self-center col-12"
        :user="gameSet.gameUserOrder[tmpNum]"
        :gameIdx="tmpNum"
        @next="gameIdxUp"
        :userEmail="gameSet.gameUserList.value[tmpNum]"
      />
      <MainVideo
        v-if="tmpNum == 5"
        class="self-center col-12"
        :user="gameSet.gameUserOrder[tmpNum]"
        :gameIdx="tmpNum"
        @next="gameIdxUp"
        :userEmail="gameSet.gameUserList.value[tmpNum]"
      />
      <MainVideo
        v-if="tmpNum == 6"
        class="self-center col-12"
        :user="gameSet.gameUserOrder[tmpNum]"
        :gameIdx="tmpNum"
        @next="gameIdxUp"
        :userEmail="gameSet.gameUserList.value[tmpNum]"
      />
      <MainVideo
        v-if="tmpNum == 7"
        class="self-center col-12"
        :user="gameSet.gameUserOrder[tmpNum]"
        :gameIdx="tmpNum"
        @next="gameIdxUp"
        :userEmail="gameSet.gameUserList.value[tmpNum]"
      />
      <MainVideo
        v-if="tmpNum == 8"
        class="self-center col-12"
        :user="gameSet.gameUserOrder[tmpNum]"
        :gameIdx="tmpNum"
        @next="gameIdxUp"
        :userEmail="gameSet.gameUserList.value[tmpNum]"
      />
      <MainVideo
        v-if="tmpNum == 9"
        class="self-center col-12"
        :user="gameSet.gameUserOrder[tmpNum]"
        :gameIdx="tmpNum"
        @next="gameIdxUp"
        :userEmail="gameSet.gameUserList.value[tmpNum]"
      />
      <MainVideo
        v-if="tmpNum == 10"
        class="self-center col-12"
        :user="gameSet.gameUserOrder[tmpNum]"
        :gameIdx="tmpNum"
        @next="gameIdxUp"
        :userEmail="gameSet.gameUserList.value[tmpNum]"
      />
      <MainVideo
        v-if="tmpNum == 11"
        class="self-center col-12"
        :user="gameSet.gameUserOrder[tmpNum]"
        :gameIdx="tmpNum"
        @next="gameIdxUp"
        :userEmail="gameSet.gameUserList.value[tmpNum]"
      />
    </div>
    <AllVideo
      class="self-center"
      :class="isSide ? 'col-8' : 'col-8'"
      :gameUserOrder="gameSet.gameUserOrder"
      :isSide="isSide"
      :emailData="gameSet.gameUserList.value"
    />
    <!-- 일반인인지 라이어인지, 제시어 알려 주는 부분
    <div v-if="gameSet.liar == userId">당신은 라이어 입니다.</div>
    <div v-if="isLiar">당신은 라이어 입니다.</div>
    <div v-else>제시어는 {{ gameSet.suggestion }} 입니다.</div> -->
  </div>

  <VoteModal v-if="voteNow" :userList="tmpUserList" @vote="heIsLiar" />

  <liarInputModal v-if="liarInputNow" @answer="liarFinalInput" />
</template>

<script setup>
import { ref, reactive, defineProps, watchEffect } from "vue"
import MainVideo from "./MainVideo.vue"
import AllVideo from "./AllVideo.vue"
import VoteModal from "./modal/VoteModal.vue"
import liarInputModal from "./modal/LiarInputModal.vue"

const props = defineProps({
  isSide: {
    type: Boolean,
    required: true,
  },
  roomId: {
    type: String,
    required: true,
  },
  setting: {
    type: Object,
    required: true,
  },
  session: {
    type: Object,
    required: true,
  },
  isLiar: {
    type: Boolean,
  },
  tmpNum: {
    type: Number,
  },
  tmpvote: {
    type: Boolean,
  },
  tmpliarInputModal: {
    type: Boolean,
  },
  tmpUserList: {
    type: Array,
  },
  tmpGameResultModal: {
    type: Boolean,
  },
  tmpGameResult: {
    type: String,
  },
})
const gameSet = reactive({ ...props.setting })
const voteNow = ref(false)
const liarInputNow = ref(false)
const gameResultModal = ref(false)
const tmpGameResult = ref("")

watchEffect(() => {
  console.log(gameSet.gameIdx)
  if (!liarInputNow.value) {
    voteNow.value = props.tmpvote
  }
  liarInputNow.value = props.tmpliarInputModal
  gameResultModal.value = props.tmpGameResultModal
  tmpGameResult.value = props.tmpGameResult
})
// 역기서 부터 게임 입니다.
//여긴 game web socket
console.log("여기는 왜 안들어 가 있는거야?", gameSet.gameUserOrder)
// const gameSetting = function () {
//   gameSet.maxRound = 5
//   gameSet.suggestion = "사자"
//   shuffle(userList.value)
//   gameSet.liar = pickLiar(userList.value)
// }
const gameIdxUp = function () {
  console.log(gameSet.gameUserList)
  props.session
    .signal({
      data: 1,
      to: [],
      type: "gameIdxUp",
    })
    .then(() => {
      console.log("gameIdxUp")
    })
    .catch((error) => {
      console.error(error)
    })
}
const heIsLiar = function (suspect) {
  voteNow.value = false
  props.session
    .signal({
      data: suspect,
      to: [],
      type: "heIsLiar",
    })
    .then(() => {
      console.log("heIsLiar", suspect)
    })
    .catch((error) => {
      console.error(error)
    })
}
const liarFinalInput = function (inputValue) {
  liarInputNow.value = false
  let result = true
  if (inputValue == gameSet.suggestion) {
    console.log("정답이다 멍청아")
    result = false
  }
  console.log(result)
  props.session
    .signal({
      data: `${result},${inputValue}`,
      to: [],
      type: "liarInput",
    })
    .then(() => {
      console.log("liarInput")
    })
    .catch((error) => {
      console.error(error)
    })
  liarInputNow.value = false
}
</script>

<style scoped>
.video-list-area {
  width: 100%;
  min-width: 35rem;
}
</style>
