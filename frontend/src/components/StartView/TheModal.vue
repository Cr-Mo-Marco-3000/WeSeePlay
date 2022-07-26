<template>
    <div class="modal modal-overlay" @click.self="$emit('close')">
      <div class="modal-window">
        <div class="top">
          <button @click="$emit('close')">X</button>
        </div>
        <div class="content">
          <slot />
        </div>
        <!-- <div class="bottom">
          <button @click="$emit('close')" class="overlay__btn overlay__btn--transparent">Close</button>
        </div> -->
      </div>
    </div>
</template>

<script>
export default {
  name: "TheModal"
}
</script>

<style scope>
@import "../../assets/startpage/modal.css";
</style>