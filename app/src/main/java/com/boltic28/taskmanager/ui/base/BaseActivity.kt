package com.boltic28.taskmanager.ui.base

import android.os.Bundle
import androidx.lifecycle.ViewModel
import dagger.android.DaggerActivity
import javax.inject.Inject

abstract class BaseActivity<VM : ViewModel>(private val layout: Int)
    : DaggerActivity() {

    @Inject
    lateinit var model: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout)
    }
}
