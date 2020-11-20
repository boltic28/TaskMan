package com.boltic28.taskmanager.ui.adapter.controllers

import android.view.LayoutInflater
import android.view.ViewGroup
import com.boltic28.taskmanager.ui.adapter.DefaultViewHolder
import kotlin.reflect.KClass

abstract class HolderController {

    private var mListener: OnActionClickListener = object : OnActionClickListener{
        override fun isNeedToShowConnection(): Boolean = true
        override fun onActionButtonClick(item: Any) {}
        override fun onViewClick(item: Any) {}
    }
        var listener: OnActionClickListener
        get() = mListener
        set(value) {mListener = value}

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

    interface OnActionClickListener {
        fun isNeedToShowConnection(): Boolean
        fun onActionButtonClick(item: Any)
        fun onViewClick(item: Any)
    }

    fun provideCommonData(){

    }
}