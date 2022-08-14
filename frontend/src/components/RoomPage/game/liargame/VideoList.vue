<template>
  <div class="row video-list-area">
    <div>{{ gameSet.gameIdx }}, {{ tmpNum }}</div>
    <div>
      <MainVideo
        v-if="tmpNum == 0"
        class="self-center"
        :class="isSide ? 'col-7' : 'col-5'"
        :user="gameSet.gameUserOrder[tmpNum]"
        :gameIdx="tmpNum"
        @next="gameIdxUp"
      />
      <MainVideo
        v-if="tmpNum == 1"
        class="self-center"
        :class="isSide ? 'col-7' : 'col-5'"
        :user="gameSet.gameUserOrder[tmpNum]"
        :gameIdx="tmpNum"
        @next="gameIdxUp"
      />
      <MainVideo
        v-if="tmpNum == 2"
        class="self-center"
        :class="isSide ? 'col-7' : 'col-5'"
        :user="gameSet.gameUserOrder[tmpNum]"
        :gameIdx="tmpNum"
        @next="gameIdxUp"
      />
      <MainVideo
        v-if="tmpNum == 3"
        class="self-center"
        :class="isSide ? 'col-7' : 'col-5'"
        :user="gameSet.gameUserOrder[tmpNum]"
        :gameIdx="tmpNum"
        @next="gameIdxUp"
      />
      <MainVideo
        v-if="tmpNum == 4"
        class="self-center"
        :class="isSide ? 'col-7' : 'col-5'"
        :user="gameSet.gameUserOrder[tmpNum]"
        :gameIdx="tmpNum"
        @next="gameIdxUp"
      />
      <MainVideo
        v-if="tmpNum == 5"
        class="self-center"
        :class="isSide ? 'col-7' : 'col-5'"
        :user="gameSet.gameUserOrder[tmpNum]"
        :gameIdx="tmpNum"
        @next="gameIdxUp"
      />
      <MainVideo
        v-if="tmpNum == 6"
        class="self-center"
        :class="isSide ? 'col-7' : 'col-5'"
        :user="gameSet.gameUserOrder[tmpNum]"
        :gameIdx="tmpNum"
        @next="gameIdxUp"
      />
      <MainVideo
        v-if="tmpNum == 7"
        class="self-center"
        :class="isSide ? 'col-7' : 'col-5'"
        :user="gameSet.gameUserOrder[tmpNum]"
        :gameIdx="tmpNum"
        @next="gameIdxUp"
      />
      <MainVideo
        v-if="tmpNum == 8"
        class="self-center"
        :class="isSide ? 'col-7' : 'col-5'"
        :user="gameSet.gameUserOrder[tmpNum]"
        :gameIdx="tmpNum"
        @next="gameIdxUp"
      />
      <MainVideo
        v-if="tmpNum == 9"
        class="self-center"
        :class="isSide ? 'col-7' : 'col-5'"
        :user="gameSet.gameUserOrder[tmpNum]"
        :gameIdx="tmpNum"
        @next="gameIdxUp"
      />
      <MainVideo
        v-if="tmpNum == 10"
        class="self-center"
        :class="isSide ? 'col-7' : 'col-5'"
        :user="gameSet.gameUserOrder[tmpNum]"
        :gameIdx="tmpNum"
        @next="gameIdxUp"
      />
      <MainVideo
        v-if="tmpNum == 11"
        class="self-center"
        :class="isSide ? 'col-7' : 'col-5'"
        :user="gameSet.gameUserOrder[tmpNum]"
        :gameIdx="tmpNum"
        @next="gameIdxUp"
      />
    </div>
    <AllVideo
      class="self-center"
      :class="isSide ? 'col-5' : 'col-7'"
      :gameUserOrder="gameSet.gameUserOrder"
      :isSide="isSide"
    />
    <!-- 일반인인지 라이어인지, 제시어 알려 주는 부분 -->
    <!-- <div v-if="gameSet.liar == userId">당신은 라이어 입니다.</div> -->
    <div v-if="isLiar">당신은 라이어 입니다.</div>
    <div v-else>제시어는 {{ gameSet.suggestion }} 입니다.</div>
  </div>

  <q-dialog v-model="voteNow" persistent>
    <q-card style="min-width: 350px">
      <q-card-section>
        <div v-for="(pl, idx) in tmpUserList" :key="idx">
          <p @click="heIsLiar(pl)">
            {{ pl }}
          </p>
        </div>
        <div @click="heIsLiar('skip')">skip</div>
        <div @click="voteNow = false">끝</div>
      </q-card-section>
    </q-card>
  </q-dialog>

  <q-dialog v-model="liarInputNow" persistent>
    <q-card style="min-width: 350px">
      <q-card-section>
        <div>
          <input type="text" v-model="liarInput" />
          <button @click="liarFinalInput()">입력하</button>
        </div>

        <div @click="liarInputNow = false">끝</div>
      </q-card-section>
    </q-card>
  </q-dialog>

  <q-dialog v-model="gameResultModal" persistent>
    <q-card style="min-width: 350px">
      <q-card-section>
        <div>
          <p>
            {{ tmpGameResult }}
          </p>
        </div>

        <div @click="gameResultModal = false">끝</div>
      </q-card-section>
    </q-card>
  </q-dialog>
</template>

<script setup>
import { ref, reactive, defineProps, watchEffect } from "vue"
import MainVideo from "./MainVideo.vue"
import AllVideo from "./AllVideo.vue"

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
const liarInput = ref("")
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
      console.log("heIsLiar")
    })
    .catch((error) => {
      console.error(error)
    })
}
const liarFinalInput = function () {
  liarInputNow.value = false
  let result = true
  if (liarInput.value == gameSet.suggestion) {
    console.log("정답이다 멍청아")
    result = false
  }
  console.log(result)
  props.session
    .signal({
      data: result,
      to: [],
      type: "liarInput",
    })
    .then(() => {
      console.log("liarInput")
    })
    .catch((error) => {
      console.error(error)
    })
  liarInput.value = ""
  liarInputNow.value = false
}
// const endGame = function (result) {
//   gameSet.isGameNow = 0
//   gameSet.gameResultModal = true
//   result = Boolean(result)
//   console.log(result)
//   if (result) {
//     if (gameSet.liar === state.myUserName) {
//       gameSet.gameResult = "니가 졌어"
//     } else {
//       gameSet.gameResult = "이겼습니다."
//     }
//   } else {
//     console.log("여기까지 온거 아냐?")
//     if (gameSet.liar === state.myUserName) {
//       gameSet.gameResult = "이겼습니다"
//     } else {
//       gameSet.gameResult = "니가 졌어."
//     }
//   }
// }
</script>

<style scoped>
.video-list-area {
  width: 100%;
  min-width: 35rem;
}
</style>
