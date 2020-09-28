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
    fun readById(id: Long): Single<TaskEntity>

    @Query("SELECT * FROM task")
    fun readAll(): Single<List<TaskEntity>>

    @Query("SELECT * FROM task WHERE stepId = 0 AND keyId = 0 AND goalId = 0")
    fun readAllFree(): Single<List<TaskEntity>>

    @Query("SELECT * FROM task WHERE stepId = :stepId")
    fun readAllForStep(stepId: Long): Single<List<TaskEntity>>

    @Query("SELECT * FROM task WHERE goalId = :goalId")
    fun readAllForGoal(goalId: Long): Single<List<TaskEntity>>

    @Query("SELECT * FROM task WHERE keyId = :keyId")
    fun readAllForKey(keyId: Long): Single<List<TaskEntity>>
}