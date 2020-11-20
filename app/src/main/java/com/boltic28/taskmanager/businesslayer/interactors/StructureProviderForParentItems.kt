package com.boltic28.taskmanager.businesslayer.interactors

import com.boltic28.taskmanager.datalayer.entities.*
import io.reactivex.Single

interface StructureProviderForParentItems {

    fun setChildrenFor(goal: Goal): Single<Goal>

    fun setChildrenFor(step: Step): Single<Step>

    fun setChildrenFor(key: KeyResult): Single<KeyResult>

    fun setProgressFor(goal: Goal): Goal

    fun setProgressFor(step: Step): Step

    fun setProgressFor(key: KeyResult): KeyResult
}