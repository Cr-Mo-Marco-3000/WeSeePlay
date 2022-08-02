<template>
  <div id="meeting-list-page">  
  <nav-bar @change-pw="isChangePw = true"></nav-bar>
    <div class="flex flex-center">
      <div id="container" class="flex flex-center">
        <div class="main-text">
          <img src="../assets/logo_nontext.png">
          <h6>We See! Wee Play!</h6>
        </div>
        <div id="body-content" class="row">
          
          <div id="tool-bar" class="row col-12 flex items-center justify-center">
            <div class="search-form">
              <button id="room-create-btn" @click="isCreateRoomModal = true">
                방 생성
                <span 
                  class="material-icons"
                >
                  group_add
                </span>
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
                  <input v-model="lookupInfo.isprivate" class="tgl tgl-light" id="cb1" type="checkbox"/>
                  <label class="tgl-btn" for="cb1"></label>
                </div>
                <span v-if="lookupInfo.isprivate">공개방 조회</span>
                <span v-else>전체방 조회</span>
              </div>
              <div id="sort-method">
                <meeting-card-sort
                  :sortinglevel="sortinglevel"
                  :sortingmethod="sortingmethod"
                  @changeSortLevel="changeSortLevel"
                  @changeSortMethod="changeSortMethod"
                ></meeting-card-sort>
              </div>
            </div>

            <div id="separator">
              <hr>
            </div>
          </div>

          <div
            class="col-md-4 col-sm-6 col-12"
            v-for="info in meetingInfo"
            :key="info"
          >
            <meeting-card
              :info="info"
              @click="openDetail(info)"
            ></meeting-card>
          </div>
          <div class="col-12">
            <div class="q-pa-lg flex flex-center">
              <q-pagination
                v-model="currentpage"
                :max="maxpage"
                input
              />
            </div>
          </div>
        </div>

      </div>
    </div>
    <CreateRoomModal v-if="isCreateRoomModal" @close="isCreateRoomModal = false">
      <CreateRoomModalContent />
    </CreateRoomModal>
    
    <DetailModal v-if="isDetailModal" @close="isDetailModal = false">
      <DetailModalContent :info="detailInfo" />
    </DetailModal>

    <AuthModal v-if="isChangePw" @close="isChangePw = false">
     <AuthModalContent />
    </AuthModal>   
  </div>
  
</template>

<script>
import SearchEnzine from '@/components/MainPage/SearchEnzine'
import NavBar from '@/components/MainPage/NavBar'
import MeetingCard from '@/components/MainPage/MeetingCard'
import MeetingCardSort from '@/components/MainPage/MeetingCardSort'
import CreateRoomModal from '@/components/MainPage/Modal/CreateRoomModal.vue'
import CreateRoomModalContent from '@/components/MainPage/Modal/CreateRoomModalContent.vue' 
import DetailModal from '@/components/MainPage/Modal/DetailModal.vue'
import DetailModalContent from '@/components/MainPage/Modal/DetailModalContent.vue'
import AuthModal from "@/components/MainPage/Modal/AuthModal.vue"
import AuthModalContent from "@/components/MainPage/Modal/AuthModalContent.vue"
import { reactive, ref, watchEffect  } from 'vue'
import axios from 'axios'
import api from '@/api/api'
import { useStore } from "vuex"


export default {
  name: 'MainPage',
  components: {
    SearchEnzine,
    NavBar,
    MeetingCard,
    MeetingCardSort,
    CreateRoomModal,
    CreateRoomModalContent,
    DetailModal,
    DetailModalContent,
    AuthModal,
    AuthModalContent
  },
  setup(){
    const store = useStore();
    const token = store.state.users.token
    // meetingquery
    let meetingquery = ref('')
    let lookupErrorMsg = ref('')

    function getQuery(data){
      meetingquery.value = data
      lookupInfo.query = data
    }

    // sorting level
    let sortinglevel = ref("toUp")
    let sortingmethod = ref("byTime")
    function changeSortLevel(){
      if(sortinglevel.value=="toUp"){
        sortinglevel.value="toDown"
      }
      else{
        sortinglevel.value="toUp"
      }
    }
    function changeSortMethod(){
      if(sortingmethod.value=="byTime"){
        sortingmethod.value="byNumber"
      }
      else{
        sortingmethod.value="byTime"
      }
    }

    // request용
    let lookupInfo = reactive({
      pageNumber: 1,
      pageSize: 6,
      sort: {
        sortingLevel:sortinglevel.value,
        sortingMethod:sortingmethod.value,
      },
      query: meetingquery.value,
      isPrivate:true,
    })


    //  적당한 input 생성용
    let i
    let tmparr = ref([])
    for(i=0;i<25;i++){
      tmparr.value.push({'callStartTime': "2022.08.01" ,'descript': '방 설명', 'joinUser': ['유저1', '유저2', '유저3', '유저4', '유저5'], 'game': 0,'title': "님만 오면 고", 'host': i, 'participate': i, 'wholenum': 12, 'private': i%2,
      })
    }
    
    //paginator용 계산
    const currentpage = ref(1)
    let maxpage =ref(1)
    if(tmparr.value.length%6){
      maxpage.value = parseInt(tmparr.value.length/6)+1
    }
    else{
      maxpage.value = parseInt(tmparr.value.length/6)
    }

    // meetingInfo 대입, 여기서 데이터 받을까요?
    let meetingInfo = ref(Array)
    meetingInfo.value={
      ...tmparr.value.slice(6*currentpage.value-6, 6*currentpage.value)
    }
    // data 획득
    const roomData = async function () {
      try {
        let querystring = '/?'
        for(let key in lookupInfo){
          let value = lookupInfo[key]
          querystring +=key+"="+value+"&"
        }
        console.log(querystring.slice(0, -1))
        const response = await axios({
          url: api.room.createRoom()+querystring.slice(0, -1),
          method: 'POST',
          headers:{"authorization":"Bearer"+ token.value},
        })
        if (response.data.statusCode === 200) {
          console.log('조회 성공!')
          const roomsInfo = response.content
          console.log(roomsInfo)
        }
      } catch (err) {
        lookupErrorMsg.value =
          '조회 실패.'
      }
    }
    // data 획득
    // const getDetail = async function (conference_id) {
    //   try {
    //     const response = await axios({
    //       url: api.room.createRoom()+conference_id,
    //       method: 'get',
    //     })
    //     if (response.data.statusCode === 200) {
    //       console.log('조회 성공!')
    //       const roomDetail = response
    //       console.log(roomDetail)
    //     }
    //   } catch (err) {
    //     lookupErrorMsg.value =
    //       '조회 실패.'
    //   }
    // }

    watchEffect(() => {
      meetingInfo.value={
        ...tmparr.value.slice(6*currentpage.value-6, 6*currentpage.value)
      }
    })

    const isDetailModal = ref(false)
    const detailInfo = ref({})
    const openDetail = function(info) {
      isDetailModal.value = true
      detailInfo.value = info
    }


    // console.log(meetingInfo, currentpage, maxpage)
    return{
      // 검색창
      meetingquery,
      getQuery,

      // 정렬 방법
      sortinglevel,
      sortingmethod,
      changeSortMethod,
      changeSortLevel,

      // 회의방 정보 배열(객체)
      meetingInfo,
      // 페이지네이터
      currentpage,
      maxpage,

      // 임시
      lookupInfo,
      isCreateRoomModal: ref(false),
      isDetailModal,
      roomData,
      
      openDetail,
      detailInfo,

      isChangePw: ref(false)
    }
  }

}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
@import '../assets/mainpage/mainpage.css';
</style>
