package com.boltic28.taskmanager.datalayer.room.idea

import com.boltic28.taskmanager.datalayer.dto.Idea
import com.boltic28.taskmanager.datalayer.room.DBService
import io.reactivex.Single

class IdeaService: DBService<Idea> {
    override fun create(item: Idea): Single<Long> {
        TODO("Not yet implemented")
    }

    override fun readAll(): Single<List<Idea>> {
        TODO("Not yet implemented")
    }

    override fun readById(id: Long): Single<Idea> {
        TODO("Not yet implemented")
    }

    override fun update(item: Idea): Single<Int> {
        TODO("Not yet implemented")
    }

    override fun delete(item: Idea): Single<Int> {
        TODO("Not yet implemented")
    }
}