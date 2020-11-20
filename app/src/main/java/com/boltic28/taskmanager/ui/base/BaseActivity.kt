package com.boltic28.taskmanager.ui.base

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.di.App
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

abstract class BaseActivity<VM : ViewModel>(private val layout: Int)
    : FragmentActivity(), HasAndroidInjector {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var model: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).tryInjectActivity(this)
        super.onCreate(savedInstanceState)
        setContentView(layout)

        setActionBar(toolbar)
    }

    override fun androidInjector(): AndroidInjector<Any> = fragmentInjector
}
