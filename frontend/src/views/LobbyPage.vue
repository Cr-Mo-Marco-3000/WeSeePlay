<template>
  <h1>Webcam Test</h1>
  <div class="buttons">
    <button @click="startWebcam()">OPEN</button>
    <button @click="stopWebcam()">CLOSE</button>
  </div>
  <video id="webcam-id" autoplay="true"></video>
</template>

<script setup>
const videoEl = document.getElementById("webcam-id")
const startWebcam = function () {
  if (navigator.mediaDevices.getUserMedia) {
    navigator.mediaDevices //카메라 권한 정보 받아오기
      .getUserMedia({ video: true })
      .then((stream) => {
        videoEl.srcObject = stream // 받아온 stream을 비디오 src로 설정 \
        console.log(stream)
      })
      .catch((err) => {
        console.log("카메라 정보 없음", err)
      })
  }
}

const stopWebcam = () => {
  const stream = videoEl.srcObject
  const tracks = stream.getTracks()
  for (let i = 0; i < tracks.length; i++) {
    const track = tracks[i]
    track.stop()
  }
  videoEl.srcObject = null
}
</script>

<style scoped>
/* @import "../assets/lobbypage.css"; */
</style>
