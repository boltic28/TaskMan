package com.boltic28.taskmanager.datalayer.room.step

import com.boltic28.taskmanager.datalayer.entities.Step
import io.reactivex.Single

class DefaultStepRepository(private val dao: StepDao) : StepRepository {

    override fun insert(item: Step): Single<Long> =
        dao.insert(item.toEntity())

    override fun getAll(): Single<List<Step>> =
        dao.getAll().map { list ->
            list.map { it.toStep() }
        }

    override fun getAllForGoal(goalId: Long): Single<List<Step>> =
        dao.getAllForGoal(goalId).map { list ->
            list.map { it.toStep() }
        }

    override fun getAllFree(): Single<List<Step>> =
        dao.getAllFree().map { list ->
            list.map { it.toStep() }
        }

    override fun getAllForKey(keyId: Long): Single<List<Step>> =
        dao.getAllForKey(keyId).map { list ->
            list.map { it.toStep() }
        }

    override fun getById(id: Long): Single<Step> =
        dao.getById(id).map { it.toStep() }

    override fun update(item: Step): Single<Int> =
        dao.update(item.toEntity())

    override fun delete(item: Step): Single<Int> =
        dao.delete(item.toEntity())
}