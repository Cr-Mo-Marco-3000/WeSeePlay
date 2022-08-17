<template>
  <div class="modal modal-overlay" @mousedown.self="$emit('close')">
    <div class="modal-window">
      <div class="top">
        <button @click="$emit('close')">X</button>
      </div>
      <form>
        <div class="content">
          <slot />
        </div>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  name: "DeleteUserModal",
}
</script>

<style scoped>
@import url("../../../assets/mainpage/auth_modal.css");
.modal-window {
  height: auto;
}
</style>
