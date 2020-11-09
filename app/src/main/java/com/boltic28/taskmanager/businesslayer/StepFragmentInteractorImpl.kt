package com.boltic28.taskmanager.businesslayer

import com.boltic28.taskmanager.datalayer.entities.Goal
import com.boltic28.taskmanager.datalayer.entities.Idea
import com.boltic28.taskmanager.datalayer.entities.Step
import com.boltic28.taskmanager.datalayer.entities.Task
import com.boltic28.taskmanager.datalayer.room.goal.GoalRepository
import com.boltic28.taskmanager.datalayer.room.idea.IdeaRepository
import com.boltic28.taskmanager.datalayer.room.step.StepRepository
import com.boltic28.taskmanager.datalayer.room.task.TaskRepository
import io.reactivex.Single

class StepFragmentInteractorImpl(
    private val taskRepository: TaskRepository,
    private val ideaRepository: IdeaRepository,
    private val stepRepository: StepRepository,
    private val goalRepository: GoalRepository,
    private val itemsStructureProvider: ItemsStructureProvider
) : StepFragmentInteractor {

    override fun insert(item: Step): Single<Long> =
        stepRepository.insert(item)

    override fun update(item: Step): Single<Int> =
        stepRepository.update(item)

    override fun delete(item: Step): Single<Int> =
        stepRepository.delete(item)

    override fun getStepById(id: Long): Single<Step> =
        stepRepository.getById(id)

    override fun getParentName(id: Long): Single<String> =
        goalRepository.getById(id).map { it.name }

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
        itemsStructureProvider.setChildrenFor(item)

    override fun setProgressFor(item: Step): Step =
        itemsStructureProvider.setProgressFor(item)
}