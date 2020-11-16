package com.boltic28.taskmanager.ui.adapter.controllers

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.entities.BaseItem
import com.boltic28.taskmanager.ui.setPlaceHolderForItem
import com.bumptech.glide.Glide

abstract class BaseSmallItemController : HolderController() {

    fun fillBaseFiled(itemView: View, item: BaseItem) {
        val name: TextView = itemView.findViewById(R.id.small_item_name)
        val icon: ImageView = itemView.findViewById(R.id.small_item_image)

        name.text = item.name
        Glide.with(itemView)
            .load(setPlaceHolderForItem(item.icon))
            .centerCrop()
            .placeholder(R.drawable.undf_ph)
            .error(R.drawable.undf_ph)
            .fallback(R.drawable.undf_ph)
            .into(icon)
    }

    fun setOnItemClick(itemView: View, item: BaseItem) {
        itemView.setOnClickListener {
            listener.onViewClick(item)
        }
    }

    fun setStatus(itemView: View, isStarted: Boolean, isDone: Boolean){
        val status: ImageView = itemView.findViewById(R.id.small_item_image_status)
        if (isStarted) status.setImageResource(R.drawable.ic_started)
        if (isDone) status.setImageResource(R.drawable.ic_done)
    }

    fun setActionButton(
        itemView: View,
        item: BaseItem,
        isAttached: Boolean,
        isNeedToShowConnection: Boolean
    ) {
        val button: ImageButton = itemView.findViewById(R.id.small_item_button_action)
        if (isNeedToShowConnection) {
            if (isAttached) {
                button.setImageResource(R.drawable.ic_unlink)
            } else {
                button.setImageResource(R.drawable.ic_link)
            }
        }else{
            button.setImageResource(R.drawable.ic_attach)
        }
        button.setOnClickListener {
            listener.onActionButtonClick(item)
        }
    }
}