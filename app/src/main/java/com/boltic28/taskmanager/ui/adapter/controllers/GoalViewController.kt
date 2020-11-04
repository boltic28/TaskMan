package com.boltic28.taskmanager.ui.adapter.controllers

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.Progress
import com.boltic28.taskmanager.datalayer.entities.Goal
import com.boltic28.taskmanager.ui.adapter.DefaultViewHolder
import java.time.format.DateTimeFormatter
import kotlin.reflect.KClass

class GoalViewController : HolderController() {

    override fun getType(): Int = R.layout.item_goal

    override fun getItemType(): KClass<*> = Goal::class

    override fun bind(holder: DefaultViewHolder, item: Any) {
        item as Goal
        val itemView: View = holder.itemView

        val header: TextView = itemView.findViewById(R.id.item_goal_header)
        val dateStart: TextView = itemView.findViewById(R.id.item_goal_start)
        val icon: ImageView = itemView.findViewById(R.id.item_goal_image)

        val progress20: ImageView = itemView.findViewById(R.id.item_goal_progress_20_percent)
        val progress40: ImageView = itemView.findViewById(R.id.item_goal_progress_40_percent)
        val progress60: ImageView = itemView.findViewById(R.id.item_goal_progress_60_percent)
        val progress80: ImageView = itemView.findViewById(R.id.item_goal_progress_80_percent)
        val progress100: ImageView = itemView.findViewById(R.id.item_goal_progress_100_percent)

        header.text = item.name
        dateStart.text =
            item.date.format(DateTimeFormatter.ofPattern(itemView.resources.getString(R.string.dateFormatterForItems)))
        icon.setImageResource(R.drawable.goal_ph)

        progress20.setImageResource(R.drawable.bg_progress_off)
        progress40.setImageResource(R.drawable.bg_progress_off)
        progress60.setImageResource(R.drawable.bg_progress_off)
        progress80.setImageResource(R.drawable.bg_progress_off)
        progress100.setImageResource(R.drawable.bg_progress_off)

        if (item.progress.value >= Progress.PROGRESS_20.value) progress20.setImageResource(R.drawable.bg_progress_on)
        if (item.progress.value >= Progress.PROGRESS_40.value) progress40.setImageResource(R.drawable.bg_progress_on)
        if (item.progress.value >= Progress.PROGRESS_60.value) progress60.setImageResource(R.drawable.bg_progress_on)
        if (item.progress.value >= Progress.PROGRESS_80.value) progress80.setImageResource(R.drawable.bg_progress_on)
        if (item.progress.value == Progress.DONE.value) progress100.setImageResource(R.drawable.bg_progress_on)

        itemView.setOnClickListener {
            listener.onViewClick(item)
        }

        icon.setOnClickListener {
            listener.onActionButtonClick(item)
        }
    }
}