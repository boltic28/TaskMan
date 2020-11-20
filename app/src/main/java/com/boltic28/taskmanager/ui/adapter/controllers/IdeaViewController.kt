package com.boltic28.taskmanager.ui.adapter.controllers

import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.entities.Idea
import com.boltic28.taskmanager.ui.adapter.DefaultViewHolder
import com.boltic28.taskmanager.ui.constant.NO_ID
import kotlin.reflect.KClass

class IdeaViewController : BaseItemController() {
    override fun getType(): Int = R.layout.item_idea

    override fun getItemType(): KClass<*> = Idea::class

    override fun bind(holder: DefaultViewHolder, item: Any) {
        item as Idea
        val itemView: View = holder.itemView

        fillBaseField(itemView, item)
        setOnItemClickListener(itemView, item)

        val statusIsAttached: ImageView = itemView.findViewById(R.id.item_status_linked)
        statusIsAttached.visibility = View.VISIBLE
        statusIsAttached.setImageResource(
            if (item.goalId == NO_ID && item.stepId == NO_ID && item.keyId == NO_ID)
                R.drawable.ic_unlink
            else
                R.drawable.ic_link
        )

        val action: Button = itemView.findViewById(R.id.item_button_action)
        action.visibility = View.VISIBLE
        action.text = itemView.resources.getString(R.string.convert_to)
        action.setOnClickListener {
            listener.onActionButtonClick(item)
        }
    }

}