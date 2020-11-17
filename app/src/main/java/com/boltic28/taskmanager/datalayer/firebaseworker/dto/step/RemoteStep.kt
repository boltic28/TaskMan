package com.boltic28.taskmanager.datalayer.firebaseworker.dto.step

import com.boltic28.taskmanager.datalayer.Progress
import com.boltic28.taskmanager.datalayer.entities.Step
import com.boltic28.taskmanager.ui.constant.NO_ID
import java.time.LocalDateTime

data class RemoteStep(
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
    val goalId: Long,
    val keyId: Long
    ) {
    /**
     * firebase realtime database needs in empty constructor
     */
    constructor() : this(NO_ID,"", "", "", "", "", "", false, false, 0, NO_ID, NO_ID)
}

fun Step.toRemoteObject(): RemoteStep =
    RemoteStep(
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
        goalId = this.goalId,
        keyId = this.keyId
    )

fun RemoteStep.toLocalObject(): Step =
    Step(
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
        goalId = this.goalId,
        keyId = this.keyId
    )