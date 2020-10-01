package com.boltic28.taskmanager.ui.adapter

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.boltic28.taskmanager.ui.screens.MainActivity
import com.boltic28.taskmanager.ui.adapter.controllers.HolderController

class ItemAdapter(private val controllers: List<HolderController>) : RecyclerView.Adapter<DefaultViewHolder>() {

    private var items: List<Any> = emptyList()

    fun refreshData(list: List<Any>) {
        val diffUtil =
            ItemDiffUtil(list, items)
        val result = DiffUtil.calculateDiff(diffUtil)
        items = list
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultViewHolder =
        controllers.first { it.getType() == viewType }.getHolder(parent)

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int =
        controllers.first { controller ->
            controller.getItemType() == items[position]::class
        }.getType()

    override fun onBindViewHolder(holder: DefaultViewHolder, position: Int) {

        Log.d(
            MainActivity.TAG, "load instance into holder:\n " +
                    "type of the holder - ${holder.type}" +
                    "\n type of the instance - ${items[position].javaClass.canonicalName}"
        )

        controllers.first { controller ->
            controller.fitTo(holder)
        }
            .bind(holder, items[position])
    }


}