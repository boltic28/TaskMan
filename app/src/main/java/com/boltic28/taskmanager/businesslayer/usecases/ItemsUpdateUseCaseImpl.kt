package com.boltic28.taskmanager.businesslayer.usecases

import com.boltic28.taskmanager.datalayer.entities.*
import com.boltic28.taskmanager.datalayer.firebaseworker.RemoteDB
import com.boltic28.taskmanager.datalayer.room.goal.GoalRepository
import com.boltic28.taskmanager.datalayer.room.idea.IdeaRepository
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyRepository
import com.boltic28.taskmanager.datalayer.room.step.StepRepository
import com.boltic28.taskmanager.datalayer.room.task.TaskRepository
import io.reactivex.Single

class ItemsUpdateUseCaseImpl(
    private val keyRepository: KeyRepository,
    private val stepRepository: StepRepository,
    private val taskRepository: TaskRepository,
    private val ideaRepository: IdeaRepository,
    private val goalRepository: GoalRepository,
    private val remoteDB: RemoteDB
): ItemsUpdateUseCase {

    override fun update(item: Goal): Single<Int> =
        goalRepository.update(item)
            .doOnSuccess { remoteDB.write(item) }

    override fun update(item: Task): Single<Int> =
        taskRepository.update(item)
            .doOnSuccess { remoteDB.write(item) }

    override fun update(item: Step): Single<Int> =
        stepRepository.update(item)
            .doOnSuccess { remoteDB.write(item) }

    override fun update(item: Idea): Single<Int> =
        ideaRepository.update(item)
            .doOnSuccess { remoteDB.write(item) }

    override fun update(item: KeyResult): Single<Int> =
        keyRepository.update(item)
            .doOnSuccess { remoteDB.write(item) }
}