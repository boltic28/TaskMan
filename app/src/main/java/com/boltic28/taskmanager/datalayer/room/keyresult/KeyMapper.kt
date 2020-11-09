package com.boltic28.taskmanager.datalayer.room.keyresult

import com.boltic28.taskmanager.datalayer.entities.KeyResult

fun KeyResult.toEntity(): KeyEntity =
    KeyEntity(
        id = this.id,
        goalId = this.goalId,
        name = this.name,
        description = this.description,
        date = this.date,
        dateClose = this.dateClose,
        progress = this.progress,
        isDone = this.isDone,
        isStarted = this.isStarted
    )

fun KeyEntity.toKey(): KeyResult =
    KeyResult(
        id = this.id,
        goalId = this.goalId,
        name = this.name,
        description = this.description,
        date = this.date,
        dateClose = this.dateClose,
        progress = this.progress,
        isDone = this.isDone,
        isStarted = this.isStarted,
        steps = emptyList(),
        tasks = emptyList(),
        ideas = emptyList()
    )