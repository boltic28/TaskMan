package com.boltic28.taskmanager.businesslayer.usecases

import com.boltic28.taskmanager.datalayer.entities.*
import io.reactivex.Single

interface GetAllItemsUseCase{

    fun getGoals(): Single<List<Goal>>

    fun getSteps(): Single<List<Step>>

    fun getTasks(): Single<List<Task>>

    fun getIdeas(): Single<List<Idea>>

    fun getKeys(): Single<List<KeyResult>>
}