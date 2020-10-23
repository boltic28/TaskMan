package com.boltic28.taskmanager.businesslayer

import com.boltic28.taskmanager.datalayer.entities.*
import com.boltic28.taskmanager.datalayer.room.goal.GoalRepository
import com.boltic28.taskmanager.datalayer.room.idea.IdeaRepository
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyRepository
import com.boltic28.taskmanager.datalayer.room.step.StepRepository
import com.boltic28.taskmanager.datalayer.room.task.TaskRepository
import io.reactivex.Single

class GoalFragmentInteractorImpl(
    private val keyRepository: KeyRepository,
    private val stepRepository: StepRepository,
    private val taskRepository: TaskRepository,
    private val ideaRepository: IdeaRepository,
    private val goalRepository: GoalRepository,
    private val goalCase: CaseGoalStructure
): GoalFragmentInteractor {

    override fun getGoal(id: Long): Single<Goal> =
        goalRepository.getById(id)

    override fun updateGoal(item: Goal): Single<Int> =
        goalRepository.update(item)

    override fun setChildrenFor(goal: Goal): Single<Goal> =
        goalCase.setChildrenFor(goal)

    override fun setProgressFor(goal: Goal): Goal =
        goalCase.setProgressFor(goal)

    override fun addTask(item: Task): Single<Int> =
        taskRepository.update(item)

    override fun addStep(item: Step): Single<Int> =
        stepRepository.update(item)

    override fun addIdea(item: Idea): Single<Int> =
        ideaRepository.update(item)

    override fun addKey(item: KeyResult): Single<Int> =
        keyRepository.update(item)


}