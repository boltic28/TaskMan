package com.boltic28.taskmanager.businesslayer

import com.boltic28.taskmanager.datalayer.entities.KeyResult
import io.reactivex.Single

interface KeyInteractor {

    fun insert(item: KeyResult): Single<Long>

    fun update(item: KeyResult): Single<Int>

    fun delete(item: KeyResult): Single<Int>

    fun getById(id: Long): Single<KeyResult>

    fun getAll(): Single<List<KeyResult>>

    fun getChildrenFor(item: KeyResult): Single<KeyResult>
}