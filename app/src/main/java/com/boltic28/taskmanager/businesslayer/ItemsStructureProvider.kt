package com.boltic28.taskmanager.businesslayer

import com.boltic28.taskmanager.datalayer.entities.Goal
import com.boltic28.taskmanager.datalayer.entities.KeyResult
import com.boltic28.taskmanager.datalayer.entities.Step
import io.reactivex.Single

interface ItemsStructureProvider {

    fun setChildrenFor(goal: Goal): Single<Goal>

    fun setChildrenFor(step: Step): Single<Step>

    fun setChildrenFor(key: KeyResult): Single<KeyResult>

    fun setProgressFor(goal: Goal): Goal

    fun setProgressFor(step: Step): Step

    fun setProgressFor(key: KeyResult): KeyResult
}