package com.boltic28.taskmanager.businesslayer.usecases.interfaces

import io.reactivex.Single

interface ItemReadUseCase<T> {

    fun getItemById(id: Long): Single<T>
}