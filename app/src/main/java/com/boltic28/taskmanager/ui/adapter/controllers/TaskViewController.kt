package com.boltic28.taskmanager.ui.adapter.controllers

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.entities.Idea
import com.boltic28.taskmanager.datalayer.entities.Task
import com.boltic28.taskmanager.ui.adapter.DefaultViewHolder
import java.time.format.DateTimeFormatter
import kotlin.reflect.KClass

class TaskViewController: HolderController() {
    override fun getType(): Int = R.layout.item_task

    override fun getItemType(): KClass<*> = Task::class

    override fun bind(holder: DefaultViewHolder, item: Any) {
        item as Task
        val itemView: View = holder.itemView

        val header: TextView = itemView.findViewById(R.id.item_task_header)
        val description: TextView = itemView.findViewById(R.id.item_task_description)
        val dateStart: TextView = itemView.findViewById(R.id.item_task_start)
        val icon: ImageView = itemView.findViewById(R.id.item_task_image)

        header.text = item.name
        description.text = item.description
        dateStart.text = item.date.format(DateTimeFormatter.ofPattern(itemView.resources.getString(R.string.dateFormatterForItems)))
        icon.setImageResource(R.drawable.task_ph)

        // describe buttons to other entity
        // TODO
        itemView.findViewById<Button>(R.id.task_button_start).setOnClickListener {  }
        itemView.findViewById<Button>(R.id.task_button_done).setOnClickListener {  }
    }
}