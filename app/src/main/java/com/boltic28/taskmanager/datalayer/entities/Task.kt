package com.boltic28.taskmanager.datalayer.entities

import com.boltic28.taskmanager.datalayer.Cycle
import java.time.LocalDateTime

data class Task(
    val id: Long,
    val stepId: Long,
    val keyId: Long,
    val goalId: Long,
    val name: String,
    val description: String,
    val icon: String,
    val date: LocalDateTime,
    val dateClose: LocalDateTime,
    val cycle: Cycle,
    val isDone: Boolean,
    val isStarted: Boolean
)