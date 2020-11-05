package com.boltic28.taskmanager.businesslayer

import com.boltic28.taskmanager.datalayer.entities.*
import com.boltic28.taskmanager.datalayer.room.goal.GoalRepository
import com.boltic28.taskmanager.datalayer.room.idea.IdeaRepository
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyRepository
import com.boltic28.taskmanager.datalayer.room.step.StepRepository
import com.boltic28.taskmanager.datalayer.room.task.DefaultTaskRepository
import com.boltic28.taskmanager.datalayer.room.task.TaskRepository
import io.reactivex.Single
import io.reactivex.functions.BiFunction

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

    override fun getFreeStepsGoalsKeys(): Single<List<Any>> =
        Single.just(mutableListOf<Any>())
            .zipWith(
                goalRepository.getAll(),
                BiFunction<MutableList<Any>, List<Goal>, MutableList<Any>> { mList, list ->
                    mList.addAll(list)
                    return@BiFunction mList
                })
            .zipWith(
                keyRepository.getAll(),
                BiFunction<MutableList<Any>, List<KeyResult>, MutableList<Any>> { mList, list ->
                    mList.addAll(list)
                    return@BiFunction mList
                })
            .zipWith(
                stepRepository.getAll(),
                BiFunction<MutableList<Any>, List<Step>, MutableList<Any>> { mList, list ->
                    mList.addAll(list)
                    return@BiFunction mList
                })

    override fun create(item: Task): Single<Long> =
        taskRepository.insert(item)

    override fun create(item: Step): Single<Long> =
        stepRepository.insert(item)

    override fun create(item: Goal): Single<Long> =
        goalRepository.insert(item)

    override fun getParentName(item: Idea): Single<String> {
            if (item.goalId != 0L) return goalRepository.getById(item.goalId).map { it.name }
            if (item.stepId != 0L) return stepRepository.getById(item.stepId).map { it.name }
            if (item.keyId != 0L) return keyRepository.getById(item.keyId).map { it.name }
            return Single.just("error")
    }
}