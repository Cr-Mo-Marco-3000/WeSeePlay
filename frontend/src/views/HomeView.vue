<template>
  <div class="home">
    <StartPage />
  </div>
</template>

<script>
import StartPage from '@/components/HomeView/StartPage.vue'

export default {
  name: 'HomeView',
  components: {
    StartPage
  }
}
</script>

<style scoped>
.home {
  min-height: 100vh;
}
</style>
