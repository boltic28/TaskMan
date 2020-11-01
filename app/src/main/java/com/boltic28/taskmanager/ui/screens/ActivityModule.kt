package com.boltic28.taskmanager.ui.screens

import android.app.Activity
import com.boltic28.taskmanager.ui.base.BaseActivity
import dagger.Module
import dagger.Provides
import dagger.android.support.AndroidSupportInjectionModule

@Module(includes = [(AndroidSupportInjectionModule::class)])
open class ActivityModule(private val activity: BaseActivity<*>) {

    @Provides
    fun provideActivity(): Activity = activity
}