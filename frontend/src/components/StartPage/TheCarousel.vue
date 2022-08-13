<template>
  <!-- <div id="logo-box">
    <img src="../../assets/logo.png" />
    <div class="logo-box-text">
      <h4>
        <span class="text-gradient">WeSeePlay</span>
      </h4>
      <p>
        저희 서비스는 말이죠웅 수빱뚜삐두바 뚜비두비두비 뚜비두비두비두비
        두비두다~ 저희 서비스는 말이죠웅 수빱뚜삐두바 뚜비두비두비
        뚜비두비두비두비 두비두다~ 저희 서비스는 말이죠웅 수빱뚜삐두바
        뚜비두비두비 뚜비두비두비두비 두비두다~
      </p>
    </div>
  </div> -->
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
        <q-carousel-slide name="1" class="column no-wrap flex-center">
          <FirstCarousel />
        </q-carousel-slide>
        <q-carousel-slide name="2" class="column no-wrap flex-center">
          <SecondCarousel />
        </q-carousel-slide>
        <q-carousel-slide name="3" class="column no-wrap flex-center">
          <ThirdCarousel />
        </q-carousel-slide>
        <q-carousel-slide name="4" class="column no-wrap flex-center">
          <FourthCarousel />
        </q-carousel-slide>
        <q-carousel-slide name="5" class="column no-wrap flex-center">
          <FifthCarousel />
        </q-carousel-slide>
      </q-carousel>
    </div>
  </div>
</template>

<script>
import { ref } from "vue"
import SecondCarousel from "./carousels/SecondCarousel.vue"
import FirstCarousel from "./carousels/FirstCarousel.vue"
import ThirdCarousel from "./carousels/ThirdCarousel.vue"
import FourthCarousel from "./carousels/FourthCarousel.vue"
import FifthCarousel from "./carousels/FifthCarousel.vue"

export default {
  name: "TheCarousel",
  components: {
    FirstCarousel,
    SecondCarousel,
    ThirdCarousel,
    FourthCarousel,
    FifthCarousel,
  },
  setup() {
    console.log("props.slide.key")
    const slides = ref([
      {
        title: "안녕",
        content: "안녕내용",
        imageSrc: "안녕이미지",
      },
      {
        title: "안녕",
        content: "안녕내용",
        imageSrc: "안녕이미지",
      },
    ])
    console.log(slides.value)
    return {
      // 처음 표시할 슬라이드
      slide: ref("1"),
      slides,
    }
  },
}
</script>

<style></style>
