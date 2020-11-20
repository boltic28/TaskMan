package com.boltic28.taskmanager.ui.adapter.controllers

import android.view.View
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.entities.Step
import com.boltic28.taskmanager.ui.adapter.DefaultViewHolder
import com.boltic28.taskmanager.ui.constant.NO_ID
import kotlin.reflect.KClass

class StepSmallViewController : BaseSmallItemController() {

    override fun getType(): Int = R.layout.item_small_step

    override fun getItemType(): KClass<*> = Step::class

    override fun bind(holder: DefaultViewHolder, item: Any) {
        item as Step
        val itemView: View = holder.itemView
        val isAttached = (item.goalId != NO_ID) || (item.keyId != NO_ID)

        setActionButton(itemView, item, isAttached, listener.isNeedToShowConnection())
        fillBaseFiled(itemView, item)
        setOnItemClick(itemView, item)
        setStatus(itemView, item.isStarted, item.isDone)
    }
}