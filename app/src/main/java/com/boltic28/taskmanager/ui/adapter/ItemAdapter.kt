package com.boltic28.taskmanager.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.boltic28.taskmanager.datalayer.entities.BaseItem
import com.boltic28.taskmanager.ui.adapter.controllers.HolderController
import java.time.LocalDateTime

class ItemAdapter(
    private val controllers: List<HolderController>,
    private var itemListener: HolderController.OnActionClickListener
) :
    RecyclerView.Adapter<DefaultViewHolder>(), ElementManager, Filter {

    private var items: List<BaseItem> = emptyList()
    private val filter = FilterImpl(this)

    override fun clearFilter() {
        filter.clearFilter()
    }

    override fun filterData(
        stringFilter: String,
        isDoneFilterOn: Boolean,
        isStartedFilterOn: Boolean,
        isFailedFilterOn: Boolean,
        isNotStartedFilterOn: Boolean,
        lastDateFilter: LocalDateTime
    ) {
        filter.start(
            stringFilter,
            isDoneFilterOn,
            isStartedFilterOn,
            isFailedFilterOn,
            isNotStartedFilterOn,
            lastDateFilter
        )
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
        filter.updateData()
    }

    override fun addList(list: List<BaseItem>) {
        val newList = mutableListOf<BaseItem>()
        newList.addAll(items)
        newList.addAll(list)
        refreshData(newList)
        filter.updateData()
    }

    override fun loadNewData(list: List<BaseItem>) {
        refreshData(list)
        filter.updateData()
    }

    override fun getItems(): List<BaseItem> = items

    override fun clearAll() {
        refreshData(listOf())
        filter.updateData()
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