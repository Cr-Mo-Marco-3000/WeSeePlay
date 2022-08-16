<template>
  <div class="overlay error-area">
    <div id="statebox">
      <img
        style="width: 30%; height: auto"
        src="../assets/greenkiwi.png"
        alt="WeSeePlayLogo"
      />
      <div v-if="error404">
        <Error404 />
      </div>
      <div v-if="error500">
        <Error500 />
      </div>
      <span class="StartPageLink" @click="toStart">{{ whichPage }}로 이동</span>
    </div>
  </div>
</template>

<script setup>
import { useRoute } from "vue-router"
import { ref } from "vue"
import Error404 from "@/components/ErrorPage/ErrorPage404.vue"
import Error500 from "@/components/ErrorPage/ErrorPage500.vue"
import router from "@/router"
import { useStore } from "vuex"
const store = useStore()
const isLoggedin = store.getters.isLoggedin
const route = useRoute()
const errorname = route.params.errorname
const toStart = function () {
  router.push({ name: "startpage" })
}
const whichPage = isLoggedin ? "메인 페이지" : "시작 페이지"

let error404 = ref("")
let error500 = ref("")
if (errorname === "404") {
  error404.value = true
} else if (errorname === "500") {
  error500.value = true
}
</script>

<style>
@import "../assets/errorpage/error.css";
@import "../assets/startpage/startpage.css";
</style>
