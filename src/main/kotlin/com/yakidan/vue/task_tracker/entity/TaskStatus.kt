package com.yakidan.vue.task_tracker.entity

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "task_status")
class TaskStatus {
    @Id
    var id: UUID? = null

    @Column(name = "name")
    var name: String? = null
}