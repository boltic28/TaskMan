package com.boltic28.taskmanager.businesslayer.usecases

import com.boltic28.taskmanager.datalayer.entities.Idea
import com.boltic28.taskmanager.datalayer.entities.KeyResult
import com.boltic28.taskmanager.datalayer.entities.Step
import com.boltic28.taskmanager.datalayer.entities.Task
import io.reactivex.Single

interface GetFreeItemsUseCase {

    fun getFreeTasks(): Single<List<Task>>

    fun getFreeSteps(): Single<List<Step>>

    fun getFreeIdeas(): Single<List<Idea>>

    fun getFreeKeys(): Single<List<KeyResult>>
}