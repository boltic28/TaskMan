package com.boltic28.taskmanager.datalayer.room.goal

import com.boltic28.taskmanager.datalayer.entities.Goal
import io.reactivex.Single

class DefaultGoalRepository(private val dao: GoalDao) : GoalRepository {

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

}