<template>
    <div style='position: relative'>
        <button @click.stop='changeMode'>Change Mode</button>
        <div>VideoArea.vue</div>
        <GameVideo v-if="isGameMode" :users="userList"/>
        <MeetingVideo v-else :users="userList"/>
    </div>
</template>

<script>
import GameVideo from './game/liargame/VideoList.vue'
import MeetingVideo from './meeting/VideoList.vue'
import { ref, watchEffect } from 'vue'

export default {    
    name: 'VideoArea',

    components: {
        GameVideo, MeetingVideo,
    },

    setup() {
        const isGameMode= ref(false)
        const userList = ref([])
        const changeMode = function () {
            isGameMode.value = !isGameMode.value
        }
        
        const customNumber = ref(13)

        for (let i = 1; i < customNumber.value; i++) {
            const id = i
            const nickname = String.fromCharCode(i + 64)
            const onVideo = false
            userList.value.push({id, nickname, onVideo})
        }

        watchEffect((customNumber) => {
            console.log(customNumber)
        })
        return {
            isGameMode,
            changeMode,
            userList,
            customNumber
        }
    }
}
</script>

<style>

</style>