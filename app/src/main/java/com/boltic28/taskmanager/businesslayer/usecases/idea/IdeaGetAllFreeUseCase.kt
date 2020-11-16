package com.boltic28.taskmanager.businesslayer.usecases.idea

import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemGetAllFreeUseCase
import com.boltic28.taskmanager.datalayer.entities.Idea
import com.boltic28.taskmanager.datalayer.room.idea.IdeaRepository
import io.reactivex.Single

class IdeaGetAllFreeUseCase(
    private val repository: IdeaRepository
): ItemGetAllFreeUseCase<Idea> {

    override fun getAllFree(): Single<List<Idea>> =
        repository.getAllFree()
}