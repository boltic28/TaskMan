package com.boltic28.taskmanager.businesslayer

import com.boltic28.taskmanager.datalayer.Progress
import com.boltic28.taskmanager.datalayer.entities.Goal
import com.boltic28.taskmanager.datalayer.room.goal.GoalRepository
import com.boltic28.taskmanager.datalayer.room.idea.IdeaRepository
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyRepository
import com.boltic28.taskmanager.datalayer.room.step.StepRepository
import com.boltic28.taskmanager.datalayer.room.task.TaskRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class CaseGoalStructureImpl(
    private val keyRepository: KeyRepository,
    private val stepRepository: StepRepository,
    private val taskRepository: TaskRepository,
    private val ideaRepository: IdeaRepository,
    private val goalRepository: GoalRepository
) : CaseGoalStructure {

    override fun setChildrenFor(goal: Goal): Single<Goal> =
        Single.just(goal)
            .zipWith(keyRepository.getAllForGoal(goal.id), { sGoal, keys ->
                sGoal.copy(keys = keys)
            })
            .zipWith(
                stepRepository.getAllForGoal(goal.id), { sGoal, steps ->
                    sGoal.copy(steps = steps)
                })
            .zipWith(
                taskRepository.getAllForGoal(goal.id), { sGoal, tasks ->
                    sGoal.copy(tasks = tasks)
                })
            .zipWith(
                ideaRepository.getAllForGoal(goal.id), { sGoal, ideas ->
                    sGoal.copy(ideas = ideas)
                })

    override fun setProgressFor(goal: Goal): Goal {
        val pointsToFull = goal.keys.size + goal.steps.size + goal.tasks.size
        val done = goal.keys.filter { it.progress == Progress.DONE }.size +
                goal.steps.filter { it.isDone }.size +
                goal.tasks.filter { it.isDone }.size
        val isStarted = goal.keys.any { it.progress != Progress.PROGRESS_0 } ||
                goal.steps.any { it.isStarted } ||
                goal.tasks.any { it.isStarted }
        val nGoal = if (pointsToFull == 0) {
            goal.copy(progress = Progress.DONE, isDone = true, isStarted = isStarted)
        } else {
            when ((done * 100.0) / pointsToFull) {
                100.0 -> goal.copy(progress = Progress.DONE, isDone = true, isStarted = isStarted)
                in 75.0..99.0 -> goal.copy(progress = Progress.PROGRESS_80, isStarted = isStarted)
                in 55.0..74.0 -> goal.copy(progress = Progress.PROGRESS_60, isStarted = isStarted)
                in 35.0..54.0 -> goal.copy(progress = Progress.PROGRESS_40, isStarted = isStarted)
                in 15.0..34.0 -> goal.copy(progress = Progress.PROGRESS_20, isStarted = isStarted)
                else -> goal.copy(progress = Progress.PROGRESS_0, isStarted = isStarted)
            }
        }
        goalRepository.update(nGoal).subscribeOn(Schedulers.io()).subscribe()
        return nGoal
    }
}