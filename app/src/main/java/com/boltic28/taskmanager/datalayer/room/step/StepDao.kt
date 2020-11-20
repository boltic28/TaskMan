package com.boltic28.taskmanager.datalayer.room.step

import androidx.room.*
import io.reactivex.Single

@Dao
interface StepDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(stepEntity: StepEntity): Single<Long>

    @Update
    fun update(stepEntity: StepEntity): Single<Int>

    @Delete
    fun delete(stepEntity: StepEntity): Single<Int>

    @Query("DELETE FROM step")
    fun deleteAll(): Single<Int>

    @Transaction
    @Query("SELECT * FROM step WHERE id = :id ORDER BY id")
    fun getById(id: Long): Single<StepEntity>

    @Query("SELECT * FROM step")
    fun getAll(): Single<List<StepEntity>>

    @Query("SELECT * FROM step WHERE keyId = 0 AND goalId = 0")
    fun getAllFree(): Single<List<StepEntity>>

    @Transaction
    @Query("SELECT * FROM step WHERE goalId = :goalId")
    fun getAllForGoal(goalId: Long): Single<List<StepEntity>>

    @Transaction
    @Query("SELECT * FROM step WHERE goalId = :goalId")
    fun getAllForKey(goalId: Long): Single<List<StepEntity>>
}