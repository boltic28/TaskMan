package com.boltic28.taskmanager.businesslayer.di

import com.boltic28.taskmanager.businesslayer.TaskInteractorImpl
import com.boltic28.taskmanager.businesslayer.*
import com.boltic28.taskmanager.datalayer.room.goal.GoalRepository
import com.boltic28.taskmanager.datalayer.room.idea.IdeaRepository
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyRepository
import com.boltic28.taskmanager.datalayer.room.step.StepRepository
import com.boltic28.taskmanager.datalayer.room.task.TaskRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class BusinessModule(
    private val keyRepository: KeyRepository,
    private val stepRepository: StepRepository,
    private val taskRepository: TaskRepository,
    private val ideaRepository: IdeaRepository,
    private var goalRepository: GoalRepository
) {

    @Singleton
    @Provides
    fun provideGoalInteractor(): GoalInteractor =
        GoalInteractorImpl(
            keyRepository,
            stepRepository,
            taskRepository,
            ideaRepository,
            goalRepository
        )

    @Singleton
    @Provides
    fun provideStepInteractor(): StepInteractor =
        StepInteractorImpl(
            taskRepository,
            ideaRepository,
            stepRepository
        )

    @Singleton
    @Provides
    fun provideKeyInteractor(): KeyInteractor =
        KeyInteractorImpl(
            keyRepository,
            stepRepository,
            taskRepository,
            ideaRepository
        )

    @Singleton
    @Provides
    fun provideTaskInteractor(): TaskInteractor =
        TaskInteractorImpl(
            taskRepository
        )

    @Singleton
    @Provides
    fun provideIdeaInteractor(): IdeaInteractor =
        IdeaInteractorImpl(
            ideaRepository
        )
}