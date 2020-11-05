package com.boltic28.taskmanager.businesslayer

import com.boltic28.taskmanager.datalayer.entities.*
import io.reactivex.Single

interface CreatorInteractor {

    fun create(item: Goal): Single<Long>

    fun create(item: Task): Single<Long>

    fun create(item: Step): Single<Long>

    fun create(item: Idea): Single<Long>

    fun create(item: KeyResult): Single<Long>
}