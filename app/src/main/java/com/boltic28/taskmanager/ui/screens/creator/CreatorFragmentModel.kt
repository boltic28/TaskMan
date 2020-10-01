package com.boltic28.taskmanager.ui.screens.creator

import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.dagger.AppDagger
import com.boltic28.taskmanager.datalayer.Cycle
import com.boltic28.taskmanager.datalayer.classes.*
import com.boltic28.taskmanager.datalayer.room.goal.GoalService
import com.boltic28.taskmanager.datalayer.room.idea.IdeaService
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyService
import com.boltic28.taskmanager.datalayer.room.step.StepService
import com.boltic28.taskmanager.datalayer.room.task.TaskService
import io.reactivex.Single
import java.time.LocalDateTime
import javax.inject.Inject

class CreatorFragmentModel : ViewModel() {

    @Inject
    lateinit var goalService: GoalService

    @Inject
    lateinit var keyService: KeyService

    @Inject
    lateinit var stepService: StepService

    @Inject
    lateinit var taskService: TaskService

    @Inject
    lateinit var ideaService: IdeaService

    init {
        AppDagger.component.injectModel(this)
    }

    fun create(item: Goal): Single<Long> =
        goalService.insert(item)

    fun create(item: KeyResult): Single<Long> =
        keyService.insert(item)

    fun create(item: Step): Single<Long> =
        stepService.insert(item)

    fun create(item: Task): Single<Long> =
        taskService.insert(item)

    fun create(item: Idea): Single<Long> =
        ideaService.insert(item)

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
                keys = emptyList()
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
                progress = 0,
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