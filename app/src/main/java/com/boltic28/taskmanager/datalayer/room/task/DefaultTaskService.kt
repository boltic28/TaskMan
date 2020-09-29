package com.boltic28.taskmanager.datalayer.room.task

import com.boltic28.taskmanager.datalayer.classes.Task
import io.reactivex.Single

class DefaultTaskService(private val dao: TaskDao) : TaskService {
    override fun insert(item: Task): Single<Long> =
        dao.insert(item.toEntity())

    override fun getAll(): Single<List<Task>> =
        dao.getAll().map { list ->
            list.map { it.toTask() }
        }

    override fun getById(id: Long): Single<Task> =
        dao.getById(id).map { it.toTask() }

    override fun update(item: Task): Single<Int> =
        dao.update(item.toEntity())

    override fun delete(item: Task): Single<Int> =
        dao.delete(item.toEntity())

    override fun getAllFree(): Single<List<Task>> =
        dao.getAllFree().map { list ->
            list.map { it.toTask() }
        }

    override fun getAllForStep(stepId: Long): Single<List<Task>> =
        dao.getAllForStep(stepId).map { list ->
            list.map { it.toTask() }
        }

    override fun getAllForGoal(goalId: Long): Single<List<Task>> =
        dao.getAllForGoal(goalId).map { list ->
            list.map { it.toTask() }
        }

    override fun getAllForKey(keyId: Long): Single<List<Task>> =
        dao.getAllForKey(keyId).map { list ->
            list.map { it.toTask() }
        }
}