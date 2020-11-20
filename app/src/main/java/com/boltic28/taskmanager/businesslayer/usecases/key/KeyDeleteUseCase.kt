package com.boltic28.taskmanager.businesslayer.usecases.key

import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemDeleteUseCase
import com.boltic28.taskmanager.datalayer.entities.KeyResult
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyRepository
import io.reactivex.Single

class KeyDeleteUseCase(
    private val repository: KeyRepository
): ItemDeleteUseCase<KeyResult> {

    override fun delete(item: KeyResult): Single<Int> =
        repository.delete(item)
}