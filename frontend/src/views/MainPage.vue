<template>
  <TheBackground />
  <div id="meeting-list-page">
    <nav-bar @change-pw="isChangePw = true"></nav-bar>
    <div class="flex flex-center">
      <div id="container" class="flex flex-center">
        <div class="main-text">
          <img src="../assets/logo_nontext.png" />
          <h6>We See! We Play!</h6>
        </div>
        <div id="body-content" class="row">
          <div
            id="tool-bar"
            class="row col-12 flex items-center justify-center"
          >
            <div class="search-form">
              <button id="room-create-btn" @click="isCreateRoomModal = true">
                방 생성
                <span class="material-icons"> group_add </span>
              </button>
              <div id="search-enzine-section">
                <search-enzine
                  :meetingquery="lookupInfo.query"
                  @queryFromChild="getQuery"
                ></search-enzine>
              </div>
            </div>

            <div id="sort-line">
              <div class="private-box">
                <div id="private-btn">
                  <input
                    v-model="isPrivatebtn"
                    class="tgl tgl-light"
                    id="cb1"
                    type="checkbox"
                  />
                  <label class="tgl-btn" for="cb1"></label>
                </div>
                <span v-if="isPrivatebtn">공개방 조회</span>
                <span v-else>전체방 조회</span>
              </div>
              <div id="sort-method">
                <meeting-card-sort
                  :sortinglevel="lookupInfo.sortingOrder"
                  :sortingmethod="lookupInfo.sortingMethod"
                  @changeSortLevel="changeSortLevel"
                  @changeSortMethod="changeSortMethod"
                ></meeting-card-sort>
              </div>
            </div>

            <div id="separator">
              <hr />
            </div>
          </div>

          <div
            class="col-md-4 col-sm-6 col-12"
            v-for="info in roomsInfo"
            :key="info"
          >
            <meeting-card :info="info" @click="openDetail(info)"></meeting-card>
          </div>
          <div class="col-12">
            <div class="q-pa-lg flex flex-center">
              <q-pagination
                v-model="lookupInfo.pageNumber"
                :max="maxpage"
                input
              />
            </div>
          </div>
        </div>
      </div>
    </div>
    <CreateRoomModal
      v-if="isCreateRoomModal"
      @close="isCreateRoomModal = false"
    >
      <CreateRoomModalContent />
    </CreateRoomModal>

    <DetailModal v-if="isDetailModal" @close="isDetailModal = false">
      <DetailModalContent :roomId="detailRoomId" />
    </DetailModal>

    <AuthModal v-if="isChangePw" @close="isChangePw = false">
      <AuthModalContent />
    </AuthModal>
  </div>
</template>

<script>
import TheBackground from "@/components/TheBackground.vue"
import SearchEnzine from "@/components/MainPage/SearchEnzine"
import NavBar from "@/components/MainPage/NavBar"
import MeetingCard from "@/components/MainPage/MeetingCard"
import MeetingCardSort from "@/components/MainPage/MeetingCardSort"
import CreateRoomModal from "@/components/MainPage/Modal/CreateRoomModal.vue"
import CreateRoomModalContent from "@/components/MainPage/Modal/CreateRoomModalContent.vue"
import DetailModal from "@/components/MainPage/Modal/DetailModal.vue"
import DetailModalContent from "@/components/MainPage/Modal/DetailModalContent.vue"
import AuthModal from "@/components/MainPage/Modal/AuthModal.vue"
import AuthModalContent from "@/components/MainPage/Modal/AuthModalContent.vue"
import { reactive, ref, watchEffect, onBeforeMount } from "vue"
import axios from "axios"
// import api from "@/api/api"
import { useStore } from "vuex"

export default {
  name: "MainPage",
  components: {
    TheBackground,
    SearchEnzine,
    NavBar,
    MeetingCard,
    MeetingCardSort,
    CreateRoomModal,
    CreateRoomModalContent,
    DetailModal,
    DetailModalContent,
    AuthModal,
    AuthModalContent,
  },

  setup() {
    const store = useStore()
    const token = store.state.users.token
    // meetingquery
    let meetingquery = ref("")
    let lookupErrorMsg = ref("")

    function getQuery(data) {
      meetingquery.value = data
      lookupInfo.query = data
    }

    // sorting level
    function changeSortLevel() {
      if (lookupInfo.sortingOrder == "toUp") {
        lookupInfo.sortingOrder = "toDown"
      } else {
        lookupInfo.sortingOrder = "toUp"
      }
    }
    function changeSortMethod() {
      if (lookupInfo.sortingMethod == "byTime") {
        lookupInfo.sortingMethod = "byNumber"
      } else {
        lookupInfo.sortingMethod = "byTime"
      }
    }

    // request용
    let isPrivatebtn = ref(true)
    let lookupInfo = reactive({
      pageNumber: 1,
      contentsCount: 6,
      sortingOrder: "toDown",
      sortingMethod: "byTime",
      query: meetingquery.value,
      queryType: 1,
      isPrivate: false,
    })

    //  적당한 input 생성용
    let i
    let tmparr = ref([])
    for (i = 0; i < 25; i++) {
      tmparr.value.push({
        roomId: i,
        hostNickname: i,
        callStartTime: "2022.08.01",
        title: "님만 오면 고" + i,
        descript: "방 설명",
        isPrivate: i % 2,
        game: 0,
        joinUsers: ["유저1", "유저2", "유저3", "유저4", "유저5"],
      })
    }

    //paginator용
    let maxpage = ref(1)

    // data 획득
    let roomsInfo = ref(Array)
    onBeforeMount(async () => {
      console.log(lookupInfo.sortingOrder, lookupInfo.sortingMethod)
      try {
        // query String 생성
        if (isPrivatebtn.value) {
          lookupInfo.isPrivate = 1
        } else {
          lookupInfo.isPrivate = 0
        }
        let querystring = "/?"
        for (let key in lookupInfo) {
          let value = lookupInfo[key]
          querystring += key + "=" + value + "&"
        }
        console.log(querystring.slice(0, -1))
        console.log("authorization : Bearer " + token)
        const response = await axios({
          url:
            "https://i7a501.p.ssafy.io/api/v1/rooms" + querystring.slice(0, -1),
          method: "GET",
          headers: { authorization: "Bearer " + token },
        })
        console.log(response.data)
        if (response.status === 200) {
          console.log("조회 성공!")
          roomsInfo.value = response.data.content
          // paginator의 총 페이지 수
          console.log("페이지네이터 내와")
          maxpage.value = response.data.totalPage
        }
      } catch (err) {
        lookupErrorMsg.value = "조회 실패."
        // 조회 실패 시 더미 데이터
        roomsInfo.value = [
          ...tmparr.value.slice(
            6 * lookupInfo.pageNumber - 6,
            6 * lookupInfo.pageNumber
          ),
        ]
        if (tmparr.value.length % 6) {
          maxpage.value = parseInt(tmparr.value.length / 6)
        } else {
          maxpage.value = parseInt(tmparr.value.length / 6)
        }
      }
    })
    watchEffect(async () => {
      console.log(lookupInfo.sortingOrder, lookupInfo.sortingMethod)
      try {
        // query String 생성
        if (isPrivatebtn.value) {
          lookupInfo.isPrivate = 1
        } else {
          lookupInfo.isPrivate = 0
        }
        let querystring = "/?"
        for (let key in lookupInfo) {
          let value = lookupInfo[key]
          querystring += key + "=" + value + "&"
        }
        console.log(querystring.slice(0, -1))
        console.log("authorization : Bearer " + token)
        const response = await axios({
          url:
            "https://i7a501.p.ssafy.io/api/v1/rooms" + querystring.slice(0, -1),
          method: "GET",
          headers: { authorization: "Bearer " + token },
        })
        console.log(response.data)
        if (response.status === 200) {
          console.log("조회 성공!")
          roomsInfo.value = response.data.content
          // paginator의 총 페이지 수
          console.log("페이지네이터 내와")
          maxpage.value = response.data.totalPage
        }
      } catch (err) {
        lookupErrorMsg.value = "조회 실패."
        // 조회 실패 시 더미 데이터
        roomsInfo.value = [
          ...tmparr.value.slice(
            6 * lookupInfo.pageNumber - 6,
            6 * lookupInfo.pageNumber
          ),
        ]
        if (tmparr.value.length % 6) {
          maxpage.value = parseInt(tmparr.value.length / 6)
        } else {
          maxpage.value = parseInt(tmparr.value.length / 6)
        }
      }
    })

    const isDetailModal = ref(false)
    const detailRoomId = ref({})
    const openDetail = function (info) {
      isDetailModal.value = true
      detailRoomId.value = info.roomId
    }

    return {
      // 검색창
      meetingquery,
      getQuery,

      // 정렬 방법
      changeSortMethod,
      changeSortLevel,
      isPrivatebtn,

      // 회의방 정보 배열(객체)
      // 페이지네이터
      maxpage,

      // 임시
      lookupInfo,
      isCreateRoomModal: ref(false),
      isDetailModal,
      roomsInfo,
      // roomsData,

      openDetail,
      detailRoomId,

      isChangePw: ref(false),
    }
  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
@import "../assets/mainpage/mainpage.css";
</style>
