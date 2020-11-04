package com.boltic28.taskmanager.ui.adapter

interface ElementManager {

    fun refreshData(list: List<Any>)

    fun addElement(item: Any)

    fun addList(list: List<Any>)

    fun clearAll()
}