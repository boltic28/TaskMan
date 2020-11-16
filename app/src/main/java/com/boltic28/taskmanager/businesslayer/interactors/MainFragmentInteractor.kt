package com.boltic28.taskmanager.businesslayer.interactors

import com.boltic28.taskmanager.businesslayer.usecases.GetAllItemsUseCase
import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemUpdateUseCase
import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ParentItemStructureUseCase
import com.boltic28.taskmanager.datalayer.entities.Goal
import com.boltic28.taskmanager.datalayer.entities.KeyResult
import com.boltic28.taskmanager.datalayer.entities.Step
import com.boltic28.taskmanager.datalayer.entities.Task
import io.reactivex.Single

class MainFragmentInteractor(
    itemProvider: GetAllItemsUseCase,
    taskUpdater: ItemUpdateUseCase<Task>,
    private val goalStructureProvider: ParentItemStructureUseCase<Goal>,
    private val stepStructureProvider: ParentItemStructureUseCase<Step>,
    private val keyStructureProvider: ParentItemStructureUseCase<KeyResult>,
) :
    GetAllItemsUseCase by itemProvider,
    ItemUpdateUseCase<Task> by taskUpdater,
    StructureProviderForParentItems {

    override fun setChildrenFor(goal: Goal): Single<Goal> =
        goalStructureProvider.setChildrenFor(goal)

    override fun setChildrenFor(step: Step): Single<Step> =
        stepStructureProvider.setChildrenFor(step)

    override fun setChildrenFor(key: KeyResult): Single<KeyResult> =
        keyStructureProvider.setChildrenFor(key)

    override fun setProgressFor(goal: Goal): Goal =
        goalStructureProvider.setProgressFor(goal)

    override fun setProgressFor(step: Step): Step =
        stepStructureProvider.setProgressFor(step)

    override fun setProgressFor(key: KeyResult): KeyResult =
        keyStructureProvider.setProgressFor(key)
}