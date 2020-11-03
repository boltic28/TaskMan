package com.boltic28.taskmanager.ui.base

import android.app.Activity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.di.App
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

abstract class BaseActivity<VM : ViewModel>(private val layout: Int)
    : Activity(), HasAndroidInjector {

    private var fragmentInjector: DispatchingAndroidInjector<Any>? = null

    @Inject
    lateinit var model: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).tryInjectActivity(this)
        super.onCreate(savedInstanceState)
        setContentView(layout)
    }

    override fun androidInjector(): AndroidInjector<Any> =
        fragmentInjector as AndroidInjector<Any>
}
