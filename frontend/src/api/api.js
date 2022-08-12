// 회원 서버 URL
import store from "@/store"
import axios from "axios"
const HOST = "https://i7a501.p.ssafy.io/api/v1"

const USERS = "/users"

const EMAIL = "/email"

const ROOMS = "/rooms"

const CERTIFICATION = "/certification"

// 아래 카카오 로그인을 할 때는, 프론트엔드쪽으로 포트 번호를 맞추어야 한다
// 즉 로컬 서버로 연결할 때는 8080, 배포 서버로 날릴 때는 본 주소로 날리자
// const KakaoLogin = 'http://localhost:8080/login/kakao'
const KakaoLogin = "https://i7a501.p.ssafy.io/login/kakao"

const kakaoSendToken = "https://i7a501.p.ssafy.io/api/v1/users/oauth/kakao"
// const kakaoSendToken = 'http://localhost:8010/api/v1/oauth/kakao'

const setRequest = function (method, url, data = "") {
  const req = {
    url: HOST + ROOMS + url,
    method,
    headers: store.getters.authHeader,
  }
  if (data !== "") {
    req.data = data
  }
  return req
}

const sendAxios = async function (req) {
  let res = ""
  try {
    res = await axios(req)
  } catch (error) {
    res = error.response
  }
  return res
}

export default {
  // 회원 관련 API
  users: {
    // 회원가입
    signup: () => HOST + USERS,

    // 이메일 체크
    duplicateEmail: (email) =>
      HOST + USERS + EMAIL + `/duplicate/check?userEmail=${email}`,

    sendEmail: () => HOST + USERS + EMAIL + CERTIFICATION,

    verifyEmail: (email) =>
      HOST + USERS + EMAIL + CERTIFICATION + `/check?userEmail=${email}`,

    // 내 계정 정보(토큰인증)
    checkToken: () => HOST + USERS + "/me",

    // 로그인
    login: () => HOST + USERS + "/auth" + "/login",

    // 닉네임 정보 수정
    changeNickname: () => HOST + USERS + "/nickname",

    // 비밀번호 수정
    changePassword: () => HOST + USERS + "/password",

    // 이메일 체크(비밀번호 찾기)
    sendEmailForPW: () => HOST + USERS + EMAIL + CERTIFICATION + "/pw",

    verifyEmailForPW: () => HOST + USERS + EMAIL + CERTIFICATION + "/pw/check",

    // verifyEmailForPW: (email) =>
    //   HOST + USERS + EMAIL + CERTIFICATION + `/pw/check?userEmail=${email}`,

    /* ---------- 카카오 관련 API ---------- */

    // 카카오 redirect uri
    kakaoLogin: () => KakaoLogin,

    // 카카오 토큰 백엔드 전송 api => 수정 필요
    kakaoSendToken: () => kakaoSendToken,
  },
  /* ---------- ROOM 관련 API ---------- */
  room: {
    // ROOM 생성, 삭제, 조회를 한번에!

    createRoom: async (data) => {
      const req = setRequest("POST", "", data)
      const res = await sendAxios(req)
      return res
    },

    roomInfo: async (roomId) => {
      const req = setRequest("GET", `/info/${roomId}`)
      const res = await sendAxios(req)
      return res
    },

    // enterRoom: () => HOST + ROOMS + "/enter",
    enterRoom: async (data) => {
      const req = setRequest("POST", "/enter", data)
      const res = await sendAxios(req)
      return res
    },

    editRoom: async (roomId, data) => {
      const req = setRequest("PATCH", `/${roomId}`, data)
      const res = await sendAxios(req)
      return res
    },

    leaveRoom: async (data) => {
      const req = setRequest("DELETE", "/leave", data)
      const res = await sendAxios(req)
      return res
    },

    killRoom: async (data) => {
      const req = setRequest("DELETE", "", data)
      const res = await sendAxios(req)
      return res
    },
  },
}
