package com.boltic28.taskmanager.ui.di

import com.boltic28.taskmanager.businesslayer.interactors.*
import com.boltic28.taskmanager.businesslayer.usecases.*
import com.boltic28.taskmanager.businesslayer.usecases.goal.*
import com.boltic28.taskmanager.businesslayer.usecases.idea.*
import com.boltic28.taskmanager.businesslayer.usecases.interfaces.*
import com.boltic28.taskmanager.businesslayer.usecases.key.*
import com.boltic28.taskmanager.businesslayer.usecases.step.*
import com.boltic28.taskmanager.businesslayer.usecases.task.*
import com.boltic28.taskmanager.datalayer.entities.*
import com.boltic28.taskmanager.datalayer.firebaseworker.dto.RemoteRepo
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
    private val remRepoGoal: RemoteRepo<Goal>,
    private val remRepoStep: RemoteRepo<Step>,
    private val remRepoTask: RemoteRepo<Task>,
    private val remRepoIdea: RemoteRepo<Idea>,
    private val remRepoKey: RemoteRepo<KeyResult>
) {

    @Provides
    fun provideGoalCreateUseCase(): ItemCreateUseCase<Goal> =
        GoalCreateUseCase(goalRepository)

    @Provides
    fun provideGoalDeleteUseCase(): ItemDeleteUseCase<Goal> =
        GoalDeleteUseCase(goalRepository)

    @Provides
    fun provideGoalUpdateUseCase(): ItemUpdateUseCase<Goal> =
        GoalUpdateUseCase(goalRepository)

    @Provides
    fun provideGoalReadUseCase(): ItemReadUseCase<Goal> =
        GoalReadUseCase(goalRepository)

    @Provides
    fun provideGoalGetAllUseCase(): ItemGetAllUseCase<Goal> =
        GoalGetAllUseCase(goalRepository)

    @Provides
    fun provideGoalStructureUseCase(): ParentItemStructureUseCase<Goal> =
        GoalStructureUseCase(
            keyRepository,
            stepRepository,
            taskRepository,
            ideaRepository,
            goalRepository
        )

    @Provides
    fun provideStepCreateUseCase(): ItemCreateUseCase<Step> =
        StepCreateUseCase(stepRepository)

    @Provides
    fun provideStepDeleteUseCase(): ItemDeleteUseCase<Step> =
        StepDeleteUseCase(stepRepository)

    @Provides
    fun provideStepUpdateUseCase(): ItemUpdateUseCase<Step> =
        StepUpdateUseCase(stepRepository)

    @Provides
    fun provideStepReadUseCase(): ItemReadUseCase<Step> =
        StepReadUseCase(stepRepository)

    @Provides
    fun provideStepGetAllUseCase(): ItemGetAllUseCase<Step> =
        StepGetAllUseCase(stepRepository)

    @Provides
    fun provideStepGetAllFreeUseCase(): ItemGetAllFreeUseCase<Step> =
        StepGetAllFreeUseCase(stepRepository)

    @Provides
    fun provideStepStructureUseCase(): ParentItemStructureUseCase<Step> =
        StepStructureUseCase(
            stepRepository,
            taskRepository,
            ideaRepository,
            goalRepository
        )

    @Provides
    fun provideKeyCreateUseCase(): ItemCreateUseCase<KeyResult> =
        KeyCreateUseCase(keyRepository)

    @Provides
    fun provideKeyDeleteUseCase(): ItemDeleteUseCase<KeyResult> =
        KeyDeleteUseCase(keyRepository)

    @Provides
    fun provideKeyUpdateUseCase(): ItemUpdateUseCase<KeyResult> =
        KeyUpdateUseCase(keyRepository)

    @Provides
    fun provideKeyReadUseCase(): ItemReadUseCase<KeyResult> =
        KeyReadUseCase(keyRepository)

    @Provides
    fun provideKeyGetAllUseCase(): ItemGetAllUseCase<KeyResult> =
        KeyGetAllUseCase(keyRepository)

    @Provides
    fun provideKeyGetAllFreeUseCase(): ItemGetAllFreeUseCase<KeyResult> =
        KeyGetAllFreeUseCase(keyRepository)

    @Provides
    fun provideKeyStructureUseCase(): ParentItemStructureUseCase<KeyResult> =
        KeyStructureUseCase(
            keyRepository,
            taskRepository,
            ideaRepository,
            goalRepository
        )

    @Provides
    fun provideTaskCreateUseCase(): ItemCreateUseCase<Task> =
        TaskCreateUseCase(taskRepository)

    @Provides
    fun provideTaskDeleteUseCase(): ItemDeleteUseCase<Task> =
        TaskDeleteUseCase(taskRepository)

    @Provides
    fun provideTaskUpdateUseCase(): ItemUpdateUseCase<Task> =
        TaskUpdateUseCase(taskRepository)

    @Provides
    fun provideTaskReadUseCase(): ItemReadUseCase<Task> =
        TaskReadUseCase(taskRepository)

    @Provides
    fun provideTaskGetAllUseCase(): ItemGetAllUseCase<Task> =
        TaskGetAllUseCase(taskRepository)

    @Provides
    fun provideTaskGetAllFreeUseCase(): ItemGetAllFreeUseCase<Task> =
        TaskGetAllFreeUseCase(taskRepository)

    @Provides
    fun provideTaskStructureUseCase(): ChildrenItemStructureUseCase<Task> =
        TaskStructureUseCase(
            stepRepository,
            keyRepository,
            goalRepository
        )

    @Provides
    fun provideIdeaCreateUseCase(): ItemCreateUseCase<Idea> =
        IdeaCreateUseCase(ideaRepository)

    @Provides
    fun provideIdeaDeleteUseCase(): ItemDeleteUseCase<Idea> =
        IdeaDeleteUseCase(ideaRepository)

    @Provides
    fun provideIdeaUpdateUseCase(): ItemUpdateUseCase<Idea> =
        IdeaUpdateUseCase(ideaRepository)

    @Provides
    fun provideIdeaReadUseCase(): ItemReadUseCase<Idea> =
        IdeaReadUseCase(ideaRepository)

    @Provides
    fun provideIdeaGetAllUseCase(): ItemGetAllUseCase<Idea> =
        IdeaGetAllUseCase(ideaRepository)

    @Provides
    fun provideIdeaGetAllFreeUseCase(): ItemGetAllFreeUseCase<Idea> =
        IdeaGetAllFreeUseCase(ideaRepository)

    @Provides
    fun provideIdeaStructureUseCase(): ChildrenItemStructureUseCase<Idea> =
        IdeaStructureUseCase(
            stepRepository,
            keyRepository,
            goalRepository
        )

    @Provides
    fun provideFreeItemsProvider(): GetFreeItemsUseCase =
        GetFreeItemsUseCaseImpl(
            stepRepository,
            taskRepository,
            keyRepository,
            ideaRepository,
        )

    @Provides
    fun provideItemsProvider(): GetAllItemsUseCase =
        GetAllItemsUseCaseImpl(
            goalRepository,
            stepRepository,
            taskRepository,
            keyRepository,
            ideaRepository,
        )

    @Provides
    fun provideItemsCreator(): ItemsCreateUseCase =
        ItemsCreateUseCaseImpl(
            keyRepository,
            stepRepository,
            taskRepository,
            ideaRepository,
            goalRepository
        )

    @Provides
    fun provideItemsUpdater(): ItemsUpdateUseCase =
        ItemsUpdateUseCaseImpl(
            keyRepository,
            stepRepository,
            taskRepository,
            ideaRepository,
            goalRepository
        )

    @Provides
    fun provideDataRefresher(): RefreshDataUseCase =
        RefreshDataUseCaseImpl(
            keyRepository,
            stepRepository,
            taskRepository,
            ideaRepository,
            goalRepository,
            remRepoGoal,
            remRepoStep,
            remRepoTask,
            remRepoIdea,
            remRepoKey
        )

    @Provides
    fun provideMainFragmentInteractor(): MainFragmentInteractor =
        MainFragmentInteractor(
            provideItemsProvider(),
            provideTaskUpdateUseCase(),
            provideDataRefresher(),
            provideGoalStructureUseCase(),
            provideStepStructureUseCase(),
            provideKeyStructureUseCase()
        )

    @Provides
    fun provideSignFragmentInteractor(): SignFragmentInteractor =
        SignFragmentInteractor(
            provideDataRefresher()
        )

    @Provides
    fun provideGoalFragmentInteractor(): GoalFragmentInteractor =
        GoalFragmentInteractor(
            provideGoalReadUseCase(),
            provideGoalDeleteUseCase(),
            provideItemsUpdater(),
            provideGoalStructureUseCase(),
            provideFreeItemsProvider()
        )

    @Provides
    fun provideStepFragmentInteractor(): StepFragmentInteractor =
        StepFragmentInteractor(
            provideStepReadUseCase(),
            provideItemsUpdater(),
            provideStepDeleteUseCase(),
            provideStepStructureUseCase(),
            provideGoalGetAllUseCase(),
            provideFreeItemsProvider()
        )

    @Provides
    fun provideKeyFragmentInteractor(): KeyFragmentInteractor =
        KeyFragmentInteractor(
            provideKeyReadUseCase(),
            provideItemsUpdater(),
            provideKeyDeleteUseCase(),
            provideGoalGetAllUseCase(),
            provideKeyStructureUseCase(),
            provideFreeItemsProvider()
        )

    @Provides
    fun provideTaskFragmentInteractor(): TaskFragmentInteractor =
        TaskFragmentInteractor(
            provideTaskReadUseCase(),
            provideTaskUpdateUseCase(),
            provideTaskDeleteUseCase(),
            provideTaskStructureUseCase()
        )

    @Provides
    fun provideIdeaFragmentInteractor(): IdeaFragmentInteractor =
        IdeaFragmentInteractor(
            provideItemsCreator(),
            provideIdeaReadUseCase(),
            provideIdeaUpdateUseCase(),
            provideIdeaDeleteUseCase(),
            provideIdeaStructureUseCase()
        )
}