package com.boltic28.taskmanager.businesslayer.interactors

import com.boltic28.taskmanager.businesslayer.usecases.GetFreeItemsUseCase
import com.boltic28.taskmanager.businesslayer.usecases.ItemsUpdateUseCase
import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemDeleteUseCase
import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemGetAllUseCase
import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemReadUseCase
import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ParentItemStructureUseCase
import com.boltic28.taskmanager.datalayer.entities.Goal
import com.boltic28.taskmanager.datalayer.entities.Step

class StepFragmentInteractor(
    reader: ItemReadUseCase<Step>,
    updater: ItemsUpdateUseCase,
    deleter: ItemDeleteUseCase<Step>,
    structureProvider: ParentItemStructureUseCase<Step>,
    goalProvider: ItemGetAllUseCase<Goal>,
    freeItemProvider: GetFreeItemsUseCase
) :
    ItemReadUseCase<Step> by reader,
    ItemsUpdateUseCase by updater,
    ItemDeleteUseCase<Step> by deleter,
    ParentItemStructureUseCase<Step> by structureProvider,
    ItemGetAllUseCase<Goal> by goalProvider,
    GetFreeItemsUseCase by freeItemProvider