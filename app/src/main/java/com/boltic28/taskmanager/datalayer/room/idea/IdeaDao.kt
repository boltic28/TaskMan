package com.boltic28.taskmanager.datalayer.room.idea

import androidx.room.*
import io.reactivex.Single

@Dao
interface IdeaDao {
    @Insert
    fun insert(ideaEntity: IdeaEntity): Single<Long>

    @Update
    fun update(ideaEntity: IdeaEntity): Single<Int>

    @Delete
    fun delete(ideaEntity: IdeaEntity): Single<Int>

    @Query("SELECT * FROM idea WHERE id = :id ORDER BY id")
    fun readById(id: Long): Single<IdeaEntity>

    @Query("SELECT * FROM idea WHERE stepId = :stepId")
    fun readAllForStep(stepId: Long): Single<List<IdeaEntity>>

    @Query("SELECT * FROM idea WHERE goalId = :goalId")
    fun readAllForGoal(goalId: Long): Single<List<IdeaEntity>>

    @Query("SELECT * FROM idea WHERE keyId = :keyId")
    fun readAllForKey(keyId: Long): Single<List<IdeaEntity>>
}