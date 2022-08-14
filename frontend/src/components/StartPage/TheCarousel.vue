<template>
  <div id="logo-box">
    <img src="../../assets/logo.png" />
    <div class="logo-box-text">
      <h4>
        <span class="text-gradient">WeSeePlay</span>
      </h4>
      <p style="margin-top: 0.5rem">
        <br />
        처음 만나는 사람들, 이미 만나본 사람들 모두 하나되어 즐겁게 놀 수
        있습니다.<br />
        사람들과 얼굴을 마주하며 함께하는 듯한 생생한 즐거움을 누리세요!
        <br />
        지금 <span class="text-gradient">WeSeePlay</span>의 즐거운 세상으로
        초대합니다<br />
        <span style="font-size: 1.1rem">We See, We Play!</span>
      </p>
    </div>
  </div>
  <div id="carousel-box">
    <div class="q-pa-md">
      <q-carousel
        v-model="slide"
        swipeable
        animated
        control-type="flat"
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

    return {
      // 처음 표시할 슬라이드
      slide: ref("1"),
      slides,
    }
  },
}
</script>

<style></style>
