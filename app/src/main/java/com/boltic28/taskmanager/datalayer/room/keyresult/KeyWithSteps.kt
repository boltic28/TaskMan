package com.boltic28.taskmanager.datalayer.room.keyresult

import androidx.room.Relation
import com.boltic28.taskmanager.datalayer.room.step.StepEntity
import java.time.LocalDateTime

class KeyWithSteps(
    val id: Long,
    val goalId: Long,
    val name: String,
    val description: String,
    val date: LocalDateTime,
    val progress: Int,
    @Relation(parentColumn = "id", entityColumn = "keyId")
    var steps: List<StepEntity>
)