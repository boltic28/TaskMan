package com.boltic28.taskmanager.businesslayer.usecases.idea

import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemUpdateUseCase
import com.boltic28.taskmanager.datalayer.entities.Idea
import com.boltic28.taskmanager.datalayer.room.idea.IdeaRepository
import io.reactivex.Single

class IdeaUpdateUseCase(
    private val repository: IdeaRepository
): ItemUpdateUseCase<Idea> {

    override fun update(item: Idea): Single<Int> =
        repository.update(item)
}