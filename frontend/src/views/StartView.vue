<template>
  <div class="home">
    <StartPage />
  </div>
</template>

<script>
import StartPage from '@/components/StartView/StartPage.vue'

export default {
  name: 'StartView',
  setup() {
    /* eslint-disable */
    Kakao.init(process.env.VUE_APP_KAKAO_JAVASCRIPT_API_KEY)
    console.log(Kakao.isInitialized())
  },
  components: {
    StartPage,
  },
}
</script>

<style scoped>
.home {
  min-height: 100vh;
}
</style>
