package com.boltic28.taskmanager.ui.adapter.controllers

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.Progress
import com.boltic28.taskmanager.datalayer.entities.KeyResult
import com.boltic28.taskmanager.datalayer.entities.ParentItem
import com.boltic28.taskmanager.datalayer.entities.Step
import com.boltic28.taskmanager.ui.constant.NO_ID
import java.time.LocalDateTime

abstract class ParentItemController: BaseItemController() {

    fun fillProgress(itemView: View, item: ParentItem){

        val progressContainer: LinearLayout = itemView.findViewById(R.id.item_progress_container)
        progressContainer.visibility = View.VISIBLE

        val progress20: ImageView = itemView.findViewById(R.id.item_progress_20_percent)
        val progress40: ImageView = itemView.findViewById(R.id.item_progress_40_percent)
        val progress60: ImageView = itemView.findViewById(R.id.item_progress_60_percent)
        val progress80: ImageView = itemView.findViewById(R.id.item_progress_80_percent)
        val progress100: ImageView = itemView.findViewById(R.id.item_progress_100_percent)

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
    }

    fun setStatusContainer(itemView: View, item: ParentItem){
        val statusAttention: ImageView = itemView.findViewById(R.id.item_status_attention)
        val statusStarted: ImageView = itemView.findViewById(R.id.item_status_started)
        val statusDone: ImageView = itemView.findViewById(R.id.item_status_done)

        statusAttention.visibility = View.VISIBLE
        statusStarted.visibility = View.VISIBLE
        statusDone.visibility = View.VISIBLE

        statusAttention.setColorFilter(R.color.colorStatusOff)
        statusStarted.setColorFilter(R.color.colorStatusOff)
        statusDone.setColorFilter(R.color.colorStatusOff)

        if (item.isStarted) statusStarted.setColorFilter(itemView.context.getColor(R.color.colorStatusInfo))
        if (item.isDone) statusDone.setColorFilter(itemView.context.getColor(R.color.colorStatusInfo))
        if (LocalDateTime.now() >= item.dateClose && (!item.isDone)) statusAttention.setColorFilter(
            itemView.context.getColor(R.color.colorStatusAttention)
        )

        val isDone: ImageView = itemView.findViewById(R.id.item_status_icon)
        isDone.visibility = if (item.isDone) View.VISIBLE else View.INVISIBLE

        if (item is Step) fillIsAttachedStatus(itemView, item.goalId)
        if (item is KeyResult) fillIsAttachedStatus(itemView, item.goalId)
    }

    private fun fillIsAttachedStatus(itemView: View, parentId: Long){
        val statusIsAttached: ImageView = itemView.findViewById(R.id.item_status_linked)
        statusIsAttached.visibility = View.VISIBLE
        statusIsAttached.setImageResource(
            if (parentId == NO_ID) R.drawable.ic_unlink else R.drawable.ic_link
        )
    }
}