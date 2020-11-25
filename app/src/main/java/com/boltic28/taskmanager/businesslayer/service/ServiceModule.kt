package com.boltic28.taskmanager.businesslayer.service

import com.boltic28.taskmanager.businesslayer.interactors.ServiceInteractor
import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemGetAllUseCase
import com.boltic28.taskmanager.businesslayer.usecases.task.TaskGetAllUseCase
import com.boltic28.taskmanager.datalayer.entities.Task
import com.boltic28.taskmanager.datalayer.room.task.TaskRepository
import dagger.Module
import dagger.Provides

@Module
class ServiceModule(
    private val taskRepository: TaskRepository
) {

    @Provides
    fun provideTaskGetAllUseCase(): ItemGetAllUseCase<Task> =
        TaskGetAllUseCase(taskRepository)

    @Provides
    fun provideServiceInteractor(): ServiceInteractor =
        ServiceInteractor(
            provideTaskGetAllUseCase()
        )
}