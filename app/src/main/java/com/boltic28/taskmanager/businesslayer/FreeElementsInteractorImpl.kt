package com.boltic28.taskmanager.businesslayer

import com.boltic28.taskmanager.datalayer.entities.*
import com.boltic28.taskmanager.datalayer.room.goal.GoalRepository
import com.boltic28.taskmanager.datalayer.room.idea.IdeaRepository
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyRepository
import com.boltic28.taskmanager.datalayer.room.step.StepRepository
import com.boltic28.taskmanager.datalayer.room.task.TaskRepository
import io.reactivex.Single

class FreeElementsInteractorImpl(
    private val keyRepository: KeyRepository,
    private val stepRepository: StepRepository,
    private val taskRepository: TaskRepository,
    private val ideaRepository: IdeaRepository,
    private val goalRepository: GoalRepository,
    private val structureProvider: ItemsStructureProvider
) : FreeElementsInteractor {

    override fun getFreeTasks(): Single<List<Task>> =
        taskRepository.getAllFree()

    override fun getTasks(): Single<List<Task>> =
        taskRepository.getAll()

    override fun getFreeIdeas(): Single<List<Idea>> =
        ideaRepository.getAllFree()

    override fun getIdeas(): Single<List<Idea>> =
        ideaRepository.getAll()

    override fun getFreeKeys(): Single<List<KeyResult>> =
        keyRepository.getAllFree()

    override fun getKeys(): Single<List<KeyResult>> =
        keyRepository.getAll()

    override fun getFreeSteps(): Single<List<Step>> =
        stepRepository.getAllFree()

    override fun getSteps(): Single<List<Step>> =
        stepRepository.getAll()

    override fun getGoals(): Single<List<Goal>> =
        goalRepository.getAll()

    override fun setChildrenFor(goal: Goal): Single<Goal> =
        structureProvider.setChildrenFor(goal)

    override fun setProgressFor(goal: Goal): Goal =
        structureProvider.setProgressFor(goal)

    override fun update(item: Task): Single<Int> =
        taskRepository.update(item)
}