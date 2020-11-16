package com.boltic28.taskmanager.businesslayer.interactors

import com.boltic28.taskmanager.businesslayer.usecases.GetFreeItemsUseCase
import com.boltic28.taskmanager.businesslayer.usecases.ItemsUpdateUseCase
import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemDeleteUseCase
import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemGetAllUseCase
import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemReadUseCase
import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ParentItemStructureUseCase
import com.boltic28.taskmanager.datalayer.entities.Goal
import com.boltic28.taskmanager.datalayer.entities.KeyResult

class KeyFragmentInteractor(
    reader: ItemReadUseCase<KeyResult>,
    updater: ItemsUpdateUseCase,
    deleter: ItemDeleteUseCase<KeyResult>,
    goalProvider: ItemGetAllUseCase<Goal>,
    structureProvider: ParentItemStructureUseCase<KeyResult>,
    freeItemProvider: GetFreeItemsUseCase
) :
    ItemReadUseCase<KeyResult> by reader,
    ItemsUpdateUseCase by updater,
    ItemDeleteUseCase<KeyResult> by deleter,
    ItemGetAllUseCase<Goal> by goalProvider,
    ParentItemStructureUseCase<KeyResult> by structureProvider,
    GetFreeItemsUseCase by freeItemProvider