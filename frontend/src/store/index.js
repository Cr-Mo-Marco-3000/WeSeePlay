import { createStore } from 'vuex'
import users from './users'
import rooms from './rooms'
export default createStore({
  modules: {
    users,
    rooms,
  },
})
