package com.boltic28.taskmanager.businesslayer

import com.boltic28.taskmanager.datalayer.entities.*
import io.reactivex.Completable
import io.reactivex.Single

interface FreeElementsInteractor {

    fun getFreeTasks(): Single<List<Task>>

    fun getTasks(): Single<List<Task>>

    fun getFreeIdeas(): Single<List<Idea>>

    fun getIdeas(): Single<List<Idea>>

    fun getFreeKeys(): Single<List<KeyResult>>

    fun getKeys(): Single<List<KeyResult>>

    fun getFreeSteps(): Single<List<Step>>

    fun getSteps(): Single<List<Step>>

    fun getGoals(): Single<List<Goal>>

    fun setChildrenFor(goal: Goal): Single<Goal>

    fun setProgressFor(goal: Goal): Goal

    fun update(item: Task): Single<Int>
}