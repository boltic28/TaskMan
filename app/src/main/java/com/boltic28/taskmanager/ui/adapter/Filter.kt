package com.boltic28.taskmanager.ui.adapter

import java.time.LocalDateTime

interface Filter {

    fun filterData(stringFilter: String,
                   isDoneFilterOn: Boolean,
                   isStartedFilterOn: Boolean,
                   isFailedFilterOn: Boolean ,
                   isNotStartedFilterOn: Boolean,
                   lastDateFilter: LocalDateTime
    )

    fun clearFilter()
}
