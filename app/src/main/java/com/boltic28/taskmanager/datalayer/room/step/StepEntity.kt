package com.boltic28.taskmanager.datalayer.room.step

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "step")
data class StepEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val goalId: Long,
    val keyId: Long,
    val name: String,
    val description: String,
    val icon: String,
    val date: LocalDateTime,
    val dateClose: LocalDateTime,
    val isDone: Boolean,
    val isStarted: Boolean
)