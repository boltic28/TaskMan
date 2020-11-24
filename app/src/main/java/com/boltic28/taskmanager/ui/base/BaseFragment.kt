package com.boltic28.taskmanager.ui.base

import com.boltic28.taskmanager.ui.screens.activity.ActivityHelper
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel>(layout: Int) : DaggerFragment(layout) {

    @Inject
    lateinit var model: VM

    fun hideKeyboard(){
        (activity as? ActivityHelper)?.hideKeyBoard()
    }

    fun setToolbarText(header: String){
        (activity as? ActivityHelper)?.setToolbarText(header)
    }

    fun showProgressBar(){
        (activity as? ActivityHelper)?.showProgressBar()
    }

    fun hideProgressBar(){
        (activity as? ActivityHelper)?.hideProgressBar()
    }
}
