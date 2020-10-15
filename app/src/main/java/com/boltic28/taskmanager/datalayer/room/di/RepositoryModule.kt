package com.boltic28.taskmanager.datalayer.room.di

import com.boltic28.taskmanager.datalayer.room.AppDataBase
import com.boltic28.taskmanager.datalayer.room.goal.DefaultGoalRepository
import com.boltic28.taskmanager.datalayer.room.goal.GoalRepository
import com.boltic28.taskmanager.datalayer.room.idea.DefaultIdeaRepository
import com.boltic28.taskmanager.datalayer.room.idea.IdeaRepository
import com.boltic28.taskmanager.datalayer.room.keyresult.DefaultKeyRepository
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyRepository
import com.boltic28.taskmanager.datalayer.room.step.DefaultStepRepository
import com.boltic28.taskmanager.datalayer.room.step.StepRepository
import com.boltic28.taskmanager.datalayer.room.task.DefaultTaskRepository
import com.boltic28.taskmanager.datalayer.room.task.TaskRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule(private val db: AppDataBase) {

    @Singleton
    @Provides
    fun provideGoalService(): GoalRepository = DefaultGoalRepository(db.goalDao())

    @Singleton
    @Provides
    fun provideKeyService(): KeyRepository = DefaultKeyRepository(db.keyDao())

    @Singleton
    @Provides
    fun provideStepService(): StepRepository = DefaultStepRepository(db.stepDao())

    @Singleton
    @Provides
    fun provideTaskService(): TaskRepository = DefaultTaskRepository(db.taskDao())

    @Singleton
    @Provides
    fun provideIdeaService(): IdeaRepository = DefaultIdeaRepository(db.ideaDao())
}