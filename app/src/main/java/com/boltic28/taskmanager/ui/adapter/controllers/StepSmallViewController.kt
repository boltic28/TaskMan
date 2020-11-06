package com.boltic28.taskmanager.ui.adapter.controllers

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.Progress
import com.boltic28.taskmanager.datalayer.entities.Step
import com.boltic28.taskmanager.ui.adapter.DefaultViewHolder
import com.boltic28.taskmanager.ui.constant.NO_ID
import kotlin.reflect.KClass

class StepSmallViewController : HolderController() {

    override fun getType(): Int = R.layout.item_small_step

    override fun getItemType(): KClass<*> = Step::class

    override fun bind(holder: DefaultViewHolder, item: Any) {
        item as Step

        val itemView: View = holder.itemView

        val name: TextView = itemView.findViewById(R.id.small_step_name)
        val icon: ImageView = itemView.findViewById(R.id.small_step_image)
        val button: ImageButton = itemView.findViewById(R.id.small_step_button_action)
        val status: ImageView = itemView.findViewById(R.id.small_step_image_status)

        if (item.isStarted) status.setImageResource(R.drawable.ic_started)
        if (item.progress == Progress.DONE) status.setImageResource(R.drawable.ic_done)
        name.text = fetchName(item.name)
        icon.setImageResource(R.drawable.step_ph)

        if (listener.isNeedToShowConnection()) {
            if (item.goalId == NO_ID && item.keyId == NO_ID) {
                button.setImageResource(R.drawable.ic_link)
            } else {
                button.setImageResource(R.drawable.ic_unlink)
            }
        }else{
            button.setImageResource(R.drawable.ic_attach)
        }

        itemView.setOnClickListener {
            listener.onViewClick(item)
        }

        button.setOnClickListener {
            listener.onActionButtonClick(item)
        }
    }
}