package com.boltic28.taskmanager.ui.di

import android.app.Activity
import com.boltic28.taskmanager.di.AppScope
import com.boltic28.taskmanager.signtools.FireUserManager
import com.boltic28.taskmanager.signtools.UserManager
import com.boltic28.taskmanager.ui.base.BaseActivity
import com.boltic28.taskmanager.ui.screens.MainActivity
import com.boltic28.taskmanager.ui.screens.goalfragment.GoalFragmentModule
import com.boltic28.taskmanager.ui.screens.ideafragment.IdeaFragmentModule
import com.boltic28.taskmanager.ui.screens.keyfragment.KeyFragmentModule
import com.boltic28.taskmanager.ui.screens.stepfragment.StepFragmentModule
import com.boltic28.taskmanager.ui.screens.taskfragment.TaskFragmentModule
import com.boltic28.taskmanager.utils.Messenger
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule

@Module(includes = [AndroidInjectionModule::class])
open class ActivityModule(private val activity: BaseActivity<*>) {

    @Provides
    fun provideActivity(): Activity = activity

    @Provides
    fun provideFirebaseManager(messenger: Messenger): UserManager =
        FireUserManager(activity, messenger)
}