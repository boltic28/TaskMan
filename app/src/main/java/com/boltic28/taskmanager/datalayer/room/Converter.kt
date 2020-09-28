package com.boltic28.taskmanager.datalayer.room

import androidx.room.TypeConverter
import java.time.LocalDateTime


class Converter {
    @TypeConverter
    fun dateToLong(date: LocalDateTime): String = date.toString()

    @TypeConverter
    fun longToDate(time: String): LocalDateTime = LocalDateTime.parse(time)


}