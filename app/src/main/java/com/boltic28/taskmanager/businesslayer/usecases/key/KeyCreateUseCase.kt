package com.boltic28.taskmanager.businesslayer.usecases.key

import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemCreateUseCase
import com.boltic28.taskmanager.datalayer.entities.KeyResult
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyRepository
import io.reactivex.Single

class KeyCreateUseCase (
    private val repository: KeyRepository
): ItemCreateUseCase<KeyResult> {

    override fun create(item: KeyResult): Single<Long> =
        repository.insert(item)
}