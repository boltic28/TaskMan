package com.boltic28.taskmanager.ui.adapter.controllers

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.entities.KeyResult
import com.boltic28.taskmanager.datalayer.entities.Task
import com.boltic28.taskmanager.ui.adapter.DefaultViewHolder
import java.time.format.DateTimeFormatter
import kotlin.reflect.KClass

class KeyViewController: HolderController() {
    override fun getType(): Int = R.layout.item_key

    override fun getItemType(): KClass<*> = KeyResult::class

    override fun bind(holder: DefaultViewHolder, item: Any) {
        item as KeyResult
        val itemView: View = holder.itemView

        val header: TextView = itemView.findViewById(R.id.item_key_header)
        val description: TextView = itemView.findViewById(R.id.item_key_description)
        val dateStart: TextView = itemView.findViewById(R.id.item_key_start)
        val icon: ImageView = itemView.findViewById(R.id.item_key_image)

        header.text = item.name
        description.text = item.description
        dateStart.text = item.date.format(DateTimeFormatter.ofPattern(itemView.resources.getString(R.string.dateFormatterForItems)))


        // describe buttons to other entity
        // TODO
        itemView.findViewById<Button>(R.id.key_button_start).setOnClickListener {  }
        itemView.findViewById<Button>(R.id.key_button_done).setOnClickListener {  }
    }
}