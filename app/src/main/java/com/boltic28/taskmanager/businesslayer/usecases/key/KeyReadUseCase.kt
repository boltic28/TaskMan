package com.boltic28.taskmanager.businesslayer.usecases.key

import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemReadUseCase
import com.boltic28.taskmanager.datalayer.entities.KeyResult
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyRepository
import io.reactivex.Single

class KeyReadUseCase(
    private val repository: KeyRepository
) : ItemReadUseCase<KeyResult> {

    override fun getItemById(id: Long): Single<KeyResult> =
        repository.getById(id)
}