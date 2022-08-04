<template>
  <div class="home">
    <StartPage />
  </div>
</template>

<script>
import StartPage from "@/components/StartPage/StartPage.vue"

export default {
  name: "StartView",
  setup() {},
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
