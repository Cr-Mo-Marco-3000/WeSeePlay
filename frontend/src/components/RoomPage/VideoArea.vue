<template>
  <div>
    <!-- 이 버튼 어차피 나중에 날릴 것이므로 -->
    <button style="position: absolute; top: 1rem" @click="changeGame">
      Game On/Off
    </button>
    <!-- isGameMode가 참이면 GameVideo가 나오게 하고, false라면 MeetingVideo가 나오게 짰어-->
    <LiarGameVideo
      v-if="isGameMode === 2"
      :users="userList"
      :isSide="isSide"
      :roomId="roomId"
      :publisher="state.publisher"
      :subscribers="state.subscribers"
    />
    <MeetingVideo
      v-if="isGameMode === 1"
      :users="userList"
      :isSide="isSide"
      :publisher="state.publisher"
      :subscribers="state.subscribers"
    />
  </div>
</template>

<script setup>
import LiarGameVideo from "./game/liargame/VideoList.vue"
import MeetingVideo from "./meeting/VideoList.vue"
import { ref, defineProps, watchEffect } from "vue"
import store from "@/store"

//---------------------openvidu import-----------------------------------

import $axios from "axios"
import { reactive, onBeforeUnmount, onBeforeMount } from "vue"
import { OpenVidu } from "openvidu-browser"
// import UserVideo from "./components/UserVideo.vue"
// import VoteModal from "./components/VoteModal.vue"
// import VoteModalContent from "./components/VoteModalContent.vue"
$axios.defaults.headers.post["Content-Type"] = "application/json"
$axios.defaults.headers.post["Access-Control-Allow-Origin"] = "*"

const isGameMode = ref(store.getters.getRoomInfo.game)

watchEffect(() => {
  isGameMode.value = store.getters.getRoomInfo.game
})
const changeGame = function () {
  if (isGameMode.value === 1) {
    isGameMode.value = 2
    console.log("gamemode: ", isGameMode.value)
    return
  } else {
    isGameMode.value = 1
    console.log("gamemode: ", isGameMode.value)
    return
  }
}

const userList = ref([]) // Component에 넘겨줄 user list
const props = defineProps({
  isSide: {
    type: Boolean,
    required: true,
  },
  roomId: {
    type: Number,
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
const OPENVIDU_SERVER_URL = "https://" + "i7a501.p.ssafy.io"
const OPENVIDU_SERVER_SECRET = "MY_SECRET"
const state = reactive({
  OV: undefined,
  session: undefined,
  mainStreamManager: undefined,
  publisher: undefined,
  subscribers: [],
  mySessionId: props.roomId,
  //   computed(() => props.roomId),
  myUserName: "Participant" + Math.floor(Math.random() * 100),
  // myUserName: ,
})

// const vidoman = reactive({
//   publishVideo: true,
//   publishAudio: true,
// })

// const sortvideo = reactive({
//   videoOn: computed(() => {
//     let videoOn = []
//     if (state.subscribers.length) {
//       state.subscribers.forEach(function (element) {
//         if (element.stream.videoActive) {
//           videoOn.push(element)
//         }
//       })
//     }
//     return videoOn
//   }),
//   videoOff: computed(() => {
//     let videoOff = []
//     if (state.subscribers.length) {
//       state.subscribers.forEach(function (element) {
//         if (!element.stream.videoActive) {
//           videoOff.push(element)
//         }
//       })
//     }
//     return videoOff
//   }),
// })
// toggle
// const toggleVideo = () => {
//   vidoman.publishVideo = !vidoman.publishVideo
//   if (vidoman.publishVideo) {
//     state.publisher.publishVideo(true)
//   } else {
//     state.publisher.publishVideo(false)
//   }
// }
// const toggleAudio = () => {
//   vidoman.publishAudio = !vidoman.publishAudio
//   if (vidoman.publishAudio) {
//     state.publisher.publishAudio(true)
//   } else {
//     state.publisher.publishAudio(false)
//   }
// }
const joinSession = () => {
  // --- Get an OpenVidu object ---
  console.log("오픈 비두 시작!")
  state.mySessionId = props.roomId
  console.log(state.mySessionId)
  state.OV = new OpenVidu()
  // --- Init a session ---
  state.session = state.OV.initSession()
  // On every new Stream received...
  state.session.on("streamCreated", ({ stream }) => {
    const subscriber = state.session.subscribe(stream)
    state.subscribers.push(subscriber)
    console.log("연결 돼ㅐㅆ냐?")
    // 여기서 플레이어 숫자 계산
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
  // 'getToken' method is simulating what your server-side should do.
  // 'token' parameter should be retrieved and returned by your own backend
  getToken(state.mySessionId).then((token) => {
    state.session
      .connect(token, { clientData: state.myUserName })
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
        console.log("여기 퍼블리셔", publisher)
        state.mainStreamManager = publisher
        state.publisher = publisher
        state.session.publish(publisher)
        console.log("이건 되고", state.mainStreamManager)
        console.log("이건 안되?", state.publisher)
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

// beforeunmount
onBeforeUnmount(() => {
  leaveSession()
})
// onBeforeMount
onBeforeMount(() => {
  console.log("룸 번호요", props.roomId)
  joinSession()
  // joinGameSession()
})
</script>

<style scoped></style>
