package com.boltic28.taskmanager.ui.adapter.controllers

import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.Cycle
import com.boltic28.taskmanager.datalayer.entities.Task
import com.boltic28.taskmanager.ui.adapter.DefaultViewHolder
import com.boltic28.taskmanager.ui.constant.NO_ID
import java.time.LocalDateTime
import kotlin.reflect.KClass

class TaskViewController : BaseItemController() {
    override fun getType(): Int = R.layout.item_task

    override fun getItemType(): KClass<*> = Task::class

    override fun bind(holder: DefaultViewHolder, item: Any) {
        item as Task
        val itemView: View = holder.itemView

        fillBaseField(itemView, item)
        setStatusContainer(itemView, item)
        setActionButton(itemView, item)
        setOnItemClickListener(itemView, item)
    }

    private fun setActionButton(itemView: View, item: Task) {
        val isDone: ImageView = itemView.findViewById(R.id.item_status_icon)
        val action: Button = itemView.findViewById(R.id.item_button_action)
        action.visibility = View.VISIBLE
        action.text = if (item.isStarted) {
            itemView.resources.getString(R.string.done_it)
        } else {
            itemView.resources.getString(R.string.start_it)
        }
        if (item.isDone) {
            isDone.visibility = View.VISIBLE
            action.isEnabled = false
        } else {
            isDone.visibility = View.INVISIBLE
            action.isEnabled = true
        }
        action.setOnClickListener {
            listener.onActionButtonClick(item)
        }
    }

    private fun setStatusContainer(itemView: View, item: Task) {

        val statusCycling: ImageView = itemView.findViewById(R.id.item_status_cycle)
        val statusIsAttached: ImageView = itemView.findViewById(R.id.item_status_linked)
        val statusAttention: ImageView = itemView.findViewById(R.id.item_status_attention)
        val statusStarted: ImageView = itemView.findViewById(R.id.item_status_started)
        val statusDone: ImageView = itemView.findViewById(R.id.item_status_done)

        statusCycling.visibility = View.VISIBLE
        statusCycling.setColorFilter(
            if (item.cycle == Cycle.NOT_CYCLE) {
                itemView.context.getColor(R.color.colorStatusOff)
            }else{
                itemView.context.getColor(R.color.colorStatusDescription)
            }
        )

        statusIsAttached.visibility = View.VISIBLE
        statusIsAttached.setImageResource(
            if (item.goalId == NO_ID && item.stepId == NO_ID && item.keyId == NO_ID) {
                R.drawable.ic_unlink
            }else{
                R.drawable.ic_link
            }
        )
        statusAttention.visibility = View.VISIBLE
        statusAttention.setColorFilter(R.color.colorStatusOff)
        statusStarted.visibility = View.VISIBLE
        statusStarted.setColorFilter(R.color.colorStatusOff)
        statusDone.visibility = View.VISIBLE
        statusDone.setColorFilter(R.color.colorStatusOff)

        if (item.isStarted) statusStarted.setColorFilter(itemView.context.getColor(R.color.colorStatusInfo))
        if (item.isDone) statusDone.setColorFilter(itemView.context.getColor(R.color.colorStatusInfo))
        if (LocalDateTime.now() >= item.dateClose && (!item.isDone)) {
            statusAttention.setColorFilter(itemView.context.getColor(R.color.colorStatusAttention))
        }
    }
}