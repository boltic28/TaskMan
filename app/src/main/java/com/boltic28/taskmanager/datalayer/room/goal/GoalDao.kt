package com.boltic28.taskmanager.datalayer.room.goal

import android.database.Cursor
import androidx.room.*
import io.reactivex.Single

@Dao
interface GoalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(goalEntity: GoalEntity): Single<Long>

    @Update
    fun update(goalEntity: GoalEntity): Single<Int>

    @Delete
    fun delete(goalEntity: GoalEntity): Single<Int>

    @Query("DELETE FROM goal")
    fun deleteAll(): Single<Int>

    @Transaction
    @Query("SELECT * FROM goal WHERE id = :id")
    fun getById(id: Long): Single<GoalEntity>

    @Transaction
    @Query("SELECT * FROM goal")
    fun getAll(): Single<List<GoalEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFromContentProvider(entity: GoalEntity): Long

    @Update
    fun updateFromContentProvider(goalEntity: GoalEntity): Int

    @Query("DELETE FROM goal WHERE id = :id")
    fun deleteByIdFromContentProvider(id: Long): Int

    @Query("DELETE FROM goal")
    fun deleteAllFromContentProvider(): Int

    @Query("SELECT * FROM goal")
    fun readAllForContentProvider(): Cursor

    @Query("SELECT * FROM goal WHERE id = :id")
    fun readByIdForContentProvider(id: Long): Cursor
}