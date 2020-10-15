package com.boltic28.taskmanager.businesslayer

import com.boltic28.taskmanager.datalayer.entities.Idea
import com.boltic28.taskmanager.datalayer.room.idea.IdeaRepository
import io.reactivex.Single

class IdeaInteractorImpl(
    private val ideaRepository: IdeaRepository
): IdeaInteractor {
    override fun insert(item: Idea): Single<Long> {
        TODO("Not yet implemented")
    }

    override fun update(item: Idea): Single<Int> {
        TODO("Not yet implemented")
    }

    override fun delete(item: Idea): Single<Int> {
        TODO("Not yet implemented")
    }

    override fun getById(id: Long): Single<Idea> {
        TODO("Not yet implemented")
    }

    override fun getAll(): Single<List<Idea>> {
        TODO("Not yet implemented")
    }

    override fun getAllFree(): Single<List<Idea>> {
        TODO("Not yet implemented")
    }

    override fun getAllForStep(stepId: Long): Single<List<Idea>> {
        TODO("Not yet implemented")
    }

    override fun getAllForGoal(goalId: Long): Single<List<Idea>> {
        TODO("Not yet implemented")
    }

    override fun getAllForKey(keyId: Long): Single<List<Idea>> {
        TODO("Not yet implemented")
    }
}