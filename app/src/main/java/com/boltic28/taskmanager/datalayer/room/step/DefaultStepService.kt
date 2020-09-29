package com.boltic28.taskmanager.datalayer.room.step

import com.boltic28.taskmanager.datalayer.classes.Idea
import com.boltic28.taskmanager.datalayer.classes.Step
import com.boltic28.taskmanager.datalayer.classes.Task
import com.boltic28.taskmanager.datalayer.room.idea.DefaultIdeaService
import com.boltic28.taskmanager.datalayer.room.task.DefaultTaskService
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class DefaultStepService(private val dao: StepDao) : StepService {

    @Inject
    lateinit var taskService: DefaultTaskService

    @Inject
    lateinit var ideaService: DefaultIdeaService

    override fun insert(item: Step): Single<Long> =
        dao.insert(item.toEntity())

    override fun getAll(): Single<List<Step>> =
        dao.getAll().map { list ->
            list.map { it.toStep() }
        }

    override fun getAllForGoal(goalId: Long): Single<List<Step>> =
        dao.getAllForGoal(goalId).map { list ->
            list.map { it.toStep() }
        }

    override fun getAllForKey(keyId: Long): Single<List<Step>> =
        dao.getAllForKey(keyId).map { list ->
            list.map { it.toStep() }
        }

    override fun getById(id: Long): Single<Step> =
        dao.getById(id).map { it.toStep() }

    override fun update(item: Step): Single<Int> =
        dao.update(item.toEntity())

    override fun delete(item: Step): Single<Int> =
        dao.delete(item.toEntity())

    override fun getChildrenFor(item: Step): Single<Step> =
        Single.just(item)
            .zipWith(
                taskService.getAllForGoal(item.id),
                BiFunction<Step, List<Task>, Step> { step, tasks ->
                    step.copy(tasks = tasks)
                })
            .zipWith(
                ideaService.getAllForGoal(item.id),
                BiFunction<Step, List<Idea>, Step> { step, ideas ->
                    step.copy(ideas = ideas)
                })
}