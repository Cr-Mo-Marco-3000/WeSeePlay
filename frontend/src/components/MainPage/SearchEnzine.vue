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
@import '../../assets/mainpage/mainpage.css';
</style>