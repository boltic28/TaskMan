package com.boltic28.taskmanager.datalayer.room.task

import com.boltic28.taskmanager.datalayer.classes.Task
import io.reactivex.Single

interface TaskService {
    fun insert(item: Task): Single<Long>

    fun update(item: Task): Single<Int>

    fun delete(item: Task): Single<Int>

    fun getById(id: Long): Single<Task>

    fun getAll(): Single<List<Task>>

    fun getAllFree(): Single<List<Task>>

    fun getAllForStep(stepId: Long): Single<List<Task>>

    fun getAllForGoal(goalId: Long): Single<List<Task>>

    fun getAllForKey(keyId: Long): Single<List<Task>>
}