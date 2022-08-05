// 회원 서버 URL
// const HOST = 'http://localhost:8080/api/v1'
const AUTHHOST = 'http://i7a501.p.ssafy.io:8010/api/v1'

// 방 서버 URL
// Room Server가 배포되면 아래 주소로 바꾸어 끼자
// const ROOMHOST = "http://i7a501.p.ssafy.io:8011/api/v1"

const ROOMHOST = 'http://localhost:8080/api/v1'

const USERS = '/users'

const EMAIL = '/email'

const ROOMS = '/rooms'

const CERTIFICATION = '/certification'

// 아래 카카오 로그인을 할 때는, 프론트엔드쪽으로 포트 번호를 맞추어야 한다
// 즉 로컬 서버로 연결할 때는 8081, 배포 서버로 날릴 때는 8080으로 날리자
const KakaoLogin = 'http://localhost:8081/login/kakao'
// const KakaoLogin = 'http://localhost:8080/login/kakao'

const kakaoSendToken = 'http://i7a501.p.ssafy.io:8010/api/v1/oauth/kakao'

// const kakaoSendToken = 'http://localhost:8080/api/v1/oauth/kakao'

export default {
  // 회원 관련 API
  users: {
    // 회원가입
    signup: () => AUTHHOST + USERS,

    // 이메일 체크
    duplicateEmail: (email) =>
      AUTHHOST + USERS + EMAIL + `/duplicate/check?userEmail=${email}`,

    sendEmail: () => AUTHHOST + USERS + EMAIL + CERTIFICATION,

    verifyEmail: (email) =>
      AUTHHOST + USERS + EMAIL + CERTIFICATION + `/check?userEmail=${email}`,

    // 내 계정 정보(토큰인증)
    checkToken: () => AUTHHOST + USERS + '/me',

    // 로그인
    login: () => AUTHHOST + '/auth' + '/login',

    // 닉네임 정보 수정
    changeNickname: () => AUTHHOST + USERS + '/nickname',

    // 비밀번호 수정
    changePassword: () => AUTHHOST + USERS + '/password',

    // 이메일 체크(비밀번호 찾기)
    sendEmailForPW: () =>
      AUTHHOST + USERS + EMAIL + CERTIFICATION + '/pw',

    verifyEmailForPW: () =>
      AUTHHOST + USERS + EMAIL +CERTIFICATION + '/pw/check',


    // 이메일 인증 확인
    getTempPassword: (email) =>
      AUTHHOST + USERS + EMAIL + CERTIFICATION + `/pw/check?userEmail=${email}`,

    /* ---------- 카카오 관련 API ---------- */

    // 카카오 redirect uri
    kakaoLogin: () => KakaoLogin,

    // 카카오 토큰 백엔드 전송 api => 수정 필요
    kakaoSendToken: () => kakaoSendToken,
  },
  /* ---------- ROOM 관련 API ---------- */
  room: {
    // ROOM 생성, 삭제, 조회를 한번에!
    createRoom: () => ROOMHOST + ROOMS,
  },
}
