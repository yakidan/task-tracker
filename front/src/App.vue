<template>
  <div class="container">
    <h2>TaskManager</h2>
    <TaskTable :tasks="tasks"/>
  </div>
</template>

<script>
import TaskTable from './components/TaskTable.vue'
export default {
  name: 'App',
  components: {
    TaskTable
  },
  data() {
    return {
      tasks: [],
      backendUrl: "http://localhost:8080/api/v1/tasks",
    }
  },
  mounted() {
    return fetch(this.backendUrl)
        .then(response => response.json())
        .then(data => (data.forEach(data => this.tasks.push(data))));
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

body {
  background-color: #f3f4ec;
}
</style>
