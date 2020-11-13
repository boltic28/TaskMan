package com.boltic28.taskmanager.businesslayer.fragments

import com.boltic28.taskmanager.datalayer.entities.Goal
import com.boltic28.taskmanager.datalayer.entities.Idea
import com.boltic28.taskmanager.datalayer.entities.KeyResult
import com.boltic28.taskmanager.datalayer.entities.Task
import io.reactivex.Single

interface KeyFragmentInteractor {

    fun update(item: KeyResult): Single<Int>

    fun delete(item: KeyResult): Single<Int>

    fun getKeyById(id: Long): Single<KeyResult>

    fun setChildrenFor(item: KeyResult): Single<KeyResult>

    fun getParentName(item: KeyResult): Single<String>

    fun getGoals(): Single<List<Goal>>

    fun getFreeIdeas(): Single<List<Idea>>

    fun getFreeTasks(): Single<List<Task>>

    fun updateTask(item: Task): Single<Int>

    fun updateIdea(item: Idea): Single<Int>

    fun setProgressFor(item: KeyResult): KeyResult
}