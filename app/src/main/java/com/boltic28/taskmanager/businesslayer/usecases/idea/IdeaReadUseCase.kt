package com.boltic28.taskmanager.businesslayer.usecases.idea

import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemReadUseCase
import com.boltic28.taskmanager.datalayer.entities.Idea
import com.boltic28.taskmanager.datalayer.room.idea.IdeaRepository
import io.reactivex.Single

class IdeaReadUseCase(
    private val repository: IdeaRepository
) : ItemReadUseCase<Idea> {

    override fun getItemById(id: Long): Single<Idea> =
        repository.getById(id)
}