package com.boltic28.taskmanager.businesslayer.usecases.idea

import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemGetAllUseCase
import com.boltic28.taskmanager.datalayer.entities.Idea
import com.boltic28.taskmanager.datalayer.room.idea.IdeaRepository
import io.reactivex.Single

class IdeaGetAllUseCase(
    private val repository: IdeaRepository
): ItemGetAllUseCase<Idea> {

    override fun getAll(): Single<List<Idea>> =
        repository.getAll()
}