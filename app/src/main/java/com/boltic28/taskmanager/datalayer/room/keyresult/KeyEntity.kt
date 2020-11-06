package com.boltic28.taskmanager.datalayer.room.keyresult

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.boltic28.taskmanager.datalayer.Progress
import java.time.LocalDateTime

@Entity(tableName = "key_entity")
data class KeyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val goalId: Long,
    val name: String,
    val description: String,
    val date: LocalDateTime,
    val dateClose: LocalDateTime,
    val progress: Progress,
    val isDone: Boolean,
    val isStarted: Boolean
)