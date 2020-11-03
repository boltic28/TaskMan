package com.boltic28.taskmanager.ui.base

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.di.App
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject

abstract class BaseActivity<VM : ViewModel>(private val layout: Int)
    : AppCompatActivity(), AndroidInjector<Activity> {

    private var fragmentInjector: DispatchingAndroidInjector<Activity>? = null

    @Inject
    lateinit var model: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).tryInjectActivity(this)
        super.onCreate(savedInstanceState)
        setContentView(layout)
    }

    override fun inject(instance: Activity?) {
        TODO("Not yet implemented")
    }
}
