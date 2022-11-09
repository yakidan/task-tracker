<template>
  <transition name="fade">
    <div class="vue-modal" v-show="open">
      <div class="vue-modal-inner">
        <div class="vue-modal-content container">
          <slot/>
          <div class="row">
            <button type="button" class="btn btn-danger col-sm " @click="$emit('deleteTask')">Yes</button>
            <div class="col-4"></div>
            <button type="button" class="btn btn-secondary col-sm" @click="$emit('close')">No</button>
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>

export default {
  name: 'ModalView',
  props: {
    open: {
      type: Boolean,
      required: true
    }
  },
  setup(_, {emit}) {
    const close = () => {
      emit("close");
    }
    const deleteTask = ()=>{
      emit("deleteTask")
    }

    return {close,deleteTask}
  },

};
</script>

<style scoped>
*,
::before,
::after {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.vue-modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  overflow-x: hidden;
  overflow-y: auto;
  background-color: rgba(0, 0, 0, 0.4);
  z-index: 1;
}

.vue-modal-inner {
  max-width: 500px;
  margin: 2rem auto;
}

.vue-modal-content {
  position: relative;
  background-color: #fff;
  background-clip: padding-box;
  border-radius: 0.3rem;
  padding: 1rem;
  border: 1px solid rgba(0, 0, 0, 0.3);
}

.fade-enter-active, .fade-leave-active {
  transition: opacity 0.5s;
}

.fade-enter-from, .fade-leave-to {
  opacity: 0;
}
</style>