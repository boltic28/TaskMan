package com.boltic28.taskmanager.ui.screens.creator

import com.boltic28.taskmanager.datalayer.Cycle
import com.boltic28.taskmanager.datalayer.Progress
import com.boltic28.taskmanager.datalayer.entities.*
import com.boltic28.taskmanager.datalayer.room.goal.GoalRepository
import com.boltic28.taskmanager.datalayer.room.idea.IdeaRepository
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyRepository
import com.boltic28.taskmanager.datalayer.room.step.StepRepository
import com.boltic28.taskmanager.datalayer.room.task.TaskRepository
import com.boltic28.taskmanager.signtools.UserManager
import com.boltic28.taskmanager.ui.base.BaseViewModel
import io.reactivex.Single
import java.time.LocalDateTime
import javax.inject.Inject

class CreatorFragmentModel @Inject constructor(
    private val goalRepository: GoalRepository,
    private val keyRepository: KeyRepository,
    private val stepRepository: StepRepository,
    private val taskRepository: TaskRepository,
    private val ideaRepository: IdeaRepository,
    override var userManager: UserManager
) : BaseViewModel() {

    fun create(item: Goal): Single<Long> =
        goalRepository.insert(item)

    fun create(item: KeyResult): Single<Long> =
        keyRepository.insert(item)

    fun create(item: Step): Single<Long> =
        stepRepository.insert(item)

    fun create(item: Task): Single<Long> =
        taskRepository.insert(item)

    fun create(item: Idea): Single<Long> =
        ideaRepository.insert(item)

    fun saveGoal(name: String, description: String, endDate: LocalDateTime): Single<Long> =
        create(
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
        create(
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
        create(
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
        create(
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
        create(
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