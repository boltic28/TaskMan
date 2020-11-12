package com.boltic28.taskmanager.datalayer.firebaseworker

import com.boltic28.taskmanager.datalayer.entities.*
import io.reactivex.Observable

interface RemoteDB {

    val goal: Observable<Goal>
    val step: Observable<Step>
    val task: Observable<Task>
    val idea: Observable<Idea>
    val key: Observable<KeyResult>

    fun write(item: Goal)
    fun write(item: Step)
    fun write(item: Task)
    fun write(item: Idea)
    fun write(item: KeyResult)

    fun observeGoals()
    fun observeSteps()
    fun observeTasks()
    fun observeIdeas()
    fun observeKeys()
}