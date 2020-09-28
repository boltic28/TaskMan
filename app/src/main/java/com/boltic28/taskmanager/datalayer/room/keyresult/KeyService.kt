package com.boltic28.taskmanager.datalayer.room.keyresult

import com.boltic28.taskmanager.datalayer.dto.KeyResult
import com.boltic28.taskmanager.datalayer.room.DBService
import io.reactivex.Single

class KeyService(private val dao: KeyDao) : DBService<KeyResult, KeyEntity> {
    override fun create(item: KeyResult): Single<Long> =
        dao.insert(getEntity(item))

    override fun readAll(): Single<List<KeyResult>> =
        dao.readAll().map { list ->
            list.map { entity ->
                getItem(entity)
            }
        }

    override fun readById(id: Long): Single<KeyResult> =
        dao.readById(id).map { entity ->
            getItem(entity)
        }

    override fun update(item: KeyResult): Single<Int> =
        dao.update(getEntity(item))

    override fun delete(item: KeyResult): Single<Int> =
        dao.delete(getEntity(item))

    override fun getEntity(item: KeyResult): KeyEntity =
        KeyEntity(
            item.id, item.goalId, item.name, item.description, item.date, item.progress
        )

    override fun getItem(entity: KeyEntity): KeyResult =
        KeyResult(
            entity.id, entity.goalId, entity.name, entity.description, entity.date, entity.progress
        )
}