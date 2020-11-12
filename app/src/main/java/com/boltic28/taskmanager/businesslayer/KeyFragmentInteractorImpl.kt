package com.boltic28.taskmanager.businesslayer

import com.boltic28.taskmanager.datalayer.entities.Goal
import com.boltic28.taskmanager.datalayer.entities.Idea
import com.boltic28.taskmanager.datalayer.entities.KeyResult
import com.boltic28.taskmanager.datalayer.entities.Task
import io.reactivex.Single

class KeyFragmentInteractorImpl(
    private val itemsProvider: ItemsProvider,
    private val itemsUpdater: ItemsUpdater,
    private val itemsDeleter: ItemsDeleter,
    private val structureProvider: ItemsStructureProvider
) : KeyFragmentInteractor {

    override fun update(item: KeyResult): Single<Int> =
        itemsUpdater.update(item)

    override fun updateIdea(item: Idea): Single<Int> =
        itemsUpdater.update(item)

    override fun updateTask(item: Task): Single<Int> =
        itemsUpdater.update(item)

    override fun delete(item: KeyResult): Single<Int> =
        itemsDeleter.delete(item)

    override fun getKeyById(id: Long): Single<KeyResult> =
        itemsProvider.getKeyById(id)

    override fun getParentName(item: KeyResult): Single<String> =
        structureProvider.getParentName(item)

    override fun getGoals(): Single<List<Goal>> =
        itemsProvider.getGoals()

    override fun getFreeIdeas(): Single<List<Idea>> =
        itemsProvider.getFreeIdeas()

    override fun getFreeTasks(): Single<List<Task>> =
        itemsProvider.getFreeTasks()

    override fun setChildrenFor(item: KeyResult): Single<KeyResult> =
        structureProvider.setChildrenFor(item)

    override fun setProgressFor(item: KeyResult): KeyResult =
        structureProvider.setProgressFor(item)
}