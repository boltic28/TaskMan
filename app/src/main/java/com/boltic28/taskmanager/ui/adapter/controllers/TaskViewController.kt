package com.boltic28.taskmanager.ui.adapter.controllers

import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.classes.Task
import com.boltic28.taskmanager.ui.adapter.DefaultViewHolder
import kotlin.reflect.KClass

class TaskViewController: HolderController() {
    override fun getType(): Int = R.layout.item_task

    override fun getItemType(): KClass<*> = Task::class

    override fun bind(holder: DefaultViewHolder, item: Any) {
        TODO("Not yet implemented")
    }
}