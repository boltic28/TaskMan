package com.boltic28.taskmanager.datalayer

enum class Progress(val value: Int) {
    PROGRESS_0(0),
    PROGRESS_20(20),
    PROGRESS_40(40),
    PROGRESS_60(60),
    PROGRESS_80(80),
    DONE(100);

    companion object {
        private val map = values().associateBy(Progress::value)
        fun fromInteger(type: Int): Progress = map[type]?: PROGRESS_0
    }
}