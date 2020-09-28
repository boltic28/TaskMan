package com.boltic28.taskmanager.datalayer.room.keyresult

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.TypeConverters
import com.boltic28.taskmanager.datalayer.room.Converter
import com.boltic28.taskmanager.datalayer.room.goal.GoalEntity
import java.time.LocalDateTime

@Entity(
    tableName = "key_entity",
    foreignKeys = [
        ForeignKey(
            entity = GoalEntity::class,
            parentColumns = ["id"],
            childColumns = ["ownerId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class KeyEntity(
    val id: Long,
    val ownerId: Long,
    val date: LocalDateTime,
    val progress: Int
)