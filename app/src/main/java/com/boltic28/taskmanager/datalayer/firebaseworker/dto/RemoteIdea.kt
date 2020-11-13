package com.boltic28.taskmanager.datalayer.firebaseworker.dto

import com.boltic28.taskmanager.datalayer.entities.Idea
import com.boltic28.taskmanager.ui.constant.NO_ID
import java.time.LocalDateTime

data class RemoteIdea(
    val id: Long,
    val name: String,
    val description: String,
    val icon: String,
    val date: String,
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
        NO_ID,
        NO_ID,
        NO_ID
    )
}

fun Idea.toRemoteObject(): RemoteIdea =
    RemoteIdea(
        id = this.id,
        name = this.name,
        description = this.description,
        icon = this.icon,
        date = this.date.toString(),
        goalId = this.goalId,
        keyId = this.keyId,
        stepId = this.stepId
    )

fun RemoteIdea.toLocalObject(): Idea =
    Idea(
        id = this.id,
        name = this.name,
        description = this.description,
        icon = this.icon,
        date = LocalDateTime.parse(this.date),
        dateClose = LocalDateTime.parse(this.date),
        goalId = this.goalId,
        keyId = this.keyId,
        stepId = this.stepId
    )