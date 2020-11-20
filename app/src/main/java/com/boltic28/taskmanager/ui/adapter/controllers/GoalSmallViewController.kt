package com.boltic28.taskmanager.ui.adapter.controllers

import android.view.View
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.entities.Goal
import com.boltic28.taskmanager.ui.adapter.DefaultViewHolder
import kotlin.reflect.KClass

class GoalSmallViewController : BaseSmallItemController() {

    override fun getType(): Int = R.layout.item_small_goal

    override fun getItemType(): KClass<*> = Goal::class

    override fun bind(holder: DefaultViewHolder, item: Any) {
        item as Goal
        val itemView: View = holder.itemView

        fillBaseFiled(itemView, item)
        setOnItemClick(itemView, item)
        setActionButton(itemView, item, isAttached = false, listener.isNeedToShowConnection())
        setStatus(itemView, item.isStarted, item.isDone)
    }
}