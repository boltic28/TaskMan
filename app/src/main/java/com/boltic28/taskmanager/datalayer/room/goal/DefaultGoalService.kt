package com.boltic28.taskmanager.datalayer.room.goal

import com.boltic28.taskmanager.datalayer.classes.Goal
import io.reactivex.Single

class DefaultGoalService(private val dao: GoalDao) : GoalService {

    override fun insert(item: Goal): Single<Long> =
        dao.insert(item.toEntity())

    override fun readAll(): Single<List<Goal>> =
        dao.readAll().map { list ->
            list.map { it.toGoal() }
        }

    override fun readById(id: Long): Single<Goal> =
        dao.readById(id).map { it.toGoal() }

    override fun update(item: Goal): Single<Int> =
        dao.update(item.toEntity())

    override fun delete(item: Goal): Single<Int> =
        dao.delete(item.toEntity())
}