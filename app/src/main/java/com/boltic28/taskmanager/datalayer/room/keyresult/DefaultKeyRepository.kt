package com.boltic28.taskmanager.datalayer.room.keyresult

import com.boltic28.taskmanager.datalayer.entities.KeyResult
import com.boltic28.taskmanager.datalayer.firebaseworker.dto.RemoteRepo
import io.reactivex.Single

class DefaultKeyRepository(private val dao: KeyDao, private val remoteDB: RemoteRepo<KeyResult>) :
    KeyRepository {

    override fun insert(item: KeyResult): Single<Long> =
        dao.insert(item.toEntity())
            .doOnSuccess { remoteDB.create(item) }

    override fun getAll(): Single<List<KeyResult>> =
        dao.getAll().map { list ->
            list.map { it.toKey() }
        }

    override fun getAllFree(): Single<List<KeyResult>> =
        dao.getAllFree().map { list ->
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
            .doOnSuccess { remoteDB.create(item) }

    override fun delete(item: KeyResult): Single<Int> =
        dao.delete(item.toEntity())
            .doOnSuccess { remoteDB.delete(item) }

    override fun deleteAll(): Single<Int> =
        dao.deleteAll()

    override fun refreshData(item: KeyResult): Single<Long> =
        dao.insert(item.toEntity())
}