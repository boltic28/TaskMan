package com.boltic28.taskmanager.businesslayer

import com.boltic28.taskmanager.datalayer.entities.*
import com.boltic28.taskmanager.datalayer.room.goal.GoalRepository
import com.boltic28.taskmanager.datalayer.room.idea.IdeaRepository
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyRepository
import com.boltic28.taskmanager.datalayer.room.step.StepRepository
import com.boltic28.taskmanager.datalayer.room.task.TaskRepository
import io.reactivex.Single

class FreeElementsInteractorImpl(
    private val keyRepository: KeyRepository,
    private val stepRepository: StepRepository,
    private val taskRepository: TaskRepository,
    private val ideaRepository: IdeaRepository,
    private val goalRepository: GoalRepository,
    private val goalCase: CaseGoalStructure
) : FreeElementsInteractor {

    override fun getFreeTasks(): Single<List<Task>> =
        taskRepository.getAllFree()

    override fun getFreeIdeas(): Single<List<Idea>> =
        ideaRepository.getAllFree()

    override fun getFreeKeys(): Single<List<KeyResult>> =
        keyRepository.getAllFree()

    override fun getFreeSteps(): Single<List<Step>> =
        stepRepository.getAllFree()

    override fun getGoals(): Single<List<Goal>> =
        goalRepository.getAll()

    override fun setChildrenFor(goal: Goal): Single<Goal> =
        goalCase.setChildrenFor(goal)

    override fun setProgressFor(goal: Goal): Goal =
        goalCase.setProgressFor(goal)


}