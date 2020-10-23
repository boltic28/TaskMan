package com.boltic28.taskmanager.datalayer.room.step

import com.boltic28.taskmanager.datalayer.entities.Step
import io.reactivex.Single

interface StepRepository {
    fun insert(item: Step): Single<Long>

    fun update(item: Step): Single<Int>

    fun delete(item: Step): Single<Int>

    fun getById(id: Long): Single<Step>

    fun getAll(): Single<List<Step>>

    fun getAllFree(): Single<List<Step>>

    fun getAllForGoal(goalId: Long): Single<List<Step>>

    fun getAllForKey(keyId: Long): Single<List<Step>>
}