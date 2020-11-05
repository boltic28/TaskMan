package com.boltic28.taskmanager.ui.adapter.controllers

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.Cycle
import com.boltic28.taskmanager.datalayer.entities.Task
import com.boltic28.taskmanager.ui.adapter.DefaultViewHolder
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

class TaskViewController : HolderController() {
    override fun getType(): Int = R.layout.item_task

    override fun getItemType(): KClass<*> = Task::class

    override fun bind(holder: DefaultViewHolder, item: Any) {
        item as Task
        val itemView: View = holder.itemView

        val header: TextView = itemView.findViewById(R.id.item_task_header)
        val description: TextView = itemView.findViewById(R.id.item_task_description)
        val dateStart: TextView = itemView.findViewById(R.id.item_task_start)
        val icon: ImageView = itemView.findViewById(R.id.item_task_image)
        val action: Button = itemView.findViewById(R.id.item_task_button_action)
        val isDone: ImageView = itemView.findViewById(R.id.item_task_status_icon)

        header.text = fetchName(item.name)
        description.text = fetchDescription(item.description)
        dateStart.text =
            item.date.format(DateTimeFormatter.ofPattern(itemView.resources.getString(R.string.dateFormatterForItems)))
        icon.setImageResource(R.drawable.task_ph)
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


        val statusCycling: ImageView = itemView.findViewById(R.id.item_task_status_cycle)
        val statusIsAttached: ImageView = itemView.findViewById(R.id.item_task_status_linked)
        val statusAttention: ImageView = itemView.findViewById(R.id.item_task_status_attention)
        val statusStarted: ImageView = itemView.findViewById(R.id.item_task_status_started)
        val statusDone: ImageView = itemView.findViewById(R.id.item_task_status_done)
        val days = TimeUnit.DAYS.toDays(
            LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) - item.dateClose.toEpochSecond(
                ZoneOffset.UTC
            )
        )

        statusCycling.setColorFilter(
            if (item.cycle == Cycle.NOT_CYCLE)
                R.color.colorStatusOff
            else
                R.color.colorStatusDescription
        )
        statusIsAttached.setImageResource(
            if (item.goalId == 0L && item.stepId == 0L && item.keyId == 0L)
                R.drawable.ic_unlink
            else
                R.drawable.ic_link
        )
        statusAttention.setColorFilter(R.color.colorStatusOff)
        statusStarted.setColorFilter(R.color.colorStatusOff)
        statusDone.setColorFilter(R.color.colorStatusOff)

        if (item.isStarted) statusStarted.setColorFilter(itemView.context.getColor(R.color.colorStatusInfo))
        if (item.isDone) statusDone.setColorFilter(itemView.context.getColor(R.color.colorStatusInfo))
        if (days < 2) statusAttention.setColorFilter(itemView.context.getColor(R.color.colorStatusAttention))

        itemView.setOnClickListener {
            listener.onViewClick(item)
        }

        action.setOnClickListener {
            listener.onActionButtonClick(item)
        }
    }
}