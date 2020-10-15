package com.boltic28.taskmanager.businesslayer

import com.boltic28.taskmanager.datalayer.entities.Task
import com.boltic28.taskmanager.datalayer.room.task.TaskRepository
import io.reactivex.Single

class TaskInteractorImpl(
    private val taskRepository: TaskRepository
): TaskInteractor {
    override fun insert(item: Task): Single<Long> {
        TODO("Not yet implemented")
    }

    override fun update(item: Task): Single<Int> {
        TODO("Not yet implemented")
    }

    override fun delete(item: Task): Single<Int> {
        TODO("Not yet implemented")
    }

    override fun getById(id: Long): Single<Task> {
        TODO("Not yet implemented")
    }

    override fun getAll(): Single<List<Task>> {
        TODO("Not yet implemented")
    }

    override fun getAllFree(): Single<List<Task>> {
        TODO("Not yet implemented")
    }

    override fun getAllForStep(stepId: Long): Single<List<Task>> {
        TODO("Not yet implemented")
    }

    override fun getAllForGoal(goalId: Long): Single<List<Task>> {
        TODO("Not yet implemented")
    }

    override fun getAllForKey(keyId: Long): Single<List<Task>> {
        TODO("Not yet implemented")
    }
}