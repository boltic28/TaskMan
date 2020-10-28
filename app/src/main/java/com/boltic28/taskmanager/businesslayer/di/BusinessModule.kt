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

@Module
class BusinessModule(
    private val keyRepository: KeyRepository,
    private val stepRepository: StepRepository,
    private val taskRepository: TaskRepository,
    private val ideaRepository: IdeaRepository,
    private var goalRepository: GoalRepository
) {
    @Provides
    fun provideMainFragmentInteractor(): FreeElementsInteractor =
        FreeElementsInteractorImpl(
            keyRepository,
            stepRepository,
            taskRepository,
            ideaRepository,
            goalRepository,
            provideCaseGoalStructure()
        )

    @Provides
    fun provideGoalFragmentInteractor(): GoalFragmentInteractor =
        GoalFragmentInteractorImpl(
            keyRepository,
            stepRepository,
            taskRepository,
            ideaRepository,
            goalRepository,
            provideCaseGoalStructure()
        )

    @Provides
    fun provideFreeElementsInteractor(): FreeElementsInteractorImpl =
        FreeElementsInteractorImpl(
            keyRepository,
            stepRepository,
            taskRepository,
            ideaRepository,
            goalRepository,
            provideCaseGoalStructure()
        )

    @Provides
    fun provideCaseGoalStructure(): CaseGoalStructure =
        CaseGoalStructureImpl(
            keyRepository,
            stepRepository,
            taskRepository,
            ideaRepository
        )

    @Provides
    fun provideStepInteractor(): StepFragmentInteractor =
        StepFragmentInteractorImpl(
            taskRepository,
            ideaRepository,
            stepRepository,
            goalRepository
        )

    @Provides
    fun provideKeyFragmentInteractor(): KeyFragmentInteractor =
        KeyFragmentInteractorImpl(
            keyRepository,
            taskRepository,
            ideaRepository,
            goalRepository
        )

    @Provides
    fun provideTaskInteractor(): TaskInteractor =
        TaskInteractorImpl(
            taskRepository
        )

    @Provides
    fun provideIdeaInteractor(): IdeaInteractor =
        IdeaInteractorImpl(
            ideaRepository
        )
}