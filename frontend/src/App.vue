<template>
  <router-view />
</template>

<script>
import { useStore } from 'vuex'

export default {
  name: 'LayoutDefault',
  setup() {
    /* eslint-disable */
    Kakao.init(process.env.VUE_APP_KAKAO_JAVASCRIPT_API_KEY)
    console.log(Kakao.isInitialized())
    const store = useStore()
    store.dispatch('fetchMe')
  },
}
</script>
<style>
#app {
  font-family: 'paybooc-Bold';
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  min-width: 100%;
  height: 100%;
  padding: 0;
}
</style>
