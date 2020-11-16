package com.boltic28.taskmanager.datalayer.room.idea

import com.boltic28.taskmanager.datalayer.entities.Idea
import com.boltic28.taskmanager.datalayer.room.ChildItem
import io.reactivex.Single

interface IdeaRepository: ChildItem<Idea> {

    fun getAllForStep(stepId: Long): Single<List<Idea>>

    fun getAllForGoal(goalId: Long): Single<List<Idea>>

    fun getAllForKey(keyId: Long): Single<List<Idea>>
}