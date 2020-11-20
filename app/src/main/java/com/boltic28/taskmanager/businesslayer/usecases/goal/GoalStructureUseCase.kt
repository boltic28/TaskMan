package com.boltic28.taskmanager.businesslayer.usecases.goal

import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ParentItemStructureUseCase
import com.boltic28.taskmanager.datalayer.Progress
import com.boltic28.taskmanager.datalayer.entities.Goal
import com.boltic28.taskmanager.datalayer.room.goal.GoalRepository
import com.boltic28.taskmanager.datalayer.room.idea.IdeaRepository
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyRepository
import com.boltic28.taskmanager.datalayer.room.step.StepRepository
import com.boltic28.taskmanager.datalayer.room.task.TaskRepository
import io.reactivex.Single
import io.reactivex.Single.zip
import io.reactivex.schedulers.Schedulers

class GoalStructureUseCase(
    private val keyRepository: KeyRepository,
    private val stepRepository: StepRepository,
    private val taskRepository: TaskRepository,
    private val ideaRepository: IdeaRepository,
    private val goalRepository: GoalRepository
) : ParentItemStructureUseCase<Goal> {
    override fun setChildrenFor(item: Goal): Single<Goal> =
        zip(
            Single.just(item),
            keyRepository.getAllForGoal(item.id),
            stepRepository.getAllForGoal(item.id),
            taskRepository.getAllForGoal(item.id),
            ideaRepository.getAllForGoal(item.id),
            { sGoal, keys, steps, tasks, ideas ->
                sGoal.copy(keys = keys, steps = steps, tasks = tasks, ideas = ideas)
            })

    override fun setProgressFor(item: Goal): Goal {
        val pointsToFull = item.keys.size + item.steps.size + item.tasks.size
        val done = item.keys.filter { it.isDone }.size +
                item.steps.filter { it.isDone }.size +
                item.tasks.filter { it.isDone }.size
        val isStarted = item.keys.any { it.isStarted } ||
                item.steps.any { it.isStarted } ||
                item.tasks.any { it.isStarted }
        val nGoal = if (pointsToFull == 0) {
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
        goalRepository.update(nGoal).subscribeOn(Schedulers.io()).subscribe()
        return nGoal
    }

    override fun getParentName(item: Goal): Single<String> = Single.just(item.name)
}