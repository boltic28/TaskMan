package com.boltic28.taskmanager.datalayer.room.step

import com.boltic28.taskmanager.datalayer.dto.Step
import com.boltic28.taskmanager.datalayer.room.DBService
import io.reactivex.Single

class StepService: DBService<Step> {
    override fun create(item: Step): Single<Long> {
        TODO("Not yet implemented")
    }

    override fun readAll(): Single<List<Step>> {
        TODO("Not yet implemented")
    }

    override fun readById(id: Long): Single<Step> {
        TODO("Not yet implemented")
    }

    override fun update(item: Step): Single<Int> {
        TODO("Not yet implemented")
    }

    override fun delete(item: Step): Single<Int> {
        TODO("Not yet implemented")
    }
}