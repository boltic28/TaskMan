package com.boltic28.taskmanager.datalayer.room

import io.reactivex.Single

interface DBService<T, K> {
    fun create(item: T): Single<Long>
    fun readAll(): Single<List<T>>
    fun readById(id: Long): Single<T>
    fun update(item: T): Single<Int>
    fun delete(item: T): Single<Int>
    fun getEntity(item: T): K
    fun getItem(entity: K): T
}