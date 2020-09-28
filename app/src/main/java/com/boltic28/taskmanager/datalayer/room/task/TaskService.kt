package com.boltic28.taskmanager.datalayer.room.task

import com.boltic28.taskmanager.datalayer.dto.Task
import com.boltic28.taskmanager.datalayer.room.DBService
import io.reactivex.Single

class TaskService(private val dao: TaskDao) : DBService<Task, TaskEntity> {
    override fun create(item: Task): Single<Long> =
        dao.insert(getEntity(item))

    override fun readAll(): Single<List<Task>> =
        dao.readAll().map { list ->
            list.map { entity ->
                getItem(entity)
            }
        }

    override fun readById(id: Long): Single<Task> =
        dao.readById(id).map { entity ->
            getItem(entity)
        }

    override fun update(item: Task): Single<Int> =
        dao.update(getEntity(item))

    override fun delete(item: Task): Single<Int> =
        dao.delete(getEntity(item))

    override fun getEntity(item: Task): TaskEntity =
        TaskEntity(
            item.id,
            item.stepId,
            item.keyId,
            item.goalId,
            item.name,
            item.description,
            item.icon,
            item.date,
            item.dateClose,
            item.cycle,
            item.isDone,
            item.isStarted
        )

    override fun getItem(entity: TaskEntity): Task =
        Task(
            entity.id,
            entity.stepId,
            entity.keyId,
            entity.goalId,
            entity.name,
            entity.description,
            entity.icon,
            entity.date,
            entity.dateClose,
            entity.cycle,
            entity.isDone,
            entity.isStarted
        )
}