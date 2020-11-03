package com.boltic28.taskmanager.ui.di

import com.boltic28.taskmanager.ui.screens.mainfragment.MainFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ScreensModule {

    @ContributesAndroidInjector(modules = [MainFragmentModule::class])
    abstract fun mainFragment(): MainFragmentModule

//    @ContributesAndroidInjector(modules = [ActivityModule::class])
//    abstract fun activity(): MainActivity
//
//    @ContributesAndroidInjector(modules = [InteractModule::class])
//    abstract fun screensModule(): Fragment
}
