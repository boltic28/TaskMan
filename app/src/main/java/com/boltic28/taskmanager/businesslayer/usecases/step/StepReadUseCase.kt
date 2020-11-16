package com.boltic28.taskmanager.businesslayer.usecases.step

import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemReadUseCase
import com.boltic28.taskmanager.datalayer.entities.Step
import com.boltic28.taskmanager.datalayer.room.step.StepRepository
import io.reactivex.Single

class StepReadUseCase(
    private val repository: StepRepository
) : ItemReadUseCase<Step> {

    override fun getItemById(id: Long): Single<Step> =
        repository.getById(id)
}