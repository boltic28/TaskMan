package com.boltic28.taskmanager.businesslayer.usecases.interfaces

import io.reactivex.Single

interface ItemUpdateUseCase<T> {

    fun update(item: T): Single<Int>
}