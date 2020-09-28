package com.boltic28.taskmanager.datalayer.room.goal

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.boltic28.taskmanager.datalayer.Cycle
import com.boltic28.taskmanager.datalayer.room.Converter
import java.time.LocalDateTime

@Entity(tableName = "goal")
data class GoalEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val icon: String,
    val date: LocalDateTime,
    val dateClose: LocalDateTime,
    val cycle: Cycle,
    val isDone: Boolean,
    val isStarted: Boolean
)