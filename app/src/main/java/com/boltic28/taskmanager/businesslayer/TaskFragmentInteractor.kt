package com.boltic28.taskmanager.businesslayer

import com.boltic28.taskmanager.datalayer.entities.ParentItem
import com.boltic28.taskmanager.datalayer.entities.Task
import io.reactivex.Single

interface TaskFragmentInteractor {

    fun update(item: Task): Single<Int>

    fun delete(item: Task): Single<Int>

    fun getTaskById(id: Long): Single<Task>

    fun getParentName(item: Task): Single<String>

    fun getFreeStepsGoalsKeys(): Single<List<ParentItem>>
}