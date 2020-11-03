package com.boltic28.taskmanager.ui.base

import android.content.Context
import androidx.fragment.app.Fragment
import com.boltic28.taskmanager.di.App
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel>(layout: Int) : Fragment(layout),
    AndroidInjector<Fragment> {

    private var fragmentInjector: DispatchingAndroidInjector<Fragment>? = null

    @Inject
    lateinit var model: VM

    override fun onAttach(context: Context) {
        (activity?.application as App).tryInjectActivity(this)
        super.onAttach(context)
    }

    override fun inject(instance: Fragment?) {
        TODO("Not yet implemented")
    }
}
