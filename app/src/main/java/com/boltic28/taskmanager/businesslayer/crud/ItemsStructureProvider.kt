package com.boltic28.taskmanager.businesslayer.crud

import com.boltic28.taskmanager.datalayer.entities.*
import io.reactivex.Single

interface ItemsStructureProvider {

    fun setChildrenFor(goal: Goal): Single<Goal>

    fun setChildrenFor(step: Step): Single<Step>

    fun setChildrenFor(key: KeyResult): Single<KeyResult>

    fun setProgressFor(goal: Goal): Goal

    fun setProgressFor(step: Step): Step

    fun setProgressFor(key: KeyResult): KeyResult

    fun getParentName(item: Step): Single<String>

    fun getParentName(item: KeyResult): Single<String>

    fun getParentName(item: Task): Single<String>

    fun getParentName(item: Idea): Single<String>
}