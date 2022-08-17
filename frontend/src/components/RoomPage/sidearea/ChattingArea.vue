<template>
  <div class="title">채팅</div>
  <div class="chat-list-area">
    <!-- 채팅 표시 부분 -->
    <div class="messages-area">
      <!-- 채팅 로그는 for 돌려 결정 -->
      <!-- 메시지 형식 -->
      <div v-for="(chat, idx) in chattingLog" :key="idx" class="message">
        <div v-if="chat.isDifferentNameAndTime" class="message-user-info">
          <div class="message-send-user">{{ chat.userNickname }}</div>
          <div class="message-send-time">{{ chat.sendTime }}</div>
        </div>
        <div class="message-send-content">{{ chat.content }}</div>
      </div>
    </div>
    <!-- 받는 사람 정하는 부분 -->
    <!-- <div class="recipient-area">
      <div>받는 사람:</div>
      <div>
        <select name="recipient" id="recipient">
          <option value="아무무">아무무</option>
          <option value="노틸러스">노틸러스</option>
          <option value="야스오">야스오</option>
        </select>
      </div>
      <div><button>모두에게</button></div>
    </div> -->
    <!-- 메시지 보내는 textarea -->
    <div class="typping-area">
      <div>
        <textarea
          id="text-area"
          @keydown.enter.prevent="sendingMsgBtn"
          class="text-area"
          autocomplete="off"
          placeholder="사람들에게 메세지 보내기"
          v-model="sendingMessageFromMe"
        />
        <div @click="sendingMsgBtn">
          <i class="fa-solid fa-paper-plane fa-lg"></i>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import { watchEffect, defineEmits } from "vue"
import { onUpdated } from "vue"
import { useStore } from "vuex"
import { ref } from "vue"

const store = useStore()
const chattingLog = ref([])
const sendingMessageFromMe = ref("")

const emit = defineEmits(["send-message-from-me"])
const sendingMsgBtn = function () {
  emit("send-message-from-me", sendingMessageFromMe.value)
  sendingMessageFromMe.value = ""
}

// 채팅 컴포넌트를 껐다가 켰을 때도 채팅이 유지되어야 함으로, 스토어에 저장해주기로 했습니다.
watchEffect(() => {
  if (store.state.rooms.chattings.length) {
    console.log(store.state.rooms.chattings.length)
    chattingLog.value = store.getters.getChattings
  }
})

onUpdated(() => {
  // 워치이펙트 내부에 스크롤을 달아주면, 렌더링되기 전의 높이로 내리므로 항상 대화 한 마디가 끝에 남게 된다
  // 렌더링 후 발동하는 onUpdated를 써준다.
  const messagesArea = document.querySelector(".messages-area")
  messagesArea.scrollTo(0, messagesArea.scrollHeight)
  console.log(messagesArea.scrollHeight)
})

// add Message하는 함수인데, 아직 통째로 돌릴지, 이렇게 할지 잘 모르겠어서 일단 만들어 놓는다.
// eslint-disable-next-line
</script>
<style scoped>
@import url("../../../../src/assets/roompage/sidearea.css");
</style>
