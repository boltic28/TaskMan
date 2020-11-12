package com.boltic28.taskmanager.businesslayer

import com.boltic28.taskmanager.datalayer.entities.*
import io.reactivex.Single

interface ItemsDeleter {

    fun delete(item: Goal): Single<Int>

    fun delete(item: Step): Single<Int>

    fun delete(item: Task): Single<Int>

    fun delete(item: Idea): Single<Int>

    fun delete(item: KeyResult): Single<Int>
}