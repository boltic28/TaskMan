package com.boltic28.taskmanager.datalayer.room.di

import com.boltic28.taskmanager.datalayer.room.AppDataBase
import com.boltic28.taskmanager.datalayer.room.goal.GoalDao
import com.boltic28.taskmanager.datalayer.room.goal.GoalService
import com.boltic28.taskmanager.datalayer.room.idea.IdeaDao
import com.boltic28.taskmanager.datalayer.room.idea.IdeaService
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyDao
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyService
import com.boltic28.taskmanager.datalayer.room.step.StepDao
import com.boltic28.taskmanager.datalayer.room.step.StepService
import com.boltic28.taskmanager.datalayer.room.task.TaskDao
import com.boltic28.taskmanager.datalayer.room.task.TaskService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ServiceModule(private val db: AppDataBase) {

    @Singleton
    @Provides
    fun provideGoalService(): GoalService = GoalService(db.goalDao())

    @Singleton
    @Provides
    fun provideKeyService(): KeyService = KeyService(db.keyDao())

    @Singleton
    @Provides
    fun provideStepService(): StepService = StepService(db.stepDao())

    @Singleton
    @Provides
    fun provideTaskService(): TaskService = TaskService(db.taskDao())

    @Singleton
    @Provides
    fun provideIdeaService(): IdeaService = IdeaService(db.ideaDao())


// for delete
    @Singleton
    @Provides
    fun provideGoalDao(): GoalDao = db.goalDao()

    @Singleton
    @Provides
    fun provideKeyDao(): KeyDao = db.keyDao()

    @Singleton
    @Provides
    fun provideStepDao(): StepDao = db.stepDao()

    @Singleton
    @Provides
    fun provideTaskDao(): TaskDao = db.taskDao()

    @Singleton
    @Provides
    fun provideIdeaDao(): IdeaDao = db.ideaDao()

}