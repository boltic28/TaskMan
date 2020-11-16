package com.boltic28.taskmanager.businesslayer.usecases.key

import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemGetAllFreeUseCase
import com.boltic28.taskmanager.datalayer.entities.KeyResult
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyRepository
import io.reactivex.Single

class KeyGetAllFreeUseCase(
    private val repository: KeyRepository
) : ItemGetAllFreeUseCase<KeyResult> {

    override fun getAllFree(): Single<List<KeyResult>> =
        repository.getAllFree()
}