package com.boltic28.taskmanager.ui.di

import android.app.Activity
import com.boltic28.taskmanager.di.App
import com.boltic28.taskmanager.ui.base.BaseViewModel
import com.boltic28.taskmanager.ui.screens.MainActivity
import dagger.Subcomponent
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector

@ActivityScope
@Subcomponent(
    modules = [AndroidInjectionModule::class, ActivityModule::class]
)
interface ActivityComponent{

    val activityInjector: DispatchingAndroidInjector<Activity>

    fun inject(app: App)
}