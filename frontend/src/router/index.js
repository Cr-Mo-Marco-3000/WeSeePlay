import Swal from "sweetalert2"
import { createRouter, createWebHistory } from "vue-router"
import StartPage from "../views/StartPage.vue"
import MainPage from "@/views/MainPage.vue"
import ErrorPage from "@/views/ErrorPage.vue"
import RoomPage from "@/views/RoomPage.vue"

import KakaoLoginPage from "@/views/KakaoLoginPage.vue"
// eslint-disable-next-line
import store from "@/store"

const routes = [
  {
    path: "/",
    name: "startpage",
    component: StartPage,
  },
  {
    path: "/lounge",
    name: "mainpage",
    component: MainPage,
  },
  {
    path: "/room/:roomID",
    name: "roompage",
    component: RoomPage,
  },
  // 보내는 에러 종류에 따라서 띄우는 에러를 다양하게 함
  {
    path: "/error/:errorname",
    name: "errorpage",
    component: ErrorPage,
  },
  {
    path: "/login/kakao",
    name: "kakaologin",
    component: KakaoLoginPage,
  },
  {
    path: "/:pathMatch(.*)*",
    redirect: "/error/404",
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
})

router.beforeEach((to, from, next) => {
  // 실제 서비스용 navguard
  // const { isLoggedin } = store.getters
  // 디버깅용 변수 선언
  const isLoggedin = true

  // 스타트페이지가 아닌 곳으로 접근하고, 로그인이 되어 있지 않을 때,
  if (to.name !== "startpage" && !isLoggedin) {
    Swal.fire({
      icon: "warning",
      text: "로그인이 필요한 페이지입니다!",
    })
    next({ name: "startpage" })
  } else if (to.name === "startpage" && isLoggedin) {
    next({ name: "mainpage" })
  } else {
    next()
  }
})

export default router
