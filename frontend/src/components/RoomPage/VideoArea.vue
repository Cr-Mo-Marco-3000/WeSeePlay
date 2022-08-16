<template>
  <div class="video-area" :class="{ 'video-area-on-sidebar': isSide }">
    <!-- 이 버튼 어차피 나중에 날릴 것이므로 -->
    <button
      v-if="isGameMode === 1"
      style="position: absolute; top: 1rem"
      @click="callmyNameGameStart"
    >
      이건 왜 안떠요Game On/Off 이게 콜마네
    </button>
    <!-- 이 버튼 어차피 나중에 날릴 것이므로 -->
    <button
      v-if="isGameMode === 1"
      style="position: absolute; top: 1rem"
      @click="selectingCategory = true"
    >
      Game On/Off
    </button>

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
    :liar="gameSet.liar"
    @close="isLiarWaiting = false"
  />

  <LiarResultModal
    v-if="tmpGameResultModal"
    :whoWin="tmpGameResult"
    :gameSet="gameSet"
    @close="tmpGameResultModal = false"
  />

  <CallmynameResultModal
    v-if="callMyNamegameSet.gameResultModal"
    @close="callMyNamegameSet.gameResultModal = false"
    :winner="callMyNameGameResultWinner"
    :isWin="callMyNameGameResult"
    :suggestion="callMyNameMyName"
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

import { ref, defineProps, watchEffect } from "vue"
import store from "@/store"
import { useRouter } from "vue-router"
import { useStore } from "vuex"
import api from "@/api/api"
import axios from "axios"

//---------------------openvidu import-----------------------------------

import $axios from "axios"
import { reactive, onBeforeUnmount, onMounted } from "vue"
import { OpenVidu } from "openvidu-browser"
// import UserVideo from "./components/UserVideo.vue"

$axios.defaults.headers.post["Content-Type"] = "application/json"
$axios.defaults.headers.post["Access-Control-Allow-Origin"] = "*"

// 다른 파일에서와는 다르게, 얘가 useStore임
const usestore = useStore()

const isGameMode = ref(store.getters.getRoomInfo.game)
const router = useRouter()
const initSetting = function () {
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
const callMyNameGameResult = ref(false)
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
// const callMyNameInitSetting = function () {
//   callMyNameGameIdx = ref(0)
//   callMyNameMyName = ref("")
//   callMyNameCoolDown = ref(3)
//   callMyNamegameSet = reactive({
//     isGameNow: 0,
//     suggestion: [],
//     maxRound: 0,
//     targetPlayer: [],
//     nontargetPlayer: [],
//     passedTurn: 0,
//     passedRound: 0,
//     gameUserOrder: [],
//     gameResult: false,
//     gameResultWinner: "",
//     gameResultModal: false,
//     userList: [],
//   })
// }
const callmyNameGameStart = async function () {
  await api.room.editRoom(state.mySessionId, { game: 3 })
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
  callMyNamegameSet.suggestion = ["아이유", "산타클로스", "뽀로로"]
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
watchEffect(() => {
  isGameMode.value = store.getters.getRoomInfo.game
})
// 게임 정보
const isLiarWaiting = ref(false)
const selectingCategory = ref(false)
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
const gameStart = async function () {
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
  gameSet.suggestion = "사자"
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
  state.session
    .signal({
      data: "2," + gameSet.suggestion + "," + String(gameSet.maxRound),
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
// const toggleAudio = () => {
//   vidoman.publishAudio = !vidoman.publishAudio
//   if (vidoman.publishAudio) {
//     state.publisher.publishAudio(true)
//   } else {
//     state.publisher.publishAudio(false)
//   }
// }
const endGame = async function (result) {
  await api.room.editRoom(state.mySessionId, { game: 1 })
  result = Boolean(result)
  tmpGameResult.value = result
  tmpGameResultModal.value = true
  // if (result) {
  //   if (gameSet.liar === state.myUserName) {
  //     gameSet.gameResult = "유저 승리"
  //   } else {
  //     gameSet.gameResult = "라이어 패배"
  //   }
  // } else {
  //   console.log("여기까지 온거 아냐?")
  //   if (gameSet.liar === state.myUserName) {
  //     gameSet.gameResult = "라이어 승리"
  //   } else {
  //     gameSet.gameResult = "유저 패배"
  //   }
  // }
  // setTimeout(() => {
  //   tmpGameResult.value = gameSet.gameResultModal
  // }, 5000)
  initSetting()
  isGameMode.value = 1
}

const isLiar = ref(false)
const tmpNum = ref(0)
const tmpvote = ref(false)
const tmpliarInputModal = ref(false)
const tmpUserList = ref([])
const tmpGameResultModal = ref(false)
const tmpGameResult = ref(false)

const joinSession = () => {
  // --- Get an OpenVidu object ---
  console.log("오픈 비두 시작!")
  state.mySessionId = props.roomId

  state.OV = new OpenVidu()
  console.log("%cSTATE 시작 전", "color: white; background: red")
  console.log(state)
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
    console.log("여기까진 되니?")
    const userList = usestore.getters.getUserInfo
    const rawMessage = e.data
    let splitIdx = -1
    for (let i = 0; i < rawMessage.length; i++) {
      if (rawMessage[i] === ":") {
        splitIdx = i
        break
      }
    }
    const Email = rawMessage.slice(0, splitIdx - 1)
    const Message = rawMessage.slice(splitIdx + 2, rawMessage.length) + " "
    let userNickname = ""
    userList.forEach((userInfo) => {
      if (userInfo.userEmail === Email) {
        userNickname = userInfo.userNickname
        return
      }
    })
    const sendTime = new Date().toLocaleTimeString().slice(0, -3)
    const messageObject = {
      userNickname: userNickname,
      content: Message,
      sendTime: sendTime,
      isDifferentNameAndTime: true,
    }
    const exMessage = usestore.getters.getChattings.at(-1)
    if (
      exMessage &&
      exMessage.userNickname === userNickname &&
      exMessage.sendTime === sendTime
    ) {
      messageObject.isDifferentNameAndTime = false
    }
    console.log("왜안들감?")
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
    console.log(data.data)
    if (state.myUserName !== data.data) {
      leaveSession()
      router.push({ name: "mainpage" })
    }
  })
  //call my name game
  state.session.on("signal:callMyNamegameStart", (data) => {
    let info = data.data.split(",")
    isGameMode.value = parseInt(info[0])
    callMyNameGameIdx.value = 0
    callMyNameCoolDown.value = 3
    callMyNamegameSet.maxRound = parseInt(info[1])
  })
  state.session.on("signal:callMyNamegameSuggestion", (data) => {
    console.log(data.data)
    callMyNamegameSet.suggestion = data.data.split(",")
  })
  state.session.on("signal:callMyNamegameOrder", (data) => {
    console.log(data.data)
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
  state.session.on("signal:Call my name, idx up", (data) => {
    console.log(data.data)
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
    let data = parseInt(info.data)
    if (!data) {
      callMyNameGameResultWinner.value = callMyNamegameSet.userList[0]
    } else {
      callMyNameGameResultWinner.value = callMyNamegameSet.userList[data]
    }
    for (let i = 0; i < 3; i++) {
      if (callMyNamegameSet.userList[i] === state.myUserName) {
        callMyNameMyName.value = callMyNamegameSet.suggestion[i]
      }
    }

    if (state.myUserName === callMyNameGameResultWinner.value) {
      callMyNameGameResult.value = true
      await api.room.editRoom(state.mySessionId, { game: 1 })
    }
    callMyNamegameSet.gameResultModal = true
    isGameMode.value = 1
  })
  state.session.on("signal:callMynamechooseIncorrect", (data) => {
    console.log("잘못된 값을 적었다~", data.data)
  })

  // liar Game
  state.session.on("signal:gameStart", (data) => {
    console.log("게임이 시작 되었는가?????????????")
    let info = data.data.split(",")
    console.log(info)
    gameSet.suggestion = info[1]
    gameSet.maxRound = parseInt(info[2])
    isGameMode.value = parseInt(info[0])
  })
  state.session.on("signal:whoIsLiar", (data) => {
    console.log(data.data)
    gameSet.liar = data.data
    if (gameSet.liar == state.myUserName) {
      isLiar.value = true
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
          flag = 1
        }
      })
      if (!flag) {
        gameSet.gameUserOrder.push(state.publisher)
      }
    }
  })
  state.session.on("signal:gameIdxUp", (data) => {
    console.log(data.data)
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
      console.log("왜 투표를 안하나요")
      gameSet.isVoteNow = true
      tmpvote.value = gameSet.isVoteNow
      gameSet.isVoteNow = false
    }
    console.log("gameSet.gameIdx : ", gameSet.gameIdx)
  })
  state.session.on("signal:heIsLiar", (data) => {
    console.log(data.data)
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
      if (gameSet.skipNum < gameSet.voteNum) {
        // 스킵 되지 않은 경우
        for (const [key, value] of Object.entries(gameSet.votingProcess)) {
          if (value > maxValue) {
            maxValue = value
            maxKey = key
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
    gameSet.liarInput = inputValue
    isLiarWaiting.value = false
    endGame(result)
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
  console.log(
    "야야야야야야야야야야야야야야야야",
    state.myUserName,
    hostEmail.value
  )
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
  console.log(data)
  state.session
    .signal({
      data: state.myUserName + " : " + data,
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
    console.log(response)
    userId.value = response.data.userEmail
    state.myUserName = userId.value
  } catch (err) {
    console.log(err)
  }
  console.log("본인 아이디요", userId.value)

  // try {
  //   const response = await axios({
  //     method: 'GET',
  //     headers: { authorization: "Bearer " + localStorage.getItem("token") },
  //     url: api.room.roomInfo(roomID),
  //   })

  //   if (response.status === 200) {
  //     dispatch('setRoomInfo', response.data)
  //   } else {
  //     Swal.fire({
  //       icon: 'error',
  //       text: '존재하지 않는 방입니다',
  //     })
  //     router.push({ name: 'errorpage', params: { errorname: '404' } })
  //   }
  // } catch (err) {
  //   Swal.fire({
  //     icon: 'error',
  //     text: '잘못된 접근입니다',
  //   })
  //   router.push({ name: 'errorpage', params: { errorname: '500' } })
  // }
  console.log("룸 번호요", props.roomId)

  console.log("본인 아이디요", userId.value)
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
