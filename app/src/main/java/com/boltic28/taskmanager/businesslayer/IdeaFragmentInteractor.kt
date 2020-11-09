package com.boltic28.taskmanager.businesslayer

import com.boltic28.taskmanager.datalayer.entities.*
import io.reactivex.Single

interface IdeaFragmentInteractor {

    fun update(item: Idea): Single<Int>

    fun delete(item: Idea): Single<Int>

    fun getIdeaById(id: Long): Single<Idea>

    fun getStepsGoalsKeys(): Single<List<Any>>

    fun create(item: Task): Single<Long>

    fun create(item: Step): Single<Long>

    fun create(item: Goal): Single<Long>

    fun create(item: KeyResult): Single<Long>

    fun getParentName(item: Idea): Single<String>
}