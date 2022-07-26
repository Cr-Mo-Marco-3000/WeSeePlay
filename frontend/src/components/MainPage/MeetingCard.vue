<template>
  <div id="card-box" class="flex flex-center">
      <div id="card-container" @click="icon = true">
        <div id="card-head" class="row">
   
          <h4 id="card-title" class="col-11">
            {{cardInfo.title}}
          </h4>
          <i
            id="isprivate-icon"
            class="material-icons col-1"
            v-show="is_private"
          >lock</i>   
          <div id="card-host" class="col-12">
            host: {{cardInfo.host}}
          </div>
        </div>
        
        <div class="col-12 flex justify-end items-center">
          <span id="people-number">
            &nbsp; {{cardInfo.participate}}명/{{cardInfo.wholenum}}명
          </span>
        </div>
      </div>
  </div>
  <card-modal></card-modal>
</template>

<script>
import { ref } from 'vue'
import CardModal from '@/components/MainPage/CardModal'

export default {
  components: {
    CardModal,
  },
  props:{
    info: Object,
  },
  setup(props){
    // console.log(props)
    // console.log(props.info)
    const cardInfo = ref(props.info)
    const is_private = ref(1)
    is_private.value =cardInfo.value['private']
    return{
      is_private, cardInfo,
      icon:ref(false)
    }
  }

}
</script>

<style scoped>

#card-container {
  width: 100%;
  margin-bottom:5%;
  height: 80%;
  padding: 5%;
  background: rgba(255, 255, 255, 0.375);
  box-shadow: 0 0.75rem 2rem 0 rgba(0, 0, 0, 0.1);
  border-radius: 2rem;
  border: 1px solid rgba(255, 255, 255, 0.125);
}
#card-head{
  text-align: left;
}
#card-title{
  margin:0%;
  margin-top:3%;
  margin-bottom:15%;
}
#card-host{
  min-height: 50px;
}
#isprivate-icon{
  position: relative;
  display:block;
  color:#027BE3;
  font-size: 300%;
}

#people-number {
  vertical-align: 20%;

}
#card-container:hover,
#card-container:focus {
  box-shadow: inset 6.5em 0 0 0 var(--hover);
}
@media only screen and (min-width: 600px){
  #card-container {
    width: 95%;
    margin-bottom:5%;
    height: 80%;
    padding: 5%;
    background: rgba(255, 255, 255, 0.375);
    box-shadow: 0 0.75rem 2rem 0 rgba(0, 0, 0, 0.1);
    border-radius: 2rem;
    border: 1px solid rgba(255, 255, 255, 0.125);
  }
}
</style>

