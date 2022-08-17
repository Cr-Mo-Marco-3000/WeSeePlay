import { createStore } from "vuex"
import users from "./users"
import rooms from "./rooms"
import liargame from "./liargame"
export default createStore({
  modules: {
    users,
    rooms,
    liargame,
  },
})
