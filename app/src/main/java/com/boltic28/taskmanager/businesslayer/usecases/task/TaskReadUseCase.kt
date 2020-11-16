package com.boltic28.taskmanager.businesslayer.usecases.task

import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemReadUseCase
import com.boltic28.taskmanager.datalayer.entities.Task
import com.boltic28.taskmanager.datalayer.room.task.TaskRepository
import io.reactivex.Single

class TaskReadUseCase(
    private val repository: TaskRepository
) : ItemReadUseCase<Task> {
    override fun getItemById(id: Long): Single<Task> =
        repository.getById(id)
}