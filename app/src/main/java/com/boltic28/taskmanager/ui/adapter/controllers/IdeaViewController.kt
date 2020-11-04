package com.boltic28.taskmanager.ui.adapter.controllers

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.entities.Idea
import com.boltic28.taskmanager.ui.adapter.DefaultViewHolder
import java.time.format.DateTimeFormatter
import kotlin.reflect.KClass

class IdeaViewController: HolderController() {
    override fun getType(): Int = R.layout.item_idea

    override fun getItemType(): KClass<*> = Idea::class

    override fun bind(holder: DefaultViewHolder, item: Any) {
        item as Idea
        val itemView: View = holder.itemView

        val header: TextView = itemView.findViewById(R.id.item_idea_header)
        val description: TextView = itemView.findViewById(R.id.item_idea_description)
        val dateStart: TextView = itemView.findViewById(R.id.item_idea_start)
        val icon: ImageView = itemView.findViewById(R.id.item_idea_image)

        header.text = item.name
        description.text = item.description
        dateStart.text = item.date.format(DateTimeFormatter.ofPattern(itemView.resources.getString(R.string.dateFormatterForItems)))
        icon.setImageResource(R.drawable.idea_ph)

        // describe buttons to other entity
        // TODO
        itemView.findViewById<Button>(R.id.idea_button_convert_to_goal).setOnClickListener {  }
        itemView.findViewById<Button>(R.id.idea_button_convert_to_task).setOnClickListener {  }
        itemView.findViewById<Button>(R.id.idea_button_convert_to_step).setOnClickListener {  }
    }

}