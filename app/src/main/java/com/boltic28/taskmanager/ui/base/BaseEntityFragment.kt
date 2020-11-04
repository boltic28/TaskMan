package com.boltic28.taskmanager.ui.base

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.ui.screens.ActivityHelper
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

abstract class BaseEntityFragment<VM : BaseEntityFragmentModel<*>,>(layout: Int, private val keyForItem: String): BaseFragment<VM>(layout) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initItemIfExist()
    }

    override fun onStop() {
        super.onStop()
        model.disposables.forEach { it.dispose() }
    }

    private fun initItemIfExist(){
        val itemId: Long? = arguments?.getLong(keyForItem)

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

    fun fetchName(name: String): String =
        if (name.length > 13) {
            name.substring(0..10) + ".."
        } else {
            name
        }

    fun fetchStatus(isStarted: Boolean, isDone: Boolean): String = if (isStarted) {
        if (isDone) {
            resources.getString(R.string.status_done)
        } else {
            resources.getString(R.string.status_in_progress)
        }
    } else {
        resources.getString(R.string.status_not_started)
    }

    fun fetchDate(date: LocalDateTime): String = date.format(
        DateTimeFormatter
            .ofPattern(resources.getString(R.string.dateFormatterForItems))
    )

    fun fetchVisibility(isDone: Boolean): Int =
        if (isDone) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }

}