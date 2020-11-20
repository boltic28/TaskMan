package com.boltic28.taskmanager.businesslayer.usecases.interfaces

import io.reactivex.Single

interface ItemDeleteUseCase<T> {

    fun delete(item: T): Single<Int>
}