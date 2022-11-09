package com.yakidan.vue.task_tracker.entity

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "task")
class TaskEntity {
    @Id
    @GeneratedValue
    var id: UUID? = null

    @Column(name = "name")
    var name: String? = null

    @Column(name = "description")
    var description: String? = null

    @ManyToOne
    @JoinColumn(name = "status_id")
    var taskStatus: TaskStatus? = null
}