package com.yakidan.vue.task_tracker.repository

import com.yakidan.vue.task_tracker.entity.TaskEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TaskRepository : JpaRepository<TaskEntity, UUID>