package com.boltic28.taskmanager.businesslayer.fragments

import com.boltic28.taskmanager.businesslayer.crud.ItemsProvider
import com.boltic28.taskmanager.businesslayer.crud.ItemsStructureProvider
import com.boltic28.taskmanager.businesslayer.crud.ItemsUpdater
import com.boltic28.taskmanager.datalayer.entities.*
import io.reactivex.Single

class MainFragmentInteractorImpl(
    private val itemsProvider: ItemsProvider,
    private val structureProvider: ItemsStructureProvider,
    private val itemsUpdater: ItemsUpdater
) : MainFragmentInteractor {

    override fun getFreeTasks(): Single<List<Task>> =
        itemsProvider.getFreeTasks()

    override fun getTasks(): Single<List<Task>> =
        itemsProvider.getTasks()

    override fun getFreeIdeas(): Single<List<Idea>> =
        itemsProvider.getFreeIdeas()

    override fun getIdeas(): Single<List<Idea>> =
        itemsProvider.getIdeas()

    override fun getFreeKeys(): Single<List<KeyResult>> =
        itemsProvider.getFreeKeys()

    override fun getKeys(): Single<List<KeyResult>> =
        itemsProvider.getKeys()

    override fun getFreeSteps(): Single<List<Step>> =
        itemsProvider.getFreeSteps()

    override fun getSteps(): Single<List<Step>> =
        itemsProvider.getSteps()

    override fun getGoals(): Single<List<Goal>> =
        itemsProvider.getGoals()

    override fun setChildrenFor(goal: Goal): Single<Goal> =
        structureProvider.setChildrenFor(goal)

    override fun setChildrenFor(step: Step): Single<Step> =
        structureProvider.setChildrenFor(step)

    override fun setChildrenFor(key: KeyResult): Single<KeyResult> =
        structureProvider.setChildrenFor(key)

    override fun setProgressFor(goal: Goal): Goal =
        structureProvider.setProgressFor(goal)

    override fun setProgressFor(step: Step): Step =
        structureProvider.setProgressFor(step)

    override fun setProgressFor(key: KeyResult): KeyResult =
        structureProvider.setProgressFor(key)

    override fun update(item: Task): Single<Int> =
        itemsUpdater.update(item)
}