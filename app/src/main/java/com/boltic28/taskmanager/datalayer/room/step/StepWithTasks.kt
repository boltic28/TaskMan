package com.boltic28.taskmanager.datalayer.room.step

import androidx.room.Embedded
import androidx.room.Relation
import com.boltic28.taskmanager.datalayer.room.task.TaskEntity

data class StepWithTasks(
    @Embedded
    val stepEntity: StepEntity,
    @Relation(parentColumn = "id", entityColumn = "owner")
    var tasks: List<TaskEntity>
)