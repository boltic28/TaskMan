package com.boltic28.taskmanager.businesslayer.usecases

import com.boltic28.taskmanager.datalayer.entities.*
import com.boltic28.taskmanager.datalayer.firebaseworker.RemoteDB
import com.boltic28.taskmanager.datalayer.firebaseworker.dto.RemoteRepo
import com.boltic28.taskmanager.datalayer.room.goal.GoalRepository
import com.boltic28.taskmanager.datalayer.room.idea.IdeaRepository
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyRepository
import com.boltic28.taskmanager.datalayer.room.step.StepRepository
import com.boltic28.taskmanager.datalayer.room.task.TaskRepository
import io.reactivex.Single

class ItemsCreateUseCaseImpl(
    private val keyRepository: KeyRepository,
    private val stepRepository: StepRepository,
    private val taskRepository: TaskRepository,
    private val ideaRepository: IdeaRepository,
    private val goalRepository: GoalRepository
) : ItemsCreateUseCase {

    override fun create(item: Goal): Single<Long> =
        goalRepository.insert(item)

    override fun create(item: Task): Single<Long> =
        taskRepository.insert(item)

    override fun create(item: Step): Single<Long> =
        stepRepository.insert(item)

    override fun create(item: Idea): Single<Long> =
        ideaRepository.insert(item)

    override fun create(item: KeyResult): Single<Long> =
        keyRepository.insert(item)
}