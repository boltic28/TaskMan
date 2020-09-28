package com.boltic28.taskmanager.datalayer.room.step

import com.boltic28.taskmanager.datalayer.dto.Step
import com.boltic28.taskmanager.datalayer.room.DBService
import io.reactivex.Single

class StepService(private val dao: StepDao) : DBService<Step, StepEntity> {
    override fun create(item: Step): Single<Long> =
        dao.insert(getEntity(item))

    override fun readAll(): Single<List<Step>> =
        dao.readAll().map { list ->
            list.map { entity ->
                getItem(entity)
            }
        }

    override fun readById(id: Long): Single<Step> =
        dao.readById(id).map { entity ->
            getItem(entity)
        }

    override fun update(item: Step): Single<Int> =
        dao.update(getEntity(item))

    override fun delete(item: Step): Single<Int> =
        dao.delete(getEntity(item))

    override fun getEntity(item: Step): StepEntity =
        StepEntity(
            item.id,
            item.goalId,
            item.keyId,
            item.name,
            item.description,
            item.icon,
            item.date,
            item.dateClose,
            item.isDone,
            item.isStarted
        )

    override fun getItem(entity: StepEntity): Step =
        Step(
            entity.id,
            entity.goalId,
            entity.keyId,
            entity.name,
            entity.description,
            entity.icon,
            entity.date,
            entity.dateClose,
            entity.isDone,
            entity.isStarted
        )
}