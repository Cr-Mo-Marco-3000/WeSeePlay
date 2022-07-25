<template>
    <div class="modal modal-overlay" @click.self="$emit('close')">
      <div class="modal-window">
        <div class="content">
          <slot />
        </div>
        <div class="bottom">
          <button class="btn btn-link" @click="$emit('close')">Close</button>
        </div>
      </div>
    </div>
</template>

<script>
export default {
  name: "TheModal"
}
</script>

<style>
@import "../../assets/startpage/modal.css";
</style>