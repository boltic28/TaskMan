package com.boltic28.taskmanager.ui.screens.creator

import com.boltic28.taskmanager.businesslayer.usecases.ItemsCreateUseCase
import com.boltic28.taskmanager.datalayer.Cycle
import com.boltic28.taskmanager.datalayer.Progress
import com.boltic28.taskmanager.datalayer.entities.*
import com.boltic28.taskmanager.signtools.UserManager
import com.boltic28.taskmanager.ui.base.BaseViewModel
import com.boltic28.taskmanager.ui.constant.*
import com.boltic28.taskmanager.utils.Messenger
import io.reactivex.Single
import java.time.LocalDateTime
import javax.inject.Inject

class CreatorFragmentModel @Inject constructor(
    private val interactor: ItemsCreateUseCase,
    override var userManager: UserManager,
    val messenger: Messenger
) : BaseViewModel() {

    fun saveGoal(name: String, description: String, endDate: LocalDateTime): Single<Long> =
        interactor.create(
            Goal(
                id = NO_ID,
                name = name,
                description = description,
                icon = GOAL_EXTRA,
                date = LocalDateTime.now(),
                dateClose = endDate,
                isDone = false,
                isStarted = false,
                steps = emptyList(),
                tasks = emptyList(),
                ideas = emptyList(),
                keys = emptyList(),
                progress = Progress.PROGRESS_0
            )
        )

    fun saveKey(name: String, description: String, endDate: LocalDateTime): Single<Long> =
        interactor.create(
            KeyResult(
                id = NO_ID,
                goalId = NO_ID,
                name = name,
                description = description,
                icon = KEY_EXTRA,
                date = LocalDateTime.now(),
                dateClose = endDate,
                isDone = false,
                isStarted = false,
                progress = Progress.PROGRESS_0,
                steps = emptyList(),
                tasks = emptyList(),
                ideas = emptyList()
            )
        )

    fun saveStep(name: String, description: String, endDate: LocalDateTime): Single<Long> =
        interactor.create(
            Step(
                id = NO_ID,
                goalId = NO_ID,
                keyId = NO_ID,
                name = name,
                description = description,
                icon = STEP_EXTRA,
                date = LocalDateTime.now(),
                dateClose = endDate,
                isDone = false,
                isStarted = false,
                progress = Progress.PROGRESS_0,
                tasks = emptyList(),
                ideas = emptyList()
            )
        )

    fun saveTask(
        name: String,
        description: String,
        endDate: LocalDateTime,
        cycle: String
    ): Single<Long> =
        interactor.create(
            Task(
                id = NO_ID,
                stepId = NO_ID,
                goalId = NO_ID,
                keyId = NO_ID,
                name = name,
                description = description,
                icon = TASK_EXTRA,
                date = LocalDateTime.now(),
                dateClose = endDate,
                cycle = Cycle.fromString(cycle),
                isDone = false,
                isStarted = false
            )
        )

    fun saveIdea(name: String, description: String): Single<Long> =
        interactor.create(
            Idea(
                id = NO_ID,
                stepId = NO_ID,
                goalId = NO_ID,
                keyId = NO_ID,
                name = name,
                description = description,
                icon = IDEA_EXTRA,
                date = LocalDateTime.now(),
                dateClose = LocalDateTime.now()
            )
        )
}