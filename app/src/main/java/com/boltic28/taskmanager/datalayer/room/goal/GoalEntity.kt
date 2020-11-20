package com.boltic28.taskmanager.datalayer.room.goal

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "goal")
data class GoalEntity(
    @PrimaryKey
    val id: Long,
    val uid: String,
    val name: String,
    val description: String,
    val icon: String,
    val date: LocalDateTime,
    val dateClose: LocalDateTime,
    val isDone: Boolean,
    val isStarted: Boolean
)