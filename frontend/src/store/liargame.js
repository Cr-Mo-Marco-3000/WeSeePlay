export default {
  state: {
    gameSet: {
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
    },
  },
  getters: {
    getLiarGameSet: (state) => state.gameSet,
  },
  mutations: {
    setGameSet: (state, gameSet) => (state.gameSet = gameSet),
  },
  actions: {
    setGameSet: function ({ commit }, gameSet) {
      commit("setGameSet", gameSet)
    },
  },
}
