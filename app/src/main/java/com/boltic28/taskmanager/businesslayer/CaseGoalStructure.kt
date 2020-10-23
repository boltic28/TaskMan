package com.boltic28.taskmanager.businesslayer

import com.boltic28.taskmanager.datalayer.entities.Goal
import io.reactivex.Single

interface CaseGoalStructure {

    fun setChildrenFor(goal: Goal): Single<Goal>

    fun setProgressFor(goal: Goal): Goal
}