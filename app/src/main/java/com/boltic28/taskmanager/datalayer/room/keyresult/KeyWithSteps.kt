package com.boltic28.taskmanager.datalayer.room.keyresult

import androidx.room.Embedded
import androidx.room.Relation
import com.boltic28.taskmanager.datalayer.room.step.StepWithTasks

data class KeyWithSteps(
    @Embedded
    val key: KeyEntity,
    @Relation(parentColumn = "id", entityColumn = "keyId")
    var steps: List<StepWithTasks>
)