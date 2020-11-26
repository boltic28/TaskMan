package com.boltic28.taskmanager.datalayer.room.task

import android.database.Cursor
import androidx.room.*
import io.reactivex.Single

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(taskEntity: TaskEntity): Single<Long>

    @Update
    fun update(taskEntity: TaskEntity): Single<Int>

    @Delete
    fun delete(taskEntity: TaskEntity): Single<Int>

    @Query("DELETE FROM task")
    fun deleteAll(): Single<Int>

    @Query("SELECT * FROM task WHERE id = :id")
    fun getById(id: Long): Single<TaskEntity>

    @Query("SELECT * FROM task")
    fun getAll(): Single<List<TaskEntity>>

    @Query("SELECT * FROM task WHERE stepId = 0 AND keyId = 0 AND goalId = 0")
    fun getAllFree(): Single<List<TaskEntity>>

    @Query("SELECT * FROM task WHERE stepId = :stepId")
    fun getAllForStep(stepId: Long): Single<List<TaskEntity>>

    @Query("SELECT * FROM task WHERE goalId = :goalId")
    fun getAllForGoal(goalId: Long): Single<List<TaskEntity>>

    @Query("SELECT * FROM task WHERE keyId = :keyId")
    fun getAllForKey(keyId: Long): Single<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFromContentProvider(entity: TaskEntity): Long

    @Update
    fun updateFromContentProvider(entity: TaskEntity): Int

    @Query("DELETE FROM task WHERE id = :id")
    fun deleteByIdFromContentProvider(id: Long): Int

    @Query("DELETE FROM task")
    fun deleteAllFromContentProvider(): Int

    @Query("SELECT * FROM task")
    fun readAllForContentProvider(): Cursor

    @Query("SELECT * FROM task WHERE id = :id")
    fun readByIdForContentProvider(id: Long): Cursor
}