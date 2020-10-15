package com.boltic28.taskmanager.datalayer.room.goal

import com.boltic28.taskmanager.datalayer.entities.Goal

fun Goal.toEntity(): GoalEntity =
    GoalEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        icon = this.icon,
        date = this.date,
        dateClose = this.dateClose,
        isDone = this.isDone,
        isStarted = this.isStarted
    )

fun GoalEntity.toGoal(): Goal =
    Goal(
        id = this.id,
        name = this.name,
        description = this.description,
        icon = this.icon,
        date = this.date,
        dateClose = this.dateClose,
        isDone = this.isDone,
        isStarted = this.isStarted,
        steps = emptyList(),
        tasks = emptyList(),
        ideas = emptyList(),
        keys = emptyList()
    )