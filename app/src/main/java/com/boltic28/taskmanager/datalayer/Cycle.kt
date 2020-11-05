package com.boltic28.taskmanager.datalayer

enum class Cycle(val value: String) {
    NOT_CYCLE("not cycle"),
    HOUR("once an hour"),
    DAY("once a day"),
    WEEK("once a week"),
    MONTH("once a month"),
    YEAR("once an year");

    companion object {
        private val map = values().associateBy(Cycle::value)
        fun fromString(type: String): Cycle = map[type]?: NOT_CYCLE
    }
}