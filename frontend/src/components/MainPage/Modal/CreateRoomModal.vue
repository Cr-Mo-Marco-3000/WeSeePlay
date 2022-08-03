<template>
  <div class="modal modal-overlay" @click.self="$emit('close')">
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
  name: "CreateRoomModal",
};
</script>

<style>
@import "../../../assets/mainpage/create_room_modal.css";
.modal-window {
  height: 31rem;
}
</style>
