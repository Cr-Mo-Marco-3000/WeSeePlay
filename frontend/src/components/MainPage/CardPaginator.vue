<template>
  <div class="q-pa-lg flex flex-center">
    <q-pagination
      v-model="current"
      :max="maxpage"
      input
    />
  </div>
</template>

<script>
import { ref, defineEmits } from 'vue'

export default {
    props:{
        currentpage: Number,
        maxpage: Number
    },
  setup (props) {
    const current = ref(props.currentpage)
    const emit = defineEmits(current.value)
    console.log(emit, current.value)
    return {
      current,
      emit
    }
  }
}
</script>
