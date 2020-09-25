package com.boltic28.taskmanager.datalayer.dto

import com.boltic28.taskmanager.datalayer.Cycle
import java.time.LocalDateTime

data class Goal(
    val id: Long,
    val name: String,
    val icon: String,
    val date: LocalDateTime,
    val dateClose: LocalDateTime,
    val cycle: Cycle,
    val isDone: Boolean,
    val isProcess: Process
)