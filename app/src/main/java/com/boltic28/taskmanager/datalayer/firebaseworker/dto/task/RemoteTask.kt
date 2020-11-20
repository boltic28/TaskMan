package com.boltic28.taskmanager.datalayer.firebaseworker.dto.task

import com.boltic28.taskmanager.datalayer.Cycle
import com.boltic28.taskmanager.datalayer.entities.Task
import com.boltic28.taskmanager.ui.constant.NO_ID
import java.time.LocalDateTime

data class RemoteTask(
    val id: Long,
    val uid: String,
    val name: String,
    val description: String,
    val icon: String,
    val date: String,
    val dateClose: String,
    val done: Boolean,
    val started: Boolean,
    val cycle: String,
    val goalId: Long,
    val keyId: Long,
    val stepId: Long
) {
    /**
     * firebase realtime database needs in empty constructor
     */
    constructor() : this(
        NO_ID,
        "",
        "",
        "",
        "",
        "",
        "",
        false,
        false,
        Cycle.NOT_CYCLE.value,
        NO_ID,
        NO_ID,
        NO_ID
    )
}

fun Task.toRemoteObject(): RemoteTask =
    RemoteTask(
        id = this.id,
        uid = this.uid,
        name = this.name,
        description = this.description,
        icon = this.icon,
        date = this.date.toString(),
        dateClose = this.dateClose.toString(),
        done = this.isDone,
        started = this.isStarted,
        cycle = this.cycle.value,
        goalId = this.goalId,
        keyId = this.keyId,
        stepId = this.stepId
    )

fun RemoteTask.toLocalObject(): Task =
    Task(
        id = this.id,
        uid = this.uid,
        name = this.name,
        description = this.description,
        icon = this.icon,
        date = LocalDateTime.parse(this.date),
        dateClose = LocalDateTime.parse(this.dateClose),
        isDone = this.done,
        isStarted = this.started,
        goalId = this.goalId,
        keyId = this.keyId,
        stepId = this.stepId,
        cycle = Cycle.fromString(this.cycle)
    )