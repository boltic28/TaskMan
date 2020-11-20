package com.boltic28.taskmanager.ui.adapter

import com.boltic28.taskmanager.datalayer.entities.BaseItem

interface ElementManager {

    fun refreshData(list: List<BaseItem>)

    fun addElement(item: BaseItem)

    fun addList(list: List<BaseItem>)

    fun loadNewData(list: List<BaseItem>)

    fun getItems(): List<BaseItem>

    fun clearAll()
}