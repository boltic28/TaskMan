package com.boltic28.taskmanager.businesslayer.interactors

import com.boltic28.taskmanager.businesslayer.usecases.ItemsCreateUseCase
import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ChildrenItemStructureUseCase
import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemDeleteUseCase
import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemReadUseCase
import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemUpdateUseCase
import com.boltic28.taskmanager.datalayer.entities.Idea

class IdeaFragmentInteractor(
    creator: ItemsCreateUseCase,
    reader: ItemReadUseCase<Idea>,
    updater: ItemUpdateUseCase<Idea>,
    deleter: ItemDeleteUseCase<Idea>,
    structureProvider: ChildrenItemStructureUseCase<Idea>,
) :
    ItemsCreateUseCase by creator,
    ItemUpdateUseCase<Idea> by updater,
    ItemDeleteUseCase<Idea> by deleter,
    ItemReadUseCase<Idea> by reader,
    ChildrenItemStructureUseCase<Idea> by structureProvider