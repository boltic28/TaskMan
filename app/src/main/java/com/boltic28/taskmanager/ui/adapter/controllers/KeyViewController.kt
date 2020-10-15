package com.boltic28.taskmanager.ui.adapter.controllers

import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.entities.KeyResult
import com.boltic28.taskmanager.ui.adapter.DefaultViewHolder
import kotlin.reflect.KClass

class KeyViewController: HolderController() {
    override fun getType(): Int = R.layout.item_key

    override fun getItemType(): KClass<*> = KeyResult::class

    override fun bind(holder: DefaultViewHolder, item: Any) {
        TODO("Not yet implemented")
    }
}