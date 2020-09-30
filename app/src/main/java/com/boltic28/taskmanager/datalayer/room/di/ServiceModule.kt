package com.boltic28.taskmanager.datalayer.room.di

import com.boltic28.taskmanager.datalayer.room.AppDataBase
import com.boltic28.taskmanager.datalayer.room.goal.DefaultGoalService
import com.boltic28.taskmanager.datalayer.room.goal.GoalService
import com.boltic28.taskmanager.datalayer.room.idea.DefaultIdeaService
import com.boltic28.taskmanager.datalayer.room.idea.IdeaService
import com.boltic28.taskmanager.datalayer.room.keyresult.DefaultKeyService
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyService
import com.boltic28.taskmanager.datalayer.room.step.DefaultStepService
import com.boltic28.taskmanager.datalayer.room.step.StepService
import com.boltic28.taskmanager.datalayer.room.task.DefaultTaskService
import com.boltic28.taskmanager.datalayer.room.task.TaskService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ServiceModule(private val db: AppDataBase) {

    @Singleton
    @Provides
    fun provideGoalService(): GoalService = DefaultGoalService(db.goalDao())

    @Singleton
    @Provides
    fun provideKeyService(): KeyService = DefaultKeyService(db.keyDao())

    @Singleton
    @Provides
    fun provideStepService(): StepService = DefaultStepService(db.stepDao())

    @Singleton
    @Provides
    fun provideTaskService(): TaskService = DefaultTaskService(db.taskDao())

    @Singleton
    @Provides
    fun provideIdeaService(): IdeaService = DefaultIdeaService(db.ideaDao())
}