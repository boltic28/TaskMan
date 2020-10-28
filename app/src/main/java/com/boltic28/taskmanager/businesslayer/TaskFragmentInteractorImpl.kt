package com.boltic28.taskmanager.businesslayer

import com.boltic28.taskmanager.datalayer.entities.Task
import com.boltic28.taskmanager.datalayer.room.task.TaskRepository
import io.reactivex.Single

class TaskFragmentInteractorImpl(
    private val taskRepository: TaskRepository
): TaskFragmentInteractor {

    override fun update(item: Task): Single<Int> =
        taskRepository.update(item)

    override fun delete(item: Task): Single<Int> =
        taskRepository.delete(item)

    override fun getTaskById(id: Long): Single<Task> =
        taskRepository.getById(id)
}