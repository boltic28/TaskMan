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

class IdeaViewController : HolderController() {
    override fun getType(): Int = R.layout.item_idea

    override fun getItemType(): KClass<*> = Idea::class

    override fun bind(holder: DefaultViewHolder, item: Any) {
        item as Idea
        val itemView: View = holder.itemView

        val header: TextView = itemView.findViewById(R.id.item_idea_header)
        val description: TextView = itemView.findViewById(R.id.item_idea_description)
        val dateStart: TextView = itemView.findViewById(R.id.item_idea_start)
        val icon: ImageView = itemView.findViewById(R.id.item_idea_image)
        val action: Button = itemView.findViewById(R.id.item_idea_button_action)

        header.text = fetchName(item.name)
        description.text = fetchDescription(item.description)
        dateStart.text =
            item.date.format(DateTimeFormatter.ofPattern(itemView.resources.getString(R.string.dateFormatterForItems)))
        icon.setImageResource(R.drawable.idea_ph)

        val statusIsAttached: ImageView = itemView.findViewById(R.id.item_idea_status_linked)

        statusIsAttached.setImageResource(
            if (item.goalId == 0L && item.stepId == 0L && item.keyId == 0L)
                R.drawable.ic_unlink
            else
                R.drawable.ic_link
        )

        itemView.setOnClickListener {
            listener.onViewClick(item)
        }

        action.setOnClickListener {
            listener.onActionButtonClick(item)
        }
    }

}