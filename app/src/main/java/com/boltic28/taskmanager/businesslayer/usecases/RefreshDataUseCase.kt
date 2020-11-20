package com.boltic28.taskmanager.businesslayer.usecases

import com.boltic28.taskmanager.datalayer.entities.*
import io.reactivex.Observable

interface RefreshDataUseCase {

    fun refreshAllData()

    fun refreshGoals(): Observable<Goal>

    fun refreshSteps(): Observable<Step>

    fun refreshTasks(): Observable<Task>

    fun refreshIdeas(): Observable<Idea>

    fun refreshKeys(): Observable<KeyResult>
}
