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

        <div class="row">
          <div id="tool-bar" class="row col-12 flex items-center justify-center">
            <button id="room-create-btn" class="col-md-2 col-sm-2 col-12" @click="createRoomModal = true">
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
            class="col-lg-4 col-md-6 col-12"
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
// import MeetingToggleBtn from '@/components/MainPage/MeetingToggleBtn'
import CardModal from '@/components/MainPage/CardModal' 
import { reactive, ref, watchEffect  } from 'vue'
import axios from 'axios'
import api from '@/api/api'


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
        const response = await axios({
          url: api.room.createRoom(),
          method: 'POST',
          data: {
            userEmail: lookupInfo,
          },
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
      // console.log(sortinglevel.value, meetingquery.value)
      // console.log(lookupInfo)
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
<style scoped>
#header {
  margin-bottom:5%;
  height: 80%;
  padding: 5%;
  background: rgba(255, 255, 255, 0.375);
  box-shadow: 0 0.75rem 2rem 0 rgba(0, 0, 0, 0.1);
  border-radius: 2rem;
  border: 1px solid rgba(255, 255, 255, 0.125);
}
#header-name{
  display: none;
}
#header-slogan{

  display:none;
}
#header-slogan2{
  display:none;
}

#meeting-list-page{
  position:relative;
}
#container {
  width:80%;
}
/* #search-enzine-section {
  margin:2rem;
} */
#room-create-btn{
  border-radius: 0.5rem;
  background-color: #9C27B0;
  color:white;
  min-height: 37.375px;
}
#private-btn{
  margin-left: 4%;
  margin-bottom: 0.5%
}
#tool-bar{
  margin-bottom: 3%;
}
#sort-line{
  margin-top: 1%;
}
#separator {
  background-color: white;
  width: 95%;
  margin-left: 1%;
  margin-right: 1%
}

.tgl {
  display: none;
}
.tgl, .tgl:after, .tgl:before, .tgl *, .tgl *:after, .tgl *:before, .tgl + .tgl-btn {
  box-sizing: border-box;
}
.tgl::-moz-selection, .tgl:after::-moz-selection, .tgl:before::-moz-selection, .tgl *::-moz-selection, .tgl *:after::-moz-selection, .tgl *:before::-moz-selection, .tgl + .tgl-btn::-moz-selection {
  background: none;
}
.tgl::selection, .tgl:after::selection, .tgl:before::selection, .tgl *::selection, .tgl *:after::selection, .tgl *:before::selection, .tgl + .tgl-btn::selection {
  background: none;
}
.tgl + .tgl-btn {
  outline: 0;
  display: block;
  width: 4rem;
  height: 2rem;
  position: relative;
  cursor: pointer;
  -webkit-user-select: none;
     -moz-user-select: none;
      -ms-user-select: none;
          user-select: none;
}
.tgl + .tgl-btn:after, .tgl + .tgl-btn:before {
  position: relative;
  display: block;
  content: "";
  width: 50%;
  height: 100%;
}
.tgl + .tgl-btn:after {
  left: 0;
}
.tgl + .tgl-btn:before {
  display: none;
}
.tgl:checked + .tgl-btn:after {
  left: 50%;
}

.tgl-light + .tgl-btn {
  background: #f0f0f0;
  border-radius: 2rem;
  padding: 2px;
  transition: all 0.4s ease;
}
.tgl-light + .tgl-btn:after {
  border-radius: 50%;
  background: #fff;
  transition: all 0.2s ease;
}
.tgl-light:checked + .tgl-btn {
  background: #027BE3;
}

@media only screen and (min-width: 600px){
  #room-create-btn{
    border-radius: 0.5rem;
    margin-left: 0.5%;
    margin-right: 1%;
    background-color: #9C27B0;
    color:white;
  }
  #header {
    margin-bottom:5%;
    margin-left:2%;
    margin-right:2%;
    padding: 5%;
    background: rgba(255, 255, 255, 0.375);
    box-shadow: 0 0.75rem 2rem 0 rgba(0, 0, 0, 0.1);
    border-radius: 2rem;
    border: 1px solid rgba(255, 255, 255, 0.125);
  }
}

@media only screen and (min-width: 770px){
  /* #room-create-btn{
    border-radius: 0.5rem;
    margin:0.75%;
    background-color: #9C27B0;
    color:white;
  } */
}

@media only screen and (min-width: 1024px){
  /* #room-create-btn{
    border-radius: 0.5rem;
    margin:1px;
    background-color: #9C27B0;
    color:white;
  } */
  #header-slogan2{
    font-size: 300%;
    left: 0; 
    right: 0; 
    margin-left: auto; 
    margin-right: auto; 
    top:29%;
    display:block;
  }
}

@media only screen and (min-width: 1125px){
  #header-slogan2{
    font-size: 300%;
    left: 0; 
    right: 0; 
    margin-left: auto; 
    margin-right: auto; 
    top:36%;
    display:block;
  }
}

@media only screen and (min-width: 1440px){
  #header-slogan2{
    display:none;
  }
  #header-name{
    position: absolute;
    font-size: 700%;
    display:block;
  }
  #header-slogan{
    position: absolute;
    font-size: 450%;
    right: 15%;
    top:34%;
    display:block;
  }
}
</style>
