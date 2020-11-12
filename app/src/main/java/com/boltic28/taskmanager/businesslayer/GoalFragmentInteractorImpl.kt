package com.boltic28.taskmanager.businesslayer

import com.boltic28.taskmanager.datalayer.entities.*
import com.boltic28.taskmanager.datalayer.room.goal.GoalRepository
import io.reactivex.Single

class GoalFragmentInteractorImpl(
    private val goalRepository: GoalRepository,
    private val structureProvider: ItemsStructureProvider,
    private val itemsUpdater: ItemsUpdater
) : GoalFragmentInteractor {

    override fun getGoal(id: Long): Single<Goal> =
        goalRepository.getById(id)

    override fun setChildrenFor(goal: Goal): Single<Goal> =
        structureProvider.setChildrenFor(goal)

    override fun setProgressFor(goal: Goal): Goal =
        structureProvider.setProgressFor(goal)

    override fun updateGoal(item: Goal): Single<Int> =
        itemsUpdater.update(item)

    override fun updateTask(item: Task): Single<Int> =
        itemsUpdater.update(item)

    override fun updateIdea(item: Idea): Single<Int> =
        itemsUpdater.update(item)

    override fun updateStep(item: Step): Single<Int> =
        itemsUpdater.update(item)

    override fun updateKey(item: KeyResult): Single<Int> =
        itemsUpdater.update(item)
}