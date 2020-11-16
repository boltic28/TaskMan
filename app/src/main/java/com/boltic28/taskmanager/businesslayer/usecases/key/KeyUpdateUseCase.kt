package com.boltic28.taskmanager.businesslayer.usecases.key

import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemUpdateUseCase
import com.boltic28.taskmanager.datalayer.entities.KeyResult
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyRepository
import io.reactivex.Single

class KeyUpdateUseCase(
    private val repository: KeyRepository
): ItemUpdateUseCase<KeyResult> {

    override fun update(item: KeyResult): Single<Int> =
        repository.update(item)
}