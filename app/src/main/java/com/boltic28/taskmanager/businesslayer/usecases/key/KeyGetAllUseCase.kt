package com.boltic28.taskmanager.businesslayer.usecases.key

import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemGetAllUseCase
import com.boltic28.taskmanager.datalayer.entities.KeyResult
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyRepository
import io.reactivex.Single

class KeyGetAllUseCase(
    private val repository: KeyRepository
) : ItemGetAllUseCase<KeyResult> {

    override fun getAll(): Single<List<KeyResult>> =
        repository.getAll()
}