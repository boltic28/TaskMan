package com.boltic28.taskmanager.datalayer.room.task

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.boltic28.taskmanager.datalayer.Cycle
import com.boltic28.taskmanager.datalayer.room.Converter
import com.boltic28.taskmanager.datalayer.room.goal.GoalEntity
import com.boltic28.taskmanager.datalayer.room.step.StepEntity
import java.time.LocalDateTime

@Entity(
    tableName = "task",
    foreignKeys = [
        ForeignKey(
            entity = StepEntity::class,
            parentColumns = ["id"],
            childColumns = ["stepId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val stepId: Long,
    val keyId: Long,
    val goalId: Long,
    val name: String,
    val icon: String,
    val date: LocalDateTime,
    val dateClose: LocalDateTime,
    val cycle: Cycle,
    val isDone: Boolean,
    val isStarted: Boolean
)