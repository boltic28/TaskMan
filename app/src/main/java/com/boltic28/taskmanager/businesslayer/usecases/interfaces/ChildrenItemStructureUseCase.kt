package com.boltic28.taskmanager.businesslayer.usecases.interfaces

import com.boltic28.taskmanager.datalayer.entities.ParentItem
import io.reactivex.Single

interface ChildrenItemStructureUseCase<T> {

    fun getParentName(item: T): Single<String>

    fun getParentItems(): Single<List<ParentItem>>
}