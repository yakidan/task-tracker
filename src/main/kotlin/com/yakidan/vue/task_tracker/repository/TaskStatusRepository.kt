package com.yakidan.vue.task_tracker.repository

import com.yakidan.vue.task_tracker.entity.TaskStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TaskStatusRepository : JpaRepository<TaskStatus, UUID> {
    fun findByName(name: String): Optional<TaskStatus>
}