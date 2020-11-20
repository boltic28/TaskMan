package com.boltic28.taskmanager.datalayer.room.keyresult

import androidx.room.*
import io.reactivex.Single

@Dao
interface KeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(keyEntity: KeyEntity): Single<Long>

    @Update
    fun update(keyEntity: KeyEntity): Single<Int>

    @Delete
    fun delete(keyEntity: KeyEntity): Single<Int>

    @Query("DELETE FROM key_entity")
    fun deleteAll(): Single<Int>

    @Transaction
    @Query("SELECT * FROM key_entity WHERE id = :id ORDER BY id")
    fun getById(id: Long): Single<KeyEntity>

    @Query("SELECT * FROM key_entity")
    fun getAll(): Single<List<KeyEntity>>

    @Query("SELECT * FROM key_entity WHERE goalId = 0")
    fun getAllFree(): Single<List<KeyEntity>>

    @Transaction
    @Query("SELECT * FROM key_entity WHERE goalId = :goalId")
    fun getAllForGoal(goalId: Long): Single<List<KeyEntity>>
}