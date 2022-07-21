<template>
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
        <div class="q-mt-md text-center text-black">
          <div class="overlay__inner first-carousel">
            <div class="text-box">
              <h1 class="overlay__title">
                Hey, would you like to learn how to create a
                <span class="text-gradient">generative</span> UI just like this?
              </h1>
              <p class="overlay__description">
                응애 서비스는 만들어야겠는데 템플릿 다시 짜기 귀찮아
                <strong>We're gonna talk accessibility, too.</strong>
              </p>
            </div>
            <img class="logo-img" src="../../assets/logo.png">
          </div>
        </div>
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
</template>

<script>
import { ref } from 'vue'

export default {
  name: "TheCarousel",
  setup () {
    return {
      slide: ref('style'),
    }
  }
}
</script>

<style>
</style>