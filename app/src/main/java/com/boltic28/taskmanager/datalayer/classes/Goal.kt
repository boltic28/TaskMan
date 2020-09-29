package com.boltic28.taskmanager.datalayer.classes

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
    val steps: List<Step>,
    val tasks: List<Task>,
    val ideas: List<Idea>,
    val keys: List<KeyResult>
)