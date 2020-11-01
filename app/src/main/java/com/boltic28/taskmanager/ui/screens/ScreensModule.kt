package com.boltic28.taskmanager.ui.screens

import com.boltic28.taskmanager.ui.base.BaseEntityFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ScreensModule {

    @ContributesAndroidInjector(modules = [ActivityModule::class])
    abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [EntityFragmentsModule::class])
    abstract fun entityFragment(): BaseEntityFragment

    @ContributesAndroidInjector(modules = [FragmentsModule::class])
    abstract fun commonFragment(): BaseEntityFragment
}
