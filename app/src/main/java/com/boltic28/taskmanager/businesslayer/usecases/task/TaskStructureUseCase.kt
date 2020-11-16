package com.boltic28.taskmanager.businesslayer.usecases.task

import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ChildrenItemStructureUseCase
import com.boltic28.taskmanager.datalayer.entities.ParentItem
import com.boltic28.taskmanager.datalayer.entities.Task
import com.boltic28.taskmanager.datalayer.room.goal.GoalRepository
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyRepository
import com.boltic28.taskmanager.datalayer.room.step.StepRepository
import com.boltic28.taskmanager.ui.constant.NO_ID
import io.reactivex.Single

class TaskStructureUseCase (
    private val stepRepository: StepRepository,
    private val keyRepository: KeyRepository,
    private val goalRepository: GoalRepository
): ChildrenItemStructureUseCase<Task> {

    override fun getParentName(item: Task): Single<String> {
        if (item.goalId != NO_ID) return goalRepository.getById(item.goalId).map { it.name }
        if (item.stepId != NO_ID) return stepRepository.getById(item.stepId).map { it.name }
        if (item.keyId != NO_ID) return keyRepository.getById(item.keyId).map { it.name }
        return Single.just(item.name)
    }

    override fun getParentItems(): Single<List<ParentItem>> =
        Single.zip(Single.just(mutableListOf<ParentItem>()),
            goalRepository.getAll(),
            keyRepository.getAll(),
            stepRepository.getAll(),
            { list, goals, keys, steps ->
                list.addAll(goals)
                list.addAll(keys)
                list.addAll(steps)
                return@zip list
            }
        )
}