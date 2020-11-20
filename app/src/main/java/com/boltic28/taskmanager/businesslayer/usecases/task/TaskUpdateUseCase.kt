package com.boltic28.taskmanager.businesslayer.usecases.task

import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemUpdateUseCase
import com.boltic28.taskmanager.datalayer.entities.Task
import com.boltic28.taskmanager.datalayer.room.task.TaskRepository
import io.reactivex.Single

class TaskUpdateUseCase(
    private val repository: TaskRepository
): ItemUpdateUseCase<Task> {

    override fun update(item: Task): Single<Int> =
        repository.update(item)
}