<template>
  <div id="meeting-list-page">
    <nav-bar></nav-bar>
    <div class="flex flex-center">
      <div id="container" class="flex flex-center">
        <header id="header">
          <div id="header-name">
            <span>
              We See
              <br>
              Play
            </span>
          </div>
          <img class="logo-img" src="../assets/loungekiwi.png">
          <div id="header-slogan">
            <span>
              We See!
              <br>
              We Play!
            </span>
          </div>
          <div id="header-slogan2">
            <span>
              We See! We Play!
            </span>
          </div>
        </header>

        <div id="body-content" class="row">
          
          <div id="tool-bar" class="row col-12 flex items-center justify-center">
            <button id="room-create-btn" class="col-12 col-sm-2 col-md-2" @click="createRoomModal = true">
              방 생성
              <span 
                class="material-icons"
              >
                group_add
              </span>
            </button>
            <div id="search-enzine-section" class="col-md-9 col-sm-9 col-12">
              <search-enzine
                :meetingquery="lookupInfo.query"
                @queryFromChild="getQuery"
              ></search-enzine>
            </div>

            <div id="sort-line" class="row col-12">
              <div id="private-btn" class="col-5">
                <input v-model="lookupInfo.isprivate" class="tgl tgl-light" id="cb1" type="checkbox"/>
                <label class="tgl-btn" for="cb1"></label>
              </div>

              <div id="sort-method" class="col-6 row items-center justify-end">
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
              @click="getDetail"
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
    <card-modal
      v-model="createRoomModal"
    ></card-modal>
  </div>
  
</template>

<script>
import SearchEnzine from '@/components/MainPage/SearchEnzine'
import NavBar from '@/components/MainPage/NavBar'
import MeetingCard from '@/components/MainPage/MeetingCard'
import MeetingCardSort from '@/components/MainPage/MeetingCardSort'
import CardModal from '@/components/MainPage/CardModal' 
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
    CardModal
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
      tmparr.value.push({'title': "님만 오면 고", 'host': i, 'participate': i, 'wholenum': 12, 'private': i%2})
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
      createRoomModal: ref(false),
      roomData,
      
    }
  }

}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
@import '../assets/mainpage/mainpage.css';
</style>
