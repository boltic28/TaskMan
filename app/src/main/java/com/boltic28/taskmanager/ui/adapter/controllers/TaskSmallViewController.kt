package com.boltic28.taskmanager.ui.adapter.controllers

import android.view.View
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.entities.Task
import com.boltic28.taskmanager.ui.adapter.DefaultViewHolder
import com.boltic28.taskmanager.ui.constant.NO_ID
import kotlin.reflect.KClass

class TaskSmallViewController : BaseSmallItemController() {

    override fun getType(): Int = R.layout.item_small_task

    override fun getItemType(): KClass<*> = Task::class

    override fun bind(holder: DefaultViewHolder, item: Any) {
        item as Task
        val itemView: View = holder.itemView
        val isAttached = item.goalId != NO_ID || item.keyId != NO_ID || item.stepId != NO_ID

        setActionButton(itemView, item, isAttached, listener.isNeedToShowConnection())
        fillBaseFiled(itemView, item)
        setOnItemClick(itemView, item)
        setStatus(itemView, item.isStarted, item.isDone)
    }
}