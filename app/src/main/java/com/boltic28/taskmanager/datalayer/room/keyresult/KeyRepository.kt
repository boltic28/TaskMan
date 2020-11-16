package com.boltic28.taskmanager.datalayer.room.keyresult

import com.boltic28.taskmanager.datalayer.entities.KeyResult
import com.boltic28.taskmanager.datalayer.room.ChildItem
import io.reactivex.Single

interface KeyRepository: ChildItem<KeyResult> {

    fun getAllForGoal(goalId: Long): Single<List<KeyResult>>
}