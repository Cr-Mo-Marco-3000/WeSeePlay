<template>
  <!-- 채팅 중일 때 참가자 리스트 -->
  <div class="title">참가자</div>
  <div id="participant-chat-list-area" class="participant-chat-list-area">
    <!-- 아래 양식 그대로 가져가면 됩니다. => class 순서가 아니라 nth-child로 적용 -->
    <div id="1" class="participant-item">
      <div>참가자 이름</div>
      <div>참가자 권한</div>
      <div>
        <i class="fa-solid fa-microphone-lines fa-xl"></i>
      </div>
      <div>
        <i class="fa-solid fa-video fa-xl"></i>
      </div>
    </div>
    <!-- 바닐라 JS 사랑해요 -->
    <div id="2" class="participant-item">
      <div>바닐라</div>
      <div>호스트</div>
      <div>
        <i class="fa-solid fa-microphone-lines fa-xl"></i>
      </div>
      <div>
        <i class="fa-solid fa-video fa-xl"></i>
      </div>
    </div>
    <div id="3" class="participant-item">
      <div>초콜릿</div>
      <div>참여자</div>
      <div>
        <i
          style="color: rgba(255, 255, 255, 0.75)"
          class="fa-solid fa-microphone-lines-slash fa-xl"
        ></i>
      </div>
      <div>
        <i
          style="color: rgba(255, 255, 255, 0.75)"
          class="fa-solid fa-video-slash fa-xl"
        ></i>
      </div>
    </div>
    <button id="temp-button">임시버튼</button>
  </div>
</template>

<script setup>
// import { reactive, onMounted } from 'vue'

// // 임시변수
// const userNickname = '모나코'
// const userStatus = '호스트'
// const userId = 1
// const isMicOpen = true
// const isVideoOpen = true

// const userInfo = reactive({
//   id: userId,
//   userNickname: userNickname,
//   userStatus: userStatus,
//   isMicOpen: isMicOpen,
//   isVidioOpen: isVideoOpen,
// })

// onMounted(() => {
//   // 임시 버튼
//   const tempButton = document.querySelector('#temp-button')

// const addUser = function () {
//   const chatListArea = document.querySelector('#participant-chat-list-area')

//   // 유저 아이템
//   const chatListItem = document.createElement('div')
//   chatListItem.classList('participant-item')
//   chatListItem.id(userInfo.userId)
//   // 닉네임
//   const userNickname = document.createElement('div')
//   userNickname.innerText = userInfo.userNickname

//   const userStatus = document.createElement('div')
//   userStatus.innerText = userInfo.userNickname
//   const userMicOpen = document.createElement('div')
//   const isMicOpen = document.createElement('i')
//   if (userInfo.isMicOpen === true) {
//   } else {
//   }
//   const userVideoOpen = document.createElement('div')
//   const isVideoOpen = document.createElement('i')
//   if (userInfo.isVideoOpen === false) {
//   } else {
//   }
// }
// const removeUser = function () {
//   console.log('삭제')
// }
// tempButton.addEventListener('click', addUser)
// })
</script>

<style scoped>
@import url('../../../../src/assets/roompage/sidearea.css');
</style>
