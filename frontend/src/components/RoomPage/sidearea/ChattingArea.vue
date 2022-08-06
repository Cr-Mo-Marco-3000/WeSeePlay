<template>
  <div class="title">채팅</div>
  <div class="chat-list-area">
    <div class="messages-area">
      <div class="message">레이아웃은 다 짜놨음</div>
      <div class="message">.</div>
      <div class="message">.</div>
      <div class="message">.</div>
      <div class="message">.</div>
      <div class="message">.</div>
      <div class="message">.</div>
      <div class="message">.</div>
      <div class="message">.</div>
      <div class="message">.</div>
      <div class="message">.</div>
      <div class="message">.</div>
      <div class="message">.</div>
      <div class="message">.</div>
      <div class="message">.</div>
      <div class="message">.</div>
      <div class="message">.</div>
      <div class="message">.</div>
      <div class="message">.</div>
      <div class="message">.</div>
      <div class="message">.</div>
      <div class="message">.</div>
      <div class="message">.</div>
      <div class="message">.</div>
      <div class="message">.</div>
      <div class="message">.</div>
      <div class="message">.</div>
      <div class="message">.</div>
    </div>
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
</script>
<style scoped>
@import url('../../../../src/assets/roompage/sidearea.css');
</style>
