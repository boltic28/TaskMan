package com.boltic28.taskmanager.datalayer.room.goal

import androidx.room.Embedded
import androidx.room.Relation
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyWithSteps
import com.boltic28.taskmanager.datalayer.room.step.StepWithTasks

data class GoalWithChildren(
    @Embedded
    val goal: GoalEntity,
    @Relation(parentColumn = "id", entityColumn = "ownerId")
    var keys: List<KeyWithSteps>,
    @Relation(parentColumn = "id", entityColumn = "ownerId")
    var steps: List<StepWithTasks>
)