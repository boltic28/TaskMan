package com.boltic28.taskmanager.datalayer.room.goal

import com.boltic28.taskmanager.datalayer.classes.*
import com.boltic28.taskmanager.datalayer.room.idea.DefaultIdeaService
import com.boltic28.taskmanager.datalayer.room.keyresult.DefaultKeyService
import com.boltic28.taskmanager.datalayer.room.step.DefaultStepService
import com.boltic28.taskmanager.datalayer.room.task.DefaultTaskService
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class DefaultGoalService(private val dao: GoalDao) : GoalService {

    @Inject
    lateinit var keyService: DefaultKeyService

    @Inject
    lateinit var stepService: DefaultStepService

    @Inject
    lateinit var taskService: DefaultTaskService

    @Inject
    lateinit var ideaService: DefaultIdeaService

    override fun insert(item: Goal): Single<Long> =
        dao.insert(item.toEntity())

    override fun getAll(): Single<List<Goal>> =
        dao.getAll().map { list ->
            list.map { it.toGoal() }
        }

    override fun getById(id: Long): Single<Goal> =
        dao.getById(id).map { it.toGoal() }

    override fun update(item: Goal): Single<Int> =
        dao.update(item.toEntity())

    override fun delete(item: Goal): Single<Int> =
        dao.delete(item.toEntity())

    override fun getChildrenFor(item: Goal): Single<Goal> =
        Single.just(item)
            .zipWith(keyService.getAllForGoal(item.id),
                BiFunction<Goal, List<KeyResult>, Goal> { goal, keys ->
                    goal.copy(keys = keys)
                })
            .zipWith(
                stepService.getAllForGoal(item.id),
                BiFunction<Goal, List<Step>, Goal> { goal, steps ->
                    goal.copy(steps = steps)
                })
            .zipWith(
                taskService.getAllForGoal(item.id),
                BiFunction<Goal, List<Task>, Goal> { goal, tasks ->
                    goal.copy(tasks = tasks)
                })
            .zipWith(
                ideaService.getAllForGoal(item.id),
                BiFunction<Goal, List<Idea>, Goal> { goal, ideas ->
                    goal.copy(ideas = ideas)
                })
}