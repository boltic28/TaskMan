package com.boltic28.taskmanager.datalayer.room.idea

import com.boltic28.taskmanager.datalayer.entities.Idea

fun Idea.toEntity(): IdeaEntity =
    IdeaEntity(
        id = this.id,
        uid = this.uid,
        stepId = this.stepId,
        keyId = this.keyId,
        goalId = this.goalId,
        name = this.name,
        description = this.description,
        icon = this.icon,
        date = this.date
    )

fun IdeaEntity.toIdea(): Idea =
    Idea(
        id = this.id,
        uid = this.uid,
        stepId = this.stepId,
        keyId = this.keyId,
        goalId = this.goalId,
        name = this.name,
        description = this.description,
        icon = this.icon,
        date = this.date,
        dateClose = this.date
    )