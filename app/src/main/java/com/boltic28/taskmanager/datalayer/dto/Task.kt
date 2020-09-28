package com.boltic28.taskmanager.datalayer.dto

import com.boltic28.taskmanager.datalayer.Cycle
import java.time.LocalDateTime

class Task(
    var id: Long,
    var stepId: Long,
    var keyId: Long,
    var goalId: Long,
    var name: String,
    var description: String,
    var icon: String,
    var date: LocalDateTime,
    var dateClose: LocalDateTime,
    var cycle: Cycle,
    var isDone: Boolean,
    var isStarted: Boolean
)