package com.boltic28.taskmanager.businesslayer.fragments

import com.boltic28.taskmanager.businesslayer.crud.ItemsDeleter
import com.boltic28.taskmanager.businesslayer.crud.ItemsProvider
import com.boltic28.taskmanager.businesslayer.crud.ItemsStructureProvider
import com.boltic28.taskmanager.businesslayer.crud.ItemsUpdater
import com.boltic28.taskmanager.datalayer.entities.ParentItem
import com.boltic28.taskmanager.datalayer.entities.Task
import io.reactivex.Single

class TaskFragmentInteractorImpl(
    private val itemsProvider: ItemsProvider,
    private val itemsUpdater: ItemsUpdater,
    private val itemsDeleter: ItemsDeleter,
    private val structureProvider: ItemsStructureProvider
) : TaskFragmentInteractor {

    override fun update(item: Task): Single<Int> =
        itemsUpdater.update(item)

    override fun delete(item: Task): Single<Int> =
        itemsDeleter.delete(item)

    override fun getTaskById(id: Long): Single<Task> =
        itemsProvider.getTaskById(id)

    override fun getParentName(item: Task): Single<String> =
        structureProvider.getParentName(item)

    override fun getFreeStepsGoalsKeys(): Single<List<ParentItem>> =
        itemsProvider.getFreeParents()
}