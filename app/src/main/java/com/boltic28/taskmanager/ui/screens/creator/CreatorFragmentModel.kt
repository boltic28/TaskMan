package com.boltic28.taskmanager.ui.screens.creator

import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.di.AppDagger
import com.boltic28.taskmanager.datalayer.Cycle
import com.boltic28.taskmanager.datalayer.Progress
import com.boltic28.taskmanager.datalayer.entities.*
import com.boltic28.taskmanager.datalayer.room.goal.GoalRepository
import com.boltic28.taskmanager.datalayer.room.idea.IdeaRepository
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyRepository
import com.boltic28.taskmanager.datalayer.room.step.StepRepository
import com.boltic28.taskmanager.datalayer.room.task.TaskRepository
import io.reactivex.Single
import java.time.LocalDateTime
import javax.inject.Inject

class CreatorFragmentModel : ViewModel() {

    @Inject
    lateinit var goalRepository: GoalRepository

    @Inject
    lateinit var keyRepository: KeyRepository

    @Inject
    lateinit var stepRepository: StepRepository

    @Inject
    lateinit var taskRepository: TaskRepository

    @Inject
    lateinit var ideaRepository: IdeaRepository

    init {
        AppDagger.component.injectModel(this)
    }

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