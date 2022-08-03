<template>
  <div>Meeting Video Area</div>
  <div class='row justify-center'>
    <VideoItem class='col' :class="users.length < 5 ? 'col-6' : 'col-4'" v-for="user in users" :key="user" :user='user'/>
  </div>
</template>

<script>
import VideoItem from './VideoItem.vue'

export default {
    components: { VideoItem },
    props: {
        users: {
            type: Array,
            required: true
        }
    },
}
</script>

<style>

</style>