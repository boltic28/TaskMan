package com.boltic28.taskmanager.businesslayer.usecases.interfaces

import io.reactivex.Single

interface ProvideParentItems<T> {

    fun getItemParent(): Single<List<T>>
}