package com.boltic28.taskmanager.businesslayer

import com.boltic28.taskmanager.datalayer.entities.*
import com.boltic28.taskmanager.datalayer.firebaseworker.RemoteDB
import com.boltic28.taskmanager.datalayer.room.goal.GoalRepository
import com.boltic28.taskmanager.datalayer.room.idea.IdeaRepository
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyRepository
import com.boltic28.taskmanager.datalayer.room.step.StepRepository
import com.boltic28.taskmanager.datalayer.room.task.TaskRepository
import io.reactivex.Single

class ItemsDeleterImpl(
    private val keyRepository: KeyRepository,
    private val stepRepository: StepRepository,
    private val taskRepository: TaskRepository,
    private val ideaRepository: IdeaRepository,
    private val goalRepository: GoalRepository,
    private val remoteDB: RemoteDB
): ItemsDeleter {

    override fun delete(item: Goal): Single<Int> =
        goalRepository.delete(item).doOnSuccess {
            //todo delete on firebase
        }

    override fun delete(item: Step): Single<Int> =
        stepRepository.delete(item).doOnSuccess {
            //todo delete on firebase
        }

    override fun delete(item: Task): Single<Int> =
        taskRepository.delete(item).doOnSuccess {
            //todo delete on firebase
        }

    override fun delete(item: Idea): Single<Int> =
        ideaRepository.delete(item).doOnSuccess {
            //todo delete on firebase
        }

    override fun delete(item: KeyResult): Single<Int> =
        keyRepository.delete(item).doOnSuccess {
            //todo delete on firebase
        }
}