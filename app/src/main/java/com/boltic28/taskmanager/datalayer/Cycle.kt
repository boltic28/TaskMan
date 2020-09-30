package com.boltic28.taskmanager.datalayer

enum class Cycle(val value: String) {
    HOUR("hour"),
    DAY("day"),
    WEEK("week"),
    MONTH("month"),
    YEAR("year");

    companion object {
        private val map = values().associateBy(Cycle::value)
        fun fromString(type: String): Cycle = map[type]?: DAY
    }
}