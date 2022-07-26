<template>
    <div id="sort-area">
        <a
            style="color: black; cursor: pointer;"
            v-if="sortingmethod === 'byTime'"
            @click="changeSortMethod"
        >시간 순 </a>
        <a
            style="color: black; cursor: pointer;"
            v-else
            @click="changeSortMethod"
        >인원 순 </a>
        <span> | </span>
        <a
            style="color: black; cursor: pointer;"
            v-if="sortinglevel === 'toUp'"
            @click="changeSortLevel"
        >오름 </a>
        <a
            v-else
            style="color: black; cursor: pointer;"
            @click="changeSortLevel"
        >내림 </a>
    </div>

</template>

<script>
// import { ref } from 'vue'

export default {
  props:{
    sortinglevel: String,
    sortingmethod: String,
  },
  setup(props, { emit }){
    function changeSortLevel(e){
        emit('changeSortLevel', e)
    }
    function changeSortMethod(e){
        emit('changeSortMethod', e)
    }
    return{
        changeSortLevel,
        changeSortMethod
    }
  }

}
</script>

<style scoped>
@import '../../assets/mainpage/mainpage.css';
</style>