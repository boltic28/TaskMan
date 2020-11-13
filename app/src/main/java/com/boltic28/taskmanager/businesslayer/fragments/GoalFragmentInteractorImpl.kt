package com.boltic28.taskmanager.businesslayer.fragments

import com.boltic28.taskmanager.businesslayer.crud.ItemsDeleter
import com.boltic28.taskmanager.businesslayer.crud.ItemsStructureProvider
import com.boltic28.taskmanager.businesslayer.crud.ItemsUpdater
import com.boltic28.taskmanager.datalayer.entities.*
import com.boltic28.taskmanager.datalayer.room.goal.GoalRepository
import io.reactivex.Single

class GoalFragmentInteractorImpl(
    private val goalRepository: GoalRepository,
    private val structureProvider: ItemsStructureProvider,
    private val itemsUpdater: ItemsUpdater,
    private val itemsDeleter: ItemsDeleter
) : GoalFragmentInteractor {

    override fun getGoal(id: Long): Single<Goal> =
        goalRepository.getById(id)

    override fun setChildrenFor(goal: Goal): Single<Goal> =
        structureProvider.setChildrenFor(goal)

    override fun setProgressFor(goal: Goal): Goal =
        structureProvider.setProgressFor(goal)

    override fun update(item: Goal): Single<Int> =
        itemsUpdater.update(item)

    override fun delete(item: Goal): Single<Int> =
        itemsDeleter.delete(item)

    override fun updateTask(item: Task): Single<Int> =
        itemsUpdater.update(item)

    override fun updateIdea(item: Idea): Single<Int> =
        itemsUpdater.update(item)

    override fun updateStep(item: Step): Single<Int> =
        itemsUpdater.update(item)

    override fun updateKey(item: KeyResult): Single<Int> =
        itemsUpdater.update(item)
}