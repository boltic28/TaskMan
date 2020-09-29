package com.boltic28.taskmanager.datalayer.room.task

import com.boltic28.taskmanager.datalayer.classes.Task
import io.reactivex.Single

class DefaultTaskService(private val dao: TaskDao) : TaskService {
    override fun insert(item: Task): Single<Long> =
        dao.insert(item.toEntity())

    override fun readAll(): Single<List<Task>> =
        dao.getAll().map { list ->
            list.map { it.toTask() }
        }

    override fun readById(id: Long): Single<Task> =
        dao.getById(id).map { it.toTask() }

    override fun update(item: Task): Single<Int> =
        dao.update(item.toEntity())

    override fun delete(item: Task): Single<Int> =
        dao.delete(item.toEntity())

    override fun readAllFree(): Single<List<Task>> =
        dao.getAllFree().map { list ->
            list.map { it.toTask() }
        }

    override fun readAllForStep(stepId: Long): Single<List<Task>> =
        dao.getAllForStep(stepId).map { list ->
            list.map { it.toTask() }
        }

    override fun readAllForGoal(goalId: Long): Single<List<Task>> =
        dao.getAllForGoal(goalId).map { list ->
            list.map { it.toTask() }
        }

    override fun readAllForKey(keyId: Long): Single<List<Task>> =
        dao.getAllForKey(keyId).map { list ->
            list.map { it.toTask() }
        }


}

private fun Task.toEntity(): TaskEntity =
    TaskEntity(
        id = this.id,
        stepId = this.stepId,
        keyId = this.keyId,
        goalId = this.goalId,
        name = this.name,
        description = this.description,
        icon = this.icon,
        date = this.date,
        dateClose = this.dateClose,
        cycle = this.cycle,
        isDone = this.isDone,
        isStarted = this.isStarted
    )

private fun TaskEntity.toTask(): Task =
    Task(
        id = this.id,
        stepId = this.stepId,
        keyId = this.keyId,
        goalId = this.goalId,
        name = this.name,
        description = this.description,
        icon = this.icon,
        date = this.date,
        dateClose = this.dateClose,
        cycle = this.cycle,
        isDone = this.isDone,
        isStarted = this.isStarted
    )