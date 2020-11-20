package com.boltic28.taskmanager.businesslayer.usecases.interfaces

import io.reactivex.Single

interface ItemGetAllUseCase<T> {

    fun getAll(): Single<List<T>>
}