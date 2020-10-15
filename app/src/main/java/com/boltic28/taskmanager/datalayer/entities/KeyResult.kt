package com.boltic28.taskmanager.datalayer.entities

import java.time.LocalDateTime

data class KeyResult(
    val id: Long,
    val goalId: Long,
    val name: String,
    val description: String,
    val date: LocalDateTime,
    val progress: Int,
    val steps: List<Step>,
    val tasks: List<Task>,
    val ideas: List<Idea>
)