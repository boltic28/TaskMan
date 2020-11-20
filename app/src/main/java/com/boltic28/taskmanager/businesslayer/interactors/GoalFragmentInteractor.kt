package com.boltic28.taskmanager.businesslayer.interactors

import com.boltic28.taskmanager.businesslayer.usecases.GetFreeItemsUseCase
import com.boltic28.taskmanager.businesslayer.usecases.ItemsUpdateUseCase
import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemDeleteUseCase
import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemReadUseCase
import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ParentItemStructureUseCase
import com.boltic28.taskmanager.datalayer.entities.*

class GoalFragmentInteractor(
    reader: ItemReadUseCase<Goal>,
    deleter: ItemDeleteUseCase<Goal>,
    updater: ItemsUpdateUseCase,
    goalStructureProvider: ParentItemStructureUseCase<Goal>,
    freeItemProvider: GetFreeItemsUseCase
) :
    ItemReadUseCase<Goal> by reader,
    ItemDeleteUseCase<Goal> by deleter,
    ParentItemStructureUseCase<Goal> by goalStructureProvider,
    ItemsUpdateUseCase by updater,
    GetFreeItemsUseCase by freeItemProvider