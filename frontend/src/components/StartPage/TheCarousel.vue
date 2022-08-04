<template>
<div id="logo-box">
  <img src="../../assets/logo.png">
  <div class="logo-box-text">
    <h4>
      <span class="text-gradient">WeSeePlay</span>
    </h4>
    <p>
      저희 서비스는 말이죠웅 수빱뚜삐두바 뚜비두비두비 뚜비두비두비두비 두비두다~
      저희 서비스는 말이죠웅 수빱뚜삐두바 뚜비두비두비 뚜비두비두비두비 두비두다~
      저희 서비스는 말이죠웅 수빱뚜삐두바 뚜비두비두비 뚜비두비두비두비 두비두다~
    </p>
  </div>
</div>
<div id="carousel-box">
  <div class="q-pa-md">
    <q-carousel
      v-model="slide"
      swipeable
      animated
      :control-type="controlType"
      control-color="purple"
      padding
      navigation
      arrows
      infinite
      height="30rem"
      class="text-purple rounded-borders"
    >
      <q-carousel-slide name="style" class="column no-wrap flex-center">
        <FirstCarousel />
      </q-carousel-slide>
      <q-carousel-slide name="tv" class="column no-wrap flex-center">
        <div class="q-mt-md text-center text-black">
          2
        </div>
      </q-carousel-slide>
      <q-carousel-slide name="layers" class="column no-wrap flex-center">
        <div class="q-mt-md text-center text-black">
          3
        </div>
      </q-carousel-slide>
    </q-carousel>  
  </div>
</div>
</template>

<script>
import { ref } from 'vue'
import FirstCarousel from "./FirstCarousel.vue"

export default {
  name: "TheCarousel",
  components: {
    FirstCarousel
  },
  setup () {
    return {
      slide: ref('style'),
    }
  }
}
</script>

<style>
</style>