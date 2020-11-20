package com.boltic28.taskmanager.businesslayer.usecases.interfaces

import io.reactivex.Single

interface ItemGetAllFreeUseCase<T> {

    fun getAllFree(): Single<List<T>>
}