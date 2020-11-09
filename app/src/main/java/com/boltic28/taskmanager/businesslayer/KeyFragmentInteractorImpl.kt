package com.boltic28.taskmanager.businesslayer

import com.boltic28.taskmanager.datalayer.entities.Goal
import com.boltic28.taskmanager.datalayer.entities.Idea
import com.boltic28.taskmanager.datalayer.entities.KeyResult
import com.boltic28.taskmanager.datalayer.entities.Task
import com.boltic28.taskmanager.datalayer.room.goal.GoalRepository
import com.boltic28.taskmanager.datalayer.room.idea.IdeaRepository
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyRepository
import com.boltic28.taskmanager.datalayer.room.task.TaskRepository
import io.reactivex.Single

class KeyFragmentInteractorImpl(
    private val keyRepository: KeyRepository,
    private val taskRepository: TaskRepository,
    private val ideaRepository: IdeaRepository,
    private val goalRepository: GoalRepository,
    private val itemsStructureProvider: ItemsStructureProvider
) : KeyFragmentInteractor {

    override fun insert(item: KeyResult): Single<Long> =
        keyRepository.insert(item)

    override fun update(item: KeyResult): Single<Int> =
        keyRepository.update(item)

    override fun delete(item: KeyResult): Single<Int> =
        keyRepository.delete(item)

    override fun getKeyById(id: Long): Single<KeyResult> =
        keyRepository.getById(id)

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

    override fun setChildrenFor(item: KeyResult): Single<KeyResult> =
        itemsStructureProvider.setChildrenFor(item)

    override fun setProgressFor(item: KeyResult): KeyResult =
        itemsStructureProvider.setProgressFor(item)
}