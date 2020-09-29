package com.boltic28.taskmanager.datalayer.room.task

import com.boltic28.taskmanager.datalayer.classes.Task

fun Task.toEntity(): TaskEntity =
    TaskEntity(
        id = this.id,
        stepId = this.stepId,
        keyId = this.keyId,
        goalId = this.goalId,
        name = this.name,
        description = this.description,
        icon = this.icon,
        date = this.date,
        dateClose = this.dateClose,
        cycle = this.cycle,
        isDone = this.isDone,
        isStarted = this.isStarted
    )

fun TaskEntity.toTask(): Task =
    Task(
        id = this.id,
        stepId = this.stepId,
        keyId = this.keyId,
        goalId = this.goalId,
        name = this.name,
        description = this.description,
        icon = this.icon,
        date = this.date,
        dateClose = this.dateClose,
        cycle = this.cycle,
        isDone = this.isDone,
        isStarted = this.isStarted
    )