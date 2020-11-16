package com.boltic28.taskmanager.datalayer.room.step

import com.boltic28.taskmanager.datalayer.entities.Step
import com.boltic28.taskmanager.datalayer.room.ChildItem
import io.reactivex.Single

interface StepRepository: ChildItem<Step> {

    fun getAllForGoal(goalId: Long): Single<List<Step>>

    fun getAllForKey(keyId: Long): Single<List<Step>>
}