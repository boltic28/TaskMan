package com.boltic28.taskmanager.datalayer.entities

import com.boltic28.taskmanager.datalayer.Progress
import java.time.LocalDateTime

data class Step(
    val id: Long,
    val goalId: Long,
    val keyId: Long,
    val name: String,
    val description: String,
    val icon: String,
    val date: LocalDateTime,
    val dateClose: LocalDateTime,
    val isDone: Boolean,
    val isStarted: Boolean,
    val progress: Progress,
    val tasks: List<Task>,
    val ideas: List<Idea>
)