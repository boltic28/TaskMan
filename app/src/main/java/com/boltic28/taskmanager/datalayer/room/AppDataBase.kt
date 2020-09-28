package com.boltic28.taskmanager.datalayer.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.boltic28.taskmanager.datalayer.room.goal.GoalDao
import com.boltic28.taskmanager.datalayer.room.goal.GoalEntity
import com.boltic28.taskmanager.datalayer.room.idea.IdeaDao
import com.boltic28.taskmanager.datalayer.room.idea.IdeaEntity
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyDao
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyEntity
import com.boltic28.taskmanager.datalayer.room.step.StepDao
import com.boltic28.taskmanager.datalayer.room.step.StepEntity
import com.boltic28.taskmanager.datalayer.room.task.TaskDao
import com.boltic28.taskmanager.datalayer.room.task.TaskEntity

@Database(
    entities = [GoalEntity::class, IdeaEntity::class, StepEntity::class, TaskEntity::class, KeyEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class AppDataBase: RoomDatabase() {

    companion object{
        const val DB_NAME = "task_db"
    }

    abstract fun ideaDao(): IdeaDao
    abstract fun taskDao(): TaskDao
    abstract fun stepDao(): StepDao
    abstract fun goalDao(): GoalDao
    abstract fun keyDao(): KeyDao
}