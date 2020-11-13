package com.boltic28.taskmanager.businesslayer.fragments

import com.boltic28.taskmanager.businesslayer.crud.*
import com.boltic28.taskmanager.datalayer.entities.*
import io.reactivex.Single

class IdeaFragmentInteractorImpl(
    private val itemsCreator: ItemsCreator,
    private val itemsProvider: ItemsProvider,
    private val itemsUpdater: ItemsUpdater,
    private val itemsDeleter: ItemsDeleter,
    private val structureProvider: ItemsStructureProvider
) : IdeaFragmentInteractor {

    override fun update(item: Idea): Single<Int> =
        itemsUpdater.update(item)

    override fun delete(item: Idea): Single<Int> =
        itemsDeleter.delete(item)

    override fun getIdeaById(id: Long): Single<Idea> =
        itemsProvider.getIdeaById(id)

    override fun getStepsGoalsKeys(): Single<List<ParentItem>> =
        itemsProvider.getFreeParents()

    override fun create(item: Task): Single<Long> =
        itemsCreator.create(item)

    override fun create(item: Step): Single<Long> =
        itemsCreator.create(item)

    override fun create(item: Goal): Single<Long> =
        itemsCreator.create(item)

    override fun create(item: KeyResult): Single<Long> =
        itemsCreator.create(item)

    override fun getParentName(item: Idea): Single<String> =
        structureProvider.getParentName(item)
}