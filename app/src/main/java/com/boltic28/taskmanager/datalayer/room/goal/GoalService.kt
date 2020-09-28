package com.boltic28.taskmanager.datalayer.room.goal

import androidx.room.Dao
import com.boltic28.taskmanager.datalayer.dto.Goal
import com.boltic28.taskmanager.datalayer.room.DBService
import io.reactivex.Single

class GoalService: DBService<Goal> {
    override fun create(item: Goal): Single<Long> {
        TODO("Not yet implemented")
    }

    override fun readAll(): Single<List<Goal>> {
        TODO("Not yet implemented")
    }

    override fun readById(id: Long): Single<Goal> {
        TODO("Not yet implemented")
    }

    override fun update(item: Goal): Single<Int> {
        TODO("Not yet implemented")
    }

    override fun delete(item: Goal): Single<Int> {
        TODO("Not yet implemented")
    }
}