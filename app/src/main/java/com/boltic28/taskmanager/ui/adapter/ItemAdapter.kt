package com.boltic28.taskmanager.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.boltic28.taskmanager.datalayer.entities.BaseItem
import com.boltic28.taskmanager.ui.adapter.controllers.HolderController
import java.util.*

class ItemAdapter(
    private val controllers: List<HolderController>,
    private var itemListener: HolderController.OnActionClickListener
) :
    RecyclerView.Adapter<DefaultViewHolder>(), ElementManager, Filter {

    override var listForFilter: List<BaseItem> = emptyList()
    private var items: List<BaseItem> = emptyList()

    override fun updateListForFilter(){
        listForFilter = items
    }

    override fun filter(str: String){
        refreshData(listForFilter.filter { it.name.toLowerCase(Locale.ROOT).contains(str) })
    }

    override fun clearFilter(){
        refreshData(listForFilter)
    }

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
        val sameItem = newList.firstOrNull { it.uid == item.uid }
        if (sameItem != null) {
            newList.remove(sameItem)
        }
        newList.add(item)
        refreshData(newList)
        updateListForFilter()
    }

    override fun addList(list: List<BaseItem>) {
        val newList = mutableListOf<BaseItem>()
        newList.addAll(items)
        newList.addAll(list)
        refreshData(newList)
        updateListForFilter()
    }

    override fun loadNewData(list: List<BaseItem>) {
        refreshData(list)
        updateListForFilter()
    }

    override fun getItems(): List<BaseItem> = items

    override fun clearAll() {
        refreshData(listOf())
        updateListForFilter()
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
            controller.fitTo(holder)
        }.apply {
            listener = itemListener
            bind(holder, items[position])
        }
    }

    fun setAdapterListener(listener: HolderController.OnActionClickListener) {
        itemListener = listener
    }
}