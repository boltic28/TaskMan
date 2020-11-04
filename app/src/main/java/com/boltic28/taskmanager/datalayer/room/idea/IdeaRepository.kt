package com.boltic28.taskmanager.datalayer.room.idea

import com.boltic28.taskmanager.datalayer.entities.Idea
import io.reactivex.Single

interface IdeaRepository {
    fun insert(item: Idea): Single<Long>

    fun update(item: Idea): Single<Int>

    fun delete(item: Idea): Single<Int>

    fun getById(id: Long): Single<Idea>

    fun getAll(): Single<List<Idea>>

    fun getAllFree(): Single<List<Idea>>

    fun getAllForStep(stepId: Long): Single<List<Idea>>

    fun getAllForGoal(goalId: Long): Single<List<Idea>>

    fun getAllForKey(keyId: Long): Single<List<Idea>>
}