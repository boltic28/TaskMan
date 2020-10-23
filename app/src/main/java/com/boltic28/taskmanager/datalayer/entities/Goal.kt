package com.boltic28.taskmanager.datalayer.entities

import com.boltic28.taskmanager.datalayer.Progress
import java.time.LocalDateTime

data class Goal(
    val id: Long,
    val name: String,
    val description: String,
    val icon: String,
    val date: LocalDateTime,
    val dateClose: LocalDateTime,
    val isDone: Boolean,
    val isStarted: Boolean,
    val steps: List<Step>,
    val tasks: List<Task>,
    val ideas: List<Idea>,
    val keys: List<KeyResult>,
    val progress: Progress
)