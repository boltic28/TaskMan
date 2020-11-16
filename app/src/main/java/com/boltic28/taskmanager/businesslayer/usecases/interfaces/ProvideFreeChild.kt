package com.boltic28.taskmanager.businesslayer.usecases.interfaces

import io.reactivex.Single

interface ProvideFreeChild<T> {

    fun getFreeChildItem(): Single<List<T>>
}