package com.boltic28.taskmanager.businesslayer.usecases.step

import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ParentItemStructureUseCase
import com.boltic28.taskmanager.datalayer.Progress
import com.boltic28.taskmanager.datalayer.entities.Step
import com.boltic28.taskmanager.datalayer.room.goal.GoalRepository
import com.boltic28.taskmanager.datalayer.room.idea.IdeaRepository
import com.boltic28.taskmanager.datalayer.room.step.StepRepository
import com.boltic28.taskmanager.datalayer.room.task.TaskRepository
import io.reactivex.Single
import io.reactivex.Single.zip
import io.reactivex.schedulers.Schedulers

class StepStructureUseCase(
    private val stepRepository: StepRepository,
    private val taskRepository: TaskRepository,
    private val ideaRepository: IdeaRepository,
    private val goalRepository: GoalRepository
) : ParentItemStructureUseCase<Step> {
    override fun setChildrenFor(item: Step): Single<Step> =
        zip(
            Single.just(item),
            taskRepository.getAllForStep(item.id),
            ideaRepository.getAllForStep(item.id), { sStep, tasks, ideas ->
                sStep.copy(tasks = tasks, ideas = ideas)
            })

    override fun setProgressFor(item: Step): Step {
        val pointsToFull = item.tasks.size
        val done = item.tasks.filter { it.isDone }.size
        val isStarted = item.tasks.any { it.isStarted }
        val nStep = if (pointsToFull == 0) {
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
        stepRepository.update(nStep).subscribeOn(Schedulers.io()).subscribe()
        return nStep
    }

    override fun getParentName(item: Step): Single<String> =
        goalRepository.getById(item.goalId).map { it.name }
}