package com.yakidan.vue.task_tracker.model

import java.util.*

data class Task(
    val id: UUID?,
    val name: String,
    val description: String?,
    val status: String,
)