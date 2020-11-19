package com.boltic28.taskmanager.ui.adapter

import com.boltic28.taskmanager.datalayer.entities.BaseItem
import com.boltic28.taskmanager.datalayer.entities.CompletableItem
import java.time.LocalDateTime
import java.util.*

class FilterImpl(private val adapter: ItemAdapter) {

    private var listForFilter: List<BaseItem> = emptyList()

    fun updateData() {
        listForFilter = adapter.getItems()
    }

    fun clearFilter() {
        adapter.refreshData(listForFilter)
    }

    fun start(
        stringFilter: String,
        isDoneFilterOn: Boolean,
        isStartedFilterOn: Boolean,
        isFailedFilterOn: Boolean,
        isNotStartedFilterOn: Boolean,
        lastDateFilter: LocalDateTime
    ) {
        var result = listForFilter

        if (stringFilter.isNotEmpty()) {
            result = listForFilter.filter {
                it.name.toLowerCase(Locale.ROOT).contains(stringFilter) ||
                        it.description.toLowerCase(Locale.ROOT).contains(stringFilter)
            }
        }
        if (isDoneFilterOn) {
            result = result.filter { it is CompletableItem && it.isDone }
        }
        if (isStartedFilterOn) {
            result = result.filter { it is CompletableItem && it.isStarted }
        }
        if (isNotStartedFilterOn) {
            result = result.filter {
                (it is CompletableItem && !it.isStarted) || (it !is CompletableItem)
            }
        }
        if (isFailedFilterOn) {
            result = result.filter {
                it.dateClose.isBefore(LocalDateTime.now()) &&
                        (if (it is CompletableItem) !it.isDone else true)
            }
        }
        if (lastDateFilter != LocalDateTime.MAX) {
            result = result.filter { it.dateClose.isBefore(lastDateFilter) }
        }

        adapter.refreshData(result)
    }
}