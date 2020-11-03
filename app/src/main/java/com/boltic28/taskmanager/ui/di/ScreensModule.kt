package com.boltic28.taskmanager.ui.di

import com.boltic28.taskmanager.di.AppScope
import com.boltic28.taskmanager.ui.screens.MainActivity
import com.boltic28.taskmanager.ui.screens.MainActivityModel
import com.boltic28.taskmanager.ui.screens.MainActivityModule
import com.boltic28.taskmanager.ui.screens.mainfragment.MainFragment
import com.boltic28.taskmanager.ui.screens.mainfragment.MainFragmentModule
import com.boltic28.taskmanager.ui.screens.mainfragment.MainFragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ScreensModule {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun activity(): MainActivity

    @MainFragmentScope
    @ContributesAndroidInjector(modules = [MainFragmentModule::class])
    abstract fun mainFragment(): MainFragment

//     write providers for each screen

//    @ContributesAndroidInjector(modules = [InteractModule::class])
//    abstract fun screensModule(): Fragment
}
