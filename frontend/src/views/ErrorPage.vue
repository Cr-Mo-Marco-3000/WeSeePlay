<template>
  <div style="margin-top: 10vh" class="overlay">
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
    </div>
  </div>
</template>

<script setup>
import { useRoute } from 'vue-router'
import { ref } from 'vue'
import Error404 from '@/components/ErrorPage/ErrorPage404.vue'
import Error500 from '@/components/ErrorPage/ErrorPage500.vue'
const route = useRoute()
const errorname = route.params.errorname
let error404 = ref('')
let error500 = ref('')
if (errorname === '404') {
  error404.value = true
} else if (errorname === '500') {
  error500.value = true
}
</script>

<style>
@import '../assets/errorpage/error.css';
@import '../assets/startpage/startpage.css';
</style>
