package com.boltic28.taskmanager.datalayer.room

import io.reactivex.Single

interface ChildItem<T>: Crud<T> {

    fun getAllFree(): Single<List<T>>
}