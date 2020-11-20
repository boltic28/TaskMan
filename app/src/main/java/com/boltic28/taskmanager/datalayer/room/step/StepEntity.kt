package com.boltic28.taskmanager.datalayer.room.step

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.boltic28.taskmanager.datalayer.Progress
import java.time.LocalDateTime

@Entity(tableName = "step")
data class StepEntity(
    @PrimaryKey
    val id: Long,
    val uid: String,
    val goalId: Long,
    val keyId: Long,
    val name: String,
    val description: String,
    val icon: String,
    val date: LocalDateTime,
    val dateClose: LocalDateTime,
    val progress: Progress,
    val isDone: Boolean,
    val isStarted: Boolean
)