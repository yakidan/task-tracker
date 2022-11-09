<template>
  <div class="container col-6 ">
    <h4>Добавить Task</h4>
    <form id="dataForm" @submit="addOrUpdateTask" method="post">
      <div>
        <label for="exampleInputEmail1" class="form-label">Название</label>
        <input type="text" class="form-control" id="exampleInputEmail1" name="name" :value=task.name
               @input="insertData">
      </div>
      <div class="mb-2 ">
        <label for="description" class="form-label">Описание</label>
        <input class="form-control" id="description" name="description" :value=task.description @input="insertData"
        >
      </div>
      <div class="mb-3 row ">
        <div class="col-4">
          <input type="radio" id="radio_to_do"
                 @input="insertData"
                 name="status" :value=status.TODO
                 :checked="task.status===status.TODO">
          <label class="form-check-label" for="radio">{{ status.TODO }}</label>
        </div>
        <div class="col-4">
          <input type="radio" id="radio_in_progress"
                 @input="insertData"
                 name="status" :value=status.IN_PROGRESS
                 :checked="task.status===status.IN_PROGRESS">
          <label class=" ml-3 " for="radio_in_progress">{{ status.IN_PROGRESS }}</label>
        </div>
        <div class="col-4">
          <input type="radio" id="radio_done" name="status"
                 @input="insertData"
                 :value=status.DONE
                 :checked="task.status===status.DONE">
          <label for="radio_done">{{ status.DONE }}</label>
        </div>
      </div>
      <div v-if="task.id">
        <button type="submit" class="btn btn-primary m-lg-2">Update</button>
        <button type="submit" class="btn btn-light btn_clear" @click="clearTask">Clear</button>
      </div>
      <div v-if="!task.id">
        <button type="submit" class="btn btn-primary">Submit</button>
      </div>
    </form>
  </div>
</template>

<script>

export default {
  name: "FormaddOrUpdateTask",
  props: ['tasks', 'task', 'url'],
  data() {
    return {
      newTask: {
        id: null,
        name: null,
        description: null,
        status: null
      },
      status: {
        TODO: "TO_DO",
        IN_PROGRESS: "IN_PROGRESS",
        DONE: "DONE",
      },
      taskData: {
        NAME: 'name',
        DESCRIPTION: 'description',
        STATUS: 'status',
      },
      backendUrl: "http://localhost:8080/api/v1/tasks"
    }
  },
  methods: {
    addOrUpdateTask: function (e) {
      e.preventDefault();
      const update = this.$props.tasks.some(t => t.id === this.task.id)
      var methodName = update == true ? 'PUT' : 'POST';
      fetch(this.backendUrl, {
        method: methodName,
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(this.task)
      })
          .then((response) => response.json())
          .then((t) => {
            let newTask = this.$props.tasks.find(ts => t.id === ts.id)
            if (!newTask) {
              this.$props.tasks.push(t)
            } else {
              newTask.name = t.name
              newTask.description = t.description
              newTask.status = t.status
            }
          })
      this.clearTask();
    },
    insertData: function (event) {
      switch (event.target.name) {
        case this.taskData.NAME:
          this.$props.task.name = event.target.value
          break
        case this.taskData.DESCRIPTION:
          this.$props.task.description = event.target.value
          break
        case this.taskData.STATUS:
          this.$props.task.status = event.target.value
          break
      }
    },
    clearTask: function () {
      this.$props.task.id = null;
      this.$props.task.name = null;
      this.$props.task.description = null;
      this.$props.task.status = null;
    }
  },
}
</script>

<style scoped>
.btn_clear {
  background-color: moccasin;
  border-color: moccasin;
}
</style>