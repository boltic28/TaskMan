package com.boltic28.taskmanager.datalayer.room.task

import com.boltic28.taskmanager.datalayer.dto.Task
import com.boltic28.taskmanager.datalayer.room.DBService
import io.reactivex.Single

class TaskService: DBService<Task> {
    override fun create(item: Task): Single<Long> {
        TODO("Not yet implemented")
    }

    override fun readAll(): Single<List<Task>> {
        TODO("Not yet implemented")
    }

    override fun readById(id: Long): Single<Task> {
        TODO("Not yet implemented")
    }

    override fun update(item: Task): Single<Int> {
        TODO("Not yet implemented")
    }

    override fun delete(item: Task): Single<Int> {
        TODO("Not yet implemented")
    }
}