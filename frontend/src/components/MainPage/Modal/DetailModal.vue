<template>
  <div class="modal modal-overlay" @mousedown.self="$emit('close')">
    <div class="modal-window">
      <div class="top">
        <button @click="$emit('close')">X</button>
      </div>
      <div class="content">
        <slot />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "DetailModal",
}
</script>

<style scoped>
@import url("../../../assets/mainpage/room_detail_modal.css");
.modal-window {
  height: 31rem;
}
</style>
