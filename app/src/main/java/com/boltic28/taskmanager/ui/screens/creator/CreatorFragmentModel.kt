package com.boltic28.taskmanager.ui.screens.creator

import com.boltic28.taskmanager.businesslayer.CreatorInteractor
import com.boltic28.taskmanager.datalayer.Cycle
import com.boltic28.taskmanager.datalayer.Progress
import com.boltic28.taskmanager.datalayer.entities.*
import com.boltic28.taskmanager.signtools.UserManager
import com.boltic28.taskmanager.ui.base.BaseViewModel
import com.boltic28.taskmanager.utils.Messenger
import io.reactivex.Single
import java.time.LocalDateTime
import javax.inject.Inject

class CreatorFragmentModel @Inject constructor(
    private val interactor: CreatorInteractor,
    override var userManager: UserManager,
    val messenger: Messenger
) : BaseViewModel() {

    fun saveGoal(name: String, description: String, endDate: LocalDateTime): Single<Long> =
        interactor.create(
            Goal(
                id = 0,
                name = name,
                description = description,
                icon = "",
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

    fun saveKey(name: String, description: String): Single<Long> =
        interactor.create(
            KeyResult(
                id = 0,
                goalId = 0,
                name = name,
                description = description,
                date = LocalDateTime.now(),
                progress = Progress.PROGRESS_0,
                steps = emptyList(),
                tasks = emptyList(),
                ideas = emptyList()
            )
        )

    fun saveStep(name: String, description: String, endDate: LocalDateTime): Single<Long> =
        interactor.create(
            Step(
                id = 0,
                goalId = 0,
                keyId = 0,
                name = name,
                description = description,
                icon = "",
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
                id = 0,
                stepId = 0,
                goalId = 0,
                keyId = 0,
                name = name,
                description = description,
                icon = "",
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
                id = 0,
                stepId = 0,
                goalId = 0,
                keyId = 0,
                name = name,
                description = description,
                icon = "",
                date = LocalDateTime.now()
            )
        )
}