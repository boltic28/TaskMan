package com.boltic28.taskmanager.datalayer.room.step

import com.boltic28.taskmanager.datalayer.classes.Step
import io.reactivex.Single

interface StepService {
    fun insert(item: Step): Single<Long>

    fun update(item: Step): Single<Int>

    fun delete(item: Step): Single<Int>

    fun readById(id: Long): Single<Step>

    fun readAll(): Single<List<Step>>

    fun readAllForGoal(goalId: Long): Single<List<Step>>

    fun readAllForKey(keyId: Long): Single<List<Step>>
}