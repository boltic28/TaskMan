package com.boltic28.taskmanager.ui.adapter.controllers

import android.view.View
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.entities.Goal
import com.boltic28.taskmanager.ui.adapter.DefaultViewHolder
import kotlin.reflect.KClass

class GoalViewController : ParentItemController() {

    override fun getType(): Int = R.layout.item_goal

    override fun getItemType(): KClass<*> = Goal::class

    override fun bind(holder: DefaultViewHolder, item: Any) {
        item as Goal
        val itemView: View = holder.itemView

        fillBaseField(itemView, item)
        fillProgress(itemView, item)
        setStatusContainer(itemView, item)
        setOnItemClickListener(itemView, item)
    }
}