package com.boltic28.taskmanager.businesslayer.usecases.step

import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemDeleteUseCase
import com.boltic28.taskmanager.datalayer.entities.Step
import com.boltic28.taskmanager.datalayer.room.step.StepRepository
import io.reactivex.Single

class StepDeleteUseCase(
    private val repository: StepRepository
) : ItemDeleteUseCase<Step> {

    override fun delete(item: Step): Single<Int> =
        repository.delete(item)
}