package com.boltic28.taskmanager.ui.adapter.controllers

import android.view.LayoutInflater
import android.view.ViewGroup
import com.boltic28.taskmanager.ui.adapter.DefaultViewHolder
import kotlin.reflect.KClass

abstract class HolderController{

    abstract fun getType(): Int

    abstract fun getItemType(): KClass<*>

    abstract fun bind(holder: DefaultViewHolder, item: Any)

    fun getHolder(parent: ViewGroup): DefaultViewHolder =
        DefaultViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    getType(),
                    parent,
                    false
                ),
            getType()
        )

    fun fitTo(holder: DefaultViewHolder): Boolean =
        holder.type == getType()


}