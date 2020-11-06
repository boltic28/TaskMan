package com.boltic28.taskmanager.businesslayer

import com.boltic28.taskmanager.datalayer.Progress
import com.boltic28.taskmanager.datalayer.entities.Goal
import com.boltic28.taskmanager.datalayer.entities.Idea
import com.boltic28.taskmanager.datalayer.entities.Step
import com.boltic28.taskmanager.datalayer.entities.Task
import com.boltic28.taskmanager.datalayer.room.goal.GoalRepository
import com.boltic28.taskmanager.datalayer.room.idea.IdeaRepository
import com.boltic28.taskmanager.datalayer.room.step.StepRepository
import com.boltic28.taskmanager.datalayer.room.task.TaskRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class StepFragmentInteractorImpl(
    private var taskRepository: TaskRepository,
    private var ideaRepository: IdeaRepository,
    private var stepRepository: StepRepository,
    private var goalRepository: GoalRepository
) : StepFragmentInteractor {

    override fun insert(item: Step): Single<Long> =
        stepRepository.insert(item)

    override fun update(item: Step): Single<Int> =
        stepRepository.update(item)

    override fun delete(item: Step): Single<Int> =
        stepRepository.delete(item)

    override fun getStepById(id: Long): Single<Step> =
        stepRepository.getById(id)

    override fun getGoalById(id: Long): Single<Goal> =
        goalRepository.getById(id)

    override fun getGoals(): Single<List<Goal>> =
        goalRepository.getAll()

    override fun getFreeIdeas(): Single<List<Idea>> =
        ideaRepository.getAllFree()

    override fun getFreeTasks(): Single<List<Task>> =
        taskRepository.getAllFree()

    override fun updateTask(item: Task): Single<Int> =
        taskRepository.update(item)

    override fun updateIdea(item: Idea): Single<Int> =
        ideaRepository.update(item)

    override fun setChildrenFor(item: Step): Single<Step> =
        Single.just(item)
            .zipWith(
                taskRepository.getAllForStep(item.id), { step, tasks ->
                    step.copy(tasks = tasks)
                })
            .zipWith(
                ideaRepository.getAllForStep(item.id), { step, ideas ->
                    step.copy(ideas = ideas)
                })

    override fun setProgressFor(step: Step): Step {
        val pointsToFull = step.tasks.size
        val done = step.tasks.filter { it.isDone }.size
        val isStarted = step.tasks.any { it.isStarted }
        val nStep = if (pointsToFull == 0) {
            step.copy(progress = Progress.DONE, isDone = true, isStarted = isStarted)
        } else {
            when ((done * 100.0) / pointsToFull) {
                100.0 -> step.copy(progress = Progress.DONE, isDone = true, isStarted = isStarted)
                in 75.0..99.0 -> step.copy(progress = Progress.PROGRESS_80, isStarted = isStarted)
                in 55.0..74.0 -> step.copy(progress = Progress.PROGRESS_60, isStarted = isStarted)
                in 35.0..54.0 -> step.copy(progress = Progress.PROGRESS_40, isStarted = isStarted)
                in 15.0..34.0 -> step.copy(progress = Progress.PROGRESS_20, isStarted = isStarted)
                else -> step.copy(progress = Progress.PROGRESS_0, isStarted = isStarted)
            }
        }
        stepRepository.update(nStep).subscribeOn(Schedulers.io()).subscribe()
        return nStep
    }
}