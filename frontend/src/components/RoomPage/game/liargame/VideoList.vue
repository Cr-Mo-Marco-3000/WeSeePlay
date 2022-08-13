<template>
  <div class="row video-list-area">
    <button v-if="!gameSet.liar" @click="hostStart">시작!</button>
    <div>
      <MainVideo
        v-if="gameIdx == 0"
        class="self-center"
        :class="isSide ? 'col-7' : 'col-5'"
        :user="gameSet.gameUserOrder[gameIdx]"
        :gameIdx="gameIdx"
        :myGameIdx="myGameIdx"
        @next="gameIdxUp"
      />
      <MainVideo
        v-if="gameIdx == 1"
        class="self-center"
        :class="isSide ? 'col-7' : 'col-5'"
        :user="gameSet.gameUserOrder[gameIdx]"
        :gameIdx="gameIdx"
        :myGameIdx="myGameIdx"
        @next="gameIdxUp"
      />
      <MainVideo
        v-if="gameIdx == 2"
        class="self-center"
        :class="isSide ? 'col-7' : 'col-5'"
        :user="gameSet.gameUserOrder[gameIdx]"
        :gameIdx="gameIdx"
        :myGameIdx="myGameIdx"
        @next="gameIdxUp"
      />
      <MainVideo
        v-if="gameIdx == 3"
        class="self-center"
        :class="isSide ? 'col-7' : 'col-5'"
        :user="gameSet.gameUserOrder[gameIdx]"
        :gameIdx="gameIdx"
        :myGameIdx="myGameIdx"
        @next="gameIdxUp"
      />
      <MainVideo
        v-if="gameIdx == 4"
        class="self-center"
        :class="isSide ? 'col-7' : 'col-5'"
        :user="gameSet.gameUserOrder[gameIdx]"
        :gameIdx="gameIdx"
        :myGameIdx="myGameIdx"
        @next="gameIdxUp"
      />
      <MainVideo
        v-if="gameIdx == 5"
        class="self-center"
        :class="isSide ? 'col-7' : 'col-5'"
        :user="gameSet.gameUserOrder[gameIdx]"
        :gameIdx="gameIdx"
        :myGameIdx="myGameIdx"
        @next="gameIdxUp"
      />
      <MainVideo
        v-if="gameIdx == 6"
        class="self-center"
        :class="isSide ? 'col-7' : 'col-5'"
        :user="gameSet.gameUserOrder[gameIdx]"
        :gameIdx="gameIdx"
        :myGameIdx="myGameIdx"
        @next="gameIdxUp"
      />
      <MainVideo
        v-if="gameIdx == 7"
        class="self-center"
        :class="isSide ? 'col-7' : 'col-5'"
        :user="gameSet.gameUserOrder[gameIdx]"
        :gameIdx="gameIdx"
        :myGameIdx="myGameIdx"
        @next="gameIdxUp"
      />
      <MainVideo
        v-if="gameIdx == 8"
        class="self-center"
        :class="isSide ? 'col-7' : 'col-5'"
        :user="gameSet.gameUserOrder[gameIdx]"
        :gameIdx="gameIdx"
        :myGameIdx="myGameIdx"
        @next="gameIdxUp"
      />
      <MainVideo
        v-if="gameIdx == 9"
        class="self-center"
        :class="isSide ? 'col-7' : 'col-5'"
        :user="gameSet.gameUserOrder[gameIdx]"
        :gameIdx="gameIdx"
        :myGameIdx="myGameIdx"
        @next="gameIdxUp"
      />
      <MainVideo
        v-if="gameIdx == 10"
        class="self-center"
        :class="isSide ? 'col-7' : 'col-5'"
        :user="gameSet.gameUserOrder[gameIdx]"
        :gameIdx="gameIdx"
        :myGameIdx="myGameIdx"
        @next="gameIdxUp"
      />
      <MainVideo
        v-if="gameIdx == 11"
        class="self-center"
        :class="isSide ? 'col-7' : 'col-5'"
        :user="gameSet.gameUserOrder[gameIdx]"
        :gameIdx="gameIdx"
        :myGameIdx="myGameIdx"
        @next="gameIdxUp"
      />
    </div>
    <AllVideo
      class="self-center"
      :class="isSide ? 'col-5' : 'col-7'"
      :users="users"
      :gameUserOrder="gameSet.gameUserOrder"
      :isSide="isSide"
    />
    <!-- 일반인인지 라이어인지, 제시어 알려 주는 부분 -->
    <div v-if="gameSet.liar == userId">당신은 라이어 입니다.</div>
    <div v-else>제시어는 {{ gameSet.suggestion }} 입니다.</div>
  </div>

  <q-dialog v-model="gameSet.isVoteNow" persistent>
    <q-card style="min-width: 350px">
      <q-card-section>
        <div v-for="pl in userList" :key="pl">
          <p @click="heIsLiar(pl)">
            {{ pl }}
          </p>
        </div>
        <div @click="heIsLiar('skip')">skip</div>
        <div @click="gameSet.isVoteNow = false">끝</div>
      </q-card-section>
    </q-card>
  </q-dialog>

  <q-dialog v-model="gameSet.liarInputModal" persistent>
    <q-card style="min-width: 350px">
      <q-card-section>
        <div>
          <input type="text" v-model="gameSet.liarInput" />
          <button @click="liarFinalInput()">입력하</button>
        </div>

        <div @click="gameSet.liarInputModal = false">끝</div>
      </q-card-section>
    </q-card>
  </q-dialog>

  <q-dialog v-model="gameSet.gameResultModal" persistent>
    <q-card style="min-width: 350px">
      <q-card-section>
        <div>
          <p>
            {{ gameSet.gameResult }}
          </p>
        </div>

        <div @click="gameSet.gameResultModal = false">끝</div>
      </q-card-section>
    </q-card>
  </q-dialog>
</template>

<script setup>
import io from "socket.io-client"
import { ref, reactive, defineProps, onBeforeMount, watchEffect } from "vue"
import MainVideo from "./MainVideo.vue"
import AllVideo from "./AllVideo.vue"
import store from "@/store"

const socket = io("http://localhost:3000", {
  transports: ["websocket"],
  withCredentials: true,
})

const props = defineProps({
  isSide: {
    type: Boolean,
    required: true,
  },
  users: {
    type: Array,
    required: true,
  },
  roomId: {
    type: String,
    required: true,
  },
  publisher: {
    type: Object,
    required: true,
  },
  subscribers: {
    type: Array,
    required: true,
  },
})
// const speaker = ref(0)

// const nextPlayer = function () {
//   if (speaker.value + 1 === props.users.length) {
//     speaker.value = 0
//     return
//   }
//   speaker.value += 1
// }

// 역기서 부터 게임 입니다.
const userId = store.getters.me.userEmail
//여긴 game web socket
const userList = ref()
const usersNum = ref(0)
const gameIdx = ref(0)
const myGameIdx = ref(0)
const gameSet = reactive({
  isGameNow: 0,
  suggestion: "",
  maxRound: 0,
  liar: "",
  passedTurn: 0,
  passedRound: 0,
  gameUserOrder: [],
  liarInput: "",
  liarInputModal: false,
  isVoteNow: false,
  gameResult: false,
  gameResultModal: false,
})
const joinGameSession = function () {
  socket.emit("joinGame", {
    user: userId,
    session: props.roomId,
  })
}
const hostStart = function () {
  socket.emit("hostStart", props.roomId)
}
socket.on("letGameStart", (session) => {
  if (session == props.roomId) {
    console.log("방 초기화는 되었니?")
    gameSet.gameUserOrder = [] // 초기화
    socket.emit("gameStart", props.roomId) // 나중에 subject(제시어 범위), 최대 턴 수
  }
})
// const gameStart = function () {
//   console.log("게임 시작합니다.")
//   gameSet.gameUserOrder = [] // 초기화
//   socket.emit("gameStart", props.roomId) // 나중에 subject(제시어 범위), 최대 턴 수
// }
socket.on("UALiar", (data) => {
  console.log("UALiar 들어왔나?")
  if (data.session == props.roomId) {
    gameSet.isGameNow = 1
    console.log(data)
    userList.value = data["userList"]
    usersNum.value = data["usersNum"]
    gameSet.suggestion = data["suggestion"]
    gameSet.maxRound = data["maxRound"]
    gameSet.liar = data["liar"]
    gameSet.passedTurn = data["passedTurn"]
    gameSet.passedRound = data["passedRound"]

    if (props.subscribers.length) {
      gameSet.gameUserOrder = []
      for (let p = 0; p < usersNum.value; p++) {
        console.log("반복중", userList.value[p])
        let flag = 0
        props.subscribers.forEach(function (element) {
          console.log(JSON.parse(element.stream.connection.data).clientData)
          if (
            userList.value[p] ===
            JSON.parse(element.stream.connection.data).clientData
          ) {
            console.log(JSON.parse(element.stream.connection.data).clientData)
            gameSet.gameUserOrder.push(element)
            flag = 1
          }
        })
        if (!flag) {
          console.log(userId)
          myGameIdx.value = p
          gameSet.gameUserOrder.push(props.publisher)
        }
      }
      console.log(gameSet.gameUserOrder)
    }
  }
})

const gameIdxUp = function () {
  socket.emit("gameIdxUp", props.roomId)
}
socket.on("gameidxIncreased", (data) => {
  console.log("인덱스가 증가한닷")
  if (props.roomId == data.session) {
    gameIdx.value = data["gameIdx"]
    gameSet.passedTurn = data["passedTurn"]
    gameSet.passedRound = data["passedRound"]
  }
  console.log(gameIdx.value)
})
socket.on("openVoteModal", (info) => {
  console.log("투표 개신", info)
  if (info.session == props.roomId) {
    console.log("투표시작", info.data)
    gameSet.isVoteNow = info.data
  }
})
const heIsLiar = function (suspect) {
  socket.emit("heIsLiar", { suspect: suspect, session: props.roomId })
}
socket.on("liarInput", (session) => {
  console.log("마지막 입력")
  if (session == props.roomId) {
    if (gameSet.liar === userId) {
      gameSet.liarInputModal = true
    }
  }
})
const liarFinalInput = function () {
  socket.emit("finalInput", {
    data: gameSet.liarInput,
    session: props.roomId,
  })
  gameSet.liarInput = ""
  gameSet.liarInputModal = false
}
socket.on("endGame", (info) => {
  let data = info.data
  let current = info.session
  if (current == props.roomId) {
    console.log("게임 끝", data)
    gameSet.isGameNow = 0
    gameSet.gameResultModal = true
    if (data) {
      if (gameSet.liar === userId) {
        gameSet.gameResult = "니가 졌어"
      } else {
        gameSet.gameResult = "이겼습니다."
      }
    } else {
      if (gameSet.liar === userId) {
        gameSet.gameResult = "이겼습니다"
      } else {
        gameSet.gameResult = "니가 졌어."
      }
    }
  }
})
watchEffect(() => {
  console.log("게임 유저 순서", gameSet.gameUserOrder.length)
})

onBeforeMount(() => {
  joinGameSession()
})
</script>

<style scoped>
.video-list-area {
  width: 100%;
  min-width: 35rem;
}
</style>
