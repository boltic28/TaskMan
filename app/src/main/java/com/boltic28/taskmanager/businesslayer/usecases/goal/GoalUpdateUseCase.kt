package com.boltic28.taskmanager.businesslayer.usecases.goal

import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemUpdateUseCase
import com.boltic28.taskmanager.datalayer.entities.Goal
import com.boltic28.taskmanager.datalayer.room.goal.GoalRepository
import io.reactivex.Single

class GoalUpdateUseCase(
    private val repository: GoalRepository
): ItemUpdateUseCase<Goal> {

    override fun update(item: Goal): Single<Int> =
        repository.update(item)
}