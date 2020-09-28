package com.boltic28.taskmanager.datalayer.room.step

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.boltic28.taskmanager.datalayer.Cycle
import com.boltic28.taskmanager.datalayer.room.Converter
import com.boltic28.taskmanager.datalayer.room.goal.GoalEntity
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyEntity
import java.time.LocalDateTime

@Entity(
    tableName = "step",
    foreignKeys = [
        ForeignKey(
            entity = GoalEntity::class,
            parentColumns = ["id"],
            childColumns = ["ownerId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = KeyEntity::class,
            parentColumns = ["id"],
            childColumns = ["keyId"],
            onDelete = ForeignKey.NO_ACTION
        )
    ]
)
data class StepEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val ownerId: Long,
    val keyId: Long,
    val name: String,
    val icon: String,
    val date: LocalDateTime,
    val dateClose: LocalDateTime,
    val cycle: Cycle,
    val isDone: Boolean,
    val isStarted: Boolean
)