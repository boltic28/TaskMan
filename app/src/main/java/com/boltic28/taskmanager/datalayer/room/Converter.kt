package com.boltic28.taskmanager.datalayer.room

import androidx.room.TypeConverter
import com.boltic28.taskmanager.datalayer.Cycle
import com.boltic28.taskmanager.datalayer.Progress
import java.time.LocalDateTime


class Converter {
    @TypeConverter
    fun dateToLong(date: LocalDateTime): String = date.toString()

    @TypeConverter
    fun longToDate(time: String): LocalDateTime = LocalDateTime.parse(time)

    @TypeConverter
    fun cycleToString(cycle: Cycle): String = cycle.value

    @TypeConverter
    fun stringToCycle(cycle: String): Cycle = Cycle.fromString(cycle)

    @TypeConverter
    fun progressToInt(progress: Progress): Int = progress.value

    @TypeConverter
    fun intToProgress(progress: Int): Progress = Progress.fromInteger(progress)
}