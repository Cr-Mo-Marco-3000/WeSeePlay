<template>
  <div class="top-bar">
    <div>
      <button
        v-if="isGameMode === 2"
        class="manual-button"
        @click="liarGameManual"
      >
        <span>게임설명</span>
      </button>
      <button
        v-if="isGameMode === 3"
        class="manual-button"
        @click="callMyNameManual"
      >
        <span>게임설명</span>
      </button>
    </div>
    <div>
      <!-- 라이어일때 뜨는 부분 -->
      <div v-if="isLiar">
        <span class="notify-span">당신은 Liar입니다</span>
      </div>
      <!-- 일반 참가자일때 뜨는 부분 -->
      <div v-if="!isLiar">
        <span class="notify-span">제시어: {{ suggestion }}</span>
      </div>
    </div>
    <div>
      <button v-if="isHost" class="end-button">
        <div>
          <i class="fa-solid fa-person-running fa-xl"></i>
        </div>
        <div>
          <span>게임 종료</span>
        </div>
      </button>
    </div>
  </div>
</template>
<script setup>
import store from "@/store"
import { defineProps, watchEffect, ref } from "vue"
import Swal from "sweetalert2"
// eslint-disable-next-line

const props = defineProps({
  isHost: Boolean,
  isGameMode: Number,
  suggestion: String,
  isLiar: Boolean,
})
const gameSet = ref(store.getters.getLiarGameSet)
const isLiar = ref(JSON.parse(props.isLiar))
watchEffect(() => {
  console.log("탑바")
  gameSet.value = store.getters.getLiarGameSet
  console.log(
    "%c gameSet 정보 불러옴 (from TopBar.vue) ",
    "color: white;background: pink",
    props.suggestion,
    props.isLiar
  )
})

// 라이어게임 설명
const liarGameManual = () => {
  Swal.fire({
    imageHeight: 300,
    imageAlt: "game image",
    title: "게임 설명",
    text: "당신은 라이어인가요?",
    showCancelButton: true,
    showDenyButton: true,
    denyButtonText: "라이어입니다",
    confirmButtonText: "라이어가 아닙니다",
    cancelButtonText: "닫기",
  }).then((result) => {
    if (result.isConfirmed === true) {
      Swal.fire({
        title: "당신은 라이어가 아닙니다",
        html: "여러분은 주어진 제시어에 대해 사람들에게 설명해야 합니다.<br>당신의 차례가 오면 화면 상단 중앙에 표시된<br>제시어에 대해 설명하고 버튼으로 턴을 넘기세요.<br>하지만 너무 자세하게 설명하면<br>라이어에게 제시어를 들켜 패배하게 되니 주의해야겠죠?<br><br>다른 사람의 설명을 듣고<br>제시어에 대해 모르는 것 같은 사람을 찾아내<br>최종 투표에서 라이어를 찾아내세요!",
        confirmButtonText: "확인",
        width: 600,
      })
    } else if (result.isDenied === true) {
      Swal.fire({
        title: "당신은 라이어입니다",
        html: "다른 사람들이 말하는 설명을 듣고, 제시어가 무엇인지 추측해 보세요.<br>제시어가 어떤 분류에 속하는지 화면 상단 중앙에 표시되어 있습니다.<br>당신 차례가 오면 사람들이 당신이 라이어란 사실을 눈치채지 못하게<br>숨겨진 제시어에 대해 그럴듯하게 설명한 후 버튼으로 턴을 넘기세요.<br>최종 투표를 무사히 넘긴다면 당신의 승리입니다!",
        showCancelButton: true,
        confirmButtonText: "라이어인걸 들켰어요",
        confirmButtonColor: "#dc3741",
        cancelButtonText: "닫기",
        width: 600,
      }).then((result) => {
        if (result.isConfirmed === true) {
          Swal.fire({
            title: "라이어인걸 들켰다면",
            html: "걱정하지 마세요!<br>당신에게는 마지막 기회가 남아 있습니다!<br>다른 사람들의 설명을 들으면서<br>지금까지 추측했던 제시어를 입력해 주세요.<br>만약 당신이 추측한 단어가 실제 제시어라면<br>당신의 승리입니다!",
            confirmButtonText: "확인",
            confirmButtonColor: "#dc3741",
            width: 600,
          })
        }
      })
    }
  })
}

// 콜 마이 네임 설명
const callMyNameManual = () => {
  Swal.fire({
    title: "당신의 정체를 맞춰보세요!",
    html: "다른 사람들은 당신이 누구인지 알지만<br>당신은 당신의 정체를 모릅니다.<br><br>억울할 필요는 없죠.<br> 당신도 다른 사람들의 정체를 알고 있으니까요.<br><br> 다른 사람들이 자신이 누구인지 맞추기 전에<br>당신이 누구인지 알아맞혀보세요!",
    confirmButtonText: "확인",
  })
}
</script>
<style scoped>
@import url("../../../../assets/roompage/topbar.css");
</style>
