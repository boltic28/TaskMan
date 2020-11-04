package com.boltic28.taskmanager.datalayer.di

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
import com.boltic28.taskmanager.di.AppScope
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule(private val db: AppDataBase) {

    @AppScope
    @Provides
    fun provideGoalRepo(): GoalRepository = DefaultGoalRepository(db.goalDao())

    @AppScope
    @Provides
    fun provideKeyRepo(): KeyRepository = DefaultKeyRepository(db.keyDao())

    @AppScope
    @Provides
    fun provideStepRepo(): StepRepository = DefaultStepRepository(db.stepDao())

    @AppScope
    @Provides
    fun provideTaskRepo(): TaskRepository = DefaultTaskRepository(db.taskDao())

    @AppScope
    @Provides
    fun provideIdeaRepo(): IdeaRepository = DefaultIdeaRepository(db.ideaDao())
}