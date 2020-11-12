package com.boltic28.taskmanager.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.boltic28.taskmanager.datalayer.entities.BaseItem
import com.boltic28.taskmanager.ui.adapter.controllers.HolderController

class ItemAdapter(
    private val controllers: List<HolderController>,
    private var itemListener: HolderController.OnActionClickListener
) :
    RecyclerView.Adapter<DefaultViewHolder>(), ElementManager {

    private var items: List<BaseItem> = emptyList()

    override fun refreshData(list: List<BaseItem>) {
        val diffUtil =
            ItemDiffUtil(list, items)
        val result = DiffUtil.calculateDiff(diffUtil)
        items = list
        result.dispatchUpdatesTo(this)
    }

    override fun addElement(item: BaseItem) {
        val newList = mutableListOf<BaseItem>()
        newList.addAll(items)
        newList.add(item)
        refreshData(newList)
    }

    override fun addList(list: List<BaseItem>) {
        val newList = mutableListOf<BaseItem>()
        newList.addAll(items)
        newList.addAll(list)
        refreshData(newList)
    }

    override fun clearAll() {
        refreshData(listOf())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultViewHolder =
        controllers.first { it.getType() == viewType }.getHolder(parent)

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int =
        controllers.first { controller ->
            controller.getItemType() == items[position]::class
        }.getType()

    override fun onBindViewHolder(holder: DefaultViewHolder, position: Int) {
        controllers.first { controller ->
            controller.fitTo(holder) }.apply {
            listener = itemListener
            bind(holder, items[position])
        }
    }

    fun setAdapterListener(listener: HolderController.OnActionClickListener) {
        itemListener = listener
    }
}