package com.boltic28.taskmanager.datalayer.room.idea

import com.boltic28.taskmanager.datalayer.entities.Idea
import com.boltic28.taskmanager.datalayer.firebaseworker.dto.RemoteRepo
import io.reactivex.Single

class DefaultIdeaRepository(private val dao: IdeaDao, private val remoteDB: RemoteRepo<Idea>) :
    IdeaRepository {
    override fun insert(item: Idea): Single<Long> =
        dao.insert(item.toEntity())
            .doOnSuccess { remoteDB.create(item) }

    override fun getAll(): Single<List<Idea>> =
        dao.getAll().map { list ->
            list.map { it.toIdea() }
        }

    override fun getAllFree(): Single<List<Idea>> =
        dao.getAllFree().map { list ->
            list.map { it.toIdea() }
        }

    override fun getAllForStep(stepId: Long): Single<List<Idea>> =
        dao.getAllForStep(stepId).map { list ->
            list.map { it.toIdea() }
        }

    override fun getAllForGoal(goalId: Long): Single<List<Idea>> =
        dao.getAllForGoal(goalId).map { list ->
            list.map { it.toIdea() }
        }

    override fun getAllForKey(keyId: Long): Single<List<Idea>> =
        dao.getAllForKey(keyId).map { list ->
            list.map { it.toIdea() }
        }

    override fun getById(id: Long): Single<Idea> =
        dao.getById(id).map { it.toIdea() }

    override fun update(item: Idea): Single<Int> =
        dao.update(item.toEntity())
            .doOnSuccess { remoteDB.create(item) }

    override fun delete(item: Idea): Single<Int> =
        dao.delete(item.toEntity())
            .doOnSuccess { remoteDB.delete(item) }

    override fun deleteAll(): Single<Int> =
        dao.deleteAll()

    override fun refreshData(item: Idea): Single<Long> =
        dao.insert(item.toEntity())
}