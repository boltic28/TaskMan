package com.boltic28.taskmanager.businesslayer.usecases

import com.boltic28.taskmanager.datalayer.entities.*
import io.reactivex.Single

interface ItemsUpdateUseCase {

    fun update(item: Goal): Single<Int>

    fun update(item: Task): Single<Int>

    fun update(item: Step): Single<Int>

    fun update(item: Idea): Single<Int>

    fun update(item: KeyResult): Single<Int>
}
