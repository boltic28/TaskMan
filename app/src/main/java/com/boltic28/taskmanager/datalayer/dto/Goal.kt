package com.boltic28.taskmanager.datalayer.dto

import java.time.LocalDateTime

class Goal(
    var id: Long,
    var name: String,
    var description: String,
    var icon: String,
    var date: LocalDateTime,
    var dateClose: LocalDateTime,
    var isDone: Boolean,
    val isStarted: Boolean,
    var steps: MutableList<Step>,
    var keys: MutableList<KeyResult>,
    var tasks: MutableList<Task>
)