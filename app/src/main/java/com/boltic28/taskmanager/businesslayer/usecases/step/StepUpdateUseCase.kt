package com.boltic28.taskmanager.businesslayer.usecases.step

import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemUpdateUseCase
import com.boltic28.taskmanager.datalayer.entities.Step
import com.boltic28.taskmanager.datalayer.room.step.StepRepository
import io.reactivex.Single

class StepUpdateUseCase(
    private val repository: StepRepository
): ItemUpdateUseCase<Step> {

    override fun update(item: Step): Single<Int> =
        repository.update(item)
}