package com.boltic28.taskmanager.datalayer.entities

import com.boltic28.taskmanager.datalayer.Cycle
import java.time.LocalDateTime

data class Task(
    override val id: Long,
    override val uid: String,
    override val name: String,
    override val description: String,
    override val icon: String,
    override val date: LocalDateTime,
    override val dateClose: LocalDateTime,
    override val isDone: Boolean,
    override val isStarted: Boolean,
    val stepId: Long,
    val keyId: Long,
    val goalId: Long,
    val cycle: Cycle
): CompletableItem