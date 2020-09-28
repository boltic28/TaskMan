package com.boltic28.taskmanager.datalayer.room.goal

import com.boltic28.taskmanager.datalayer.dto.Goal
import com.boltic28.taskmanager.datalayer.room.DBService
import io.reactivex.Single

class GoalService(private val dao: GoalDao) : DBService<Goal, GoalEntity> {

    override fun create(item: Goal): Single<Long> =
        dao.insert(getEntity(item))

    override fun readAll(): Single<List<Goal>> =
        dao.readAll().map { list ->
            list.map { entity ->
                getItem(entity) }
        }

    override fun readById(id: Long): Single<Goal> =
        dao.readById(id).map { entity ->
            getItem(entity)
        }

    override fun update(item: Goal): Single<Int> =
        dao.update(getEntity(item))

    override fun delete(item: Goal): Single<Int> =
        dao.delete(getEntity(item))

    override fun getEntity(item: Goal): GoalEntity =
        GoalEntity(
            item.id,
            item.name,
            item.description,
            item.icon,
            item.date,
            item.dateClose,
            item.isDone,
            item.isStarted
        )

    override fun getItem(entity: GoalEntity): Goal =
        Goal(
            entity.id,
            entity.name,
            entity.description,
            entity.icon,
            entity.date,
            entity.dateClose,
            entity.isDone,
            entity.isStarted,
            mutableListOf(),
            mutableListOf(),
            mutableListOf()
        )

}