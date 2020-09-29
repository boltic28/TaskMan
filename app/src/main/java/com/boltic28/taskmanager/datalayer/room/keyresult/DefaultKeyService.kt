package com.boltic28.taskmanager.datalayer.room.keyresult

import com.boltic28.taskmanager.datalayer.classes.KeyResult
import io.reactivex.Single

class DefaultKeyService(private val dao: KeyDao) : KeyService {
    override fun insert(item: KeyResult): Single<Long> =
        dao.insert(item.toEntity())

    override fun readAll(): Single<List<KeyResult>> =
        dao.readAll().map { list ->
            list.map { it.toKey() }
        }

    override fun readAllForGoal(goalId: Long): Single<List<KeyResult>> =
        dao.readAllForGoal(goalId).map { list ->
            list.map { it.toKey() }
        }

    override fun readById(id: Long): Single<KeyResult> =
        dao.readById(id).map { it.toKey() }

    override fun update(item: KeyResult): Single<Int> =
        dao.update(item.toEntity())

    override fun delete(item: KeyResult): Single<Int> =
        dao.delete(item.toEntity())
}

private fun KeyResult.toEntity(): KeyEntity =
    KeyEntity(
        id = this.id,
        goalId = this.goalId,
        name = this.name,
        description = this.description,
        date = this.date,
        progress = this.progress
    )

private fun KeyEntity.toKey(): KeyResult =
    KeyResult(
        id = this.id,
        goalId = this.goalId,
        name = this.name,
        description = this.description,
        date = this.date,
        progress = this.progress,
        steps = emptyList(),
        tasks = emptyList(),
        ideas = emptyList()
    )