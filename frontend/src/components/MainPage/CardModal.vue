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
          name="description"
          v-model="roomInfo.description"
        />
      </div>
      <div>
        <p>hostId</p>
        <input
          type="number"
          name="hostId"
          v-model="roomInfo.hostId"
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
        <p>game</p>
        <input
          type="number"
          name="game"
          v-model="roomInfo.game"
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
import api from '@/api/api'
import axios from 'axios'


export default {

  setup(){
    // 방 생성 정보
    let roomInfo = reactive({
      title: '',
      description: '',
      hostId: '',
      conferencePassword: '',
      game:'',
      is_private:false
    })

    // 오류 메시지
    const roomCreateError = ref('') 
    const createRoom = async function () {
      try {
        console.log(roomInfo)
        const response = await axios({
          url: api.room.createRoom(),
          method: 'POST',
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