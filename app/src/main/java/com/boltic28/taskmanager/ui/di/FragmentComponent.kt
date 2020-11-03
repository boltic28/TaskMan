package com.boltic28.taskmanager.ui.di

import androidx.fragment.app.Fragment
import com.boltic28.taskmanager.di.App
import com.boltic28.taskmanager.ui.base.BaseViewModel
import dagger.Subcomponent
import dagger.android.AndroidInjectionModule
import dagger.android.DispatchingAndroidInjector

@Subcomponent(
    modules = [ AndroidInjectionModule::class, FragmentModule::class]
)
interface FragmentComponent {

    val fragmentInjector: DispatchingAndroidInjector<Fragment>

    fun inject(app: App)

    fun inject(model: BaseViewModel)
}