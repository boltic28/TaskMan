package com.boltic28.taskmanager.datalayer.room.keyresult

import androidx.room.*
import com.boltic28.taskmanager.datalayer.room.task.TaskEntity
import io.reactivex.Single

@Dao
interface KeyDao {

    @Insert
    fun insert(key: KeyEntity): Single<Long>

    @Update
    fun update(key: KeyEntity): Single<Int>

    @Delete
    fun delete(key: KeyEntity): Single<Int>

    @Transaction
    @Query("SELECT * FROM task WHERE id = :id ORDER BY id")
    fun readById(id: Long): Single<KeyWithSteps>

    @Transaction
    @Query("SELECT * FROM task WHERE stepId = :ownerId")
    fun readAllForOwner(ownerId: Long): Single<List<KeyWithSteps>>
}