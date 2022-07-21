<template>
  <div class="home">
    <FirstPage />
  </div>
</template>

<script>
import FirstPage from '@/components/HomeView/FirstPage.vue'

export default {
  name: 'HomeView',
  components: {
    FirstPage
  }
}
</script>

<style scoped>
.home {
  min-height: 100vh;
}
</style>
