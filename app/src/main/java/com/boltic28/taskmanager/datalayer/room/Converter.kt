package com.boltic28.taskmanager.datalayer.room

import androidx.room.TypeConverter
import com.boltic28.taskmanager.datalayer.Cycle
import java.time.LocalDateTime


class Converter {
    @TypeConverter
    fun dateToLong(date: LocalDateTime): String = date.toString()

    @TypeConverter
    fun longToDate(time: String): LocalDateTime = LocalDateTime.parse(time)

    @TypeConverter
    fun cycleToString(cycle: Cycle): String = cycle.value

    @TypeConverter
    fun stringToCycle(cycle: String): Cycle = Cycle.valueOf(cycle)
}