package com.boltic28.taskmanager.businesslayer

import com.boltic28.taskmanager.datalayer.entities.Task
import io.reactivex.Single

interface TaskInteractor {

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