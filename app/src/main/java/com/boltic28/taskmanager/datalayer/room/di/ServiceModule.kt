package com.boltic28.taskmanager.datalayer.room.di

import com.boltic28.taskmanager.datalayer.room.AppDataBase
import com.boltic28.taskmanager.datalayer.room.goal.GoalDao
import com.boltic28.taskmanager.datalayer.room.goal.DefaultGoalService
import com.boltic28.taskmanager.datalayer.room.idea.IdeaDao
import com.boltic28.taskmanager.datalayer.room.idea.DefaultIdeaService
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyDao
import com.boltic28.taskmanager.datalayer.room.keyresult.DefaultKeyService
import com.boltic28.taskmanager.datalayer.room.step.StepDao
import com.boltic28.taskmanager.datalayer.room.step.DefaultStepService
import com.boltic28.taskmanager.datalayer.room.task.TaskDao
import com.boltic28.taskmanager.datalayer.room.task.DefaultTaskService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ServiceModule(private val db: AppDataBase) {

    @Singleton
    @Provides
    fun provideGoalService(): DefaultGoalService = DefaultGoalService(db.goalDao())

    @Singleton
    @Provides
    fun provideKeyService(): DefaultKeyService = DefaultKeyService(db.keyDao())

    @Singleton
    @Provides
    fun provideStepService(): DefaultStepService = DefaultStepService(db.stepDao())

    @Singleton
    @Provides
    fun provideTaskService(): DefaultTaskService = DefaultTaskService(db.taskDao())

    @Singleton
    @Provides
    fun provideIdeaService(): DefaultIdeaService = DefaultIdeaService(db.ideaDao())


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