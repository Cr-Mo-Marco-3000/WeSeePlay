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
    <input type="checkbox" name="is_private" v-model="roomInfo.is_private" />
  </div>
  <div class="user-input">
    <input
      type="password"
      name="conferencePassword"
      v-model="roomInfo.conferencePassword"
      placeholder="비밀번호"
      :disabled="roomInfo.is_private == false"
    />
  </div>
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
      title: "",
      descript: "어서와요",
      roomPassword: "",
      game: 0,
      is_private: false,
    });

    // 오류 메시지
    const roomCreateError = ref("");
    const createRoom = async function () {
      try {
        const response = await axios({
          url: api.room.createRoom(),
          method: "POST",
          headers: { authorization: "Bearer" + token.value },
          data: roomInfo,
        });
        if (response.data.statusCode === 201) {
          console.log("방 생성 성공");
          roomCreateError.value = "방이 생성 되었습니다.";
        }
      } catch (err) {
        roomCreateError.value = "방 생성에 실패하였습니다.";
      }
    };

    return {
      roomInfo,
      createRoom,
    };
  },
};
</script>

<style></style>
