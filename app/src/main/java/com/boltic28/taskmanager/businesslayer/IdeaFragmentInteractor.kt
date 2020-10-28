package com.boltic28.taskmanager.businesslayer

import com.boltic28.taskmanager.datalayer.entities.Goal
import com.boltic28.taskmanager.datalayer.entities.Idea
import com.boltic28.taskmanager.datalayer.entities.Step
import com.boltic28.taskmanager.datalayer.entities.Task
import io.reactivex.Single

interface IdeaFragmentInteractor {

    fun update(item: Idea): Single<Int>

    fun delete(item: Idea): Single<Int>

    fun getIdeaById(id: Long): Single<Idea>

    fun getAllElements(): Single<List<Any>>

    fun create(item: Task): Single<Long>

    fun create(item: Step): Single<Long>

    fun create(item: Goal): Single<Long>
}