package com.boltic28.taskmanager.businesslayer

import com.boltic28.taskmanager.datalayer.Progress
import com.boltic28.taskmanager.datalayer.entities.*
import com.boltic28.taskmanager.datalayer.room.goal.GoalRepository
import com.boltic28.taskmanager.datalayer.room.idea.IdeaRepository
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyRepository
import com.boltic28.taskmanager.datalayer.room.step.StepRepository
import com.boltic28.taskmanager.datalayer.room.task.TaskRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction

class CaseGoalStructureImpl(
    private val keyRepository: KeyRepository,
    private val stepRepository: StepRepository,
    private val taskRepository: TaskRepository,
    private val ideaRepository: IdeaRepository
): CaseGoalStructure {

    override fun setChildrenFor(goal: Goal): Single<Goal> =
        Single.just(goal)
            .zipWith(keyRepository.getAllForGoal(goal.id),
                BiFunction<Goal, List<KeyResult>, Goal> { sGoal, keys ->
                    sGoal.copy(keys = keys)
                })
            .zipWith(
                stepRepository.getAllForGoal(goal.id),
                BiFunction<Goal, List<Step>, Goal> { sGoal, steps ->
                    sGoal.copy(steps = steps)
                })
            .zipWith(
                taskRepository.getAllForGoal(goal.id),
                BiFunction<Goal, List<Task>, Goal> { sGoal, tasks ->
                    sGoal.copy(tasks = tasks)
                })
            .zipWith(
                ideaRepository.getAllForGoal(goal.id),
                BiFunction<Goal, List<Idea>, Goal> { sGoal, ideas ->
                    sGoal.copy(ideas = ideas)
                })

    override fun setProgressFor(goal: Goal): Goal {
        val pointsToFull = goal.keys.size + goal.steps.size
        val done = goal.keys.filter { it.progress == Progress.DONE }.size +
                goal.steps.filter { it.isDone }.size

        return if (pointsToFull == 0) {
            goal.copy(progress = Progress.DONE)
        } else {
            when ((done * 100.0 )/ pointsToFull) {
                100.0 -> goal.copy(progress = Progress.DONE)
                in 75.0..99.0 -> goal.copy(progress = Progress.PROGRESS_80)
                in 55.0..74.0 -> goal.copy(progress = Progress.PROGRESS_60)
                in 35.0..54.0 -> goal.copy(progress = Progress.PROGRESS_40)
                in 15.0..34.0 -> goal.copy(progress = Progress.PROGRESS_20)
                else -> goal.copy(progress = Progress.PROGRESS_0)
            }
        }
    }
}