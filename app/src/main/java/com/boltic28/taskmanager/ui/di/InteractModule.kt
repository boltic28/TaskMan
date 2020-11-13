package com.boltic28.taskmanager.ui.di

import com.boltic28.taskmanager.businesslayer.crud.*
import com.boltic28.taskmanager.businesslayer.fragments.*
import com.boltic28.taskmanager.datalayer.firebaseworker.RemoteDB
import com.boltic28.taskmanager.datalayer.room.goal.GoalRepository
import com.boltic28.taskmanager.datalayer.room.idea.IdeaRepository
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyRepository
import com.boltic28.taskmanager.datalayer.room.step.StepRepository
import com.boltic28.taskmanager.datalayer.room.task.TaskRepository
import dagger.Module
import dagger.Provides

@Module
class InteractModule(
    private val keyRepository: KeyRepository,
    private val stepRepository: StepRepository,
    private val taskRepository: TaskRepository,
    private val ideaRepository: IdeaRepository,
    private val goalRepository: GoalRepository,
    private val remoteDB: RemoteDB
) {

    @Provides
    fun provideItemProvider(): ItemsProvider =
        ItemsProviderImpl(
            taskRepository,
            ideaRepository,
            stepRepository,
            goalRepository,
            keyRepository
        )

    @Provides
    fun provideCreator(): ItemsCreator =
        ItemsCreatorImpl(
            keyRepository,
            stepRepository,
            taskRepository,
            ideaRepository,
            goalRepository,
            remoteDB
        )

    @Provides
    fun provideUpdater(): ItemsUpdater =
        ItemsUpdaterImpl(
            keyRepository,
            stepRepository,
            taskRepository,
            ideaRepository,
            goalRepository,
            remoteDB
        )

    @Provides
    fun provideDeleter(): ItemsDeleter =
        ItemsDeleterImpl(
            keyRepository,
            stepRepository,
            taskRepository,
            ideaRepository,
            goalRepository,
            remoteDB
        )

    @Provides
    fun provideStructureProvider(): ItemsStructureProvider =
        ItemsStructureProviderImpl(
            keyRepository,
            stepRepository,
            taskRepository,
            ideaRepository,
            goalRepository,
            provideItemProvider(),
            provideUpdater()
        )

    @Provides
    fun provideMainFragmentInteractor(): MainFragmentInteractor =
        MainFragmentInteractorImpl(
            provideItemProvider(),
            provideStructureProvider(),
            provideUpdater()
        )

    @Provides
    fun provideGoalFragmentInteractor(): GoalFragmentInteractor =
        GoalFragmentInteractorImpl(
            goalRepository,
            provideStructureProvider(),
            provideUpdater(),
            provideDeleter()
        )

    @Provides
    fun provideFreeElementsInteractor(): MainFragmentInteractorImpl =
        MainFragmentInteractorImpl(
            provideItemProvider(),
            provideStructureProvider(),
            provideUpdater()
        )

    @Provides
    fun provideStepInteractor(): StepFragmentInteractor =
        StepFragmentInteractorImpl(
            provideItemProvider(),
            provideUpdater(),
            provideDeleter(),
            provideStructureProvider()
        )

    @Provides
    fun provideKeyFragmentInteractor(): KeyFragmentInteractor =
        KeyFragmentInteractorImpl(
            provideItemProvider(),
            provideUpdater(),
            provideDeleter(),
            provideStructureProvider()
        )

    @Provides
    fun provideTaskInteractor(): TaskFragmentInteractor =
        TaskFragmentInteractorImpl(
            provideItemProvider(),
            provideUpdater(),
            provideDeleter(),
            provideStructureProvider()
        )

    @Provides
    fun provideIdeaInteractor(): IdeaFragmentInteractor =
        IdeaFragmentInteractorImpl(
            provideCreator(),
            provideItemProvider(),
            provideUpdater(),
            provideDeleter(),
            provideStructureProvider()
        )
}