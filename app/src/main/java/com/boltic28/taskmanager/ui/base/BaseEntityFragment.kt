package com.boltic28.taskmanager.ui.base

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.ui.screens.activity.ActivityHelper

abstract class BaseEntityFragment<VM : BaseEntityFragmentModel<*>,>(layout: Int, private val keyForItem: String): BaseFragment<VM>(layout) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initItemIfExist()
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

    override fun onStop() {
        super.onStop()
        model.disposables.forEach { it.dispose() }
    }

}