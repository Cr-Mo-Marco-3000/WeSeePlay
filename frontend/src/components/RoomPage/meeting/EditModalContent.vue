<template>
  <div class="user-input">
    <span>방 제목</span>
    <input type="text" name="title" v-model="roomInfo.title" />
  </div>

  <div class="user-input">
    <span>호스트</span>
    <select v-model="roomInfo.hostId">
      <option disabled value="">호스트를 위임할 사용자를 정해주세요</option>
      <option selected :value="roomInfo.hostId">
        {{ roomInfo.hostNickname }}(호스트)
      </option>
      <option v-for="(user, key) in userInfo" :key="key" :value="user.userId">
        {{ user.userNickname }}
      </option>
    </select>
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
    <input type="checkbox" name="is_private" v-model="isPrivate" />
  </div>

  <div class="user-input">
    <input
      type="password"
      name="roomPassword"
      placeholder="4 ~ 12자리의 비밀번호를 입력해 주세요"
      :disabled="isPrivate == false"
      v-model="inputPassword"
    />
  </div>

  <span class="edit-error-msg">{{ editErrorMsg }}</span>

  <div class="btns-box">
    <button class="overlay__btn room_edit_btn" @click="editRoom">수정</button>
  </div>
</template>

<script>
import Swal from 'sweetalert2'
import { reactive, ref, watchEffect } from 'vue'
import { useStore } from 'vuex'
import { useRoute } from 'vue-router'
import api from '@/api/api'

export default {
  name: 'EditModalContent',
  emits: ['close'],
  props: [],
  setup(props, context) {
    const editErrorMsg = ref('')
    const store = useStore()
    // const router = useRouter()
    const route = useRoute()
    // const token = store.state.users.token
    const roomId = route.params.roomId
    const inputPassword = ref('')
    const isPrivate = ref(true)

    const roomInfo = ref({})
    const userInfo = ref({})
    const originPrivate = ref(true)
    store.dispatch('getRoomInfo', roomId)
    watchEffect(() => {
      roomInfo.value = store.getters.getRoomInfo
      userInfo.value = store.getters.getUserInfo
      if (roomInfo.value.isPrivate == 0) {
        originPrivate.value = false
        isPrivate.value = false
      }
    })

    const editRoom = async function () {
      try {
        const data = reactive({
          title: roomInfo.value.title,
          descript: roomInfo.value.descript,
          hostId: roomInfo.value.hostId,
        })

        let errorFlag = 0

        if (isPrivate.value == false) {
          inputPassword.value = ''
        }

        // 공개방에서 공개, 비공개로 전환하는 경우
        if (!originPrivate.value) {
          if (isPrivate.value) {
            if (
              4 <= inputPassword.value.length &&
              inputPassword.value.length <= 12
            ) {
              data.roomPassword = inputPassword.value
            } else {
              editErrorMsg.value = '4 ~ 12자리의 비밀번호를 입력해주세요'
              errorFlag = true
            }
          }
        } else {
          if (!isPrivate.value) {
            inputPassword.value = ''
            data.roomPassword = ''
          } else {
            if (inputPassword.value.length == 0) {
              console.log('비밀번호 변경 없음')
            } else if (
              4 <= inputPassword.value.length &&
              inputPassword.value.length <= 12
            ) {
              data.roomPassword = inputPassword.value
            } else {
              editErrorMsg.value = '4 ~ 12자리의 비밀번호를 입력해주세요'
              errorFlag = true
            }
          }
        }

        if (roomInfo.value.title == '') {
          editErrorMsg.value = '방 이름을 정해 주세요'
          errorFlag = true
        }

        if (errorFlag) {
          roomInfo.value.isPrivate = Boolean(roomInfo.value.isPrivate)
          errorFlag = false
          return
        }

        const response = await api.room.editRoom(roomId, data)

        if (response.data.statusCode === 200) {
          Swal.fire({
            icon: 'success',
            text: '방 정보 수정이 완료되었습니다',
          })
          store.dispatch('getRoomInfo', roomId)
          context.emit('close')
        }
      } catch (err) {
        console.log(err)
      }
    }
    return {
      editErrorMsg,
      originPrivate,
      inputPassword,
      roomInfo,
      userInfo,
      editRoom,
      isPrivate,
    }
  },
}
</script>

<style></style>
