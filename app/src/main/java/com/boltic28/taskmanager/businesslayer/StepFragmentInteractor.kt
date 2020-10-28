package com.boltic28.taskmanager.businesslayer

import com.boltic28.taskmanager.datalayer.entities.Goal
import com.boltic28.taskmanager.datalayer.entities.Idea
import com.boltic28.taskmanager.datalayer.entities.Step
import com.boltic28.taskmanager.datalayer.entities.Task
import io.reactivex.Single

interface StepFragmentInteractor {

    fun insert(item: Step): Single<Long>

    fun update(item: Step): Single<Int>

    fun delete(item: Step): Single<Int>

    fun getStepById(id: Long): Single<Step>

    fun setChildrenFor(item: Step): Single<Step>

    fun getGoalById(id: Long): Single<Goal>

    fun getGoals(): Single<List<Goal>>

    fun getFreeIdeas(): Single<List<Idea>>

    fun getFreeTasks(): Single<List<Task>>

    fun updateTask(item: Task): Single<Int>

    fun updateIdea(item: Idea): Single<Int>

    fun setProgressFor(step: Step): Step
}