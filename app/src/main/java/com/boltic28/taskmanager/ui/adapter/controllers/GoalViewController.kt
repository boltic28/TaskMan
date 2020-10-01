package com.boltic28.taskmanager.ui.adapter.controllers

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.classes.Goal
import com.boltic28.taskmanager.ui.screens.MainActivity
import com.boltic28.taskmanager.ui.adapter.DefaultViewHolder
import kotlin.reflect.KClass

class GoalViewController : HolderController() {

    override fun getType(): Int = R.layout.item_goal

    override fun getItemType(): KClass<*> = Goal::class

    override fun bind(holder: DefaultViewHolder, item: Any) {
        item as Goal
        val itemView: View = holder.itemView

// this functional doesn't work now, set it later
        val header: TextView = itemView.findViewById(R.id.item_goal_header)
        val dateStart: TextView = itemView.findViewById(R.id.item_goal_start)
        val icon: ImageView = itemView.findViewById(R.id.item_goal_image)

        val progress20: ImageView = itemView.findViewById(R.id.item_goal_progress_20_percent)
        val progress40: ImageView = itemView.findViewById(R.id.item_goal_progress_40_percent)
        val progress60: ImageView = itemView.findViewById(R.id.item_goal_progress_60_percent)
        val progress80: ImageView = itemView.findViewById(R.id.item_goal_progress_80_percent)
        val progress100: ImageView = itemView.findViewById(R.id.item_goal_progress_100_percent)

        header.text = item.name
        dateStart.text = item.date.toString()// add formatter
        // TODO icon inject
        progress20.setBackgroundColor(itemView.context.resources.getColor(R.color.colorItemProgressOn))
        progress40.setBackgroundColor(itemView.context.resources.getColor(R.color.colorItemProgressOn))


        itemView.setOnClickListener {
            // go to item page
            Log.d(MainActivity.TAG, "Item Goal is clicked")
        }
    }
}