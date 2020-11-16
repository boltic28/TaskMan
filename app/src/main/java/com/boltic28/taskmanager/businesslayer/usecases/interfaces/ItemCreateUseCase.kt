package com.boltic28.taskmanager.businesslayer.usecases.interfaces

import io.reactivex.Single

interface ItemCreateUseCase<T> {

    fun create(item: T): Single<Long>
}