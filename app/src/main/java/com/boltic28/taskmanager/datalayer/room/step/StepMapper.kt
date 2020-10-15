package com.boltic28.taskmanager.datalayer.room.step

import com.boltic28.taskmanager.datalayer.entities.Step

fun Step.toEntity(): StepEntity =
    StepEntity(
        id = this.id,
        goalId = this.goalId,
        keyId = this.keyId,
        name = this.name,
        description = this.description,
        icon = this.icon,
        date = this.date,
        dateClose = this.dateClose,
        isDone = this.isDone,
        isStarted = this.isStarted
    )

fun StepEntity.toStep(): Step =
    Step(
        id = this.id,
        goalId = this.goalId,
        keyId = this.keyId,
        name = this.name,
        description = this.description,
        icon = this.icon,
        date = this.date,
        dateClose = this.dateClose,
        isDone = this.isDone,
        isStarted = this.isStarted,
        tasks = emptyList(),
        ideas = emptyList()
    )