package com.boltic28.taskmanager.datalayer.room

import io.reactivex.Single

interface Crud<T> {

    fun insert(item: T): Single<Long>

    fun update(item: T): Single<Int>

    fun delete(item: T): Single<Int>

    fun deleteAll(): Single<Int>

    fun getById(id: Long): Single<T>

    fun getAll(): Single<List<T>>

    fun refreshData(item: T): Single<Long>
}