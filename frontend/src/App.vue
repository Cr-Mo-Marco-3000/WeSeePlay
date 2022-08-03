<template>
  <router-view />
</template>

<script>
export default {
  name: 'LayoutDefault',
  setup() {
    /* eslint-disable */
    Kakao.init(process.env.VUE_APP_KAKAO_JAVASCRIPT_API_KEY)
    console.log(Kakao.isInitialized())
  },
}
</script>
<style>
@font-face {
  font-family: 'paybooc-Bold';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-07@1.0/paybooc-Bold.woff')
    format('woff');
  font-weight: normal;
  font-style: normal;
}

#app {
  font-family: 'paybooc-Bold';
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  min-width: 100%;
  height: 100%;
  padding: 0;
}
</style>
