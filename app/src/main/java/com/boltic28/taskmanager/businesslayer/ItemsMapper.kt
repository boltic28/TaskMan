package com.boltic28.taskmanager.businesslayer

import android.content.ContentValues
import com.boltic28.taskmanager.datalayer.Cycle
import com.boltic28.taskmanager.datalayer.Progress
import com.boltic28.taskmanager.datalayer.room.goal.*
import com.boltic28.taskmanager.datalayer.room.idea.IdeaEntity
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyEntity
import com.boltic28.taskmanager.datalayer.room.step.StepEntity
import com.boltic28.taskmanager.datalayer.room.task.TaskEntity
import java.time.LocalDateTime


fun goalEntityFromContentValues(cv: ContentValues): GoalEntity =
    GoalEntity(
        id = cv.getAsLong(ID),
        uid = cv.getAsString(UID),
        name = cv.getAsString(NAME),
        description = cv.getAsString(DESCRIPTION),
        icon = cv.getAsString(ICON),
        date = LocalDateTime.parse(cv.getAsString(DATE)),
        dateClose = LocalDateTime.parse(cv.getAsString(DATE_CLOSE)),
        isDone = cv.getAsBoolean(IS_DONE),
        isStarted = cv.getAsBoolean(IS_STARTED)
    )

fun stepEntityFromContentValues(cv: ContentValues): StepEntity =
    StepEntity(
        id = cv.getAsLong(ID),
        uid = cv.getAsString(UID),
        goalId = cv.getAsLong(GOAL_ID),
        keyId = cv.getAsLong(KEY_ID),
        name = cv.getAsString(NAME),
        description = cv.getAsString(DESCRIPTION),
        icon = cv.getAsString(ICON),
        date = LocalDateTime.parse(cv.getAsString(DATE)),
        dateClose = LocalDateTime.parse(cv.getAsString(DATE_CLOSE)),
        isDone = cv.getAsBoolean(IS_DONE),
        isStarted = cv.getAsBoolean(IS_STARTED),
        progress = Progress.fromInteger(cv.getAsInteger(PROGRESS))
    )

fun keyEntityFromContentValues(cv: ContentValues): KeyEntity =
    KeyEntity(
        id = cv.getAsLong(ID),
        uid = cv.getAsString(UID),
        goalId = cv.getAsLong(GOAL_ID),
        name = cv.getAsString(NAME),
        description = cv.getAsString(DESCRIPTION),
        icon = cv.getAsString(ICON),
        date = LocalDateTime.parse(cv.getAsString(DATE)),
        dateClose = LocalDateTime.parse(cv.getAsString(DATE_CLOSE)),
        isDone = cv.getAsBoolean(IS_DONE),
        isStarted = cv.getAsBoolean(IS_STARTED),
        progress = Progress.fromInteger(cv.getAsInteger(PROGRESS))
    )

fun taskEntityFromContentValues(cv: ContentValues): TaskEntity =
    TaskEntity(
        id = cv.getAsLong(ID),
        uid = cv.getAsString(UID),
        goalId = cv.getAsLong(GOAL_ID),
        keyId = cv.getAsLong(KEY_ID),
        stepId = cv.getAsLong(STEP_ID),
        name = cv.getAsString(NAME),
        description = cv.getAsString(DESCRIPTION),
        icon = cv.getAsString(ICON),
        date = LocalDateTime.parse(cv.getAsString(DATE)),
        dateClose = LocalDateTime.parse(cv.getAsString(DATE_CLOSE)),
        isDone = cv.getAsBoolean(IS_DONE),
        isStarted = cv.getAsBoolean(IS_STARTED),
        cycle = Cycle.fromString(cv.getAsString(IS_CYCLE))
    )

fun ideaEntityFromContentValues(cv: ContentValues): IdeaEntity =
    IdeaEntity(
        id = cv.getAsLong(ID),
        uid = cv.getAsString(UID),
        goalId = cv.getAsLong(GOAL_ID),
        keyId = cv.getAsLong(KEY_ID),
        stepId = cv.getAsLong(STEP_ID),
        name = cv.getAsString(NAME),
        description = cv.getAsString(DESCRIPTION),
        icon = cv.getAsString(ICON),
        date = LocalDateTime.parse(cv.getAsString(DATE))
    )