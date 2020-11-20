package com.boltic28.taskmanager.datalayer.room.task

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.boltic28.taskmanager.datalayer.Cycle
import java.time.LocalDateTime

@Entity(tableName = "task")
data class TaskEntity(
    @PrimaryKey
    val id: Long,
    val uid: String,
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