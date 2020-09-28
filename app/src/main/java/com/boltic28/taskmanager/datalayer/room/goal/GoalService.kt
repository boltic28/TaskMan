package com.boltic28.taskmanager.datalayer.room.goal

import com.boltic28.taskmanager.datalayer.dto.Goal
import com.boltic28.taskmanager.datalayer.room.DBService
import io.reactivex.Single

class GoalService(private val dao: GoalDao) : DBService<Goal, GoalEntity> {

    override fun create(item: Goal): Single<Long> {
        TODO("Not yet implemented")
    }

    override fun readAll(): Single<List<Goal>> {
        TODO("Not yet implemented")
    }

    override fun readById(id: Long): Single<Goal> {
        TODO("Not yet implemented")
    }

    override fun update(item: Goal): Single<Int> {
        TODO("Not yet implemented")
    }

    override fun delete(item: Goal): Single<Int> {
        TODO("Not yet implemented")
    }

    override fun toEntity(item: Goal): GoalEntity =
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

    override fun fromEntity(entity: GoalEntity): Goal {
        TODO("Not yet implemented")
    }

}