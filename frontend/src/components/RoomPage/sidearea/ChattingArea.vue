<template>
  <div class="title">채팅</div>
  <div class="chat-list-area">
    <div class="messages-area">
      <!-- 메시지 형식 -->
      <div class="message">
        <div class="message-user-info">
          <div class="message-send-user">아무무</div>
          <div class="message-send-time">오후 08:30</div>
        </div>
        <div class="message-send-content">레이아웃은 다 짜놨음</div>
      </div>
    </div>
    <!-- 받는 사람 정하는 부분 -->
    <div class="recipient-area">
      <div>받는 사람:</div>
      <div>
        <select name="recipient" id="recipient">
          <option value="아무무">아무무</option>
          <option value="노틸러스">노틸러스</option>
          <option value="야스오">야스오</option>
        </select>
      </div>
      <div><button>모두에게</button></div>
    </div>
    <!-- 메시지 보내는 textarea -->
    <div class="typping-area">
      <div>
        <textarea
          class="text-area"
          autocomplete="off"
          placeholder="사람들에게 메세지 보내기"
        />
        <div @click="sendMessage">
          <i class="fa-solid fa-paper-plane fa-lg"></i>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
const sendMessage = function () {
  console.log('메시지 송출!')
}
/* eslint-disable */
const tempMessage = {
  userNickname: '김부겸',
  userMessage: '안녕하세요',
}

const messageArea = document.querySelector('.messages-area')

let exName = ''

let exTime = ''

// add Message하는 함수인데, 아직 통째로 돌릴지, 이렇게 할지 잘 모르겠어서 일단 만들어 놓는다.
// eslint-disable-next-line
const addMessage = function (message) {
  console.log('메시지 받음!')
  const userName = message.userNickname
  const nowTime = new Date().toLocaleTimeString().slice(0, -3)
  const messageBox = document.createElement('div')
  messageBox.classList.add('message')
  messageArea.append(messageBox)
  exName = message.userNickname
  exTime = nowTime
}
</script>
<style scoped>
@import url('../../../../src/assets/roompage/sidearea.css');
</style>
