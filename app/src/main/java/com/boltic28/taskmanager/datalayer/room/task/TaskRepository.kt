package com.boltic28.taskmanager.datalayer.room.task

import com.boltic28.taskmanager.datalayer.entities.Task
import com.boltic28.taskmanager.datalayer.room.ChildItem
import io.reactivex.Single

interface TaskRepository: ChildItem<Task> {

    fun getAllForStep(stepId: Long): Single<List<Task>>

    fun getAllForGoal(goalId: Long): Single<List<Task>>

    fun getAllForKey(keyId: Long): Single<List<Task>>
}