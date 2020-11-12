package com.boltic28.taskmanager.datalayer.firebaseworker.dto

import com.boltic28.taskmanager.datalayer.Progress
import com.boltic28.taskmanager.datalayer.entities.Goal
import com.boltic28.taskmanager.ui.constant.NO_ID
import java.time.LocalDateTime

data class RemoteGoal(
    val id: Long,
    val name: String,
    val description: String,
    val icon: String,
    val date: String,
    val dateClose: String,
    val isDone: Boolean,
    val isStarted: Boolean,
    val progress: Int,
) {
    /**
     * firebase realtime database needs in empty constructor
     */
    constructor() : this(NO_ID, "", "", "", "", "", false, false, 0)
}

fun Goal.toRemoteObject(): RemoteGoal =
    RemoteGoal(
        id = this.id,
        name = this.name,
        description = this.description,
        icon = this.icon,
        date = this.date.toString(),
        dateClose = this.dateClose.toString(),
        isDone = this.isDone,
        isStarted = this.isStarted,
        progress = this.progress.value
    )

fun RemoteGoal.toLocalObject(): Goal =
    Goal(
        id = this.id,
        name = this.name,
        description = this.description,
        icon = this.icon,
        date = LocalDateTime.parse(this.date),
        dateClose = LocalDateTime.parse(this.dateClose),
        isDone = this.isDone,
        isStarted = this.isStarted,
        progress = Progress.fromInteger(this.progress),
        steps = emptyList(),
        tasks = emptyList(),
        ideas = emptyList(),
        keys = emptyList(),
    )