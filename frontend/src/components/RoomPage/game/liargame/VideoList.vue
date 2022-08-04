<template>
  <div class="row all-box">
    <SpeakerArea
      class="col-6 self-center"
      @next-player="nextPlayer"
      :speaker="users[speaker]"
    />
    <AllUsers class="col-6" :users="users" />
    <!-- <div class="col-6 temp">All Users</div> -->
  </div>
</template>

<script setup>
import SpeakerArea from "./SpeakerArea.vue"
import AllUsers from "./AllUsers.vue"
import { ref, defineProps } from "vue"

const prop = defineProps({
  users: {
    type: Array,
    required: true,
  },
})

// 다음 플레이어에게 턴 넘기기
const speaker = ref(0)

const nextPlayer = function () {
  const now = speaker.value

  if (now + 1 === prop.users.length) {
    speaker.value = 0
  } else {
    speaker.value = now + 1
  }
}
</script>

<style>
.temp {
  border: solid 2px black;
}

#speaker {
  padding: 2rem;
  height: 300px;
}

.all-box {
  height: 100%;
  border-radius: 10px;
}
</style>
