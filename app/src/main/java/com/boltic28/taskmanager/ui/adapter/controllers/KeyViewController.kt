package com.boltic28.taskmanager.ui.adapter.controllers

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.Progress
import com.boltic28.taskmanager.datalayer.entities.KeyResult
import com.boltic28.taskmanager.ui.adapter.DefaultViewHolder
import java.time.format.DateTimeFormatter
import kotlin.reflect.KClass

class KeyViewController : HolderController() {
    override fun getType(): Int = R.layout.item_key

    override fun getItemType(): KClass<*> = KeyResult::class

    override fun bind(holder: DefaultViewHolder, item: Any) {
        item as KeyResult
        val itemView: View = holder.itemView

        val header: TextView = itemView.findViewById(R.id.item_key_header)
        val description: TextView = itemView.findViewById(R.id.item_key_description)
        val dateStart: TextView = itemView.findViewById(R.id.item_key_start)
        val icon: ImageView = itemView.findViewById(R.id.item_key_image)
        val isDone: ImageView = itemView.findViewById(R.id.item_key_status_icon)

        header.text = fetchName(item.name)
        description.text = fetchDescription(item.description)
        dateStart.text =
            item.date.format(DateTimeFormatter.ofPattern(itemView.resources.getString(R.string.dateFormatterForItems)))
        icon.setImageResource(R.drawable.key_ph)
        isDone.visibility = if (item.isDone) View.VISIBLE else View.INVISIBLE

        val statusIsAttached: ImageView = itemView.findViewById(R.id.item_key_status_linked)
        val statusAttention: ImageView = itemView.findViewById(R.id.item_key_status_attention)
        val statusStarted: ImageView = itemView.findViewById(R.id.item_key_status_started)
        val statusDone: ImageView = itemView.findViewById(R.id.item_key_status_done)

        statusIsAttached.setImageResource(
            if (item.goalId == 0L) R.drawable.ic_unlink else R.drawable.ic_link
        )
        statusAttention.setColorFilter(R.color.colorStatusOff)
        statusStarted.setColorFilter(R.color.colorStatusOff)
        statusDone.setColorFilter(R.color.colorStatusOff)

        if (item.isDone) statusDone.setColorFilter(itemView.context.getColor(R.color.colorStatusInfo))

        val progress20: ImageView = itemView.findViewById(R.id.item_key_progress_20_percent)
        val progress40: ImageView = itemView.findViewById(R.id.item_key_progress_40_percent)
        val progress60: ImageView = itemView.findViewById(R.id.item_key_progress_60_percent)
        val progress80: ImageView = itemView.findViewById(R.id.item_key_progress_80_percent)
        val progress100: ImageView = itemView.findViewById(R.id.item_key_progress_100_percent)

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