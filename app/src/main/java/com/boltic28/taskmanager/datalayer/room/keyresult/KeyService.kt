package com.boltic28.taskmanager.datalayer.room.keyresult

import com.boltic28.taskmanager.datalayer.classes.Goal
import com.boltic28.taskmanager.datalayer.classes.KeyResult
import io.reactivex.Single

interface KeyService {
    fun insert(item: KeyResult): Single<Long>

    fun update(item: KeyResult): Single<Int>

    fun delete(item: KeyResult): Single<Int>

    fun getById(id: Long): Single<KeyResult>

    fun getAll(): Single<List<KeyResult>>

    fun getAllForGoal(goalId: Long): Single<List<KeyResult>>

    fun getChildrenFor(item: KeyResult): Single<KeyResult>
}