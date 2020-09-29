package com.boltic28.taskmanager.datalayer.room.goal

import androidx.room.*
import io.reactivex.Single

@Dao
interface GoalDao {

    @Insert
    fun insert(goalEntity: GoalEntity): Single<Long>

    @Update
    fun update(goalEntity: GoalEntity): Single<Int>

    @Delete
    fun delete(goalEntity: GoalEntity): Single<Int>

    @Transaction
    @Query("SELECT * FROM goal WHERE id = :id ORDER BY id")
    fun getById(id: Long): Single<GoalEntity>

    @Transaction
    @Query("SELECT * FROM goal")
    fun getAll(): Single<List<GoalEntity>>
}