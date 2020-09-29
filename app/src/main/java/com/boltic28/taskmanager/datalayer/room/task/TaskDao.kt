package com.boltic28.taskmanager.datalayer.room.task

import androidx.room.*
import io.reactivex.Single

@Dao
interface TaskDao {

    @Insert
    fun insert(taskEntity: TaskEntity): Single<Long>

    @Update
    fun update(taskEntity: TaskEntity): Single<Int>

    @Delete
    fun delete(taskEntity: TaskEntity): Single<Int>

    @Query("SELECT * FROM task WHERE id = :id ORDER BY id")
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
}