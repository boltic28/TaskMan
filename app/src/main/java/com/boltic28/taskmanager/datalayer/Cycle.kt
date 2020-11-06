package com.boltic28.taskmanager.datalayer

import com.boltic28.taskmanager.datalayer.entities.Task
import java.time.LocalDateTime

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

        fun increasedPeriod(task: Task): LocalDateTime =
            when(task.cycle){
                NOT_CYCLE -> {task.dateClose}
                HOUR -> {task.dateClose.plusHours(1)}
                DAY -> {task.dateClose.plusDays(1)}
                WEEK -> {task.dateClose.plusWeeks(1)}
                MONTH -> {task.dateClose.plusMonths(1)}
                YEAR -> {task.dateClose.plusYears(1)}
            }
    }
}