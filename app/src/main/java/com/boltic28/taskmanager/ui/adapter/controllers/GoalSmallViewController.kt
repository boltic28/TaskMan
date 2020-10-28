package com.boltic28.taskmanager.ui.adapter.controllers

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.entities.Goal
import com.boltic28.taskmanager.ui.adapter.DefaultViewHolder
import java.time.format.DateTimeFormatter
import kotlin.reflect.KClass

class GoalSmallViewController : HolderController() {

    override fun getType(): Int = R.layout.item_small_goal

    override fun getItemType(): KClass<*> = Goal::class

    override fun bind(holder: DefaultViewHolder, item: Any) {
        item as Goal

        val itemView: View = holder.itemView

        val name: TextView = itemView.findViewById(R.id.small_goal_name)
        val dateStart: TextView = itemView.findViewById(R.id.small_goal_date)
        val icon: ImageView = itemView.findViewById(R.id.small_goal_image)

        name.text = item.name
        dateStart.text = item.date.format(DateTimeFormatter.ofPattern(itemView.resources.getString(R.string.dateFormatterForItems)))
        // TODO icon inject
        val button: ImageButton = itemView.findViewById(R.id.small_goal_button_action)

        button.setImageResource(R.drawable.ic_attach)

        itemView.setOnClickListener { listener.onViewClick(item) }
        button.setOnClickListener { listener.onActionButtonClick(item) }
    }
}