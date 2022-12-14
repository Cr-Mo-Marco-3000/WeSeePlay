import router from '@/router'
import api from '@/api/api'
import axios from 'axios'

export default {
  state: {
    token: localStorage.getItem('token') || '',
    me: {},
  },
  getters: {
    isLoggedin: (state) => !!state.token,
    authHeader: (state) => ({ Authorization: `Bearer ${state.token}` }),
    me: (state) => state.me,
  },
  mutations: {
    // 아래가 예시에 적힌 방식인데, 필요 없을 것 같다.
    // SET_TOKEN: (state, token) => state.token = token,
    SET_TOKEN: (state, token) => (state.token = token),
    SET_ME: (state, me) => (state.me = me),
    REMOVE_TOKEN: () => {
      localStorage.setItem('token', '')
    },
    REMOVE_ME: (state) => {
      state.me = {}
    },
  },
  actions: {
    saveToken: (context, token) => {
      context.commit('SET_TOKEN', token)
      localStorage.setItem('token', token)
    },
    removeToken: (context) => {
      context.commit('SET_TOKEN', '')
    },
    logout: ({ commit }) => {
      commit('SET_TOKEN', '')
      commit('REMOVE_TOKEN')
      commit('REMOVE_ME')
      router.push({ name: 'startpage' })
    },
    // 본인 정보 불러오기
    fetchMe: ({ getters, commit, dispatch }) => {
      if (getters.isLoggedin) {
        const fetchMe = async function () {
          try {
            const response = await axios({
              url: api.users.checkToken(),
              method: 'GET',
              headers: getters.authHeader,
            })
            commit('SET_ME', response.data)
          } catch (err) {
            console.log(err)
            dispatch('removeToken')
          }
        }
        fetchMe()
      }
    },
  },
}
