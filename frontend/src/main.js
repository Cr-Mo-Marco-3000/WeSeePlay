import { createApp } from 'vue'
import App from './App.vue'
import store from './store'
import router from './router'
import { Quasar } from 'quasar'
import quasarUserOptions from './quasar-user-options'
import axios from 'axios'
import VueAxios from 'vue-axios'

createApp(App)
  .use(Quasar, quasarUserOptions)
  .use(router)
  .use(store)
  .use(VueAxios, axios)
  .mount('#app')
