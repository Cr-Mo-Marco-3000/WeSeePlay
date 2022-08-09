import api from "@/api/api"
import axios from "axios"
import Swal from "sweetalert2"
import router from "@/router"

export default {
  state: {
    // 방 정보
    roomInfo: {},
    // 사이드바 오픈용 변수
    sidebar: "0",
  },
  getters: {
    get_sidebar: (state) => state.sidebar,
    // 방 정보 가져오기
    getRoomInfo: (state) => state.roomInfo,
    // 방에 있는 User 정보 가져오기
    getUserInfo: (state) => state.roomInfo.joinUsers,
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
  },
  actions: {
    openSidebar: ({ commit }, data) => {
      commit("SET_SIDEBAR", data)
    },
    setRoomInfo: ({ commit }, data) => {
      commit("SET_ROOM_INFO", data)
    },
    // 룸 정보 받는 함수
    getRoomInfo: async function ({ getters, dispatch }, roomID) {
      try {
        const response = await axios({
          method: "GET",
          headers: getters.authHeader,
          url: api.room.roomInfo(roomID),
        })

        if (response.status === 200) {
          dispatch("setRoomInfo", response.data)
        } else {
          Swal.fire({
            icon: "error",
            text: "존재하지 않는 방입니다",
          })
          router.push({ name: "errorpage", params: { errorname: "404" } })
        }
      } catch (err) {
        Swal.fire({
          icon: "error",
          text: "잘못된 접근입니다",
        })
        router.push({ name: "errorpage", params: { errorname: "500" } })
      }
    },
  },
}
