import api from '@/api/api'
import axios from 'axios'
import Swal from 'sweetalert2'
import router from '@/router'

export default {
  state: {
    // 방 정보
    roomInfo: {},
    // 사이드바 오픈용 변수
    sidebar: '0',
    chattings: [],
  },
  getters: {
    get_sidebar: (state) => state.sidebar,
    // 방 정보 가져오기
    getRoomInfo: (state) => state.roomInfo,
    // 방에 있는 User 정보 가져오기
    getUserInfo: (state) => state.roomInfo.joinUsers,
    // 방에 있는 Chatting 정보 가져오기
    getChattings: (state) => state.roomInfo.chattings,
  },
  mutations: {
    SET_SIDEBAR: (state, data) => {
      if (state.sidebar === data) {
        state.sidebar = '0'
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
  },
  actions: {
    openSidebar: ({ commit }, data) => {
      commit('SET_SIDEBAR', data)
    },
    setRoomInfo: ({ commit }, data) => {
      commit('SET_ROOM_INFO', data)
    },
    // 룸 정보 받는 함수
    getRoomInfo: async function ({ getters, dispatch }, roomID) {
      try {
        const response = await axios({
          method: 'GET',
          headers: getters.authHeader,
          url: api.room.roomInfo(roomID),
        })

        if (response.status === 200) {
          dispatch('setRoomInfo', response.data)
        } else {
          Swal.fire({
            icon: 'error',
            text: '존재하지 않는 방입니다',
          })
          router.push({ name: 'errorpage', params: { errorname: '404' } })
        }
      } catch (err) {
        Swal.fire({
          icon: 'error',
          text: '잘못된 접근입니다',
        })
        router.push({ name: 'errorpage', params: { errorname: '500' } })
      }
    },
    // 매시지 추가하는 함수
    addMessage: function ({ commit }, message) {
      commit('ADD_MESSAGE', message)
    },
    // 방 폭파시는 name에 빈칸을 넣으면 되고, 아니면 userName을 넣으면 됩니다.
    forceExit: function (context, name) {
      router.push({ name: 'mainpage' })
      let alertTitle = '방이 폭파되었습니다!'
      if (!name) {
        alertTitle = `${name}님은 방에서 강퇴당하셨습니다!`
      }
      Swal.fire({
        title: alertTitle,
        text: '라운지로 이동했습니다',
        icon: 'warning',
      })
    },
  },
}
