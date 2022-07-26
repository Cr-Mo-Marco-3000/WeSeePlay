const HOST = 'http://localhost:8080/api/v1'

const USERS = '/users'
const EMAIL = '/email'

const ROOMS = '/rooms'

export default {
  // 회원 관련 API
  users: {
    // 회원가입
    signup: () => HOST + USERS,

    // 이메일 체크
    duplicateEmail: () => HOST + USERS + EMAIL + '/duplicate/check',
    sendEmail: () => HOST + USERS + EMAIL + '/certification',
    verifyEmail: (email) =>
      HOST + USERS + EMAIL + `/certification?userEmail=${email}`,

    // 내 계정 정보(토큰인증)
    checkToken: () => HOST + USERS + '/me',

    // 로그인
    login: () => HOST + USERS + '/auth' + '/login',

    // 닉네임 정보 수정
    changeNickname: () => HOST + USERS + '/nickname',

    // 비밀번호 수정
    changePassword: () => HOST + USERS + '/password',

    // 회원 삭제? => 보류
  },
  // ROOM 관련 API
  room: {
    // ROOM 생성, 삭제, 조회를 한번에!
    createRoom: () => HOST + ROOMS,
  },
}
