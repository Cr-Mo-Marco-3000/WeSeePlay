<template>
  <div></div>
</template>

<script setup>
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'
import api from '@/api/api'
import store from '@/store'
// import store from '@/store/index.js'
const route = useRoute()
const router = useRouter()

const sendQuery = async function () {
  try {
    const code = route.query.code
    // 파라미터(!)로 다음 정보들을 담아서 POST로(!) 전송하면 토큰이 옴
    const params = {
      grant_type: 'authorization_code',
      client_id: process.env.VUE_APP_KAKAO_JAVASCRIPT_API_KEY,
      code: code,
      redirect_uri: api.users.kakaoLogin(),
      client_secret: process.env.VUE_APP_KAKAO_CLIENT_SECRET,
    }
    let response = await axios({
      url: 'https://kauth.kakao.com/oauth/token',
      method: 'POST',
      params: params,
    })
    // 받은 토큰을 백으로 전송하는 부분에서부터는 백에서 담당
    let token = response.data.access_token
    // 백으로부터 받은 response를 가지고 또 일해야 한다.
    response = await axios({
      url: api.users.kakaoSendToken() + `?accessToken=${token}`,
      method: 'GET',
    })

    token = response.data.accessToken
    if (response.status === 200) {
      store.dispatch('saveToken', token)
      store.dispatch('fetchMe')
      router.push({ name: 'mainpage' })
    }
  } catch (err) {
    router.push({ name: 'errorpage', params: { errorname: 500 } })
  }
}
sendQuery()
</script>

<style></style>
