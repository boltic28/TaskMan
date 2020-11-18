package com.boltic28.taskmanager.ui.adapter

import com.boltic28.taskmanager.datalayer.entities.BaseItem

interface Filter {

    var listForFilter: List<BaseItem>

    fun updateListForFilter()

    fun filter(str: String)

    fun clearFilter()
}