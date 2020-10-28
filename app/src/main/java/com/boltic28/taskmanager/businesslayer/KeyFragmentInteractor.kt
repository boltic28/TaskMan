package com.boltic28.taskmanager.businesslayer

import com.boltic28.taskmanager.datalayer.entities.*
import io.reactivex.Single

interface KeyFragmentInteractor {

    fun insert(item: KeyResult): Single<Long>

    fun update(item: KeyResult): Single<Int>

    fun delete(item: KeyResult): Single<Int>

    fun getKeyById(id: Long): Single<KeyResult>

    fun setChildrenFor(item: KeyResult): Single<KeyResult>

    fun getGoalById(id: Long): Single<Goal>

    fun getGoals(): Single<List<Goal>>

    fun getFreeIdeas(): Single<List<Idea>>

    fun getFreeTasks(): Single<List<Task>>

    fun updateTask(item: Task): Single<Int>

    fun updateIdea(item: Idea): Single<Int>

    fun setProgressFor(key: KeyResult): KeyResult
}