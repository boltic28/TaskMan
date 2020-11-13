package com.boltic28.taskmanager.businesslayer.fragments

import com.boltic28.taskmanager.datalayer.entities.*
import io.reactivex.Single

interface GoalFragmentInteractor {

    fun getGoal(id: Long): Single<Goal>

    fun update(item: Goal): Single<Int>

    fun delete(item: Goal): Single<Int>

    fun setChildrenFor(goal: Goal): Single<Goal>

    fun setProgressFor(goal: Goal): Goal

    fun updateTask(item: Task): Single<Int>

    fun updateStep(item: Step): Single<Int>

    fun updateIdea(item: Idea): Single<Int>

    fun updateKey(item: KeyResult): Single<Int>
}