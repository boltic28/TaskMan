package com.boltic28.taskmanager.businesslayer.usecases.task

import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemDeleteUseCase
import com.boltic28.taskmanager.datalayer.entities.Task
import com.boltic28.taskmanager.datalayer.room.task.TaskRepository
import io.reactivex.Single

class TaskDeleteUseCase(
    private val repository: TaskRepository
) : ItemDeleteUseCase<Task> {

    override fun delete(item: Task): Single<Int> =
        repository.delete(item)
}