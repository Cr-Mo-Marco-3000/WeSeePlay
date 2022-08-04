<template>
  <div class="user-input">
    <span>방 제목</span>
    <input type="text" name="title" v-model="roomInfo.title" />
  </div>
  <div class="user-input">
    <span>방 설명</span>
    <textarea
      type="text"
      rows="5"
      name="descript"
      v-model="roomInfo.descript"
    />
  </div>
  <div class="private-check">
    <span>비공개</span>
    <input
      type="checkbox"
      name="is_private"
      v-model="roomInfo.isPrivate"
    />
  </div>
  <div class="user-input">
    <input
      type="password"
      name="roomPassword"
      v-model="roomInfo.roomPassword"
      placeholder="비밀번호"
      :disabled="roomInfo.isPrivate == false"
    />
  </div>
  <span class="room-create-error-msg">{{roomCreateInputError}}</span>
  <div class="btns-box">
    <button class="overlay__btn" @click="createRoom">생성</button>
  </div>
</template>

<script>
import { ref, reactive } from "vue";
// import { computed } from "vue"
import { useStore } from "vuex";
import api from "@/api/api";
import axios from "axios";

export default {
  name: "CreateRoomModalContent",
  setup() {
    const store = useStore();
    const token = store.state.users.token;
    // 방 생성 정보
    let roomInfo = reactive({
      title: '',
      descript: '어서와요',
      roomPassword: '',
      game:0,
      isPrivate:false
    })

    // 오류 메시지
    const roomCreateInputError = ref('')
    const createRoom = async function () {
      try {
        console.log("header: ", "authorization : Bearer "+ token)
        console.log("body: ",roomInfo)
        if (roomInfo.title == ''){
          roomCreateInputError.value = "방 이름을 정해 주세요"
        }
        if (roomInfo.isPrivate){
          if (9<=roomInfo.roomPassword.length<=16){
            roomCreateInputError.value = "9~16자리의 비밀번호를 정해주세요"
          }
        }
        if (roomCreateInputError.value){
          return 0
        }
        const response = await axios({
          url: api.room.createRoom(),
          method: 'POST',
          headers:{"authorization":"Bearer "+ token},
          data: roomInfo
        })
        if (response.data.statusCode === 201) {
          console.log("방 생성 성공");
        }
      } catch (err) {
        console.log("실패")
      }
    };

    return {
      roomInfo,
      createRoom,
      roomCreateInputError,

    }
  }
}
</script>

<style>
</style>