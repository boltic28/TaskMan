package com.boltic28.taskmanager.businesslayer.usecases.idea

import com.boltic28.taskmanager.businesslayer.usecases.interfaces.ItemCreateUseCase
import com.boltic28.taskmanager.datalayer.entities.Idea
import com.boltic28.taskmanager.datalayer.room.idea.IdeaRepository
import io.reactivex.Single

class IdeaCreateUseCase (
    private val repository: IdeaRepository
): ItemCreateUseCase<Idea> {

    override fun create(item: Idea): Single<Long> =
        repository.insert(item)
}