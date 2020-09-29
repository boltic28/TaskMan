package com.boltic28.taskmanager.datalayer.room.idea

import com.boltic28.taskmanager.datalayer.classes.Idea
import io.reactivex.Single

interface IdeaService {
    fun insert(item: Idea): Single<Long>

    fun update(item: Idea): Single<Int>

    fun delete(item: Idea): Single<Int>

    fun readById(id: Long): Single<Idea>

    fun readAll(): Single<List<Idea>>

    fun readAllFree(): Single<List<Idea>>

    fun readAllForStep(stepId: Long): Single<List<Idea>>

    fun readAllForGoal(goalId: Long): Single<List<Idea>>

    fun readAllForKey(keyId: Long): Single<List<Idea>>
}