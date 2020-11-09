package com.boltic28.taskmanager.ui.adapter.controllers

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.entities.BaseItem
import java.time.format.DateTimeFormatter

abstract class BaseItemController: HolderController() {

    fun fillBaseField(itemView: View, item: BaseItem){
        val header: TextView = itemView.findViewById(R.id.item_header)
        val dateStart: TextView = itemView.findViewById(R.id.item_start)
        val description: TextView = itemView.findViewById(R.id.item_description)
        val icon: ImageView = itemView.findViewById(R.id.item_image)

        header.text = item.name
        description.text = item.description
        dateStart.text =
            item.date.format(DateTimeFormatter.ofPattern(itemView.resources.getString(R.string.dateFormatterForItems)))
        icon.setImageResource(R.drawable.goal_ph)// make with glide and placeholder
    }

    fun setOnItemClickListener(itemView: View, item: BaseItem){
        itemView.setOnClickListener {
            listener.onViewClick(item)
        }
    }
}