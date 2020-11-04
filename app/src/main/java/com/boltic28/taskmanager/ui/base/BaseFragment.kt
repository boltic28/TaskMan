package com.boltic28.taskmanager.ui.base

import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel>(layout: Int) : DaggerFragment(layout) {

    @Inject
    lateinit var model: VM
}
