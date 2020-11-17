package com.boltic28.taskmanager.datalayer.room.idea

import androidx.room.*
import io.reactivex.Single

@Dao
interface IdeaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ideaEntity: IdeaEntity): Single<Long>

    @Update
    fun update(ideaEntity: IdeaEntity): Single<Int>

    @Delete
    fun delete(ideaEntity: IdeaEntity): Single<Int>

    @Query("DELETE FROM idea")
    fun deleteAll(): Single<Int>

    @Query("SELECT * FROM idea WHERE id = :id ORDER BY id")
    fun getById(id: Long): Single<IdeaEntity>

    @Query("SELECT * FROM idea")
    fun getAll(): Single<List<IdeaEntity>>

    @Query("SELECT * FROM idea WHERE stepId = 0 AND keyId = 0 AND goalId = 0")
    fun getAllFree(): Single<List<IdeaEntity>>

    @Query("SELECT * FROM idea WHERE stepId = :stepId")
    fun getAllForStep(stepId: Long): Single<List<IdeaEntity>>

    @Query("SELECT * FROM idea WHERE goalId = :goalId")
    fun getAllForGoal(goalId: Long): Single<List<IdeaEntity>>

    @Query("SELECT * FROM idea WHERE keyId = :keyId")
    fun getAllForKey(keyId: Long): Single<List<IdeaEntity>>
}