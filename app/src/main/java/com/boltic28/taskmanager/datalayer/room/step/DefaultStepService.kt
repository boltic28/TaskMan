package com.boltic28.taskmanager.datalayer.room.step

import com.boltic28.taskmanager.datalayer.classes.Step
import io.reactivex.Single

class DefaultStepService(private val dao: StepDao) : StepService {
    override fun insert(item: Step): Single<Long> =
        dao.insert(item.toEntity())

    override fun readAll(): Single<List<Step>> =
        dao.readAll().map { list ->
            list.map { it.toStep() }
        }

    override fun readAllForGoal(goalId: Long): Single<List<Step>> =
        dao.readAllForGoal(goalId).map { list ->
            list.map { it.toStep() }
        }

    override fun readAllForKey(keyId: Long): Single<List<Step>> =
        dao.readAllForKey(keyId).map { list ->
            list.map { it.toStep() }
        }

    override fun readById(id: Long): Single<Step> =
        dao.readById(id).map { it.toStep() }

    override fun update(item: Step): Single<Int> =
        dao.update(item.toEntity())

    override fun delete(item: Step): Single<Int> =
        dao.delete(item.toEntity())
}

private fun Step.toEntity(): StepEntity =
    StepEntity(
        id = this.id,
        goalId = this.goalId,
        keyId = this.keyId,
        name = this.name,
        description = this.description,
        icon = this.icon,
        date = this.date,
        dateClose = this.dateClose,
        isDone = this.isDone,
        isStarted = this.isStarted
    )

private fun StepEntity.toStep(): Step =
    Step(
        id = this.id,
        goalId = this.goalId,
        keyId = this.keyId,
        name = this.name,
        description = this.description,
        icon = this.icon,
        date = this.date,
        dateClose = this.dateClose,
        isDone = this.isDone,
        isStarted = this.isStarted,
        tasks = emptyList()
    )