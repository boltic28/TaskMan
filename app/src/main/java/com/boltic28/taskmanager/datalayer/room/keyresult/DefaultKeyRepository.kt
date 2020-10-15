package com.boltic28.taskmanager.datalayer.room.keyresult

import com.boltic28.taskmanager.datalayer.entities.KeyResult
import io.reactivex.Single

class DefaultKeyRepository(private val dao: KeyDao) : KeyRepository {

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
}