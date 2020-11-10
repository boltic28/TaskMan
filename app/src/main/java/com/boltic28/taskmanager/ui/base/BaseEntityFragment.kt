package com.boltic28.taskmanager.ui.base

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.datalayer.Progress
import com.boltic28.taskmanager.datalayer.entities.BaseItem
import com.boltic28.taskmanager.ui.adapter.ItemAdapter
import com.boltic28.taskmanager.ui.screens.activity.ActivityHelper
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_block_buttons.*
import kotlinx.android.synthetic.main.fragment_block_head.*
import kotlinx.android.synthetic.main.fragment_block_info.*
import kotlinx.android.synthetic.main.fragment_block_progress.*
import kotlinx.android.synthetic.main.fragment_block_recycler.*
import kotlinx.android.synthetic.main.fragment_item.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

abstract class BaseEntityFragment<VM : BaseEntityFragmentModel<*>> :
    BaseFragment<VM>(R.layout.fragment_item) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initItemIfExist()
    }

    override fun onStop() {
        super.onStop()
        model.disposables.forEach { it.dispose() }
    }

    private fun initItemIfExist() {
        val itemId: Long? = arguments?.getLong(model.extraKey)

        if (!model.userManager.isUserSigned()) {
            (activity as? ActivityHelper)?.setToolbarText(resources.getString(R.string.app_name))
            findNavController().navigate(R.id.signFragment)
        }

        if (itemId != null) {
            model.itemId = itemId
            model.refresh()
        } else {
            findNavController().navigate(R.id.mainFragment)
        }

        initView()
    }

    abstract fun initView()

    fun fillStatusData(isStarted: Boolean, isDone: Boolean, dateClose: LocalDateTime) {
        item_fr_status.text = fetchStatus(isStarted, isDone)
        item_fr_isDone_icon.visibility = fetchVisibilityIsDoneStatus(isDone)
        item_fr_close_date_content.text = fetchDate(dateClose)
    }

    private fun fetchStatus(isStarted: Boolean, isDone: Boolean): String = if (isStarted) {
        if (isDone) {
            resources.getString(R.string.status_done)
        } else {
            resources.getString(R.string.status_in_progress)
        }
    } else {
        resources.getString(R.string.status_not_started)
    }

    fun fillBaseDate(item: BaseItem) {
        (activity as? ActivityHelper)?.setToolbarText(item.name)
        item_fr_name.text = item.name
        item_fr_start_date.text = fetchDate(item.date)
        item_fr_description_content.text = item.description
        item_fr_recycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        fetchIcon(item_fr_image, item.icon)
    }

    private fun fetchDate(date: LocalDateTime): String = date.format(
        DateTimeFormatter
            .ofPattern(resources.getString(R.string.dateFormatterForItems))
    )

    private fun fetchVisibilityIsDoneStatus(isDone: Boolean): Int =
        if (isDone) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }

    private fun fetchIcon(itemView: View, icon: String) {
        Glide.with(itemView)
            .load(icon.toInt())
            .centerCrop()
            .placeholder(icon.toInt())
            .error(icon.toInt())
            .fallback(icon.toInt())
            .into(itemView.findViewById(R.id.item_fr_image))
    }

    fun attachAdapter(adapter: ItemAdapter) {
        item_fr_recycler.adapter = adapter

    }

    fun setProgress(progress: Progress) {
        item_progress_block.visibility = View.VISIBLE
        item_fr_progress.text = resources.getString(R.string.progres_00, 0)
        item_fr_progress_20.setImageResource(R.drawable.bg_progress_off)
        item_fr_progress_40.setImageResource(R.drawable.bg_progress_off)
        item_fr_progress_60.setImageResource(R.drawable.bg_progress_off)
        item_fr_progress_80.setImageResource(R.drawable.bg_progress_off)
        item_fr_progress_100.setImageResource(R.drawable.bg_progress_off)
        if (progress.value >= Progress.PROGRESS_20.value) {
            item_fr_progress_20.setImageResource(R.drawable.bg_progress_on)
            item_fr_progress.text = resources.getString(R.string.progres_00, 20)
        }
        if (progress.value >= Progress.PROGRESS_40.value) {
            item_fr_progress_40.setImageResource(R.drawable.bg_progress_on)
            item_fr_progress.text = resources.getString(R.string.progres_00, 40)
        }
        if (progress.value >= Progress.PROGRESS_60.value) {
            item_fr_progress_60.setImageResource(R.drawable.bg_progress_on)
            item_fr_progress.text = resources.getString(R.string.progres_00, 60)
        }
        if (progress.value >= Progress.PROGRESS_80.value) {
            item_fr_progress_80.setImageResource(R.drawable.bg_progress_on)
            item_fr_progress.text = resources.getString(R.string.progres_00, 80)
        }
        if (progress.value == Progress.DONE.value) {
            item_fr_progress_100.setImageResource(R.drawable.bg_progress_on)
            item_fr_progress.text = resources.getString(R.string.progres_00, 100)
        }
    }

    fun setButtonsBack() {
        item_fr_button_back.setOnClickListener {
            activity?.onBackPressed()
        }
        item_fr_button_home.setOnClickListener {
            findNavController().navigate(R.id.mainFragment)
        }
    }

    fun activateRelativeLine() {
        item_fr_relative_header.visibility = View.VISIBLE
        item_fr_relative_owner.visibility = View.VISIBLE
        item_fr_relative_button.visibility = View.VISIBLE
    }

    fun activateCycleLine() {
        item_fr_cycle_header.visibility = View.VISIBLE
        item_fr_cycle_content.visibility = View.VISIBLE
    }

    fun deactivateDataClosing() {
        item_fr_close_date_header.visibility = View.GONE
        item_fr_close_date_content.visibility = View.GONE
    }
}