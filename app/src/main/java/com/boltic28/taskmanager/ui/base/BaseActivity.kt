package com.boltic28.taskmanager.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.di.App
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject

abstract class BaseActivity<VM : ViewModel>(private val layout: Int)
    : AppCompatActivity() {

    private var fragmentInjector: DispatchingAndroidInjector<Fragment>? = null

    @Inject
    lateinit var vm: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).tryInjectActivity(this)
        super.onCreate(savedInstanceState)
        setContentView(layout)
    }

    @Inject
    fun injectDependencies(fragmentInjector: DispatchingAndroidInjector<Fragment>) {
        this.fragmentInjector = fragmentInjector
    }

}
