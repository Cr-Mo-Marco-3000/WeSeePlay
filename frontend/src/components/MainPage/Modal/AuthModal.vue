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
  name: "AuthModal",
};
</script>

<style scoped>
@import url("../../../assets/mainpage/auth_modal.css");
.modal-window {
  height: 26.5rem;
}
</style>
