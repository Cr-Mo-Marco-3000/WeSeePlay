<template>
  <div class="user-input">
    <span>방 제목</span>
    <input type="text" name="title" />
  </div>

  <div class="user-input">
    <span>호스트</span>
    <select>
      <option disabled value="">호스트를 위임할 사용자를 정해주세요</option>
      <option>참가자1</option>
      <option selected>참가자2</option>
    </select>
  </div>

  <div class="user-input">
    <span>방 설명</span>
    <textarea type="text" rows="5" name="descript" />
  </div>

  <div class="private-check">
    <span>비공개</span>
    <input type="checkbox" name="is_private" v-model="roomInfo.isPrivate" />
  </div>

  <div class="user-input">
    <input
      type="password"
      name="roomPassword"
      placeholder="비밀번호"
      :disabled="roomInfo.isPrivate == false"
    />
  </div>

  <span class="edit-error-msg">{{ editErrorMsg }}</span>

  <div class="btns-box">
    <button class="overlay__btn room_edit_btn" @click="createRoom">생성</button>
  </div>
</template>

<script>
import { ref, reactive } from "vue"

export default {
  name: "EditModalContent",
  setup() {
    const editErrorMsg = ref("응애 미안해")

    /* 원래는 게임 정보를 받아서 처리해야하지만 일단 템플릿 확인용으로 넣은 더미데이텅 */
    let roomInfo = reactive({
      title: "",
      descript: "어서와요",
      roomPassword: "",
      game: 0,
      isPrivate: false,
    })

    return { roomInfo, editErrorMsg }
  },
}
</script>

<style></style>
