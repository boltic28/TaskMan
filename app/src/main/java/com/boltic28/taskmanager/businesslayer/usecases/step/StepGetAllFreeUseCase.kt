package com.boltic28.taskmanager.businesslayer.usecases.step

import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemGetAllFreeUseCase
import com.boltic28.taskmanager.datalayer.entities.Step
import com.boltic28.taskmanager.datalayer.room.step.StepRepository
import io.reactivex.Single

class StepGetAllFreeUseCase(
    private val repository: StepRepository
) : ItemGetAllFreeUseCase<Step> {

    override fun getAllFree(): Single<List<Step>> =
        repository.getAllFree()
}