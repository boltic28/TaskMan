package com.boltic28.taskmanager.businesslayer.usecases.task

import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemGetAllUseCase
import com.boltic28.taskmanager.datalayer.entities.Task
import com.boltic28.taskmanager.datalayer.room.task.TaskRepository
import io.reactivex.Single

class TaskGetAllUseCase (
    private val repository: TaskRepository
): ItemGetAllUseCase<Task> {

    override fun getAll(): Single<List<Task>> =
        repository.getAll()
}