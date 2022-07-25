import { createStore } from 'vuex'
import users from './users'

export default createStore({
  modules: {
    users,
  },
})
