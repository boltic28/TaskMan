package com.boltic28.taskmanager.businesslayer.interactors

import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ChildrenItemStructureUseCase
import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemDeleteUseCase
import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemReadUseCase
import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemUpdateUseCase
import com.boltic28.taskmanager.datalayer.entities.Task

class TaskFragmentInteractor(
    reader: ItemReadUseCase<Task>,
    updater: ItemUpdateUseCase<Task>,
    deleter: ItemDeleteUseCase<Task>,
    structureUseCase: ChildrenItemStructureUseCase<Task>
) :
    ItemReadUseCase<Task> by reader,
    ItemUpdateUseCase<Task> by updater,
    ItemDeleteUseCase<Task> by deleter,
    ChildrenItemStructureUseCase<Task> by structureUseCase