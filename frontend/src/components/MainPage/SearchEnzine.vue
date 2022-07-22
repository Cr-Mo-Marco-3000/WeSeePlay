<template>
	<input
		id="search-enzine-bar"
		rounded 
		v-model="query"
		outlined
		placeholder="search with title or host"
		/>
</template>
<script>
import{ref, watchEffect} from 'vue'

export default ({
	name: 'SearchEnzine',
	props:{
		meetingquery:String,
	},
	setup(props, { emit }) {
		const query = ref('')

		watchEffect(() => {
			emit('queryFromChild',query.value)
		})
		return{
			query,
		}
	},
})
</script>

<style scoped>
#search-enzine-bar{
  margin: .5rem 0;
  height: 2.5rem;
  border-radius: 0.5rem;
  width: 100%;
  padding: 0 1rem;
  border: solid 2px rgba(0, 0, 0, 0.616);
  outline: none;
  background: rgba(255, 255, 255, 0.5);
}

#search-enzine-bar:focus{
  background: rgb(105,94,239);
	background: linear-gradient(90deg, rgba(105,94,239,0.4) 0%, rgba(114,220,233,0.4) 87%);
}
</style>