package com.boltic28.taskmanager.ui.adapter.controllers

import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.entities.Idea
import com.boltic28.taskmanager.ui.adapter.DefaultViewHolder
import kotlin.reflect.KClass

class IdeaViewController: HolderController() {
    override fun getType(): Int = R.layout.item_idea

    override fun getItemType(): KClass<*> = Idea::class

    override fun bind(holder: DefaultViewHolder, item: Any) {
        TODO("Not yet implemented")
    }

}