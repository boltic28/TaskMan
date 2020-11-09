package com.boltic28.taskmanager.businesslayer

import com.boltic28.taskmanager.datalayer.Progress
import com.boltic28.taskmanager.datalayer.entities.*
import com.boltic28.taskmanager.datalayer.room.goal.GoalRepository
import com.boltic28.taskmanager.datalayer.room.idea.IdeaRepository
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyRepository
import com.boltic28.taskmanager.datalayer.room.step.StepRepository
import com.boltic28.taskmanager.datalayer.room.task.TaskRepository
import io.reactivex.Single
import io.reactivex.Single.zip
import io.reactivex.schedulers.Schedulers

class ItemsStructureProviderImpl(
    private val keyRepository: KeyRepository,
    private val stepRepository: StepRepository,
    private val taskRepository: TaskRepository,
    private val ideaRepository: IdeaRepository,
    private val goalRepository: GoalRepository
) : ItemsStructureProvider {



    override fun setChildrenFor(goal: Goal): Single<Goal> =
        zip(
            Single.just(goal),
            keyRepository.getAllForGoal(goal.id),
            stepRepository.getAllForGoal(goal.id),
            taskRepository.getAllForGoal(goal.id),
            ideaRepository.getAllForGoal(goal.id),
            { sGoal, keys, steps, tasks, ideas ->
                sGoal.copy(keys = keys, steps = steps, tasks = tasks, ideas = ideas)
            })

    override fun setChildrenFor(step: Step): Single<Step> =
        zip(
            Single.just(step),
            taskRepository.getAllForStep(step.id),
            ideaRepository.getAllForStep(step.id), { sStep, tasks, ideas ->
                sStep.copy(tasks = tasks, ideas = ideas)
            })

    override fun setChildrenFor(key: KeyResult): Single<KeyResult> =
        zip(
            Single.just(key),
            taskRepository.getAllForKey(key.id),
            ideaRepository.getAllForKey(key.id),
            { sKey, tasks, ideas ->
                sKey.copy(tasks = tasks, ideas = ideas)
            })

    override fun setProgressFor(goal: Goal): Goal {
        val pointsToFull = goal.keys.size + goal.steps.size + goal.tasks.size
        val done = goal.keys.filter { it.isDone }.size +
                goal.steps.filter { it.isDone }.size +
                goal.tasks.filter { it.isDone }.size
        val isStarted = goal.keys.any { it.isStarted } ||
                goal.steps.any { it.isStarted } ||
                goal.tasks.any { it.isStarted }
        val nGoal = if (pointsToFull == 0) {
            goal.copy(progress = Progress.DONE, isDone = true, isStarted = isStarted)
        } else {
            when ((done * 100.0) / pointsToFull) {
                100.0 -> goal.copy(progress = Progress.DONE, isDone = true, isStarted = isStarted)
                in 75.0..99.0 -> goal.copy(progress = Progress.PROGRESS_80, isStarted = isStarted, isDone = false)
                in 55.0..74.0 -> goal.copy(progress = Progress.PROGRESS_60, isStarted = isStarted, isDone = false)
                in 35.0..54.0 -> goal.copy(progress = Progress.PROGRESS_40, isStarted = isStarted, isDone = false)
                in 15.0..34.0 -> goal.copy(progress = Progress.PROGRESS_20, isStarted = isStarted, isDone = false)
                else -> goal.copy(progress = Progress.PROGRESS_0, isStarted = isStarted, isDone = false)
            }
        }
        goalRepository.update(nGoal).subscribeOn(Schedulers.io()).subscribe()
        return nGoal
    }

    override fun setProgressFor(key: KeyResult): KeyResult {
        val pointsToFull = key.tasks.size
        val done = key.tasks.filter { it.isDone }.size
        val isStarted = key.tasks.any { it.isStarted }
        val nKey = if (pointsToFull == 0) {
            key.copy(progress = Progress.DONE, isStarted = isStarted, isDone = true)
        } else {
            when ((done * 100.0) / pointsToFull) {
                100.0 -> key.copy(progress = Progress.DONE, isDone = true, isStarted = isStarted)
                in 75.0..99.0 -> key.copy(progress = Progress.PROGRESS_80, isStarted = isStarted, isDone = false)
                in 55.0..74.0 -> key.copy(progress = Progress.PROGRESS_60, isStarted = isStarted, isDone = false)
                in 35.0..54.0 -> key.copy(progress = Progress.PROGRESS_40, isStarted = isStarted, isDone = false)
                in 15.0..34.0 -> key.copy(progress = Progress.PROGRESS_20, isStarted = isStarted, isDone = false)
                else -> key.copy(progress = Progress.PROGRESS_0, isStarted = isStarted, isDone = false)
            }
        }
        keyRepository.update(nKey).subscribeOn(Schedulers.io()).subscribe()
        return nKey
    }

    override fun setProgressFor(step: Step): Step {
        val pointsToFull = step.tasks.size
        val done = step.tasks.filter { it.isDone }.size
        val isStarted = step.tasks.any { it.isStarted }
        val nStep = if (pointsToFull == 0) {
            step.copy(progress = Progress.DONE, isDone = true, isStarted = isStarted)
        } else {
            when ((done * 100.0) / pointsToFull) {
                100.0 -> step.copy(progress = Progress.DONE, isDone = true, isStarted = isStarted)
                in 75.0..99.0 -> step.copy(progress = Progress.PROGRESS_80, isStarted = isStarted, isDone = false)
                in 55.0..74.0 -> step.copy(progress = Progress.PROGRESS_60, isStarted = isStarted, isDone = false)
                in 35.0..54.0 -> step.copy(progress = Progress.PROGRESS_40, isStarted = isStarted, isDone = false)
                in 15.0..34.0 -> step.copy(progress = Progress.PROGRESS_20, isStarted = isStarted, isDone = false)
                else -> step.copy(progress = Progress.PROGRESS_0, isStarted = isStarted, isDone = false)
            }
        }
        stepRepository.update(nStep).subscribeOn(Schedulers.io()).subscribe()
        return nStep
    }
}