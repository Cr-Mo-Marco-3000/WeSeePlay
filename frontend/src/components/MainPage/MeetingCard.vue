<template>
  <div id="card-box" class="flex flex-center">
      <div id="card-container" @click="icon = true">
        <div id="card-head">
   
          <span id="card-title">
            {{cardInfo.title}}
          </span>
          <i
            id="isprivate-icon"
            class="fa-solid fa-lock"
            v-show="is_private"
          ></i>   
        </div>

        <div id="card-host" class="col-12">
          host: {{cardInfo.host}}
        </div>
        
        <div class="col-12 flex justify-end items-center">
          <span id="people-number">
            &nbsp; {{cardInfo.participate}}명/{{cardInfo.wholenum}}명
          </span>
        </div>
      </div>
  </div>
</template>

<script>
import { ref } from 'vue'

export default {
  components: {
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

