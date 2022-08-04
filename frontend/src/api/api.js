const HOST = 'http://localhost:8080/api/v1'

const USERS = '/users'

const EMAIL = '/email'

const ROOMS = '/rooms'

const CERTIFICATION = '/certification'

const KakaoLogin = 'http://localhost:8081/login/kakao'

const kakaoSendToken = 'http://localhost:8080/api/v1/oauth/kakao'

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
    checkToken: () => HOST + USERS + '/me',

    // 로그인
    login: () => HOST + '/auth' + '/login',

    // 닉네임 정보 수정
    changeNickname: () => HOST + USERS + '/nickname',

    // 비밀번호 수정
    changePassword: () => HOST + USERS + '/password',

    // 임시 비밀번호 발송
    requireTempPassword: () => HOST + USERS + EMAIL + CERTIFICATION + '/pw',

    // 이메일 인증 확인
    getTempPassword: (email) =>
      HOST + USERS + EMAIL + CERTIFICATION + `/pw/check?userEmail=${email}`,

    // 카카오 관련 API

    // 카카오 redirect uri
    kakaoLogin: () => KakaoLogin,

    // 카카오 토큰 백엔드 전송 api => 수정 필요
    kakaoSendToken: () => kakaoSendToken,
  },
  // ROOM 관련 API
  room: {
    // ROOM 생성, 삭제, 조회를 한번에!
    createRoom: () => HOST + ROOMS,
  },
}
