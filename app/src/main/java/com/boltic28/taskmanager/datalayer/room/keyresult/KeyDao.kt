package com.boltic28.taskmanager.datalayer.room.keyresult

import android.database.Cursor
import androidx.room.*
import com.boltic28.taskmanager.datalayer.room.step.StepEntity
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
    @Query("SELECT * FROM key_entity WHERE id = :id")
    fun getById(id: Long): Single<KeyEntity>

    @Query("SELECT * FROM key_entity")
    fun getAll(): Single<List<KeyEntity>>

    @Query("SELECT * FROM key_entity WHERE goalId = 0")
    fun getAllFree(): Single<List<KeyEntity>>

    @Transaction
    @Query("SELECT * FROM key_entity WHERE goalId = :goalId")
    fun getAllForGoal(goalId: Long): Single<List<KeyEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFromContentProvider(entity: KeyEntity): Long

    @Update
    fun updateFromContentProvider(entity: KeyEntity): Int

    @Query("DELETE FROM key_entity WHERE id = :id")
    fun deleteByIdFromContentProvider(id: Long): Int

    @Query("DELETE FROM key_entity")
    fun deleteAllFromContentProvider(): Int

    @Query("SELECT * FROM key_entity")
    fun readAllForContentProvider(): Cursor

    @Query("SELECT * FROM key_entity WHERE id = :id")
    fun readByIdForContentProvider(id: Long): Cursor
}