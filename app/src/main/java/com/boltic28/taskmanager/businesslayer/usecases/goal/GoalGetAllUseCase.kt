package com.boltic28.taskmanager.businesslayer.usecases.goal

import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemGetAllUseCase
import com.boltic28.taskmanager.datalayer.entities.Goal
import com.boltic28.taskmanager.datalayer.room.goal.GoalRepository
import io.reactivex.Single

class GoalGetAllUseCase(
    private val repository: GoalRepository
): ItemGetAllUseCase<Goal> {

    override fun getAll(): Single<List<Goal>> =
        repository.getAll()
}