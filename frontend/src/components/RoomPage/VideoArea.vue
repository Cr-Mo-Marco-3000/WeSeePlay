<template>
  <div class="video-area" :class="{ 'video-area-on-sidebar': isSide }">
    <!-- isGameMode가 참이면 GameVideo가 나오게 하고, false라면 MeetingVideo가 나오게 짰어-->
    <CallMyNameVideo
      v-if="isGameMode === 3"
      :setting="callMyNamegameSet"
      :session="state.session"
      :gameIdx="callMyNameGameIdx"
      :coolDown="callMyNameCoolDown"
    />
    <LiarGameVideo
      v-if="isGameMode === 2"
      :isSide="isSide"
      :roomId="roomId"
      :setting="gameSet"
      :session="state.session"
      :isLiar="isLiar"
      :tmpNum="tmpNum"
      :tmpvote="tmpvote"
      :tmpUserList="tmpUserList"
      :tmpliarInputModal="tmpliarInputModal"
      :tmpGameResultModal="tmpGameResultModal"
      :tmpGameResult="tmpGameResult"
      :liargameNicknameList="liargameNicknameList"
    />
    <MeetingVideo
      v-if="isGameMode === 1"
      :users="userList"
      :isSide="isSide"
      :publisher="state.publisher"
      :subscribers="state.subscribers"
    />
  </div>

  <CategoryModal
    v-if="selectingCategory"
    @close="selectingCategory = false"
    @liarGameStart="gameStart"
  />
  <LiarWaitingModal
    v-if="isLiarWaiting"
    :liar="liargameLiar"
    :liarStream="liarStream"
    @close="isLiarWaiting = false"
  />

  <LiarResultModal
    v-if="tmpGameResultModal"
    :whoWin="tmpGameResult"
    :liar="liargameLiar"
    :gameSet="gameSet"
    :suggestion="resultSuggestion"
    :liarInput="liarIncorrectInput"
    :liarStream="liarStream"
    @close="tmpGameResultModal = false"
  />

  <CallmynameResultModal
    v-if="callMyNamegameSet.gameResultModal"
    @close="callMyNamegameSet.gameResultModal = false"
    :winner="callMyNameGameResultWinner"
    :isWin="callMyNameGameResult"
    :suggestion="callMyNameMyName"
    :winnerVideo="callMyNameGameWinnerVideo"
    :myEmail="state.myUserName"
  />
</template>

<script setup>
import CallMyNameVideo from "./game/callmyname/VideoList.vue"
import LiarGameVideo from "./game/liargame/VideoList.vue"
import MeetingVideo from "./meeting/VideoList.vue"
import CategoryModal from "@/components/RoomPage/game/liargame/modal/CategoryModal.vue"
import LiarWaitingModal from "@/components/RoomPage/game/liargame/modal/LiarWaitingModal.vue"
import LiarResultModal from "@/components/RoomPage/game/liargame/modal/LiarResultModal.vue"
import CallmynameResultModal from "@/components/RoomPage/game/callmyname/modal/CallmynameResultModal.vue"

import { ref, defineProps, watchEffect, defineEmits } from "vue"
import store from "@/store"
import { useRouter } from "vue-router"
import { useStore } from "vuex"
import api from "@/api/api"
import axios from "axios"

//---------------------openvidu import-----------------------------------

import $axios from "axios"
import { reactive, onBeforeUnmount, onMounted } from "vue"
import { OpenVidu } from "openvidu-browser"
import Swal from "sweetalert2"
// import UserVideo from "./components/UserVideo.vue"

$axios.defaults.headers.post["Content-Type"] = "application/json"
$axios.defaults.headers.post["Access-Control-Allow-Origin"] = "*"

// 다른 파일에서와는 다르게, 얘가 useStore임
const usestore = useStore()
const emit = defineEmits(["tbs", "tbl", "heygettopbar", "returnLiar"])

const isGameMode = ref(1)
const router = useRouter()

const initSetting = function () {
  liargameNicknameList.value = []
  liargameLiar.value = ""
  gameSet = reactive({
    gameIdx: 0,
    isGameNow: 0,
    suggestion: "",
    maxRound: 0,
    liar: "",
    passedTurn: 0,
    passedRound: 0,
    gameUserList: [],
    gameUserOrder: [],
    liarInput: "",
    liarInputModal: false,
    isVoteNow: false,
    gameResult: false,
    gameResultModal: false,
    votingProcess: {},
    skipNum: 0,
    voteNum: 0,
  })
}
//////////////////////////////////////////////////
const callMyNameGameIdx = ref(0)
const callMyNameCoolDown = ref(3)
const callMyNameMyName = ref("")
const callMyNameGameResultWinner = ref("")
const callMyNameGameWinnerVideo = ref()
const callMyNameGameResult = ref(1)
let callMyNamegameSet = reactive({
  isGameNow: 0,
  suggestion: [],
  maxRound: 0,
  targetPlayer: [],
  nontargetPlayer: [],
  passedTurn: 0,
  passedRound: 0,
  gameUserOrder: [],
  gameResultModal: false,
  userList: [],
})
const callMyNameInitSetting = function () {
  callMyNameGameIdx.value = 0
  callMyNameCoolDown.value = 3
  callMyNameMyName.value = ""
  callMyNameGameResultWinner.value = ""
  callMyNameGameResult.value = 1
  callMyNamegameSet = reactive({
    isGameNow: 0,
    suggestion: [],
    maxRound: 0,
    targetPlayer: [],
    nontargetPlayer: [],
    passedTurn: 0,
    passedRound: 0,
    gameUserOrder: [],
    gameResultModal: false,
    userList: [],
  })
}
const callmyNameGameStart = async function () {
  callMyNameCoolDown.value = 3
  callMyNameGameIdx.value = 0
  callMyNamegameSet.userList = []
  callMyNamegameSet.targetPlayer = []
  callMyNamegameSet.nontargetPlayer = []
  callMyNamegameSet.gameUserOrder = []

  state.subscribers.forEach(function (element) {
    callMyNamegameSet.userList.push(
      JSON.parse(element.stream.connection.data).clientData
    )
    // gameSet.gameUserOrder.push(element)
  })
  callMyNamegameSet.userList.push(state.myUserName)
  callMyNamegameSet.maxRound = 5
  const nameApiRequest = await api.game.getcallmyname()
  callMyNamegameSet.suggestion = nameApiRequest.data.wordList
  shuffle(callMyNamegameSet.userList)
  state.session
    .signal({
      data: callMyNamegameSet.suggestion.join(),
      to: [],
      type: "callMyNamegameSuggestion",
    })
    .then(() => {
      console.log("callMyNamegameSuggestion")
    })
    .catch((error) => {
      console.error(error)
    })

  state.session
    .signal({
      data: callMyNamegameSet.userList.join(),
      to: [],
      type: "callMyNamegameOrder",
    })
    .then(() => {
      console.log("callMyNamegameOrder")
    })
    .catch((error) => {
      console.error(error)
    })
  await api.room.editRoom(state.mySessionId, { game: 3 })
  state.session
    .signal({
      data: "3," + String(callMyNamegameSet.maxRound),
      to: [],
      type: "callMyNamegameStart",
    })
    .then(() => {
      console.log("callMyNamegame Start!")
    })
    .catch((error) => {
      console.error(error)
    })
}
//////////////////////////////////////////////////

// 게임 정보
const isLiarWaiting = ref(false)
const liargameLiar = ref("")
const selectingCategory = ref(false)
const liarIncorrectInput = ref("")
const resultSuggestion = ref("")
const liarStream = ref()
const liargameNicknameList = ref([])
let gameSet = reactive({
  gameIdx: 0,
  isGameNow: 0,
  suggestion: "",
  maxRound: 0,
  liar: "",
  passedTurn: 0,
  passedRound: 0,
  gameUserList: [],
  gameUserOrder: [],
  liarInput: "",
  liarInputModal: false,
  isVoteNow: false,
  gameResult: false,
  gameResultModal: false,
  votingProcess: {},
  skipNum: 0,
  voteNum: 0,
})

function shuffle(array) {
  for (let index = array.length - 1; index > 0; index--) {
    // 무작위 index 값을 만든다. (0 이상의 배열 길이 값)
    const randomPosition = Math.floor(Math.random() * (index + 1))

    // 임시로 원본 값을 저장하고, randomPosition을 사용해 배열 요소를 섞는다.
    const temporary = array[index]
    array[index] = array[randomPosition]
    array[randomPosition] = temporary
  }
}
function pickLiar(array) {
  const randomPosition = Math.floor(Math.random() * array.length)
  return array[randomPosition]
}
const gameStart = async function (data) {
  await api.room.editRoom(state.mySessionId, { game: 2 })
  gameSet.gameUserOrder = [] // 초기화
  gameSet.gameUserList.value = []
  state.subscribers.forEach(function (element) {
    gameSet.gameUserList.value.push(
      JSON.parse(element.stream.connection.data).clientData
    )
  })
  gameSet.gameUserList.value.push(state.myUserName)
  gameSet.maxRound = 5
  gameSet.suggestion = data.suggestion
  const liarCategory = data.category
  resultSuggestion.value = data.suggestion
  shuffle(gameSet.gameUserList.value)
  gameSet.liar = pickLiar(gameSet.gameUserList.value)
  state.session
    .signal({
      data: gameSet.liar,
      to: [],
      type: "whoIsLiar",
    })
    .then(() => {
      console.log("who is Liar")
    })
    .catch((error) => {
      console.error(error)
    })

  state.session
    .signal({
      data: gameSet.gameUserList.value.join(),
      to: [],
      type: "gameOrder",
    })
    .then(() => {
      console.log("gameOrder")
    })
    .catch((error) => {
      console.error(error)
    })

  const liarGameData = {
    suggestion: gameSet.suggestion,
    maxRound: gameSet.maxRound,
    category: liarCategory,
  }
  state.session
    .signal({
      data: JSON.stringify(liarGameData),
      to: [],
      type: "gameStart",
    })
    .then(() => {
      console.log("game Start!")
    })
    .catch((error) => {
      console.error(error)
    })
}
const userList = ref([]) // Component에 넘겨줄 user list
const props = defineProps({
  isLiarGameStart: {
    type: Number,
  },
  isCallMyStart: {
    type: Number,
  },
  isQuit: {
    type: Number,
  },
  isSide: {
    type: Boolean,
    required: true,
  },
  roomId: {
    type: String,
  },
  isVideoOpen: {
    type: Boolean,
    required: true,
  },
  isAudeoOpen: {
    type: Boolean,
    required: true,
  },
  chatData: {
    type: Object,
  },
})
// 강제 종료
watchEffect(async () => {
  if (props.isQuit === 2) {
    state.session
      .signal({
        data: 1,
        to: [],
        type: "quitgame",
      })
      .then(() => {
        console.log("quitgame")
      })
      .catch((error) => {
        console.error(error)
      })
  }
  emit("heygettopbar")
})
//라이어 게임 시작
watchEffect(() => {
  if (props.isLiarGameStart === 2) {
    // console.log("일단 찾아보자", isGameMode.value)
    // if (parseInt(isGameMode.value) !== 1) {
    //   Swal.fire({
    //     title: "이미 게임중입니다.",
    //     text: `진행 중인 게임을 먼저 종료해 주세요.`,
    //     icon: "info",
    //   })
    // } else
    if (state.subscribers.length > 1) {
      selectingCategory.value = true
    } else {
      Swal.fire({
        title: "Liar Game 인원수 부족!",
        text: `3인 이상 플레이 가능`,
        icon: "info",
      })
    }
    emit("returnLiar")
  }
})
// 콜 마이 네임 시작 isCallMyStart
watchEffect(() => {
  // if (isGameMode.value !== 1) {
  //   Swal.fire({
  //     title: "이미 게임중입니다.",
  //     text: `진행 중인 게임을 먼저 종료해 주세요.`,
  //     icon: "info",
  //   })
  // } else
  if (props.isCallMyStart === 2) {
    if (state.subscribers.length > 1) {
      callmyNameGameStart()
    } else {
      Swal.fire({
        title: "Call My Name 인원수 부족!",
        text: `3인 이상 플레이 가능`,
        icon: "info",
      })
    }
    emit("returnCallmyname")
  }
})
// 이쪽은 샘플로 넣을 User 수 정하는 거 (최종본에는 지울 내용)
const customNumber = ref(10)
for (let i = 1; i < customNumber.value; i++) {
  const id = i
  const nickname = String.fromCharCode(i + 64)
  const onVideo = false
  userList.value.push({ id, nickname, onVideo })
}

// --------------------------------------open vidu-----------------------------------------------
// 여기서 부턴 openVidu
const OPENVIDU_SERVER_URL = "https://" + "i7a501.p.ssafy.io" + ":8443"
// const OPENVIDU_SERVER_URL = "https://" + location.hostname + ":4443"
const OPENVIDU_SERVER_SECRET = "MY_SECRET"
const state = reactive({
  OV: undefined,
  session: undefined,
  mainStreamManager: undefined,
  publisher: undefined,
  subscribers: [],
  mySessionId: props.roomId,
  myUserName: undefined,
  playersNum: 1,
})

// toggle
const hostEmail = ref(store.getters.getRoomInfo.hostEmail)
watchEffect(() => {
  if (state.publisher) {
    state.publisher.publishVideo(props.isVideoOpen)
  }
  if (state.publisher) {
    state.publisher.publishAudio(props.isAudeoOpen)
  }
  hostEmail.value = store.getters.getRoomInfo.hostEmail
})

const endGame = async function (result) {
  await api.room.editRoom(state.mySessionId, { game: 1 })
  tmpGameResult.value = result
  tmpGameResultModal.value = true

  initSetting()
  isGameMode.value = 1
  store.dispatch("editRoomGame", 1)
}

const tmpNum = ref(0)
const tmpvote = ref(false)
const tmpliarInputModal = ref(false)
const tmpUserList = ref([])
const tmpGameResultModal = ref(false)
const tmpGameResult = ref(false)

const isLiar = ref(false)
const topBarSuggestion = ref("")

const joinSession = () => {
  // --- Get an OpenVidu object ---
  state.mySessionId = props.roomId
  state.OV = new OpenVidu()
  // --- Init a session ---
  state.session = state.OV.initSession()
  // On every new Stream received...
  state.session.on("streamCreated", ({ stream }) => {
    const subscriber = state.session.subscribe(stream)
    state.subscribers.push(subscriber)
    state.playersNum += 1
    // 여기서 플레이어 숫자 계산
  })
  // 메시지 캐치하는 부분
  state.session.on("signal:this is chat", (e) => {
    const rawMessage = JSON.parse(e.data)
    const { nickname, message } = rawMessage

    const sendTime = new Date().toLocaleTimeString().slice(0, -3)
    const messageObject = {
      userNickname: nickname,
      content: message,
      sendTime: sendTime,
      isDifferentNameAndTime: true,
    }

    const exMessage = usestore.getters.getChattings.at(-1)

    if (
      exMessage &&
      exMessage.userNickname === nickname &&
      exMessage.sendTime === sendTime
    ) {
      messageObject.isDifferentNameAndTime = false
    }
    usestore.dispatch("addMessage", messageObject)
  })

  // On every Stream destroyed...
  state.session.on("streamDestroyed", ({ stream }) => {
    const index = state.subscribers.indexOf(stream.streamManager, 0)
    if (index >= 0) {
      state.subscribers.splice(index, 1)
    }
  })
  // On every asynchronous exception...
  state.session.on("exception", ({ exception }) => {
    console.warn(exception)
  })
  state.session.on("signal:get out of this room!", (data) => {
    if (state.myUserName !== data.data) {
      setTimeout(() => {
        let isHostLeave = 1
        state.subscribers.forEach(function (element) {
          if (
            data.data === JSON.parse(element.stream.connection.data).clientData
          ) {
            isHostLeave = 0
          }
        })
        if (isHostLeave) {
          router.push({ name: "mainpage" })
        }
      }, 5000)
    }
  })
  //call my name game
  state.session.on("signal:callMyNamegameStart", async (data) => {
    let info = data.data.split(",")
    callMyNameGameIdx.value = 0
    callMyNameCoolDown.value = 3
    callMyNamegameSet.maxRound = parseInt(info[1])
    isGameMode.value = parseInt(info[0])
    store.dispatch("editRoomGame", 3)
  })
  state.session.on("signal:callMyNamegameSuggestion", (data) => {
    callMyNamegameSet.suggestion = data.data.split(",")
  })
  state.session.on("signal:callMyNamegameOrder", async (data) => {
    callMyNamegameSet.userList = data.data.split(",")
    callMyNamegameSet.targetPlayer = callMyNamegameSet.userList.slice(0, 3)
    callMyNamegameSet.nontargetPlayer = callMyNamegameSet.userList.slice(3)
    callMyNamegameSet.gameUserOrder = []
    for (let p = 0; p < callMyNamegameSet.userList.length; p++) {
      let flag = 0
      state.subscribers.forEach(function (element) {
        if (
          callMyNamegameSet.targetPlayer[p] ===
          JSON.parse(element.stream.connection.data).clientData
        ) {
          callMyNamegameSet.gameUserOrder.push(element)
          flag = 1
        }
      })
      if (!flag) {
        callMyNamegameSet.gameUserOrder.push(state.publisher)
      }
    }
  })
  state.session.on("signal:Call my name, idx up", () => {
    callMyNameGameIdx.value += 1
    if (callMyNameGameIdx.value >= 3) {
      callMyNameGameIdx.value = 0
      callMyNamegameSet.passedRound += 1
    }
    callMyNamegameSet.passedTurn += 1
    callMyNameCoolDown.value -= 1
    // 모든 사용자 gameidx증가
  })
  state.session.on("signal:callMynameChooseCorrect", async (info) => {
    callMyNameGameResult.value = 1
    let data = parseInt(info.data)
    if (!data) {
      callMyNameGameResultWinner.value = callMyNamegameSet.userList[0]
      callMyNameGameWinnerVideo.value = callMyNamegameSet.gameUserOrder[0]
    } else {
      callMyNameGameResultWinner.value = callMyNamegameSet.userList[data]
      callMyNameGameWinnerVideo.value = callMyNamegameSet.gameUserOrder[data]
    }
    for (let i = 0; i < 3; i++) {
      if (callMyNamegameSet.userList[i] === state.myUserName) {
        callMyNameMyName.value = callMyNamegameSet.suggestion[i]
      }
      if (
        callMyNamegameSet.userList[i] === state.myUserName &&
        state.myUserName !== callMyNameGameResultWinner.value
      ) {
        callMyNameGameResult.value = 3
      }
    }

    if (state.myUserName === callMyNameGameResultWinner.value) {
      callMyNameGameResult.value = 2
    }
    if (state.myUserName === hostEmail.value) {
      await api.room.editRoom(state.mySessionId, { game: 1 })
    }

    callMyNamegameSet.gameResultModal = true
    isGameMode.value = 1
    store.dispatch("editRoomGame", 1)
  })
  state.session.on("signal:callMynamechooseIncorrect", () => {})

  // liar Game
  state.session.on("signal:gameStart", (data) => {
    const { suggestion, maxRound, category } = JSON.parse(data.data)

    gameSet.suggestion = suggestion
    topBarSuggestion.value = suggestion
    resultSuggestion.value = suggestion
    emit("tbs", topBarSuggestion.value)
    console.log("%c maxRound는! ", "color: white; background: black", maxRound)
    gameSet.maxRound = Number(maxRound)
    isGameMode.value = 2
    gameSet.isGameNow = true

    Swal.fire({
      title: "Liar Game 시작!",
      text: `카테고리: ${category}`,
      icon: "info",
    })

    store.dispatch("editRoomGame", 2)
  })
  state.session.on("signal:whoIsLiar", (data) => {
    liargameLiar.value = data.data
    gameSet.liar = data.data
    if (gameSet.liar == state.myUserName) {
      isLiar.value = true
    } else {
      isLiar.value = false
    }
    if (isLiar.value) {
      emit("tbl", 1)
    } else {
      emit("tbl", 2)
    }
  })
  state.session.on("signal:gameOrder", (data) => {
    gameSet.gameUserList.value = data.data.split(",")
    tmpUserList.value = gameSet.gameUserList.value
    for (let p = 0; p < gameSet.gameUserList.value.length; p++) {
      let flag = 0
      state.subscribers.forEach(function (element) {
        if (
          gameSet.gameUserList.value[p] ===
          JSON.parse(element.stream.connection.data).clientData
        ) {
          // console.log(JSON.parse(element.stream.connection.data).clientData)
          gameSet.gameUserOrder.push(element)
          liargameNicknameList.value.push(
            JSON.parse(element.stream.connection.data).userNickname
          )
          flag = 1
        }
      })
      if (!flag) {
        gameSet.gameUserOrder.push(state.publisher)
        liargameNicknameList.value.push(
          JSON.parse(state.publisher.stream.connection.data).userNickname
        )
      }
    }
    for (let q = 0; q < gameSet.gameUserList.value.length; q++) {
      if (gameSet.gameUserList.value[q] === gameSet.liar) {
        liarStream.value = gameSet.gameUserOrder[q]
      }
    }
  })
  state.session.on("signal:gameIdxUp", () => {
    let flag = 0
    gameSet.gameIdx += 1
    if (gameSet.gameIdx >= gameSet.gameUserList.value.length) {
      gameSet.gameIdx = 0
      gameSet.passedRound += 1
      flag = 1
    }
    tmpNum.value = gameSet.gameIdx
    gameSet.passedTurn += 1
    // 모든 사용자 gameidx증가
    if (flag && gameSet.passedRound > 1) {
      // 만약에 라운드가 넘어갔다면 투표
      gameSet.isVoteNow = true
      tmpvote.value = gameSet.isVoteNow
      gameSet.isVoteNow = false
    }
  })
  state.session.on("signal:heIsLiar", (data) => {
    const suspect = data.data
    if (suspect == "skip") {
      gameSet.skipNum += 1
    } else {
      gameSet.voteNum += 1
      if (suspect in gameSet.votingProcess) {
        // 이미 있으면,,
        gameSet.votingProcess[suspect] += 1
      } else {
        // 없으면 추가
        gameSet.votingProcess[suspect] = 1
      }
    }
    //투표 끝
    let maxKey
    let maxValue = 0
    if (
      gameSet.gameUserList.value.length ==
      gameSet.skipNum + gameSet.voteNum
    ) {
      // 스킵 된 경우  alert 창 여기에다가 띄우면 됨
      if (gameSet.skipNum >= gameSet.voteNum) {
        if (gameSet.passedRound < 4) {
          Swal.fire({
            title: `${gameSet.passedRound + 1}번째 라운드로 넘어갑니다.`,
            text: "라이어를 찾지 못해 스킵 되었습니다.",
            icon: "info",
          })
        }
        if (gameSet.passedRound === 4) {
          Swal.fire({
            title: `마지막 라운드로 넘어갑니다.`,
            text: "라이어를 찾지 못해 스킵 되었습니다.",
            icon: "info",
          })
        }
      }
      if (gameSet.skipNum < gameSet.voteNum) {
        // 스킵 되지 않은 경우
        let isSame = ref(false)
        for (const [key, value] of Object.entries(gameSet.votingProcess)) {
          if (value > maxValue) {
            maxValue = value
            maxKey = key
            isSame.value = false
          } else if (value === maxValue) {
            isSame.value = true
          }
        }
        if (isSame.value === true) {
          maxValue = 0
          gameSet.skipNum += 1000
          if (gameSet.passedRound < 4) {
            Swal.fire({
              title: `${gameSet.passedRound + 1}번째 라운드로 넘어갑니다.`,
              text: "라이어를 찾지 못해 스킵 되었습니다.",
              icon: "info",
            })
          }
          if (gameSet.passedRound === 4) {
            Swal.fire({
              title: `마지막 라운드로 넘어갑니다.`,
              text: "라이어를 찾지 못해 스킵 되었습니다.",
              icon: "info",
            })
          }
        }
      }
      // 스킵 되며 최대 턴을 찍은 경우
      let isEnd = false
      if (
        gameSet.passedRound == gameSet.maxRound &&
        gameSet.skipNum >= gameSet.voteNum
      ) {
        isEnd = true
      }
      if (maxValue) {
        // 여기에서 maxKey가 가장 많이 지목 당한 사람
        console.log("가장 많이 지목 당한 사람", maxKey)
        if (maxKey == gameSet.liar) {
          if (state.myUserName == gameSet.liar) {
            gameSet.liarInputModal = true
            tmpliarInputModal.value = gameSet.liarInputModal
            gameSet.liarInputModal = false
          } else {
            isLiarWaiting.value = true
          }
        } else {
          isEnd = true
          Swal.fire({
            title: "라이어를 찾지 못했습니다!",
            text: `잘못된 대상이 지목 되었습니다.`,
            icon: "info",
          })
        }
      }
      if (isEnd) {
        endGame(false)
      }
      tmpvote.value = gameSet.isVoteNow
      gameSet.votingProcess = {}
      gameSet.skipNum = 0
      gameSet.voteNum = 0
    }
  })
  state.session.on("signal:liarInput", (data) => {
    tmpliarInputModal.value = gameSet.liarInputModal
    const [result, inputValue] = [...data.data.split(",")]
    liarIncorrectInput.value = inputValue
    isLiarWaiting.value = false
    endGame(JSON.parse(result))
  })
  state.session.on("signal:quitgame", async () => {
    if (state.myUserName === hostEmail.value) {
      await api.room.editRoom(state.mySessionId, { game: 1 })
    }
    initSetting()
    callMyNameInitSetting()
    isGameMode.value = 1
    store.dispatch("editRoomGame", 1)
  })
  // 'getToken' method is simulating what your server-side should do.
  // 'token' parameter should be retrieved and returned by your own backend
  getToken(state.mySessionId).then((token) => {
    state.session
      .connect(token, {
        clientData: state.myUserName,
        userNickname: store.getters.me.userNickname,
      })
      .then(() => {
        // --- Get your own camera stream with the desired properties ---
        let publisher = state.OV.initPublisher(undefined, {
          audioSource: undefined, // The source of audio. If undefined default microphone
          videoSource: undefined, // The source of video. If undefined default webcam
          publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
          publishVideo: true, // Whether you want to start publishing with your video enabled or not
          resolution: "600x320", // The resolution of your video
          frameRate: 30, // The frame rate of your video
          insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
          mirror: false, // Whether to mirror your local video or not
        })
        state.mainStreamManager = publisher
        state.publisher = publisher
        state.session.publish(publisher)
      })
      .catch((error) => {
        console.log(
          "There was an error connecting to the session:",
          error.code,
          error.message
        )
      })
  })
  window.addEventListener("beforeunload", leaveSession)
}
const leaveSession = () => {
  // --- Leave the session by calling 'disconnect' method over the Session object ---
  if (state.myUserName === hostEmail.value) {
    state.session
      .signal({
        data: hostEmail.value,
        to: [],
        type: "get out of this room!",
      })
      .then(() => {
        console.log("get out of this room!")
      })
      .catch((error) => {
        console.error(error)
      })
  }
  if (state.session) state.session.disconnect()

  state.session = undefined
  state.mainStreamManager = undefined
  state.publisher = undefined
  state.subscribers = []
  state.OV = undefined

  window.removeEventListener("beforeunload", leaveSession)
}
// const updateMainVideoStreamManager = (stream) => {
//   if (state.mainStreamManager === stream) return
//   state.mainStreamManager = stream
// }
const getToken = (mySessionId) => {
  return createSession(mySessionId).then((sessionId) => createToken(sessionId))
}
const createSession = (sessionId) => {
  return new Promise((resolve, reject) => {
    $axios
      .post(
        `${OPENVIDU_SERVER_URL}/openvidu/api/sessions`,
        JSON.stringify({
          customSessionId: sessionId,
        }),
        {
          auth: {
            username: "OPENVIDUAPP",
            password: OPENVIDU_SERVER_SECRET,
          },
        }
      )
      .then((response) => response.data)
      .then((data) => resolve(data.id))
      .catch((error) => {
        if (error.response.status === 409) {
          resolve(sessionId)
        } else {
          console.warn(
            `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`
          )
          if (
            window.confirm(
              `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`
            )
          ) {
            location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`)
          }
          reject(error.response)
        }
      })
  })
}
const createToken = (sessionId) => {
  return new Promise((resolve, reject) => {
    $axios
      .post(
        `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`,
        {},
        {
          auth: {
            username: "OPENVIDUAPP",
            password: OPENVIDU_SERVER_SECRET,
          },
        }
      )
      .then((response) => response.data)
      .then((data) => resolve(data.token))
      .catch((error) => reject(error.response))
  })
}

//---------------------------openVidu Chatting--------------------------------
// OpenVidu 채팅 기능
// const chattingList = ref([])
// const inputMsg = ref("")

watchEffect(() => {
  // 채팅용입니다.
  if (props.chatData.chatCount) {
    sendMsg(props.chatData.chatData)
  }
})
const sendMsg = (data) => {
  state.session
    .signal({
      data: JSON.stringify(data),
      to: [],
      type: "this is chat",
    })
    .then(() => {
      console.log("Chat!")
    })
    .catch((error) => {
      console.error(error)
    })
}

//-----------------------------Liar Game--------------------------------

// beforeunmount
onBeforeUnmount(() => {
  leaveSession()
})
// onBeforeMount
onMounted(async () => {
  const userId = ref("")
  try {
    console.log(localStorage.getItem("token"))
    const response = await axios({
      url: api.users.checkToken(),
      method: "GET",
      headers: { authorization: "Bearer " + localStorage.getItem("token") },
    })
    userId.value = response.data.userEmail
    state.myUserName = userId.value
  } catch (err) {
    console.log(err)
  }

  joinSession()
  // joinGameSession()
})
</script>

<style scoped>
.video-area {
  position: absolute;
  left: 3rem;
  top: 3rem;
  bottom: 5rem;
  display: flex;
  align-items: center;
  right: 3rem;
}

/* sidebar가 켜지고 꺼짐에 따라서, position absolute로 지정한 오른쪽에 여백이 증가하고 감소한다 */
/* col-3을 쓰면, 화면 사이즈의 전체 비율에 따라 조절하게 되기 때문에 레이아웃이 어그러지더라 */
.video-area-on-sidebar {
  right: 26rem;
}
</style>
