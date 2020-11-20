package com.boltic28.taskmanager.datalayer.room.goal

import com.boltic28.taskmanager.datalayer.entities.Goal
import com.boltic28.taskmanager.datalayer.firebaseworker.dto.RemoteRepo
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class DefaultGoalRepository(private val dao: GoalDao, private val remoteDB: RemoteRepo<Goal>) :
    GoalRepository {

    override fun insert(item: Goal): Single<Long> =
        dao.insert(item.toEntity())
            .doOnSuccess { remoteDB.create(item) }

    override fun getAll(): Single<List<Goal>> =
        dao.getAll().map { list ->
            list.map { it.toGoal() }
        }

    override fun getById(id: Long): Single<Goal> =
        dao.getById(id).map { it.toGoal() }

    override fun update(item: Goal): Single<Int> =
        dao.update(item.toEntity())
            .doOnSuccess { remoteDB.create(item) }

    override fun delete(item: Goal): Single<Int> =
        dao.delete(item.toEntity())
            .doOnSuccess { remoteDB.delete(item) }

    override fun deleteAll(): Single<Int> =
        dao.deleteAll()

    override fun refreshData(item: Goal): Single<Long> =
        dao.insert(item.toEntity())
}