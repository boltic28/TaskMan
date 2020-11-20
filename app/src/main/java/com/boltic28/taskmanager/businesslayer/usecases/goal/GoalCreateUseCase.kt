package com.boltic28.taskmanager.businesslayer.usecases.goal

import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemCreateUseCase
import com.boltic28.taskmanager.datalayer.entities.Goal
import com.boltic28.taskmanager.datalayer.room.goal.GoalRepository
import io.reactivex.Single

class GoalCreateUseCase(
    private val repository: GoalRepository
): ItemCreateUseCase<Goal> {

    override fun create(item: Goal): Single<Long> =
        repository.insert(item)
}