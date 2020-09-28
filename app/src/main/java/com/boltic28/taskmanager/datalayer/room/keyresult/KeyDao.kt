package com.boltic28.taskmanager.datalayer.room.keyresult

import androidx.room.*
import io.reactivex.Single

@Dao
interface KeyDao {

    @Insert
    fun insert(keyEntity: KeyEntity): Single<Long>

    @Update
    fun update(keyEntity: KeyEntity): Single<Int>

    @Delete
    fun delete(keyEntity: KeyEntity): Single<Int>

    @Transaction
    @Query("SELECT * FROM key_entity WHERE id = :id ORDER BY id")
    fun readById(id: Long): Single<KeyWithSteps>

    @Transaction
    @Query("SELECT * FROM key_entity WHERE goalId = :goalId")
    fun readAllForGoal(goalId: Long): Single<List<KeyWithSteps>>
}