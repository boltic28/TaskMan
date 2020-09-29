package com.boltic28.taskmanager.datalayer.room.step

import androidx.room.*
import io.reactivex.Single

@Dao
interface StepDao {
    @Insert
    fun insert(stepEntity: StepEntity): Single<Long>

    @Update
    fun update(stepEntity: StepEntity): Single<Int>

    @Delete
    fun delete(stepEntity: StepEntity): Single<Int>

    @Transaction
    @Query("SELECT * FROM step WHERE id = :id ORDER BY id")
    fun readById(id: Long): Single<StepEntity>

    @Query("SELECT * FROM step")
    fun readAll(): Single<List<StepEntity>>

    @Transaction
    @Query("SELECT * FROM step WHERE goalId = :goalId")
    fun readAllForGoal(goalId: Long): Single<List<StepEntity>>

    @Transaction
    @Query("SELECT * FROM step WHERE goalId = :goalId")
    fun readAllForKey(goalId: Long): Single<List<StepEntity>>
}