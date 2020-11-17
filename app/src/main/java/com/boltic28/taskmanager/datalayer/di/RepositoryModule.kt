package com.boltic28.taskmanager.datalayer.di

import com.boltic28.taskmanager.datalayer.entities.*
import com.boltic28.taskmanager.datalayer.firebaseworker.RemoteDB
import com.boltic28.taskmanager.datalayer.firebaseworker.dto.*
import com.boltic28.taskmanager.datalayer.firebaseworker.dto.goal.GoalRemoteRepo
import com.boltic28.taskmanager.datalayer.firebaseworker.dto.idea.IdeaRemoteRepo
import com.boltic28.taskmanager.datalayer.firebaseworker.dto.key.KeyRemoteRepo
import com.boltic28.taskmanager.datalayer.firebaseworker.dto.step.StepRemoteRepo
import com.boltic28.taskmanager.datalayer.firebaseworker.dto.task.TaskRemoteRepo
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

@Module
class RepositoryModule(private val db: AppDataBase, private val rdb: RemoteDB) {

    @AppScope
    @Provides
    fun provideGoalRepo(): GoalRepository = DefaultGoalRepository(db.goalDao(), provideGoalRemoteRepo())

    @AppScope
    @Provides
    fun provideKeyRepo(): KeyRepository = DefaultKeyRepository(db.keyDao(), provideKeyRemoteRepo())

    @AppScope
    @Provides
    fun provideStepRepo(): StepRepository = DefaultStepRepository(db.stepDao(), provideStepRemoteRepo())

    @AppScope
    @Provides
    fun provideTaskRepo(): TaskRepository = DefaultTaskRepository(db.taskDao(), provideTaskRemoteRepo())

    @AppScope
    @Provides
    fun provideIdeaRepo(): IdeaRepository = DefaultIdeaRepository(db.ideaDao(), provideIdeaRemoteRepo())

    @Provides
    fun provideGoalRemoteRepo(): GoalRemoteRepo =
        GoalRemoteRepo(rdb)

    @AppScope
    @Provides
    fun provideStepRemoteRepo(): RemoteRepo<Step> =
        StepRemoteRepo(rdb)

    @AppScope
    @Provides
    fun provideTaskRemoteRepo(): RemoteRepo<Task> =
        TaskRemoteRepo(rdb)

    @AppScope
    @Provides
    fun provideIdeaRemoteRepo(): RemoteRepo<Idea> =
        IdeaRemoteRepo(rdb)

    @AppScope
    @Provides
    fun provideKeyRemoteRepo(): RemoteRepo<KeyResult> =
        KeyRemoteRepo(rdb)
}