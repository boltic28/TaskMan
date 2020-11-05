package com.boltic28.taskmanager.businesslayer

import com.boltic28.taskmanager.datalayer.entities.Goal
import com.boltic28.taskmanager.datalayer.entities.KeyResult
import com.boltic28.taskmanager.datalayer.entities.Step
import com.boltic28.taskmanager.datalayer.entities.Task
import com.boltic28.taskmanager.datalayer.room.goal.GoalRepository
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyRepository
import com.boltic28.taskmanager.datalayer.room.step.StepRepository
import com.boltic28.taskmanager.datalayer.room.task.TaskRepository
import io.reactivex.Single
import io.reactivex.functions.BiFunction

class TaskFragmentInteractorImpl(
    private val taskRepository: TaskRepository,
    private val stepRepository: StepRepository,
    private val keyRepository: KeyRepository,
    private val goalRepository: GoalRepository
): TaskFragmentInteractor {

    override fun update(item: Task): Single<Int> =
        taskRepository.update(item)

    override fun delete(item: Task): Single<Int> =
        taskRepository.delete(item)

    override fun getTaskById(id: Long): Single<Task> =
        taskRepository.getById(id)

    override fun getParentName(item: Task): Single<String> {
        if (item.goalId != 0L) return goalRepository.getById(item.goalId).map { it.name }
        if (item.stepId != 0L) return stepRepository.getById(item.stepId).map { it.name }
        if (item.keyId != 0L) return keyRepository.getById(item.keyId).map { it.name }
        return Single.just("error")
    }

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
}