package com.boltic28.taskmanager.businesslayer

import com.boltic28.taskmanager.datalayer.entities.*
import io.reactivex.Single

interface ItemsProvider {

    fun getFreeTasks(): Single<List<Task>>

    fun getTasks(): Single<List<Task>>

    fun getFreeIdeas(): Single<List<Idea>>

    fun getIdeas(): Single<List<Idea>>

    fun getFreeKeys(): Single<List<KeyResult>>

    fun getKeys(): Single<List<KeyResult>>

    fun getFreeSteps(): Single<List<Step>>

    fun getSteps(): Single<List<Step>>

    fun getGoals(): Single<List<Goal>>

    fun getFreeParents(): Single<List<ParentItem>>

    fun getGoalById(id: Long): Single<Goal>

    fun getStepById(id: Long): Single<Step>

    fun getTaskById(id: Long): Single<Task>

    fun getIdeaById(id: Long): Single<Idea>

    fun getKeyById(id: Long): Single<KeyResult>
}