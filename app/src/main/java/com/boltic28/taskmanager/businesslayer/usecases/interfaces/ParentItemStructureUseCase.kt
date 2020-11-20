package com.boltic28.taskmanager.businesslayer.usecases.interfaces

import io.reactivex.Single

interface ParentItemStructureUseCase<T> {

    fun setChildrenFor(item: T): Single<T>

    fun setProgressFor(item: T): T

    fun getParentName(item: T): Single<String>
}