<template>
  <q-dialog>
    <q-card id="card-modal">
      <div>
        <p>title</p>
        <input
          type="text"
          name="title"
          v-model="roomInfo.title"
        />
      </div>
      <div>
        <p>description</p>
        <input
          type="text"
          name="descript"
          v-model="roomInfo.descript"
        />
      </div>
      <div>
        <p>conferencePassword</p>
        <input
          type="password"
          name="conferencePassword"
          v-model="roomInfo.conferencePassword"
        />
      </div>
      <div>
        <p>is_private</p>
        <input
          type="checkbox"
          name="is_private"
          v-model="roomInfo.is_private"
        />
      </div>
      <button @click="createRoom">생성</button>
    </q-card>
  </q-dialog>
</template>

<script>
import { ref, reactive } from 'vue'
// import { computed } from "vue"
import { useStore } from "vuex"
import api from '@/api/api'
import axios from 'axios'


export default {

  setup(){
    const store = useStore();
    const token = store.state.users.token
    // 방 생성 정보
    let roomInfo = reactive({
      title: '',
      descript: '어서와요',
      roomPassword: '',
      game:0,
      is_private:false
    })

    // 오류 메시지
    const roomCreateError = ref('') 
    const createRoom = async function () {
      try {
        const response = await axios({
          url: api.room.createRoom(),
          method: 'POST',
          headers:{"authorization":"Bearer"+ token.value},
          data: roomInfo
        })
        if (response.data.statusCode === 201) {
          console.log('방 생성 성공')
          roomCreateError.value = '방이 생성 되었습니다.'
        }
      } catch (err) {
        roomCreateError.value = '방 생성에 실패하였습니다.'
      }
    }

    return {
      roomInfo,
      createRoom,

    }
  }
  
}
</script>

<style scoped>
#card-modal{
  background-color:#9C27B0;
}
</style>