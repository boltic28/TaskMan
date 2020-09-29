package com.boltic28.taskmanager.datalayer.room.idea

import com.boltic28.taskmanager.datalayer.classes.Idea
import io.reactivex.Single

class DefaultIdeaService(private val dao: IdeaDao) : IdeaService {
    override fun insert(item: Idea): Single<Long> =
        dao.insert(item.toEntity())

    override fun readAll(): Single<List<Idea>> =
        dao.readAll().map { list ->
            list.map { it.toIdea() }
        }

    override fun readAllFree(): Single<List<Idea>> =
        dao.readAllFree().map { list ->
            list.map { it.toIdea() }
        }

    override fun readAllForStep(stepId: Long): Single<List<Idea>> =
        dao.readAllForStep(stepId).map { list ->
            list.map { it.toIdea() }
        }

    override fun readAllForGoal(goalId: Long): Single<List<Idea>> =
        dao.readAllForGoal(goalId).map { list ->
            list.map { it.toIdea() }
        }

    override fun readAllForKey(keyId: Long): Single<List<Idea>> =
        dao.readAllForKey(keyId).map { list ->
            list.map { it.toIdea() }
        }

    override fun readById(id: Long): Single<Idea> =
        dao.readById(id).map { it.toIdea() }

    override fun update(item: Idea): Single<Int> =
        dao.update(item.toEntity())

    override fun delete(item: Idea): Single<Int> =
        dao.delete(item.toEntity())
}

private fun Idea.toEntity(): IdeaEntity =
    IdeaEntity(
        id = this.id,
        stepId = this.stepId,
        keyId = this.keyId,
        goalId = this.goalId,
        name = this.name,
        description = this.description,
        icon = this.icon,
        date = this.date
    )

private fun IdeaEntity.toIdea(): Idea =
    Idea(
        id = this.id,
        stepId = this.stepId,
        keyId = this.keyId,
        goalId = this.goalId,
        name = this.name,
        description = this.description,
        icon = this.icon,
        date = this.date
    )