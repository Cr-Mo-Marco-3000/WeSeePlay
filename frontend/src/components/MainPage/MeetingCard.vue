<template>
  <div id="card-box" class="flex flex-center">
      <div id="card-container" @click="icon = true">
        <div id="card-head" class="row">
   
          <h5 id="card-title" class="col-11">
            {{cardInfo.title}}
          </h5>
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

@import '../../assets/mainpage/card.css';
</style>

