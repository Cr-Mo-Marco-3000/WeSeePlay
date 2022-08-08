export default {
  state: {
    users: [],
    sidebar: '0',
  },
  getters: {
    get_sidebar: (state) => state.sidebar,
  },
  mutations: {
    SET_SIDEBAR: (state, data) => {
      if (state.sidebar === data) {
        state.sidebar = '0'
      } else {
        state.sidebar = data
      }
    },
  },
  actions: {
    openSidebar: ({ commit }, data) => {
      commit('SET_SIDEBAR', data)
    },
  },
}
