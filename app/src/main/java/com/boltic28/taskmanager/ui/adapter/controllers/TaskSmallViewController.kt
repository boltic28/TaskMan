package com.boltic28.taskmanager.ui.adapter.controllers

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.Progress
import com.boltic28.taskmanager.datalayer.entities.Task
import com.boltic28.taskmanager.ui.adapter.DefaultViewHolder
import kotlin.reflect.KClass

class TaskSmallViewController : HolderController() {

    override fun getType(): Int = R.layout.item_small_task

    override fun getItemType(): KClass<*> = Task::class

    override fun bind(holder: DefaultViewHolder, item: Any) {
        item as Task

        val itemView: View = holder.itemView

        val name: TextView = itemView.findViewById(R.id.small_task_name)
        val icon: ImageView = itemView.findViewById(R.id.small_task_image)
        val button: ImageButton = itemView.findViewById(R.id.small_task_button_action)
        val status: ImageView = itemView.findViewById(R.id.small_task_image_status)

        if (item.isStarted) status.setImageResource(R.drawable.ic_started)
        if (item.isDone) status.setImageResource(R.drawable.ic_done)
        name.text = item.name
        icon.setImageResource(R.drawable.task_ph)

        if (item.goalId == 0L && item.keyId == 0L && item.stepId == 0L) {
            button.setImageResource(R.drawable.ic_link)
        } else {
            button.setImageResource(R.drawable.ic_unlink)
        }

        itemView.setOnClickListener {
            listener.onViewClick(item)
        }

        button.setOnClickListener {
            listener.onActionButtonClick(item)
        }
    }
}