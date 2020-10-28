package com.boltic28.taskmanager.ui.adapter.controllers

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.entities.Step
import com.boltic28.taskmanager.datalayer.entities.Task
import com.boltic28.taskmanager.ui.adapter.DefaultViewHolder
import java.time.format.DateTimeFormatter
import kotlin.reflect.KClass

class StepViewController: HolderController() {
    override fun getType(): Int = R.layout.item_step

    override fun getItemType(): KClass<*> = Step::class

    override fun bind(holder: DefaultViewHolder, item: Any) {
        item as Step
        val itemView: View = holder.itemView

        val header: TextView = itemView.findViewById(R.id.item_step_header)
        val description: TextView = itemView.findViewById(R.id.item_step_description)
        val dateStart: TextView = itemView.findViewById(R.id.item_step_start)
        val icon: ImageView = itemView.findViewById(R.id.item_step_image)

        header.text = item.name
        description.text = item.description
        dateStart.text = item.date.format(DateTimeFormatter.ofPattern(itemView.resources.getString(R.string.dateFormatterForItems)))


        // describe buttons to other entity
        // TODO
        itemView.findViewById<Button>(R.id.step_button_start).setOnClickListener {  }
        itemView.findViewById<Button>(R.id.step_button_done).setOnClickListener {  }
    }
}