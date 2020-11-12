package com.boltic28.taskmanager.businesslayer

import com.boltic28.taskmanager.datalayer.entities.Goal
import com.boltic28.taskmanager.datalayer.entities.Idea
import com.boltic28.taskmanager.datalayer.entities.Step
import com.boltic28.taskmanager.datalayer.entities.Task
import io.reactivex.Single

class StepFragmentInteractorImpl(
    private val itemsProvider: ItemsProvider,
    private val itemsUpdater: ItemsUpdater,
    private val itemsDeleter: ItemsDeleter,
    private val structureProvider: ItemsStructureProvider
) : StepFragmentInteractor {

    override fun update(item: Step): Single<Int> =
        itemsUpdater.update(item)

    override fun updateTask(item: Task): Single<Int> =
        itemsUpdater.update(item)

    override fun updateIdea(item: Idea): Single<Int> =
        itemsUpdater.update(item)

    override fun delete(item: Step): Single<Int> =
        itemsDeleter.delete(item)

    override fun getStepById(id: Long): Single<Step> =
        itemsProvider.getStepById(id)

    override fun getParentName(item: Step): Single<String> =
        structureProvider.getParentName(item)

    override fun getGoals(): Single<List<Goal>> =
        itemsProvider.getGoals()

    override fun getFreeIdeas(): Single<List<Idea>> =
        itemsProvider.getFreeIdeas()

    override fun getFreeTasks(): Single<List<Task>> =
        itemsProvider.getFreeTasks()

    override fun setChildrenFor(item: Step): Single<Step> =
        structureProvider.setChildrenFor(item)

    override fun setProgressFor(item: Step): Step =
        structureProvider.setProgressFor(item)
}