package com.boltic28.taskmanager.datalayer.room.keyresult

import com.boltic28.taskmanager.datalayer.classes.*
import com.boltic28.taskmanager.datalayer.room.idea.DefaultIdeaService
import com.boltic28.taskmanager.datalayer.room.step.DefaultStepService
import com.boltic28.taskmanager.datalayer.room.task.DefaultTaskService
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class DefaultKeyService(private val dao: KeyDao) : KeyService {

    @Inject
    lateinit var stepService: DefaultStepService

    @Inject
    lateinit var taskService: DefaultTaskService

    @Inject
    lateinit var ideaService: DefaultIdeaService

    override fun insert(item: KeyResult): Single<Long> =
        dao.insert(item.toEntity())

    override fun getAll(): Single<List<KeyResult>> =
        dao.getAll().map { list ->
            list.map { it.toKey() }
        }

    override fun getAllForGoal(goalId: Long): Single<List<KeyResult>> =
        dao.getAllForGoal(goalId).map { list ->
            list.map { it.toKey() }
        }

    override fun getById(id: Long): Single<KeyResult> =
        dao.getById(id).map { it.toKey() }

    override fun update(item: KeyResult): Single<Int> =
        dao.update(item.toEntity())

    override fun delete(item: KeyResult): Single<Int> =
        dao.delete(item.toEntity())

    override fun getChildrenFor(item: KeyResult): Single<KeyResult> =
        Single.just(item)
            .zipWith(
                stepService.getAllForGoal(item.id),
                BiFunction<KeyResult, List<Step>, KeyResult> { key, steps ->
                    key.copy(steps = steps)
                })
            .zipWith(
                taskService.getAllForGoal(item.id),
                BiFunction<KeyResult, List<Task>, KeyResult> { key, tasks ->
                    key.copy(tasks = tasks)
                })
            .zipWith(
                ideaService.getAllForGoal(item.id),
                BiFunction<KeyResult, List<Idea>, KeyResult> { key, ideas ->
                    key.copy(ideas = ideas)
                })
}