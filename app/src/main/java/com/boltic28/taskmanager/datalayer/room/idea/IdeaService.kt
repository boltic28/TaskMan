package com.boltic28.taskmanager.datalayer.room.idea

import com.boltic28.taskmanager.datalayer.dto.Idea
import com.boltic28.taskmanager.datalayer.room.DBService
import io.reactivex.Single

class IdeaService(private val dao: IdeaDao) : DBService<Idea, IdeaEntity> {
    override fun create(item: Idea): Single<Long> =
        dao.insert(getEntity(item))

    override fun readAll(): Single<List<Idea>> =
        dao.readAll().map { list ->
            list.map { entity ->
                getItem(entity)
            }
        }

    override fun readById(id: Long): Single<Idea> =
        dao.readById(id).map { entity ->
            getItem(entity)
        }

    override fun update(item: Idea): Single<Int> =
        dao.update(getEntity(item))

    override fun delete(item: Idea): Single<Int> =
        dao.delete(getEntity(item))

    override fun getEntity(item: Idea): IdeaEntity =
        IdeaEntity(
            item.id,
            item.stepId,
            item.keyId,
            item.goalId,
            item.name,
            item.description,
            item.icon,
            item.date
        )

    override fun getItem(entity: IdeaEntity): Idea =
        Idea(
            entity.id,
            entity.stepId,
            entity.keyId,
            entity.goalId,
            entity.name,
            entity.description,
            entity.icon,
            entity.date
        )
}