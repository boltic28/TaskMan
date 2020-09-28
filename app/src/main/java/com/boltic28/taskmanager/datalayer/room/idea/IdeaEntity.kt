package com.boltic28.taskmanager.datalayer.room.idea

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "idea")
data class IdeaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val stepId: Long,
    val keyId: Long,
    val goalId: Long,
    val name: String,
    val icon: String,
    val date: LocalDateTime
)