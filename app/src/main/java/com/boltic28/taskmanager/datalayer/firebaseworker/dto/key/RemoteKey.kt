package com.boltic28.taskmanager.datalayer.firebaseworker.dto.key

import com.boltic28.taskmanager.datalayer.Progress
import com.boltic28.taskmanager.datalayer.entities.KeyResult
import com.boltic28.taskmanager.ui.constant.NO_ID
import java.time.LocalDateTime

data class RemoteKey(
    val id: Long,
    val uid: String,
    val name: String,
    val description: String,
    val icon: String,
    val date: String,
    val dateClose: String,
    val isDone: Boolean,
    val isStarted: Boolean,
    val progress: Int,
    val goalId: Long
) {
    /**
     * firebase realtime database needs in empty constructor
     */
    constructor() : this(NO_ID, "", "", "", "", "", "", false, false, 0, NO_ID)
}

fun KeyResult.toRemoteObject(): RemoteKey =
    RemoteKey(
        id = this.id,
        uid = this.uid,
        name = this.name,
        description = this.description,
        icon = this.icon,
        date = this.date.toString(),
        dateClose = this.dateClose.toString(),
        isDone = this.isDone,
        isStarted = this.isStarted,
        progress = this.progress.value,
        goalId = this.goalId
    )

fun RemoteKey.toLocalObject(): KeyResult =
    KeyResult(
        id = this.id,
        uid = this.uid,
        name = this.name,
        description = this.description,
        icon = this.icon,
        date = LocalDateTime.parse(this.date),
        dateClose = LocalDateTime.parse(this.dateClose),
        isDone = this.isDone,
        isStarted = this.isStarted,
        progress = Progress.fromInteger(this.progress),
        tasks = emptyList(),
        ideas = emptyList(),
        steps = emptyList(),
        goalId = this.goalId
    )