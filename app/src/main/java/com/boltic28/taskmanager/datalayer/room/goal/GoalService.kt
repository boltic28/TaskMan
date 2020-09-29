package com.boltic28.taskmanager.datalayer.room.goal

import com.boltic28.taskmanager.datalayer.classes.Goal
import io.reactivex.Single

interface GoalService {
    fun insert(item: Goal): Single<Long>

    fun update(item: Goal): Single<Int>

    fun delete(item: Goal): Single<Int>

    fun readById(id: Long): Single<Goal>

    fun readAll(): Single<List<Goal>>
}