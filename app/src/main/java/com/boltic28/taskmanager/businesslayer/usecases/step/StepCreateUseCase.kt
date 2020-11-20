package com.boltic28.taskmanager.businesslayer.usecases.step

import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemCreateUseCase
import com.boltic28.taskmanager.datalayer.entities.Step
import com.boltic28.taskmanager.datalayer.room.step.StepRepository
import io.reactivex.Single

class StepCreateUseCase(
    private val repository: StepRepository
) : ItemCreateUseCase<Step> {

    override fun create(item: Step): Single<Long> =
        repository.insert(item)
}