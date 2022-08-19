import api from "@/api/api"
import Swal from "sweetalert2"
import router from "@/router"

export default {
  state: {
    // 방 정보
    roomInfo: {},
    // 사이드바 오픈용 변수
    sidebar: "0",
    chattings: [],
  },
  getters: {
    get_sidebar: (state) => state.sidebar,
    // 방 정보 가져오기
    getRoomInfo: (state) => state.roomInfo,
    // 방에 있는 User 정보 가져오기
    getUserInfo: (state) => state.roomInfo.joinUsers,
    // 방에 있는 Chatting 정보 가져오기
    getChattings: (state) => state.chattings,
    getNickname: (state) => (email) => {
      const users = state.roomInfo.joinUsers
      const nameDict = {}
      users.forEach((user) => {
        nameDict[user.userEmail] = user.userNickname
      })
      const val =
        nameDict[email] !== undefined
          ? nameDict[email]
          : state.roomInfo.hostNickname
      return val
    },
  },
  mutations: {
    SET_SIDEBAR: (state, data) => {
      if (state.sidebar === data) {
        state.sidebar = "0"
      } else {
        state.sidebar = data
      }
    },
    SET_ROOM_INFO: (state, data) => {
      state.roomInfo = data
    },
    ADD_MESSAGE: (state, message) => {
      state.chattings.push(message)
    },
    INIT_CHATTING: (state) => {
      state.chattings = []
    },
    EDIT_GAMEMODE: (state, data) => {
      state.roomInfo.game = data
    },
  },
  actions: {
    editRoomGame: ({ commit }, data) => {
      commit("EDIT_GAMEMODE", data)
    },
    openSidebar: ({ commit }, data) => {
      commit("SET_SIDEBAR", data)
    },
    setRoomInfo: ({ commit }, data) => {
      commit("SET_ROOM_INFO", data)
    },

    // 룸 정보 받는 함수
    getRoomInfo: async function ({ dispatch }, roomId) {
      const { data } = await api.room.roomInfo(roomId)
      const status = data.statusCode
      if (status === 200) {
        dispatch("setRoomInfo", data)
      } else if (status === 404) {
        router.push({ name: "errorpage", params: { errorname: "404" } })
      } else {
        router.push({ name: "errorpage", params: { errorname: "500" } })
      }
    },

    // 매시지 추가하는 함수
    addMessage: function ({ commit }, message) {
      commit("ADD_MESSAGE", message)
    },
    // 방 폭파시는 name에 빈칸을 넣으면 되고, 아니면 userName을 넣으면 됩니다.
    forceExit: function (context, name) {
      router.push({ name: "mainpage" })
      let alertTitle = "방이 폭파되었습니다!"
      if (!name) {
        alertTitle = `${name}님은 방에서 강퇴당하셨습니다!`
      }
      Swal.fire({
        title: alertTitle,
        text: "라운지로 이동했습니다",
        icon: "warning",
      })
    },
    initChatting: function ({ commit }) {
      console.log("여기까진 들어오나?")
      commit("INIT_CHATTING")
    },
  },
}
