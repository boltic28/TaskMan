package com.boltic28.taskmanager.datalayer.room.idea

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "idea")
data class IdeaEntity(
    @PrimaryKey
    val id: Long,
    val uid: String,
    val stepId: Long,
    val keyId: Long,
    val goalId: Long,
    val name: String,
    val description: String,
    val icon: String,
    val date: LocalDateTime
)