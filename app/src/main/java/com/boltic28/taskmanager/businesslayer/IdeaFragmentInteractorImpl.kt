package com.boltic28.taskmanager.businesslayer

import com.boltic28.taskmanager.datalayer.entities.*
import com.boltic28.taskmanager.datalayer.room.goal.GoalRepository
import com.boltic28.taskmanager.datalayer.room.idea.IdeaRepository
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyRepository
import com.boltic28.taskmanager.datalayer.room.step.StepRepository
import com.boltic28.taskmanager.datalayer.room.task.TaskRepository
import com.boltic28.taskmanager.ui.constant.NO_ID
import io.reactivex.Single
import io.reactivex.Single.zip

class IdeaFragmentInteractorImpl(
    private val ideaRepository: IdeaRepository,
    private val stepRepository: StepRepository,
    private val keyRepository: KeyRepository,
    private val goalRepository: GoalRepository,
    private val taskRepository: TaskRepository
) : IdeaFragmentInteractor {
    override fun update(item: Idea): Single<Int> =
        ideaRepository.update(item)

    override fun delete(item: Idea): Single<Int> =
        ideaRepository.delete(item)

    override fun getIdeaById(id: Long): Single<Idea> =
        ideaRepository.getById(id)

    override fun getStepsGoalsKeys(): Single<List<ParentItem>> =
        zip(
            Single.just(mutableListOf<ParentItem>()),
            goalRepository.getAll(),
            keyRepository.getAll(),
            stepRepository.getAll(),
            { mList, goals, keys, steps ->
                mList.addAll(goals)
                mList.addAll(keys)
                mList.addAll(steps)
                return@zip mList
            })

    override fun create(item: Task): Single<Long> =
        taskRepository.insert(item)

    override fun create(item: Step): Single<Long> =
        stepRepository.insert(item)

    override fun create(item: Goal): Single<Long> =
        goalRepository.insert(item)

    override fun create(item: KeyResult): Single<Long> =
        keyRepository.insert(item)

    override fun getParentName(item: Idea): Single<String> {
        if (item.goalId != NO_ID) return goalRepository.getById(item.goalId).map { it.name }
        if (item.stepId != NO_ID) return stepRepository.getById(item.stepId).map { it.name }
        if (item.keyId != NO_ID) return keyRepository.getById(item.keyId).map { it.name }
        return Single.just(item.name)
    }
}