package com.boltic28.taskmanager.businesslayer.usecases.idea

import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemDeleteUseCase
import com.boltic28.taskmanager.datalayer.entities.Idea
import com.boltic28.taskmanager.datalayer.room.idea.IdeaRepository
import io.reactivex.Single

class IdeaDeleteUseCase(
    private val repository: IdeaRepository
): ItemDeleteUseCase<Idea> {

    override fun delete(item: Idea): Single<Int> =
        repository.delete(item)
}