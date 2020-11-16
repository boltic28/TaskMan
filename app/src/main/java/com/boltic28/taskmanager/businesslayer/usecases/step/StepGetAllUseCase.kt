package com.boltic28.taskmanager.businesslayer.usecases.step

import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemGetAllUseCase
import com.boltic28.taskmanager.datalayer.entities.Step
import com.boltic28.taskmanager.datalayer.room.step.StepRepository
import io.reactivex.Single

class StepGetAllUseCase(
    private val repository: StepRepository
) : ItemGetAllUseCase<Step> {

    override fun getAll(): Single<List<Step>> =
        repository.getAll()
}