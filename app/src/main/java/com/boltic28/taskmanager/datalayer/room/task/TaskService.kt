package com.boltic28.taskmanager.datalayer.room.task

import com.boltic28.taskmanager.datalayer.classes.Task
import io.reactivex.Single

interface TaskService {
    fun insert(item: Task): Single<Long>

    fun update(item: Task): Single<Int>

    fun delete(item: Task): Single<Int>

    fun readById(id: Long): Single<Task>

    fun readAll(): Single<List<Task>>

    fun readAllFree(): Single<List<Task>>

    fun readAllForStep(stepId: Long): Single<List<Task>>

    fun readAllForGoal(goalId: Long): Single<List<Task>>

    fun readAllForKey(keyId: Long): Single<List<Task>>
}