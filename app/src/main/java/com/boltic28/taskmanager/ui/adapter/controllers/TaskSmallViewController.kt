package com.boltic28.taskmanager.ui.adapter.controllers

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.entities.Task
import com.boltic28.taskmanager.ui.adapter.DefaultViewHolder
import java.time.format.DateTimeFormatter
import kotlin.reflect.KClass

class TaskSmallViewController : HolderController() {

    override fun getType(): Int = R.layout.item_small_row

    override fun getItemType(): KClass<*> = Task::class

    override fun bind(holder: DefaultViewHolder, item: Any) {
        item as Task

        val itemView: View = holder.itemView

        val name: TextView = itemView.findViewById(R.id.small_item_name)
        val dateStart: TextView = itemView.findViewById(R.id.small_item_date)
        val icon: ImageView = itemView.findViewById(R.id.small_item_image)

        name.text = item.name
        dateStart.text = item.date.format(DateTimeFormatter.ofPattern(itemView.resources.getString(R.string.dateFormatterForItems)))
        // TODO icon inject
    }
}