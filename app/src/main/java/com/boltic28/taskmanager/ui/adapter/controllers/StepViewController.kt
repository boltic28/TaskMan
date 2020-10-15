package com.boltic28.taskmanager.ui.adapter.controllers

import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.entities.Step
import com.boltic28.taskmanager.ui.adapter.DefaultViewHolder
import kotlin.reflect.KClass

class StepViewController: HolderController() {
    override fun getType(): Int = R.layout.item_step

    override fun getItemType(): KClass<*> = Step::class

    override fun bind(holder: DefaultViewHolder, item: Any) {
        TODO("Not yet implemented")
    }
}