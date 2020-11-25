package com.boltic28.taskmanager.ui.di

import android.app.Activity
import com.boltic28.taskmanager.datalayer.firebaseworker.FireBaseDatabase
import com.boltic28.taskmanager.datalayer.firebaseworker.RemoteDB
import com.boltic28.taskmanager.signtools.FireUserManager
import com.boltic28.taskmanager.signtools.UserManager
import com.boltic28.taskmanager.ui.base.BaseActivity
import com.boltic28.taskmanager.utils.Messenger
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule

@Module(includes = [AndroidInjectionModule::class])
open class ActivityModule(private val activity: BaseActivity<*>) {

    @Provides
    fun provideActivity(): Activity = activity

    @ActivityScope
    @Provides
    fun provideFirebaseManager(messenger: Messenger): UserManager =
        FireUserManager(activity, messenger)
}