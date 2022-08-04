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
  name: "EditModal",
}
</script>

<style scoped>
@import url("@/assets/roompage/EditModal.css");
.modal-window {
  top: 10vh;
  height: 38rem;
}
</style>
