package com.boltic28.taskmanager.businesslayer.interactors

import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemGetAllUseCase
import com.boltic28.taskmanager.datalayer.entities.Task

class ServiceInteractor(
    reader: ItemGetAllUseCase<Task>
) : ItemGetAllUseCase<Task> by reader