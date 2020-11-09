package com.boltic28.taskmanager.datalayer.entities

import com.boltic28.taskmanager.datalayer.Progress
import java.time.LocalDateTime

data class Step(
    override val id: Long,
    override val name: String,
    override val description: String,
    override val icon: String,
    override val date: LocalDateTime,
    override val dateClose: LocalDateTime,
    override val isDone: Boolean,
    override val isStarted: Boolean,
    override val progress: Progress,
    val goalId: Long,
    val keyId: Long,
    val tasks: List<Task>,
    val ideas: List<Idea>
): ParentItem