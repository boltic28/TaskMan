package com.boltic28.taskmanager.businesslayer.usecases.key

import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ParentItemStructureUseCase
import com.boltic28.taskmanager.datalayer.Progress
import com.boltic28.taskmanager.datalayer.entities.KeyResult
import com.boltic28.taskmanager.datalayer.room.goal.GoalRepository
import com.boltic28.taskmanager.datalayer.room.idea.IdeaRepository
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyRepository
import com.boltic28.taskmanager.datalayer.room.task.TaskRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class KeyStructureUseCase(
    private val keyRepository: KeyRepository,
    private val taskRepository: TaskRepository,
    private val ideaRepository: IdeaRepository,
    private val goalRepository: GoalRepository
) : ParentItemStructureUseCase<KeyResult> {
    override fun setChildrenFor(item: KeyResult): Single<KeyResult> =
        Single.zip(
            Single.just(item),
            taskRepository.getAllForStep(item.id),
            ideaRepository.getAllForStep(item.id), { sStep, tasks, ideas ->
                sStep.copy(tasks = tasks, ideas = ideas)
            })

    override fun setProgressFor(item: KeyResult): KeyResult {
        val pointsToFull = item.tasks.size
        val done = item.tasks.filter { it.isDone }.size
        val isStarted = item.tasks.any { it.isStarted }
        val nKey = if (pointsToFull == 0) {
            item.copy(progress = Progress.DONE, isDone = true, isStarted = isStarted)
        } else {
            when ((done * 100.0) / pointsToFull) {
                100.0 -> item.copy(progress = Progress.DONE, isDone = true, isStarted = isStarted)
                in 75.0..99.0 -> item.copy(
                    progress = Progress.PROGRESS_80,
                    isStarted = isStarted,
                    isDone = false
                )
                in 55.0..74.0 -> item.copy(
                    progress = Progress.PROGRESS_60,
                    isStarted = isStarted,
                    isDone = false
                )
                in 35.0..54.0 -> item.copy(
                    progress = Progress.PROGRESS_40,
                    isStarted = isStarted,
                    isDone = false
                )
                in 15.0..34.0 -> item.copy(
                    progress = Progress.PROGRESS_20,
                    isStarted = isStarted,
                    isDone = false
                )
                else -> item.copy(
                    progress = Progress.PROGRESS_0,
                    isStarted = isStarted,
                    isDone = false
                )
            }
        }
        keyRepository.update(nKey).subscribeOn(Schedulers.io()).subscribe()
        return nKey
    }

    override fun getParentName(item: KeyResult): Single<String> =
        goalRepository.getById(item.goalId).map { it.name }
}