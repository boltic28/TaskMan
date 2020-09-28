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

    @Query("SELECT * FROM task WHERE stepId = :ownerId")
    fun readAllForOwner(ownerId: Long): Single<List<TaskEntity>>
}