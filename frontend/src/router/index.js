import { createRouter, createWebHistory } from 'vue-router'
import StartView from '../views/StartView.vue'
import MainPage from '@/views/MainPage.vue'

const routes = [
  {
    path: '/',
    name: 'startpage',
    component: StartView
  },
  {
    path: '/lounge',
    name: 'mainpage',
    component: MainPage
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
