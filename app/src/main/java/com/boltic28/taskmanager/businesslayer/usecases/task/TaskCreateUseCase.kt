package com.boltic28.taskmanager.businesslayer.usecases.task

import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemCreateUseCase
import com.boltic28.taskmanager.datalayer.entities.Task
import com.boltic28.taskmanager.datalayer.room.task.TaskRepository
import io.reactivex.Single

class TaskCreateUseCase(
    private val repository: TaskRepository
) : ItemCreateUseCase<Task> {

    override fun create(item: Task): Single<Long> =
        repository.insert(item)
}