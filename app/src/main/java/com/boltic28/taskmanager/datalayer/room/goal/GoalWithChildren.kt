package com.boltic28.taskmanager.datalayer.room.goal

import androidx.room.Relation
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyEntity
import com.boltic28.taskmanager.datalayer.room.step.StepEntity
import com.boltic28.taskmanager.datalayer.room.task.TaskEntity
import java.time.LocalDateTime

data class GoalWithChildren(

    val id: Long,
    val name: String,
    val description: String,
    val icon: String,
    val date: LocalDateTime,
    val dateClose: LocalDateTime,
    val isDone: Boolean,
    val isStarted: Boolean,
    @Relation(parentColumn = "id", entityColumn = "goalId")
    var keys: List<KeyEntity>,
    @Relation(parentColumn = "id", entityColumn = "goalId")
    var steps: List<StepEntity>,
    @Relation(parentColumn = "id", entityColumn = "goalId")
    var tasks: List<TaskEntity>
)