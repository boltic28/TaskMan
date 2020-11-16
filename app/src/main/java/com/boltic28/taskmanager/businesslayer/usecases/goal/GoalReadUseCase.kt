package com.boltic28.taskmanager.businesslayer.usecases.goal

import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemReadUseCase
import com.boltic28.taskmanager.datalayer.entities.Goal
import com.boltic28.taskmanager.datalayer.room.goal.GoalRepository
import io.reactivex.Single

class GoalReadUseCase(
    private val repository: GoalRepository
) : ItemReadUseCase<Goal> {

    override fun getItemById(id: Long): Single<Goal> =
        repository.getById(id)
}