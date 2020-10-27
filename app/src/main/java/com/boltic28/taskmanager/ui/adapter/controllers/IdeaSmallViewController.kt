package com.boltic28.taskmanager.ui.adapter.controllers

import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.entities.Idea
import com.boltic28.taskmanager.ui.adapter.DefaultViewHolder
import com.boltic28.taskmanager.ui.screens.MainActivity
import java.time.format.DateTimeFormatter
import kotlin.reflect.KClass

class IdeaSmallViewController : HolderController() {

    override fun getType(): Int = R.layout.item_small_idea

    override fun getItemType(): KClass<*> = Idea::class

    override fun bind(holder: DefaultViewHolder, item: Any) {
        item as Idea

        val itemView: View = holder.itemView

        val name: TextView = itemView.findViewById(R.id.small_idea_name)
        val dateStart: TextView = itemView.findViewById(R.id.small_idea_date)
        val icon: ImageView = itemView.findViewById(R.id.small_idea_image)
        val button: ImageButton = itemView.findViewById(R.id.small_idea_button_action)

        name.text = item.name
        dateStart.text = item.date.format(DateTimeFormatter.ofPattern(itemView.resources.getString(R.string.dateFormatterForItems)))
        // TODO icon inject

        if (item.goalId == 0L){
            button.setImageDrawable(itemView.resources.getDrawable(R.drawable.ic_add_item))
        }else{
            button.setImageDrawable(itemView.resources.getDrawable(R.drawable.ic_remove_item))
        }

        itemView.setOnClickListener {
            listener.onViewClick(item)
            // go to item page
            Log.d(MainActivity.TAG, "Item smallIdea clicked")
        }

        button.setOnClickListener {
            listener.onActionButtonClick(item)

            Log.d(MainActivity.TAG, "Plus smallIdea clicked")
        }
    }


}