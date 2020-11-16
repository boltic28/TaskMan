package com.boltic28.taskmanager.businesslayer.usecases.goal

import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemDeleteUseCase
import com.boltic28.taskmanager.datalayer.entities.Goal
import com.boltic28.taskmanager.datalayer.room.goal.GoalRepository
import io.reactivex.Single

class GoalDeleteUseCase(
    private val repository: GoalRepository
): ItemDeleteUseCase<Goal> {

    override fun delete(item: Goal): Single<Int> =
        repository.delete(item)
}