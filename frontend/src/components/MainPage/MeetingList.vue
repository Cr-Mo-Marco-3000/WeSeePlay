<template>
  <div id="meeting-list-page">
    <nav-bar></nav-bar>
    <div class="flex flex-center">

      <div id="container">
        <header class="header text-center">
          <h3>Meeting List
            <span class="material-icons">
              group_add
            </span>
          </h3>

        </header>
        <div class="row">
          <div id="sort-line" class="col-12">
            <div id="search-enzine-section">
              <search-enzine
                :meetingquery="meetingquery"
                @queryFromChild="getQuery"
              ></search-enzine>
            </div>

            <div class="row">
              <div class="col-6">
                <meeting-toggle-btn></meeting-toggle-btn>
              </div>
              <div class="col-6 flex items-end justify-end">
                <meeting-card-sort
                  :sortinglevel="sortinglevel"
                  :sortingmethod="sortingmethod"
                  @changeSortLevel="changeSortLevel"
                  @changeSortMethod="changeSortMethod"
                ></meeting-card-sort>
              </div>
            </div>

            <hr>
          </div>
          <div
            class="col-md-4 col-sm-6 col-12"
            v-for="info in meetingInfo"
            :key="info"
          >
            <meeting-card
              :info="info"
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
    
  </div>
  
</template>

<script>
import SearchEnzine from '@/components/MainPage/SearchEnzine'
import NavBar from '@/components/MainPage/NavBar'
import MeetingCard from '@/components/MainPage/MeetingCard'
import MeetingCardSort from '@/components/MainPage/MeetingCardSort'
import MeetingToggleBtn from '@/components/MainPage/MeetingToggleBtn'
import { ref, watchEffect  } from 'vue'

export default {
  name: 'MeetingList',
  components: {
    SearchEnzine,
    NavBar,
    MeetingCard,
    MeetingCardSort,
    MeetingToggleBtn,
  },
  setup(){
    // meetingquery
    let meetingquery = ref('')
    function getQuery(data){
      meetingquery = data
    }

    // sorting level
    let sortinglevel = ref("toup")
    let sortingmethod = ref("bytime")
    function changeSortLevel(){
      if(sortinglevel.value=="toup"){
        sortinglevel.value="todown"
      }
      else{
        sortinglevel.value="toup"
      }
    }
    function changeSortMethod(){
      if(sortingmethod.value=="bytime"){
        sortingmethod.value="bydomething"
      }
      else{
        sortingmethod.value="bytime"
      }
    }


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

      // 함수

      
    }
  }

}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
#container {
  width:80%;
}
#search-enzine-section {
  margin:2rem;
}
#sort-line{
  margin-bottom: 4%;
}
</style>
