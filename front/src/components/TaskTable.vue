<template>
  <div>
    <table class="table  table-bordered " style="border-radius: 50px">
      <thead>
      <tr class="thead">
        <th @click="sortTask">Название
          <nobr v-if="sortStatusByTaskField.name === sortStatus.ASCENDING">↑</nobr>
          <nobr v-if="sortStatusByTaskField.name === sortStatus.DESCENDING">↓</nobr>
        </th>

        <th @click="sortTask">Описание
          <nobr v-if="sortStatusByTaskField.description === sortStatus.ASCENDING">↑</nobr>
          <nobr v-if="sortStatusByTaskField.description === sortStatus.DESCENDING">↓</nobr>
        </th>
        <th @click="sortTask">Статус
          <nobr v-if="sortStatusByTaskField.status === sortStatus.ASCENDING">↑</nobr>
          <nobr v-if="sortStatusByTaskField.status === sortStatus.DESCENDING">↓</nobr>
        </th>
      </tr>
      </thead>
      <tbody>
      <template v-for="task in tasks" :key="task">
        <tr class="to-do" v-if="task.status==='TO_DO'" v-on:click="clickTask"
            :id=task.id v-on:dblclick="isOpen= true; deleteTaskId=task.id">
          <td class="name">{{ task.name }}</td>
          <td class="description">{{ task.description }}</td>
          <td class="status">{{ task.status }}

          </td>
        </tr>
        <tr class="in-progress" v-if="task.status==='IN_PROGRESS'" v-on:click="clickTask" :id=task.id
            v-on:dblclick="isOpen= true; deleteTaskId=task.id">
          <td class="name">{{ task.name }}</td>
          <td class="description">{{ task.description }}</td>
          <td class="status">{{ task.status }}</td>
        </tr>
        <tr class="done" v-if="task.status==='DONE'" v-on:click="clickTask" :id=task.id
            v-on:dblclick="isOpen= true; deleteTaskId=task.id">
          <td class="name">{{ task.name }}</td>
          <td class="description">{{ task.description }}</td>
          <td class="status">{{ task.status }}</td>
        </tr>
      </template>
      </tbody>
    </table>
    <FormAddTask :tasks="tasks" :task="task"></FormAddTask>
    <ModalView :open="isOpen" @close="isOpen=!isOpen" @deleteTask="deleteTask">
      <p>Ты точно хочешь удалить эту Задачу?</p>
    </ModalView>
  </div>
</template>

<script>
import FormAddTask from "@/components/FormAddTask";
import ModalView from "@/components/ModalView";
import {ref} from "vue";

export default {

  name: 'TaskTable',
  props: ['tasks'],
  components: {ModalView, FormAddTask},
  setup() {
    const isOpen = ref(false);
    return {isOpen}
  },
  data() {
    return {
      task: {
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
      sortStatus: {
        DEFAULT: "DEFAULT",
        ASCENDING: "ASCENDING",
        DESCENDING: "DESCENDING"
      },
      sortStatusByTaskField: {
        name: null,
        description: null,
        status: null,
      },
      taskData: {
        NAME: 'Название',
        DESCRIPTION: 'Описание',
        STATUS: 'Статус',
      },
      isModalVisible: false,
      deleteTaskId: String,
      backendUrl: "http://localhost:8080/api/v1/tasks",
    }
  },
  created() {
    this.sortStatusByTaskField.name = this.sortStatus.DEFAULT
    this.sortStatusByTaskField.description = this.sortStatus.DEFAULT
    this.sortStatusByTaskField.status = this.sortStatus.DEFAULT
  },
  methods: {
    showModal() {
      this.isModalVisible = true;
    },
    clearTask() {
      this.task.id = null;
      this.task.name = null;
      this.task.description = null;
      this.task.status = null;
    },
    sortTask: function (event) {
      switch (event.target.innerHTML.split(' ')[0]) {
        case this.taskData.NAME:
          switch (this.sortStatusByTaskField.name) {
            case this.sortStatus.DEFAULT:
              this.$props.tasks.sort((a, b) => (a.name > b.name) ? 1 : ((b.name > a.name) ? -1 : 0))
              this.sortStatusByTaskField.name = this.sortStatus.ASCENDING
              break
            case this.sortStatus.ASCENDING:
              this.$props.tasks.sort((a, b) => (a.name > b.name) ? 1 : ((b.name > a.name) ? -1 : 0))
              this.sortStatusByTaskField.name = this.sortStatus.DESCENDING
              break
            case this.sortStatus.DESCENDING:
              this.$props.tasks.sort(() => Math.random() - 0.5)
              this.sortStatusByTaskField.name = this.sortStatus.DEFAULT
          }
          break
        case this.taskData.DESCRIPTION:
          switch (this.sortStatusByTaskField.description) {
            case this.sortStatus.DEFAULT:
              this.$props.tasks.sort((a, b) => (a.description > b.description) ? 1 : ((b.description > a.description) ? -1 : 0))
              this.sortStatusByTaskField.description = this.sortStatus.ASCENDING
              break
            case this.sortStatus.ASCENDING:
              this.$props.tasks.sort((a, b) => (a.description > b.description) ? 1 : ((b.description > a.description) ? -1 : 0))
              this.sortStatusByTaskField.description = this.sortStatus.DESCENDING
              break
            case this.sortStatus.DESCENDING:
              this.$props.tasks.sort(() => Math.random() - 0.5)
              this.sortStatusByTaskField.description = this.sortStatus.DEFAULT
          }
          break
        case this.taskData.STATUS:
          switch (this.sortStatusByTaskField.status) {
            case this.sortStatus.DEFAULT:
              this.$props.tasks.sort((a, b) => {
                let aWeight = a.status === this.status.TODO ? 0 : (a.status === this.status.IN_PROGRESS ? 1 : 2)
                let bWeight = b.status === this.status.TODO ? 0 : (b.status === this.status.IN_PROGRESS ? 1 : 2)
                return (aWeight > bWeight) ? 1 :
                    ((bWeight > aWeight) ? -1 : 0)
              })
              this.sortStatusByTaskField.status = this.sortStatus.ASCENDING
              break
            case this.sortStatus.ASCENDING:
              this.$props.tasks.sort((a, b) => {
                let aWeight = a.status === this.status.TODO ? 0 : (a.status === this.status.IN_PROGRESS ? 1 : 2)
                let bWeight = b.status === this.status.TODO ? 0 : (b.status === this.status.IN_PROGRESS ? 1 : 2)
                return (aWeight < bWeight) ? 1 :
                    ((bWeight < aWeight) ? -1 : 0)
              })
              this.sortStatusByTaskField.status = this.sortStatus.DESCENDING
              break
            case this.sortStatus.DESCENDING:
              this.$props.tasks.sort(() => Math.random() - 0.5)
              this.sortStatusByTaskField.status = this.sortStatus.DEFAULT
          }
          break
      }
    },
    clickTask: function (event) {
      const tr = event.path[1]
      const trId = tr.id
      const name = tr.getElementsByClassName('name')[0].innerHTML
      const description = tr.getElementsByClassName('description')[0].innerHTML
      const status = tr.getElementsByClassName('status')[0].innerHTML
      this.task.id = trId
      this.task.name = name
      this.task.description = description
      this.task.status = status
    },
    deleteTask: function () {
      fetch(this.backendUrl + "/" + this.deleteTaskId, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json'
        }
      })
          .then(response => response.status)
          .then((resp) => {
            if (resp === 200) {
              const indexOfDeleteTask = this.$props.tasks.findIndex(t => t.id === this.deleteTaskId)
              this.$props.tasks.splice(indexOfDeleteTask, 1)
            }
          })
      this.isOpen = !this.isOpen
      this.clearTask();
    }
  }
}
</script>

<style scoped>
.to-do {
  background-color: #f5e1e7;
}
.in-progress {
  background-color: #fcf8d6;
}
.done {
  background-color: #d1f8d7;
}
.thead {
  background-color: #b7bfc2;
}
</style>
