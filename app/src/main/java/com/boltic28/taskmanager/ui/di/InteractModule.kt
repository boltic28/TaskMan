package com.boltic28.taskmanager.ui.di

import com.boltic28.taskmanager.businesslayer.*
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
    private val goalRepository: GoalRepository
) {

    @Provides
    fun provideCreatorInteractor(): CreatorInteractor =
        CreatorInteractorImpl(
            keyRepository,
            stepRepository,
            taskRepository,
            ideaRepository,
            goalRepository
        )

    @Provides
    fun provideMainFragmentInteractor(): FreeElementsInteractor =
        FreeElementsInteractorImpl(
            keyRepository,
            stepRepository,
            taskRepository,
            ideaRepository,
            goalRepository,
            provideCaseItemStructure()
        )

    @Provides
    fun provideGoalFragmentInteractor(): GoalFragmentInteractor =
        GoalFragmentInteractorImpl(
            keyRepository,
            stepRepository,
            taskRepository,
            ideaRepository,
            goalRepository,
            provideCaseItemStructure()
        )

    @Provides
    fun provideFreeElementsInteractor(): FreeElementsInteractorImpl =
        FreeElementsInteractorImpl(
            keyRepository,
            stepRepository,
            taskRepository,
            ideaRepository,
            goalRepository,
            provideCaseItemStructure()
        )

    @Provides
    fun provideCaseItemStructure(): ItemsStructureProvider =
        ItemsStructureProviderImpl(
            keyRepository,
            stepRepository,
            taskRepository,
            ideaRepository,
            goalRepository
        )

    @Provides
    fun provideStepInteractor(): StepFragmentInteractor =
        StepFragmentInteractorImpl(
            taskRepository,
            ideaRepository,
            stepRepository,
            goalRepository,
            provideCaseItemStructure()
        )

    @Provides
    fun provideKeyFragmentInteractor(): KeyFragmentInteractor =
        KeyFragmentInteractorImpl(
            keyRepository,
            taskRepository,
            ideaRepository,
            goalRepository,
            provideCaseItemStructure()
        )

    @Provides
    fun provideTaskInteractor(): TaskFragmentInteractor =
        TaskFragmentInteractorImpl(
            taskRepository,
            stepRepository,
            keyRepository,
            goalRepository
        )

    @Provides
    fun provideIdeaInteractor(): IdeaFragmentInteractor =
        IdeaFragmentInteractorImpl(
            ideaRepository,
            stepRepository,
            keyRepository,
            goalRepository,
            taskRepository
        )
}