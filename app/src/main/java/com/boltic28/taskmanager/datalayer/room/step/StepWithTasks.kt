package com.boltic28.taskmanager.datalayer.room.step

import androidx.room.Relation
import com.boltic28.taskmanager.datalayer.room.task.TaskEntity
import java.time.LocalDateTime

data class StepWithTasks(
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
    @Relation(parentColumn = "id", entityColumn = "stepId")
    var tasks: List<TaskEntity>
)