package com.boltic28.taskmanager.businesslayer.usecases.task

import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemGetAllFreeUseCase
import com.boltic28.taskmanager.datalayer.entities.Task
import com.boltic28.taskmanager.datalayer.room.task.TaskRepository
import io.reactivex.Single

class TaskGetAllFreeUseCase (
    private val repository: TaskRepository
): ItemGetAllFreeUseCase<Task> {

    override fun getAllFree(): Single<List<Task>> =
        repository.getAllFree()
}