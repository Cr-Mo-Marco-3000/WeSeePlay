<template>
  <div id="search-enzine">
    <input
      id="search-enzine-bar"
      rounded
      v-model="query"
      outlined
      placeholder="search with title"
    />
    <button class="on-voice-btn" @click="onVoice">
      <i class="fa-solid fa-microphone"></i>
    </button>
  </div>
</template>
<script>
import { ref, watchEffect } from "vue"

export default {
  name: "SearchEnzine",
  props: {
    meetingquery: String,
  },
  setup(props, { emit }) {
    const query = ref("")

    watchEffect(() => {
      emit("queryFromChild", query.value)
    })

    const onVoice = function () {
      const voiceBtn = document.querySelector(".on-voice-btn")
      voiceBtn.classList.add("active")
      window.SpeechRecognition =
        window.SpeechRecognition || window.webkitSpeechRecognition
      const recognition = new window.SpeechRecognition()
      recognition.interimResults = false
      recognition.lang = "ko-KR"

      let p = ""

      recognition.addEventListener("result", (e) => {
        const transcript = Array.from(e.results)
          .map((result) => result[0])
          .map((result) => result.transcript)
          .join("")

        p = transcript
        query.value += p

        if (e.results[0].isFinal) {
          p = " "
          this.inputKeyword += p
          voiceBtn.classList.remove("active")
        }
      })
      recognition.start()
    }
    return {
      query,
      onVoice,
    }
  },
}
</script>

<style scoped>
@import "../../assets/mainpage/mainpage.css";
</style>
